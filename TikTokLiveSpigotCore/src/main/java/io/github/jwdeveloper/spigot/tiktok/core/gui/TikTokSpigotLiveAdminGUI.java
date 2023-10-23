package io.github.jwdeveloper.spigot.tiktok.core.gui;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.references.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.BorderComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.simple.SimpleGUI;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.ff.plugin.implementation.listeners.ChatInputListener;
import io.github.jwdeveloper.spigot.tiktok.api.TikTokLiveSpigotApi;
import io.github.jwdeveloper.spigot.tiktok.api.profiles.models.Profile;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotConfig;
import io.github.jwdeveloper.spigot.tiktok.core.common.TikTokLiveSpigotPermissions;
import io.github.jwdeveloper.tiktok.live.ConnectionState;
import org.bukkit.ChatColor;
import org.bukkit.Material;

@Injection
public class TikTokSpigotLiveAdminGUI extends SimpleGUI {
    private final TikTokLiveSpigotApi tokLiveSpigotApi;
    private final FluentConfigFile<TikTokLiveSpigotConfig> configFile;
    private final ChatInputListener inputListener;
    private final PlayerLogger playerLogger;

    private BorderComponent borderComponent;

    public TikTokSpigotLiveAdminGUI(TikTokLiveSpigotApi tokLiveSpigotApi,
                                    FluentConfigFile<TikTokLiveSpigotConfig> config,
                                    ChatInputListener inputListener, PlayerLogger playerLogger) {
        this.tokLiveSpigotApi = tokLiveSpigotApi;
        this.configFile = config;
        this.inputListener = inputListener;
        this.playerLogger = playerLogger;
    }

    @Override
    public void onInit(InventoryDecorator decorator, InventoryApi inventoryApi)
    {
        decorator.withPermissions(TikTokLiveSpigotPermissions.GUI.ADMIN);
        createTitle(decorator, inventoryApi);
        borderComponent = createBorder(decorator, inventoryApi);
        createProfileSelectButton(inventoryApi);
        createBoolButton(inventoryApi);
        changeDefaultLiveHost(inventoryApi);
        connectButton(decorator, inventoryApi);
    }

    private void createTitle(InventoryDecorator decorator, InventoryApi inventoryApi) {
        var titleComponent = decorator.withComponent(inventoryApi.components().title());
        titleComponent.addTitleModel("default", () -> ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "TikTokLive "+ChatColor.RESET+""+  ChatColor.DARK_GREEN +"admin");
        titleComponent.enableTitleModel("default");
    }

    private BorderComponent createBorder(InventoryDecorator decorator, InventoryApi inventoryApi) {
        var component = decorator.withComponent(inventoryApi.components().border());
        component.setBorderMaterial(Material.LIME_STAINED_GLASS_PANE);
        return component;
    }

    private void createProfileSelectButton(InventoryApi inventoryApi) {
        var btn = button("Select Profile", Material.BOOK, 2, 4);
        btn.withPermissions(TikTokLiveSpigotPermissions.PROFILES.CHANGE);
        inventoryApi.buttons().<Profile>contentList(btn, contentListOptions ->
        {
            contentListOptions.setContentSource(tokLiveSpigotApi::getAvailableProfiles);
            contentListOptions.setOnSelectionChanged(profileContentSelectionEvent ->
            {
                tokLiveSpigotApi.setProfile(profileContentSelectionEvent.getPlayer(), profileContentSelectionEvent.getSelectedItem().getName());
            });
            contentListOptions.setContentMapping(Profile::getName);
            contentListOptions.setSelectedItemObserver(ObserverBag.createObserver(new Profile(null,null,null)));
        });
    }

    private void createBoolButton(InventoryApi inventoryApi) {
        var btn = button("Config", Material.REPEATER, 2, 2);
        btn.withPermissions(TikTokLiveSpigotPermissions.CONFIG.BASE);
        inventoryApi.buttons().checkBoxList(btn, checkBoxOptions ->
        {
            checkBoxOptions.addCheckBox(
                    "Reload Profiles",
                    configObserver("reloadProfiles"),
                    TikTokLiveSpigotPermissions.CONFIG.IS_PROFILE_RELOADING);

            checkBoxOptions.addCheckBox("Connect on plugin start",
                    configObserver("autoConnectOnStart"),
                    TikTokLiveSpigotPermissions.CONFIG.IS_AUTO_CONNECTING);
        });
    }

    private void changeDefaultLiveHost(InventoryApi inventoryApi) {
        var btn = button("Recent TikTok users", Material.PLAYER_HEAD, 2, 6);
        btn.withPermissions(TikTokLiveSpigotPermissions.LIVE.CHANGE_HOST);
        inventoryApi.buttons().<String>contentList(btn, contentListOptions ->
        {
            contentListOptions.setContentSource(tokLiveSpigotApi::getRecentHostsNames);
            contentListOptions.setSelectedItemObserver(ObserverBag.createObserver(configFile.get().getTiktokUser()));
        });
        btn.withStyleRenderer(styleRendererOptionsDecorator ->
        {
            styleRendererOptionsDecorator.withShiftClickInfo("Remove all TikTok live hosts");
        });
        btn.withOnShiftClick(event ->
        {
            tokLiveSpigotApi.clearHostsNames();
        });
    }


    private void connectButton(InventoryDecorator decorator, InventoryApi inventoryApi) {
        var ref = new ButtonRef();
        var btn = button(ChatColor.DARK_RED + ConnectionState.DISCONNECTED.name(), Material.RED_CONCRETE, 8, 4);
        btn.withPermissions(TikTokLiveSpigotPermissions.LIVE.BASE);
        btn.withOnLeftClick(event ->
        {
            if (tokLiveSpigotApi.getClientState() == ConnectionState.CONNECTED) {
                tokLiveSpigotApi.connect(event.getPlayer());
                return;
            }

            if (StringUtils.isNullOrEmpty(tokLiveSpigotApi.getRecentHost())) {
                this.close();
                playerLogger.info("It looks that TikTok user is not specified yet!").send(event.getPlayer());
                playerLogger.info("Write to", ChatColor.BOLD, ChatColor.AQUA, "chat", ChatColor.GRAY, "TikTok", ChatColor.AQUA, "username", ChatColor.GRAY, "you want to connect").send(event.getPlayer());
                inputListener.registerPlayer(event.getPlayer(), event1 ->
                {
                    tokLiveSpigotApi.connect(event.getPlayer(), event1.getMessage());
                    event1.setCancelled(true);
                    inputListener.unregisterPlayer(event.getPlayer());
                });
                return;
            }
            tokLiveSpigotApi.connect(event.getPlayer());
        });
        btn.withReference(ref);
        decorator.withEvents(e ->
        {
            e.onTick(guiTickEvent ->
            {
                if (guiTickEvent.getTick() % 5 == 0) {
                    return;
                }

                var state = tokLiveSpigotApi.getClientState();
                ref.get().editStyleRendererOptions(styleRendererOptionsDecorator ->
                {
                    switch (state) {
                        case CONNECTED -> {
                            ref.get().setMaterial(Material.GREEN_CONCRETE);
                            borderComponent.setBorderMaterial(Material.LIME_STAINED_GLASS_PANE);
                            styleRendererOptionsDecorator.withTitle(ChatColor.DARK_GREEN + state.name());
                        }
                        case CONNECTING -> {
                            ref.get().setMaterial(Material.YELLOW_CONCRETE);
                            borderComponent.setBorderMaterial(Material.YELLOW_STAINED_GLASS_PANE);
                            styleRendererOptionsDecorator.withTitle(ChatColor.YELLOW + state.name());
                        }
                        case DISCONNECTED -> {
                            ref.get().setMaterial(Material.RED_CONCRETE);
                            borderComponent.setBorderMaterial(Material.RED_STAINED_GLASS_PANE);
                            styleRendererOptionsDecorator.withTitle(ChatColor.DARK_RED + state.name());
                        }
                    }


                });
                guiTickEvent.getInventory().buttons().refresh(ref.get());
            });
        });
    }


    private Observer<Boolean> configObserver(String fieldName) {
        var observer = new Observer<Boolean>(configFile.get(), fieldName);
        observer.subscribe(aBoolean ->
        {
            configFile.save();
        });
        return observer;
    }


}
