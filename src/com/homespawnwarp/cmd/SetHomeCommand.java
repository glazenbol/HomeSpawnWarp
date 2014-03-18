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

	int[] homelimit = new int[5];

	public SetHomeCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
		for (int i = 0; i < homelimit.length; i ++) {
			homelimit[i] =Tools.getConfig().getInt("homelimits.group"+ i);
		}
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
				homeAmount--;
			}
		} else {
			homeAmount = 0;
		}

		Location l = player.getLocation();

		if (args.length == 0) {
			// Without args
			if (Tools.getLocations().contains(
					"homes." + player.getName() + ".default")) {
				homeAmount--;
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

	private boolean checkMaxHomes(Player player, int homeAmount) {// TODO
		boolean[] isInGroup = new boolean[homelimit.length];

		if (hasPerm(player, "HomeSpawnWarp.home.UNLIMITED", false, false)) {
			return true;
		}

		for (int i = 0; i < homelimit.length; i++) {
			if (hasPerm(player, "HomeSpawnWarp.home.GROUP" + i, true, false)) {
				isInGroup[i] = true;
			}
		}

		for (int i = 0; i < homelimit.length; i++) {
			if (isInGroup[i] && homeAmount < homelimit[i]) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return "sethome";
	}

}
