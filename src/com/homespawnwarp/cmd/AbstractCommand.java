package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.Tools;

public abstract class AbstractCommand implements CommandExecutor {

	protected HomeSpawnWarp plugin;

	protected String commandPermission;
	protected boolean isDefaultPermitted;

	protected boolean isConsoleSendable;

	abstract public String getName();
	abstract boolean doCommand(Player player, CommandSender sender,
			Command cmd, String commandLabel, String[] args);

	public AbstractCommand(final HomeSpawnWarp plugin,
			final String commandPermission, boolean isDefaultPermitted,
			boolean isConsoleSendable) {
		this.plugin = plugin;
		this.commandPermission = commandPermission;
		this.isDefaultPermitted = isDefaultPermitted;
		this.isConsoleSendable = isConsoleSendable;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player player = null;
		if (isConsoleSendable) {
			doCommand(player, sender, cmd, commandLabel, args);
		} else {
			if (sender instanceof Player) {

				player = (Player) sender;

				if (hasPerm(sender, commandPermission, isDefaultPermitted, true)) {
					//if (MoneyMachine.checkMoney(player, price)) {
						if (doCommand(player, sender, cmd, commandLabel, args)) {
							//@returns wether the command is done or not
						}
					/*} else {
						//Not using this no more
						player.sendMessage(Tools.getMessage("not-enough-money"));
					}*/

				}
			} else {
				sender.sendMessage(Tools.getMessage("cannot-perform"));
			}
		}
		return false;
	}

	public boolean hasPerm(final CommandSender sender, final String permission,
			final boolean isDefaultPermitted) {
		return hasPerm(sender, permission, isDefaultPermitted, true);
	}

	public boolean hasPerm(final CommandSender sender, final String permission,
			final boolean isDefaultPermitted, final boolean sendMessage) {
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

	protected boolean containsIllegalChars(final String s,
			final CommandSender sender) {
		if (s.indexOf(".") != -1) {
			sender.sendMessage(Tools.getMessage("cannot-use-dot"));
			return true;
		}
		return false;
	}
}
