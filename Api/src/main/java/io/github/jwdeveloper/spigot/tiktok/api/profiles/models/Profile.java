package io.github.jwdeveloper.spigot.tiktok.api.profiles.models;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.tiktok.profiles.ast.ProgramSYML;
import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Value
public class Profile
{
   String name;

   ProgramSYML intiBlock;

   Map<Class<?>, ProgramSYML> eventsBlocks;

   public static Profile EMPTY()
   {
      return new Profile(StringUtils.EMPTY,new ProgramSYML(new ArrayList<>()),new HashMap<>());
   }
}
