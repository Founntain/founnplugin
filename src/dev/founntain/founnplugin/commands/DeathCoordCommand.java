package dev.founntain.founnplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.founntain.founnplugin.classes.DeathCoord;

public class DeathCoordCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;

			DeathCoord deathCoord = DeathCoord.getDeathCoordFromPlayer(player);
			
			if(deathCoord == null) {
				player.sendMessage(ChatColor.RED + "Leider konnte der Server nicht die Todeskoordinate finden");
				return true;
			}
			
			player.teleport(deathCoord.getLocation());
			
			DeathCoord.deathCoords.remove(player.getUniqueId());
			
			player.sendMessage(ChatColor.GREEN + "Du wurdest auf die angegebene Position teleportiert.");
		}
			
		return true;
	}
}
