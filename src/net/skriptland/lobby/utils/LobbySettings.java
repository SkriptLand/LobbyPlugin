package net.skriptland.lobby.utils;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class LobbySettings {
    public static Location spawn;

    public static void init() {
        YamlConfiguration cache = FileManager.getValues().get(Files.CACHE);

        if (cache.contains("spawn"))
            spawn = (Location) cache.get("spawn");
    }
}
