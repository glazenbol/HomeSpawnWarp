package com.homespawnwarp.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.perm.Permission;

final public class WarpListCommand extends ListCommand implements
		ConsoleSendable {

	public WarpListCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		sender.sendMessage(ChatColor.DARK_AQUA + "Warps:");
		sender.sendMessage(getList("warps"));

		return false;
	}

}
