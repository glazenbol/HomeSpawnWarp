package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand extends AbstractCommand {

	static final int GROUP1 = 1;
	static final int GROUP2 = 2;
	static final int GROUP3 = 3;
	static final int GROUP4 = 5;
	static final int GROUP5 = 10;

	public SetHomeCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		int homeAmount;
		
		if (plugin.getLocations().contains("homes." + player.getName())) {
			homeAmount = plugin.getLocations()
					.getConfigurationSection("homes." + player.getName())
					.getKeys(false).size();
		} else {
			homeAmount = 0;
		}
		
		Location l = player.getLocation();

		if (args.length == 0) {
			//Without args
			if (plugin.getLocations().contains("homes." + player.getName()
					+ ".default")) {
				homeAmount -= 1;
			}
			
			if (checkMaxHomes(player, homeAmount)) {
				plugin.locationIO.write("homes." + player.getName()
						+ ".default", l);

				player.sendMessage(plugin.getMessage(
						"default-home-set"));
			} else {
				player.sendMessage(plugin.getMessage(
						"max-homes-reached"));
				return false;
			}
			
		} else {
			//With args
			if (plugin.getLocations().contains("homes." + player.getName() + "." + args[0])) {
				homeAmount -= 1;
			}
			
			if (checkMaxHomes(player, homeAmount)) {

				plugin.locationIO.write("homes." + player.getName() + "."
						+ args[0], l);

				player.sendMessage(plugin.getMessage(
						"home-set"));
			} else {
				player.sendMessage(plugin.getMessage(
						"max-homes-reached"));
				return false;
			}
		}
		return true;
	}

	boolean checkMaxHomes(Player player, int homeAmount) {
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

		if (g1 && homeAmount < GROUP1) {
			return true;
		} else {
			if (g2 && homeAmount < GROUP2) {
				return true;
			} else {
				if (g3 && homeAmount < GROUP3) {
					return true;
				} else {
					if (g4 && homeAmount < GROUP4) {
						return true;
					} else {
						if (g5 && homeAmount < GROUP5) {
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
