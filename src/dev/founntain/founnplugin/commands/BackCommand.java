package dev.founntain.founnplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.founntain.founnplugin.classes.PlayerTeleportCoords;

public class BackCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;

			Location currentLocation = player.getLocation();
			Location lastTeleportLocation = PlayerTeleportCoords.playerTeleportCoords.get(player.getUniqueId());
			
			if(lastTeleportLocation == null) {
				player.sendMessage(ChatColor.RED + "Leider konnte der Server deine letzte position nicht finden!");
				return true;
			}
			
			PlayerTeleportCoords.playerTeleportCoords.put(player.getUniqueId(), currentLocation);
			
			player.teleport(lastTeleportLocation);
			
			player.sendMessage(ChatColor.GREEN + "Du wurdest auf die angegebene Position teleportiert.");
		}
			
		return true;
	}
}
