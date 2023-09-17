package io.github.jwdeveloper.spigot.tiktok;

import io.github.jwdeveloper.ff.core.spigot.permissions.api.annotations.PermissionGroup;
import io.github.jwdeveloper.ff.core.spigot.permissions.api.annotations.PermissionProperty;

@PermissionGroup(group = "tiktoklivespigot")
public class PermissionsTemplate
{

    @PermissionGroup(group = "config")
    public static class CONFIG
    {
        @PermissionProperty(description = "enable auto reloading profiles")
        public static String isProfileReloading = "is-profile-reloading";

        @PermissionProperty(description = "enable auto connecting to live")
        public static String isAutoConnecting = "is-auto-connecting";
    }

    @PermissionGroup(group = "profiles")
    public static class PROFILES
    {
        @PermissionProperty(description = "user can change profiles")
        public static String change = "change";

        @PermissionProperty(description = "user can open ProfileEditor")
        public static String profileEditor = "profile-editor";
    }


    @PermissionGroup(group = "live")
    public static class LIVE
    {
        @PermissionProperty(description = "changes host of tiktok live")
        public static String changeHost ="change-host";

        @PermissionProperty(description = "user can connect to TikTokLive")
        public static String connect= "connect";

        @PermissionProperty(description = "user can disconnect TikTokLive")
        public static String disconnect= "disconnect";
    }

    @PermissionGroup(group = "gui")
    public static class GUI
    {
        @PermissionGroup(group = "admin")
        public static class ADMIN
        {

        }
    }
}
