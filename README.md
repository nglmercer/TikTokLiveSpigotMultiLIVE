
<div align="center" >

<h1>TikTokLiveJava</h1>
</div>


<div align="center" >


<a href="https://www.buymeacoffee.com/jwdev" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/FluentFramework/master/ff-tools/resources/socials/support.svg" width="30%" height="100%" >
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

<img src="https://raw.githubusercontent.com/jwdeveloper/TikTokLiveSpigot/master/webeditor/resources/banner.png" height="100%" >
</img>
</a>




<div align="center" >

<h1>Introdution</h1>

</div>




<a href="https://www.youtube.com/watch?v=0KSN7dfi7PQ&t=32s&ab_channel=JW" target="blank"  >

<img src="https://img.youtube.com/vi/0KSN7dfi7PQ/0.jpg" width="20%" align ="right"  >
</img>
</a>


 > Integrate your minecraft server with TikTok live in 10 seconds!


Ever wanted to bring the excitement of TikTok Lives directly into your Minecraft world? With TikTokLiveSpigot, now you can! Recieve TikTok events such as,**Likes**,**Gifts**,**Follows**,etc.. and trigger them as minecraft commands everything is configurable in yaml fileEver wanted to bring the excitement of TikTok Lives directly into your Minecraft world? With TikTokLiveSpigot, now you can! Recieve TikTok events such as,**Likes**,**Gifts**,**Follows**,etc.. and trigger them as minecraft commands everything is configurable in yaml file

<div align="center" >

<h1>Key Features</h1>
</div>




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

<h1>Video Tutorial</h1>
</div>


<h1>Events configuration</h1>
To start working go to[webeditor](https://jwdeveloper.github.io/TikTokLiveSpigot/webeditor/) and find TikTok event you are interested in
<h5>How to add new profile?</h5>
Remember before every command must start with dash

```yaml
 [profile name]: [tiktok event name]: - [command]  
```

<h5>How to inject event data?</h5>
o inject data you need to open create code block ${ }And set as its content property of TikTok event you want to useAll available properties can be found[here](https://jwdeveloper.github.io/TikTokLiveSpigot/webeditor/) 

```yaml
 example-profile: onLike: - /say ${event.user.nickName} thank you for like!  
```

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

# ======================================== ANNOTATION PermissionGroup NOT PRESENT ===
  ANNOTATION PermissionGroup NOT PRESENT.*: 
    description: full access

  ANNOTATION PermissionGroup NOT PRESENT.updater: 
    description: Players with this permission can update plugin

# ======================================== ANNOTATION PermissionGroup NOT PRESENT.TikTokLiveSpigot 
  ANNOTATION PermissionGroup NOT PRESENT.TikTokLiveSpigot.*: 
    description: full access

# ======================================== ANNOTATION PermissionGroup NOT PRESENT.TikTokLiveSpigot.commands 
  ANNOTATION PermissionGroup NOT PRESENT.TikTokLiveSpigot.commands.*: 
    description: full access

  ANNOTATION PermissionGroup NOT PRESENT.TikTokLiveSpigot.commands.language: 
    description: Change plugin language

  ANNOTATION PermissionGroup NOT PRESENT.TikTokLiveSpigot.gui: 
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
# ##<TikTokLiveSpigotConfig>
# 
# tiktok-live.auto-reload-profiles
#  Dynamic reloads profiles when `profile.yml` file got changed
# 
# 
# tiktok-live.auto-connect
#  Connects to live when server starts
# 
# 
# tiktok-live.tiktok-user
#  Default tiktok user
# 
# 
# tiktok-live.profile
#  Default  profile
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
# </UpdaterConfig>#<FluentFilesConfig>
# 
# plugin.files.saving-frequency
#  Determinate how frequent data is saved to files, value in minutes
# 
# 
# </FluentFilesConfig>#<TranslatorConfig>
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
  updater:
    force-update: false
    check-update:
      enabled: true
      check-every-minutes: 0
      send-update-message-to-console: false
      send-update-message-to-op: false
  files:
    auto-save: false
    saving-frequency: ''
tiktok-live:
  auto-reload-profiles: false
  auto-connect: false
  tiktok-user: ''
  profile: ''
 
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


commands: 
# debbug
  debbug: 

# tiktoklive
  tiktoklive: 
    children: 
      - language
      - connect
      - profile
      - disconnect
      - profile-editor
      - updater
    permissions: 
      - 
    can-use: 
      - command_sender
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

# connect
  connect: 
    permissions: 
      - tiktoklivespigot.connect
    can-use: 
      - command_sender
    arguments: 
      - tiktok-user:
          type: text

# profile
  profile: 
    permissions: 
      - tiktoklivespigot.profile.set
    can-use: 
      - command_sender
    arguments: 
      - profile-name:
          type: text

# disconnect
  disconnect: 
    permissions: 
      - tiktoklivespigot.disconnect
    can-use: 
      - command_sender

# profile-editor
  profile-editor: 
    permissions: 
      - tiktoklivespigot.editor
    can-use: 
      - command_sender

# /tiktoklive updater
  updater: 
    permissions: 
      - updater
    description: Download plugin latest version, can be trigger both by player or console
    usage: /tiktoklive updater


 
```
</div>


</details>

</div>

