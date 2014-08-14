package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.Permission;
import com.homespawnwarp.util.PermissionAgent;
import com.homespawnwarp.util.Tools;

final public class SetWarpCommand extends AbstractCommand {

	public SetWarpCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (args.length != 0) {
			if (!containsIllegalChars(args[0], sender)) {
				if (args.length == 1) {

					writeWarp(player, args[0],0);

				} else {
					if (PermissionAgent.checkPerm(player,
							Permission.SETPRICEDWARP)) {

						double customPrice;
						try {
							
							customPrice = Double.parseDouble(args[1]);
							writeWarp(player, args[0], customPrice);
							
						} catch (NumberFormatException e) {
							//on error do standard warp
							writeWarp(player, args[0],0);
						}
					}// maybe later a custom no permission message
				}
			}

		} else {
			player.sendMessage(Tools.getMessage("too-few-arguments"));
			return false;
		}
		return true;
	}

	private void writeWarp(Player player, String warpName, double customPrice) {
		if (customPrice <= 0){
			LocationIO.write("warps." + warpName, player.getLocation());

			player.sendMessage(Tools.getMessage("set-warp"));
		} else {
			LocationIO.write("warps." + warpName, player.getLocation(), customPrice);

			player.sendMessage(Tools.getMessage("set-priced-warp"));
		}
	}

}
