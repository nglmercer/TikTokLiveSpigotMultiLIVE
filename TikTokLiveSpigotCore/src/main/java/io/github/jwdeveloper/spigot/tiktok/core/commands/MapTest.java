package io.github.jwdeveloper.spigot.tiktok.core.commands;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MapTest {


    private static Map<String, Image> cache = new HashMap<>();



    public static Image downloadImage(String imageUrl) {
        if (cache.containsKey(imageUrl)) {
            return cache.get(imageUrl);
        }
        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            Image image = ImageIO.read(inputStream);
            cache.put(imageUrl, image);
            inputStream.close();
            return image;
        } catch (Exception e)
        {
            FluentLogger.LOGGER.error("UNABLE TO DONLOAD FILE",e);
            return null;
        }
    }

    public static ItemStack giveMapToPlayer(Player player, Image image) {
        // Create a new map item
        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
        MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();

        // Create or get a MapView
        MapView mapView = Bukkit.createMap(player.getWorld());

        // Clear existing renderers
        for (MapRenderer renderer : mapView.getRenderers()) {
            mapView.removeRenderer(renderer);
        }

        // Add custom renderer
        mapView.addRenderer(new MapRenderer() {
            @Override
            public void render(MapView view, MapCanvas canvas, Player player) {
                canvas.drawImage(0, 0, image);
            }
        });

        // Set the map ID for the item
        mapMeta.setMapView(mapView);
        mapItem.setItemMeta(mapMeta);

        // Give the map to the player

        player.getInventory().setItemInOffHand(mapItem);
        return mapItem;
    }
}
