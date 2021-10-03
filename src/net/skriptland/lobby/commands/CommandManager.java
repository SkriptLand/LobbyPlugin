package net.skriptland.lobby.commands;

import fr.mrcubee.langlib.Lang;
import net.skriptland.lobby.utils.LobbySettings;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author SkyfightTV
 * @since 1.0
 */
public class CommandManager implements CommandExecutor {
    private void spawn(Player player, String[] args) {
        if (LobbySettings.spawn != null) {
            player.sendMessage(Lang.getMessage(player, "spawn", "", true));
            player.teleport(LobbySettings.spawn);
        }
    }

    private void setspawn(Player player, String[] args) {
        LobbySettings.spawn = player.getLocation().getBlock().getLocation().add(0.5, 0.2, 0.5);
        player.sendMessage(Lang.getMessage(player, "setSpawn", "", true));
    }

    private void fly(Player player, String[] args) {
        player.setAllowFlight(!player.getAllowFlight());
        player.sendMessage(Lang.getMessage(player, "fly." + player.getAllowFlight(), "", true));
    }

    private void gm(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(Lang.getMessage(player, "gamemode.empty", "", true));
            return;
        }
        String value = args[0]
                .replaceAll("c", "1")
                .replaceAll("s", "0")
                .replaceAll("a", "2");
        try {
            player.setGameMode(GameMode.getByValue(Integer.parseInt(value)));
            player.sendMessage(Lang.getMessage(player, "gamemode.change", "", true)
                    .replaceAll("%gamemode%", player.getGameMode().name()));
        } catch (Exception e) {
            player.sendMessage(Lang.getMessage(player, "gamemode.error", "", true));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String category;
        String[] categoryArguments;
        Method method;

        if (args.length < 1)
            return true;
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.getMessage("command.must_be_player","&cYou must be a Player for execute this command.", true));
            return true;
        }
        category = cmd.getName().toLowerCase();
        categoryArguments = new String[args.length];
        System.arraycopy(args, 0, categoryArguments, 0, categoryArguments.length);

        try {
            method = CommandManager.class.getDeclaredMethod(category, Player.class, String[].class);
            method.invoke(this, (Player) sender, categoryArguments);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            sender.sendMessage(Lang.getMessage(sender, "command.error.subcommand_not_found", "&cError", true));
        }
        return true;
    }
}
