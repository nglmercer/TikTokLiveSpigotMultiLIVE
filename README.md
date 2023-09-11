
![alt text](https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/banner.png)

<p align="center">
<a href="https://discord.gg/2hu6fPPeF7"><img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png"  /></a><a href="https://github.com/jwdeveloper/JW_Piano"><img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png"  /></a><a href="https://www.spigotmc.org/resources/piano.103490/"><img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png"  /></a></p>

A Java library based on TikTokLive and TikTokLiveSharp. 
Use it to receive live stream events such as comments and gifts in realtime from TikTok LIVE by connecting to TikTok's internal WebCast push service. The package includes a wrapper that connects to the WebCast service using just the username (uniqueId). This allows you to connect to your own live chat as well as the live chat of other streamers. No credentials are required. Besides Chat Comments, other events such as Members Joining, Gifts, Subscriptions, Viewers, Follows, Shares, Questions, Likes and Battles can be tracked. You can also send automatic messages into the chat by providing your Session ID.

<details>
<summary>Oraxen</summary>


### Oraxen configuration
-  Setup for all users that willing to use Piano with Oraxen

[open piano Oraxen config file](https://github.com/jwdeveloper/JW_Piano/blob/master/src/main/resources/oraxen/jw_piano_oraxen_config.yml)

``` yaml
#Generated template for Oraxen, It only contains models from pianopack
#Remember to refresh config when pianopack got updated
#Note that when you change LEATHER_HORSE_ARMOR to other material functionalities as Colored keys, Pianos, will not work

bench:
 displayname: bench
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/bench
  custom_model_data: 167072

flyingnote:
 displayname: flyingnote
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/flyingnote
  custom_model_data: 167073

icon:
 displayname: icon
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/icons/icon
  custom_model_data: 167074

piano-black-key:
 displayname: piano black key
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/key/piano_black_key
  custom_model_data: 167075

piano-black-key-down:
 displayname: piano black key down
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/key/piano_black_key_down
  custom_model_data: 167076

piano-key:
 displayname: piano key
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/key/piano_key
  custom_model_data: 167077

piano-key-down:
 displayname: piano key down
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/key/piano_key_down
  custom_model_data: 167078

piano-pedal:
 displayname: piano pedal
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/pedal/piano_pedal
  custom_model_data: 167079

piano-pedal-down:
 displayname: piano pedal down
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/pedal/piano_pedal_down
  custom_model_data: 167080

pianist-body:
 displayname: pianist body
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/pianist/pianist_body
  custom_model_data: 167081

pianist-hands:
 displayname: pianist hands
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/pianist/pianist_hands
  custom_model_data: 167082

pianist-head:
 displayname: pianist head
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/pianist/pianist_head
  custom_model_data: 167083

electric-piano:
 displayname: electric piano
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/piano/electric_piano
  custom_model_data: 167084

grand-piano:
 displayname: grand piano
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/piano/grand_piano
  custom_model_data: 167085

grand-piano-close:
 displayname: grand piano close
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/piano/grand_piano_close
  custom_model_data: 167086

up-right-piano-close:
 displayname: up right piano close
 material: LEATHER_HORSE_ARMOR
 excludeFromInventory: true
 Pack:
  generate_model: false
  model: item/jw/piano/up_right_piano_close
  custom_model_data: 167087


note_a:
  texture: icons/notes/a
  ascent: 2
  height: 2
  code: 4096
note_aSharp:
  texture: icons/notes/a_sharp
  ascent: 2
  height: 2
  code: 4097
note_b:
  texture: icons/notes/b
  ascent: 2
  height: 2
  code: 4098
note_c:
  texture: icons/notes/c
  ascent: 2
  height: 2
  code: 4099
note_cSharp:
  texture: icons/notes/c_sharp
  ascent: 2
  height: 2
  code: 4100
note_d:
  texture: icons/notes/d
  ascent: 2
  height: 2
  code: 4101
note_dSharp:
  texture: icons/notes/d_sharp
  ascent: 2
  height: 2
  code: 4102
note_e:
  texture: icons/notes/e
  ascent: 2
  height: 2
  code: 4103
note_f:
  texture: icons/notes/f
  ascent: 2
  height: 2
  code: 4104
note_fSharp:
  texture: icons/notes/f_sharp
  ascent: 2
  height: 2
  code: 4105
note_g:
  texture: icons/notes/g
  ascent: 2
  height: 2
  code: 4112
note_gSharp:
  texture: icons/notes/g_sharp
  ascent: 2
  height: 2
  code: 4113
  

```
</details>
<details>
<summary>API for plugin developers</summary>

JW_Piano provides programming API to manipulate Pianos behaviour.
You can use it but adding JW_Piano.jar as soft dependency to your Plugin


### Create Piano

 ```java
        public void creatingPiano(Player player) {
         Optional<Piano> optional = PianoApi.create(player.getLocation(), "new piano");
         if (optional.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage("Unable to create piano ;<");
            return;
         }
         Piano piano = optional.get();
        }
```


### Register new skin

 ```java

  public void addSkin(Piano piano) {
     int customModelId = 100;
     String name = "custom skin";
     ItemStack itemStack = new ItemStack(Material.STICK);
     PianoSkin customSkin = new PianoSkin(customModelId, name, itemStack);
     piano.getSkinManager().register(customSkin);
     piano.getSkinManager().setCurrent(customSkin);
  }
```

### Register new effect

 ```java

  public void addNewEffect(Piano piano) {
        EffectInvoker customEffect = new CustomEffect();
        piano.getEffectManager().register(customEffect);
        piano.getEffectManager().setCurrent(customEffect);
    }


    public class CustomEffect implements EffectInvoker {
        @Override
        public String getName() {
            return "custom";
        }

        @Override
        public void onNote(PianoKey pianoKey, Location location, int noteIndex, int velocity, Color color) {
            Bukkit.getConsoleSender().sendMessage(color + "Note: " + noteIndex + "  Volume:" + velocity);
            location.getWorld().spawnParticle(Particle.NOTE, location, 1);
        }

        @Override
        public void onDestroy() {
            Bukkit.getConsoleSender().sendMessage(getName() + "Destroyed");
        }

        @Override
        public void onCreate() {
            Bukkit.getConsoleSender().sendMessage(getName() + "Created");
        }

        @Override
        public void refresh() {
            Bukkit.getConsoleSender().sendMessage(getName() + "Refreshed");
        }
    }
  }
```
</details>
<details>
<summary>Common issues</summary>


### Resourcepack
-  When you have some problems with resourcepack download it directly

### Desktop app configuration, `config.yml` > `plugin.websocket.server-ip`
-  Make sure port you are trying to use is open
-  When you've got problems with connection try to change `plugin.websocket.server-ip` or  `plugin.websocket.port`
-  Check if you need to create new port in the server hosting panel and then set in to `plugin.websocket.port`
-  When your server use proxy use Proxy IP to `plugin.websocket.server-ip`
-  When you server IP has port ignore port. Example:

Wrong: `craftplayer.com:22225`

Correct: `craftplayer.com`

-  When you are running server locally set value to `localhost` to `plugin.websocket.server-ip`
-  When above solutions does not help set IP that you use in Minecraft server lists to `plugin.websocket.server-ip`
</details>

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/0KSN7dfi7PQ/0.jpg)](https://www.youtube.com/watch?v=0KSN7dfi7PQ&ab_channel=JW)


[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/PSbwsbX7xc0/0.jpg)](https://www.youtube.com/watch?v=PSbwsbX7xc0&t=27s&ab_channel=JW)


![alt text](https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/style.png)


![alt text](https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/webclient.png)


![alt text](https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/configuration.png)


![alt text](https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/commands.png)


![alt text](https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/permissions.png)

