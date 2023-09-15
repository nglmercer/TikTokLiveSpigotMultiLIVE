package io.github.jwdeveloper.spigot.tiktok;

import io.github.jwdeveloper.ff.core.spigot.permissions.api.annotations.PermissionGroup;
import io.github.jwdeveloper.ff.core.spigot.permissions.api.annotations.PermissionProperty;

@PermissionGroup(group = "tiktoklivespigot")
public class PermissionsTemplate
{

    @PermissionGroup(group = "config")
    public static class CONFIG
    {
        @PermissionProperty(description = "user can update config")
        public static String change = "change";
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

    @PermissionGroup(group = "commands")
    public static class COMMANDS
    {

    }
}
