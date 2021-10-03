package net.skriptland.lobby.utils;

import net.skriptland.lobby.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;

public class FileManager {
    private static HashMap<Files, YamlConfiguration> values;

    public static void init() throws IOException {
        values = new HashMap<>();

        File storage = Main.getINSTANCE().getDataFolder();
        storage.mkdirs();

        for (Files files : Files.values()) {
            File fileRessource = new File(Main.getINSTANCE().getDataFolder() + "/" + files.getName() + ".yml");
            if (!fileRessource.exists()) {
                InputStream fileStream = Main.getINSTANCE().getResource(files.getName() + ".yml");
                byte[] buffer = new byte[fileStream.available()];
                fileStream.read(buffer);

                fileRessource.createNewFile();
                OutputStream outStream = new FileOutputStream(fileRessource);
                outStream.write(buffer);
            }
            values.put(files, YamlConfiguration.loadConfiguration(fileRessource));
        }
    }

    public static void save(Files files) {
        File file = new File(Main.getINSTANCE().getDataFolder() + "/" + files.getName() + ".yml");
        if (!file.exists()) {
            System.out.println("ERROR : File " + files.getName() + " not found !");
            return;
        }
        try {
            values.get(files).save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        values.put(files, YamlConfiguration.loadConfiguration(file));
    }

    public static void saveAll() {
        for (Files files : Files.values())
            save(files);
    }

    public static void reload(Files files) {
        File file = new File(Main.getINSTANCE().getDataFolder() + "/" + files.name() + ".yml");

        if (!file.exists()) {
            System.out.println("ERROR : File " + files.name() + " not found !");
            return;
        }

        values.put(files, YamlConfiguration.loadConfiguration(file));
    }

    public static void reloadAll() {
        for (Files files : Files.values())
            reload(files);
    }

    public static HashMap<Files, YamlConfiguration> getValues() {
        return values;
    }
}
