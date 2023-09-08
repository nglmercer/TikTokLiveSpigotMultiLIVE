package io.github.jwdeveloper.spigot.tiktok.api.profiles.models;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Profile
{
    public final static Profile EMPTY = new Profile();

    private String name;

    private Map<Class<?>, List<ProfileEventCommand>> eventsCommands = new HashMap<>();
}
