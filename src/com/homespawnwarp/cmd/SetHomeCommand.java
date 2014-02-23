package com.homespawnwarp.cmd;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.LocationIO;
import com.homespawnwarp.tool.Tools;

final public class SetHomeCommand extends AbstractCommand {

	public int group1;
	public int group2;
	public int group3;
	public int group4;
	public int group5;

	public SetHomeCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		int homeAmount;

		if (Tools.getLocations().contains("homes." + player.getName())) {
			Set<String> homeNames = Tools.getLocations()
					.getConfigurationSection("homes." + player.getName())
					.getKeys(false);
			homeAmount = homeNames.size();
			if (args.length != 0 && homeNames.contains(args[0])) {
				homeAmount --;
			}
		} else {
			homeAmount = 0;
		}

		Location l = player.getLocation();

		if (args.length == 0) {
			// Without args
			if (Tools.getLocations().contains(
					"homes." + player.getName() + ".default")) {
				homeAmount --;
			}

			if (checkMaxHomes(player, homeAmount)) {
				LocationIO.write("homes." + player.getName() + ".default", l);

				player.sendMessage(Tools.getMessage("default-home-set"));
			} else {
				player.sendMessage(Tools.getMessage("max-homes-reached"));
				return false;
			}

		} else {
			// With args

			if (checkMaxHomes(player, homeAmount)) {
				if (!containsIllegalChars(args[0], sender)) {
					LocationIO.write("homes." + player.getName() + "."
							+ args[0], l);

					player.sendMessage(Tools.getMessage("home-set"));
				}

			} else {
				player.sendMessage(Tools.getMessage("max-homes-reached"));
				return false;
			}
		}
		return true;
	}

	private boolean checkMaxHomes(Player player, int homeAmount) {
		boolean g1 = false;
		boolean g2 = false;
		boolean g3 = false;
		boolean g4 = false;
		boolean g5 = false;

		if (hasPerm(player, "HomeSpawnWarp.home.GROUP1", true, false)) {
			g1 = true;
		}

		if (hasPerm(player, "HomeSpawnWarp.home.GROUP2", false, false)) {
			g2 = true;
		}

		if (hasPerm(player, "HomeSpawnWarp.home.GROUP3", false, false)) {
			g3 = true;
		}

		if (hasPerm(player, "HomeSpawnWarp.home.GROUP4", false, false)) {
			g4 = true;
		}

		if (hasPerm(player, "HomeSpawnWarp.home.GROUP5", false, false)) {
			g5 = true;
		}

		if (hasPerm(player, "HomeSpawnWarp.home.UNLIMITED", false, false)) {
			return true;
		}

		if (g1 && homeAmount < group1) {
			return true;
		} else {
			if (g2 && homeAmount < group2) {
				return true;
			} else {
				if (g3 && homeAmount < group3) {
					return true;
				} else {
					if (g4 && homeAmount < group4) {
						return true;
					} else {
						if (g5 && homeAmount < group5) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
	}
}
