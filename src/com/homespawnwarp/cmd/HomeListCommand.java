package com.homespawnwarp.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.perm.Permission;

final public class HomeListCommand extends ListCommand {

	public HomeListCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		player.sendMessage(ChatColor.DARK_AQUA + "Homes:");

		player.sendMessage(getList("homes." + player.getUniqueId()));
		
		
		return true;
	}
}
