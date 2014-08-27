package com.homespawnwarp.cmd;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

final public class SetHomeCommand extends AbstractCommand {

	int[] homelimit = new int[5];

	public SetHomeCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
		for (int i = 0; i < homelimit.length; i++) {
			homelimit[i] = ConfigIO.getConfig().getInt(
					"homelimits.group" + (i + 1));
		}
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		int homeAmount;

		if (ConfigIO.getLocations().contains("homes." + player.getUniqueId())) {
			Set<String> homeNames = ConfigIO.getLocations()
					.getConfigurationSection("homes." + player.getUniqueId())
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
			if (ConfigIO.getLocations().contains(
					"homes." + player.getUniqueId() + ".default")) {
				homeAmount--;
			}

			if (checkMaxHomes(player, homeAmount)) {
				LocationIO.write("homes." + player.getUniqueId() + ".default",
						l);

				MessageSender.message(Message.HOME_SET, player);
			} else {
				MessageSender.message(Message.MAX_HOMES_REACHED, player);
				return false;
			}

		} else {
			// With args

			if (checkMaxHomes(player, homeAmount)) {
				if (!containsIllegalChars(args[0], sender)) {
					LocationIO.write("homes." + player.getUniqueId() + "."
							+ args[0], l);

					MessageSender.message(Message.HOME_SET, player);
				}

			} else {
				MessageSender.message(Message.MAX_HOMES_REACHED, player);
				return false;
			}
		}
		return true;
	}

	private boolean checkMaxHomes(Player player, int homeAmount) {
		boolean[] isInGroup = new boolean[homelimit.length];

		if (PermissionAgent
				.checkPerm(player, Permission.UNLIMITED_HOMES, false)) {
			return true;
		}

		if (PermissionAgent.checkCustomPerm(player, Permission.HOMEGROUP,
				false, 1, null, true)) {
			isInGroup[0] = true;
		}

		for (int i = 1; i < homelimit.length; i++) {
			if (PermissionAgent.checkCustomPerm(player, Permission.HOMEGROUP,
					false, i + 1, null, false)) {
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

}
