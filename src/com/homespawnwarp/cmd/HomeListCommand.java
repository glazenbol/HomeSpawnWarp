package com.homespawnwarp.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.Permission;
import com.homespawnwarp.util.Tools;

final public class HomeListCommand extends AbstractCommand {

	public HomeListCommand(HomeSpawnWarp plugin, Permission commandPermission,
			boolean isDefaultPermitted, String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		player.sendMessage(Tools.getMessage("homes"));

		if (Tools.getLocations().contains("homes." + player.getUniqueId())) {

			String[] h = Tools.getLocations()
					.getConfigurationSection("homes." + player.getUniqueId())
					.getKeys(false).toArray(new String[0]);

			if (h.length != 0) {

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < h.length; i++) {
					if (i == h.length - 1) {
						sb.append(ChatColor.DARK_AQUA + h[i]
								+ ChatColor.DARK_GREEN + ".");
					} else {
						sb.append(ChatColor.DARK_AQUA + h[i]
								+ ChatColor.DARK_GREEN + ", ");
					}
				}

				player.sendMessage(ChatColor.DARK_GREEN + sb.toString());

			} else {
				player.sendMessage(Tools.getMessage("none"));
			}
		} else {
			player.sendMessage(Tools.getMessage("none"));
		}
		return true;
	}

}
