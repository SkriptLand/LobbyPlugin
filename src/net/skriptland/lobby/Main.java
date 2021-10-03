package net.skriptland.lobby;

import fr.mrcubee.langlib.Lang;
import net.skriptland.lobby.commands.CommandManager;
import net.skriptland.lobby.utils.FileManager;
import net.skriptland.lobby.utils.LobbySettings;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {
    private static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Lang.setDefaultLang("FR_fr");

        try {
            FileManager.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LobbySettings.init();

        CommandManager commands = new CommandManager();
        getCommand("spawn").setExecutor(commands);
        getCommand("setspawn").setExecutor(commands);
        getCommand("fly").setExecutor(commands);
        getCommand("gm").setExecutor(commands);
    }

    public static Main getINSTANCE() {
        return INSTANCE;
    }
}
