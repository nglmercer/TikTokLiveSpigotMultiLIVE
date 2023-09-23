
<div align="center" >

<h1>TikTokLiveJava</h1>
</div>


<div align="center" >


<a href="https://www.buymeacoffee.com/jwdev" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/FluentFramework/master/ff-tools/resources/socials/support_old.gif" width="30%" height="100%" >
</img>
</a>



<a href="https://discord.gg/fk3W4e3K" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/FluentFramework/master/ff-tools/resources/socials/discord.svg" width="30%" height="100%" >
</img>
</a>



<a href="https://www.spigotmc.org/members/jacekwoln.869774/" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/FluentFramework/master/ff-tools/resources/socials/spigot.svg" width="30%" height="100%" >
</img>
</a>

</div>



<a target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/TikTokLiveSpigot/master/webeditor/resources/banner_small.gif" width="100%" height="100%" >
</img>
</a>




<div align="center" >

<h1>Introdution</h1>
</div>

Ever wanted to bring the excitement of TikTok Lives directly into Minecraft? With TikTokLiveSpigot, now you can!**Integrate your minecraft server with TikTok live in 10 seconds!**It is as simple as using> /tiktoklive connect MyTikTokUser


<a href="https://www.youtube.com/watch?v=0KSN7dfi7PQ&t=32s&ab_channel=JW" align="right" target="blank" >

<img src="https://img.youtube.com/vi/0KSN7dfi7PQ/hqdefault.jpg" width="38%" align="right" >
</img>
</a>




<div align="center" >


**🎁 Gifts 🎁**</div>



> Receive a gift on TikTok Live? Your Minecraft server will thank the sender by name and even spawn creepers if the gift is extravagant!





<div align="center" >


**💬 Real-Time Comments 💬**</div>



> When someone comments on your TikTok Live, watch as a zombie named after the commenter spawns in your Minecraft world!





<div align="center" >


**🤯 Live Reactions 🤯**</div>



> Get instant health boosts or other in-game perks whenever someone likes your TikTok Live.



<div align="center" >

<h1>Simple and flexible configuration</h1>
</div>

Transform Tiktok events into Minecraft commands using custom scripting language.[Learn more about it Here](https://github.com/jwdeveloper/TikTokLiveSpigot/tree/master/Examples) 

<a target="blank" >

<img src="https://github.com/jwdeveloper/TikTokLiveSpigot/assets/79764581/e31f033e-0ca1-4929-b1bc-47b99cbea817" >
</img>
</a>


<div align="center" >

<h1>Spigot documentation</h1>
</div>


<div align="center" >


<a target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/FluentFramework/master/ff-tools/resources/banners/permissions.svg" height="100%" >
</img>
</a>


<details>
<summary>open permissions</summary>

<div align="left" >


```yaml
permissions: 

# ======================================== tiktoklivespigot =========================
  tiktoklivespigot.*: 
    description: full access

# ======================================== tiktoklivespigot.gui =====================
  tiktoklivespigot.gui.*: 
    description: full access

  tiktoklivespigot.gui.admin: 
    description: default

# ======================================== tiktoklivespigot.live ====================
  tiktoklivespigot.live.*: 
    description: full access

  tiktoklivespigot.live.change-host: 
    description: changes host of tiktok live

  tiktoklivespigot.live.connect: 
    description: user can connect to TikTokLive

  tiktoklivespigot.live.disconnect: 
    description: user can disconnect TikTokLive

# ======================================== tiktoklivespigot.profiles ================
  tiktoklivespigot.profiles.*: 
    description: full access

  tiktoklivespigot.profiles.change: 
    description: user can change profiles

  tiktoklivespigot.profiles.profile-editor: 
    description: user can open ProfileEditor

# ======================================== tiktoklivespigot.config ==================
  tiktoklivespigot.config.*: 
    description: full access

  tiktoklivespigot.config.is-profile-reloading: 
    description: enable auto reloading profiles

  tiktoklivespigot.config.is-auto-connecting: 
    description: enable auto connecting to live

  tiktoklivespigot.updater: 
    description: Players with this permission can update plugin

# ======================================== tiktoklivespigot.tiktoklive ==============
  tiktoklivespigot.tiktoklive.*: 
    description: full access

# ======================================== tiktoklivespigot.tiktoklive.commands =====
  tiktoklivespigot.tiktoklive.commands.*: 
    description: full access

  tiktoklivespigot.tiktoklive.commands.language: 
    description: Change plugin language

  tiktoklivespigot.tiktoklive.gui: 
    description: default

 
```
</div>


</details>

</div>


<div align="center" >


<a target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/FluentFramework/master/ff-tools/resources/banners/config.svg" height="100%" >
</img>
</a>


<details>
<summary>open config</summary>

<div align="left" >


```yaml
# ##<FluentFilesConfig>
# 
# plugin.files.saving-frequency
#  Determinate how frequent data is saved to files, value in minutes
# 
# 
# </FluentFilesConfig>#<TikTokLiveSpigotConfig>
# 
# tiktok-live.auto-reload-profiles
#  Dynamic reloads profiles when `profile.yml` file got changed
# 
# 
# tiktok-live.auto-connect
#  Connects to live when server starts
# 
# 
# tiktok-live.active-tiktok-user
#  Active tiktok user
# 
# 
# tiktok-live.active-profile
#  Active profile
# 
# </TikTokLiveSpigotConfig>#<UpdaterConfig>
# 
# plugin.updater.force-update
#  if there is new update, it is downloaded and installed
# 
# 
# plugin.updater.check-update.plugin.updater.check-update.enabled
#  checking if there is new update
# 
# plugin.updater.check-update.plugin.updater.check-update.check-every-minutes
#  Checking update every X minutes
# 
# plugin.updater.check-update.plugin.updater.check-update.send-update-message-to-console
#  checking if there is new update
# 
# plugin.updater.check-update.plugin.updater.check-update.send-update-message-to-op
#  Sends message to op players when update is ready
# </UpdaterConfig>#<TranslatorConfig>
# 
# plugin.translator.language
#  If you want add your language open `languages` folder copy `en.yml`
# set `default-language` property to your file name and /reload server
# 
# 
# </TranslatorConfig>
plugin-meta:
  plugin-version: 1.0.0
  modules-version:
    translator: 1.0.0
    files: 1.0.0
    updater: 1.0.0
  environment: debug
plugin:
  translator:
    language: en
  files:
    auto-save: false
    saving-frequency: ''
  updater:
    force-update: false
    check-update:
      enabled: true
      check-every-minutes: 0
      send-update-message-to-console: false
      send-update-message-to-op: false
tiktok-live:
  auto-reload-profiles: false
  auto-connect: false
  active-tiktok-user: ''
  active-profile: ''
 
```
</div>


</details>

</div>


<div align="center" >


<a target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/FluentFramework/master/ff-tools/resources/banners/commands.svg" height="100%" >
</img>
</a>


<details>
<summary>open commands</summary>

<div align="left" >


```yaml
# Commands
# /tiktoklive
# /tiktoklive language <language>
# /tiktoklive connect
# /tiktoklive profile-editor
# /tiktoklive profile
# /tiktoklive admin
# /tiktoklive disconnect
# /tiktoklive updater


commands: 
# /tiktoklive
  tiktoklive: 
    children: 
      - language
      - connect
      - profile-editor
      - profile
      - admin
      - disconnect
      - updater
    permissions: 
      - 
    can-use: 
      - command_sender
    usage: /tiktoklive
# /tiktoklive language <language>
  language: 
    permissions: 
      - language
    arguments: 
      - language:
          type: text
          description: select language
          options: 
              - swedish
              - german
              - spanish
              - russian
              - turkish
              - portugal
              - english
              - italian
              - polish
              - korean
              - french
    description: Changes plugin languages, changes will be applied after server reload. Change be use both be player or console
    usage: /tiktoklive language <language>

# /tiktoklive connect
  connect: 
    permissions: 
      - tiktoklivespigot.live.connect
    can-use: 
      - command_sender
    arguments: 
      - tiktok-user:
          type: text
    usage: /tiktoklive connect

# /tiktoklive profile-editor
  profile-editor: 
    permissions: 
      - tiktoklivespigot.profiles.profile-editor
    can-use: 
      - command_sender
    usage: /tiktoklive profile-editor

# /tiktoklive profile
  profile: 
    permissions: 
      - tiktoklivespigot.profiles.change
    can-use: 
      - command_sender
    arguments: 
      - profile-name:
          type: text
    usage: /tiktoklive profile

# /tiktoklive admin
  admin: 
    permissions: 
      - tiktoklivespigot.gui.admin
    can-use: 
      - command_sender
    usage: /tiktoklive admin

# /tiktoklive disconnect
  disconnect: 
    permissions: 
      - tiktoklivespigot.live.disconnect
    can-use: 
      - command_sender
    usage: /tiktoklive disconnect

# /tiktoklive updater
  updater: 
    permissions: 
      - updater
    description: Download plugin latest version, can be trigger both by player or console
    usage: /tiktoklive updater


: 
 
```
</div>


</details>

</div>

