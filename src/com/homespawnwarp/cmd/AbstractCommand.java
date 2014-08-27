package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

public abstract class AbstractCommand implements CommandExecutor {

	protected HomeSpawnWarp plugin;
	
	protected Permission commandPermission;
	public String name;

	abstract boolean doCommand(Player player, CommandSender sender,
			Command cmd, String commandLabel, String[] args);

	public AbstractCommand(final HomeSpawnWarp plugin,
			final Permission commandPermission, String name) {
		this.plugin = plugin;
		this.commandPermission = commandPermission;
		this.name = name;
		plugin.getCommand(name).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player player = null;
		if (this instanceof ConsoleSendable) {
			doCommand(player, sender, cmd, commandLabel, args);
		} else {
			if (sender instanceof Player) {

				player = (Player) sender;

				if (PermissionAgent.checkPerm(sender, commandPermission, true)) {
					// if (MoneyMachine.checkMoney(player, price)) {
					if (doCommand(player, sender, cmd, commandLabel, args)) {
						// @returns whether the command is done or not
					}
					/*
					 * } else { //Not using this no more
					 * player.sendMessage(Tools.getMessage("not-enough-money"));
					 * }
					 */

				}
			} else {
				MessageSender.message(Message.CANNOT_PERFORM, sender);
			}
		}
		return false;
	}

	protected boolean containsIllegalChars(final String s,
			final CommandSender sender) {
		if (s.indexOf(".") != -1) {
			MessageSender.message(Message.CANNOT_USE_DOT, sender);
			return true;
		}
		return false;
	}
}
