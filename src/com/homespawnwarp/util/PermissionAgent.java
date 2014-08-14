package com.homespawnwarp.util;

import org.bukkit.command.CommandSender;

public class PermissionAgent {

	public static boolean checkPerm(final CommandSender sender,
			final Permission permission) {
		return checkPerm(sender, permission, true);
	}



	public static boolean checkPerm(final CommandSender sender,
			final Permission permission, final boolean sendMessage) {

		String node = permission.getNode();

		if (permission.isDefaultPermitted()) {
			if (sender.hasPermission(node) || !sender.isPermissionSet(node)) {
				return true;
			} else {
				if (sendMessage) {
					sender.sendMessage(Tools.getMessage("no-permission"));
				}
				return false;
			}
		} else {
			if (sender.hasPermission(node)
					|| (!sender.isPermissionSet(node) && sender.isOp())) {
				return true;
			} else {
				if (sendMessage) {
					sender.sendMessage(Tools.getMessage("no-permission"));
				}
				return false;
			} // WORKS!!
		}
	}
	
	public static boolean checkCustomPerm(final CommandSender sender,
			final Permission permission, final boolean sendMessage, int index,
			String commandName, boolean isDefaultPermitted) {

		String node = permission.getNode();

		if (commandName != null) {
			node = node + "." + commandName;
		}

		if (index >= 0) {
			node = node + index;
		}

		if (isDefaultPermitted) {
			if (sender.hasPermission(node) || !sender.isPermissionSet(node)) {
				return true;
			} else {
				if (sendMessage) {
					sender.sendMessage(Tools.getMessage("no-permission"));
				}
				return false;
			}
		} else {
			if (sender.hasPermission(node)
					|| (!sender.isPermissionSet(node) && sender.isOp())) {
				return true;
			} else {
				if (sendMessage) {
					sender.sendMessage(Tools.getMessage("no-permission"));
				}
				return false;
			} // WORKS!!
		}
	}
}
