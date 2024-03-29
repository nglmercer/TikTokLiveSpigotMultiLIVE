package io.github.jwdeveloper.spigot.tiktok.core.common;

public class PluginPermissions
{
    public static final String BASE = "tiktoklivespigot";

    public static class GUI
    {
        public static final String BASE = "tiktoklivespigot.gui";
        public static final String ADMIN = "tiktoklivespigot.gui.admin";
    }

    public static class LIVE
    {
        public static final String BASE = "tiktoklivespigot.live";
        public static final String CHANGE_HOST = "tiktoklivespigot.live.change-host";
        public static final String CONNECT = "tiktoklivespigot.live.connect";
        public static final String DISCONNECT = "tiktoklivespigot.live.disconnect";
    }

    public static class PROFILES
    {
        public static final String BASE = "tiktoklivespigot.profiles";
        public static final String CHANGE = "tiktoklivespigot.profiles.change";
        public static final String PROFILE_EDITOR = "tiktoklivespigot.profiles.profile-editor";
    }

    public static class CONFIG
    {
        public static final String BASE = "tiktoklivespigot.config";
        public static final String IS_PROFILE_RELOADING = "tiktoklivespigot.config.is-profile-reloading";
        public static final String IS_AUTO_CONNECTING = "tiktoklivespigot.config.is-auto-connecting";
    }
}
