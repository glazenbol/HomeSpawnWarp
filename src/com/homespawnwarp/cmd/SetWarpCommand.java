package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

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

			MessageSender.message(Message.TOO_FEW_ARGUMENTS, player);
			return false;
		}
		return true;
	}

	private void writeWarp(Player player, String warpName, double customPrice) {
		if (customPrice <= 0){
			LocationIO.write("warps." + warpName, player.getLocation());


			MessageSender.message(Message.SET_WARP, player);
		} else {
			LocationIO.write("warps." + warpName, player.getLocation(), customPrice);

			MessageSender.message(Message.SET_PRICED_WARP, player);
		}
	}

}
