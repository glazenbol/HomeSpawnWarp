package com.homespawnwarp.util;

import org.bukkit.command.CommandSender;

public class PermissionAgent {

	public static boolean checkPerm(final CommandSender sender,
			final Permission permission, final boolean isDefaultPermitted) {
		return checkPerm(sender, permission, isDefaultPermitted, true, -1, null);
	}

	public static boolean checkPerm(final CommandSender sender,
			final Permission permission, final boolean isDefaultPermitted,
			final boolean sendMessage) {
		return checkPerm(sender, permission, isDefaultPermitted, sendMessage,
				-1, null);
	}

	public static boolean checkPerm(final CommandSender sender,
			final Permission permission, final boolean isDefaultPermitted,
			final boolean sendMessage, int index) {
		return checkPerm(sender, permission, isDefaultPermitted, sendMessage,
				index, null);
	}

	public static boolean checkPerm(final CommandSender sender,
			final Permission permissionE, final boolean isDefaultPermitted,
			final boolean sendMessage, int index, String commandName) {

		String permission = permissionE.toString();

		if (commandName != null) {
			permission = permission + "." + commandName;
		}

		if (index >= 0) {
			permission = permission + index;
		}

		if (isDefaultPermitted) {
			if (sender.hasPermission(permission)
					|| !sender.isPermissionSet(permission)) {
				return true;
			} else {
				if (sendMessage) {
					sender.sendMessage(Tools.getMessage("no-permission"));
				}
				return false;
			}
		} else {
			if (sender.hasPermission(permission)
					|| (!sender.isPermissionSet(permission) && sender.isOp())) {
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
