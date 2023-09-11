
<a href="" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/TikTokLiveSpigot/master/webeditor/resources/banner.jpg" >
</img>
</a>


<div align="center" >


<a href="google.com" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png" >
</img>
</a>



<a href="google.com" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png" >
</img>
</a>



<a href="google.com" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png" >
</img>
</a>

</div>

A Java library based on TikTokLive and TikTokLiveSharp. Use it to receive live stream events such as comments and gifts in realtime from TikTok LIVE by connecting to TikTok's internal WebCast push service. The package includes a wrapper that connects to the WebCast service using just the username (uniqueId). This allows you to connect to your own live chat as well as the live chat of other streamers. No credentials are required. Besides Chat Comments, other events such as Members Joining, Gifts, Subscriptions, Viewers, Follows, Shares, Questions, Likes and Battles can be tracked. You can also send automatic messages into the chat by providing your Session ID.


<a href="" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/configuration.png" >
</img>
</a>


<details>
<summary>open config</summary>


```yml
# ##<FluentFilesConfig>
# 
# plugin.files.saving-frequency
#  Determinate how frequent data is saved to files, value in minutes
# 
# 
# </FluentFilesConfig>#<UpdaterConfig>
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
# </UpdaterConfig>#<TikTokLiveSpigotConfig>
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
# </TikTokLiveSpigotConfig>#<TranslatorConfig>
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
  tiktok-user: ''
  profile: ''
 
```

</details>



<a href="" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/permissions.png" >
</img>
</a>


<details>
<summary>open permissions</summary>


```yml
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

</details>



<a href="" target="blank" >

<img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/commands.png" >
</img>
</a>


<details>
<summary>open commands</summary>


```yml


commands: 
# debbug
  debbug: 

# tiktoklive
  tiktoklive: 
    children: 
      - language
      - connect
      - disconnect
      - profile
      - profile-editor
      - updater
    permissions: 
      - 
    can-use: 
      - command_sender
# /tiktokliveA language <language>
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
    usage: /tiktokliveA language <language>

# connect
  connect: 
    permissions: 
      - tiktoklivespigot.connect
    can-use: 
      - command_sender
    arguments: 
      - tiktok-user:
          type: text

# disconnect
  disconnect: 
    permissions: 
      - tiktoklivespigot.disconnect
    can-use: 
      - command_sender

# profile
  profile: 
    permissions: 
      - tiktoklivespigot.profile.set
    can-use: 
      - command_sender
    arguments: 
      - profile-name:
          type: text

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

</details>

