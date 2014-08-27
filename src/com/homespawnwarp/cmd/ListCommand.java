package com.homespawnwarp.cmd;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.ChatColor;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.perm.Permission;

public abstract class ListCommand extends AbstractCommand {

	public ListCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	protected String getList(String path) {
		if (ConfigIO.getLocations().contains(path)) {
			Set<String> list = ConfigIO.getLocations()
					.getConfigurationSection(path).getKeys(false);

			if (list.size() != 0) {

				StringBuilder sb = new StringBuilder();

				Iterator<String> iterator = list.iterator();

				while (iterator.hasNext()) {

					String curString = iterator.next();

					if (!iterator.hasNext()) {
						sb.append(ChatColor.DARK_AQUA + curString
								+ ChatColor.DARK_GREEN + ".");
					} else {
						sb.append(ChatColor.DARK_AQUA + curString
								+ ChatColor.DARK_GREEN + ", ");
					}
				}

				return ChatColor.DARK_GREEN + sb.toString();

			} else {
				return " - ";
			}
		} else {

			return " - ";
		}

	}
}
