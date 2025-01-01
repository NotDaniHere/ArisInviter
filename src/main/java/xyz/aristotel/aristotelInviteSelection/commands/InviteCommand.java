package xyz.aristotel.aristotelInviteSelection.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InviteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getName().equalsIgnoreCase("invite")) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use /invite.");
            return true;
        }

        Player player = (Player) sender;
        // Only whitelisted players can invite
        if (!player.isWhitelisted()) {
            player.sendMessage("You must be whitelisted to use /invite.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage("Usage: /invite <playerName>");
            return true;
        }

        // Whitelist target
        String targetName = args[0];
        OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(targetName);

        if (targetOffline.isWhitelisted()) {
            player.sendMessage(targetName + " is already whitelisted!");
            return true;
        }

        targetOffline.setWhitelisted(true);
        player.sendMessage("Successfully invited (whitelisted) " + targetName + "!");

        return true;
    }
}
