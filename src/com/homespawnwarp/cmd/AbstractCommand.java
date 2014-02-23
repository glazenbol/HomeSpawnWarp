package com.homespawnwarp.cmd;

import org.bukkit.ChatColor;
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

	protected boolean costsMoney;
	protected double price;

	protected boolean isConsoleSendable;

	abstract boolean doCommand(Player player, CommandSender sender,
			Command cmd, String commandLabel, String[] args);

	public AbstractCommand(final HomeSpawnWarp plugin,
			final String commandPermission, boolean isDefaultPermitted,
			boolean isConsoleSendable) {

		this.plugin = plugin;
		this.commandPermission = commandPermission;
		this.isDefaultPermitted = isDefaultPermitted;
		this.isConsoleSendable = isConsoleSendable;

		costsMoney = false;
	}

	public void setPrice(double price) {
		this.price = price;
		if (price > 0) {
			costsMoney = true;
			//It goes thru here yes
		}
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
					if (!costsMoney || (costsMoney && hasMoney(player))) {
						if (doCommand(player, sender, cmd, commandLabel, args)) {
							takeMoney(player);
						}
					}
				}
			} else {
				sender.sendMessage(Tools.getMessage("cannot-perform"));
			}
		}
		return false;
	}

	protected boolean hasPerm(final CommandSender sender,
			final String permission, final boolean isDefaultPermitted) {
		return hasPerm(sender, permission, isDefaultPermitted, true);
	}

	protected boolean hasPerm(final CommandSender sender,
			final String permission, final boolean isDefaultPermitted,
			final boolean sendMessage) {
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

	protected boolean hasMoney(final Player player) {

		if (price <= 0 || hasPerm(player, "HomeSpawnWarp.nofee", false, false)) {

			return true;

		} else {
			if (plugin.economy.getBalance(player.getName()) >= price) {

				return true;

			} else {

				Tools.getMessage("not-enough-money");
				return false;

			}
		}
	}

	protected void takeMoney(final Player player) {

		if (price <= 0) {
			return;
		}

		if (!hasPerm(player, "HomeSpawnWarp.nofee", false, false)) {

			plugin.economy.withdrawPlayer(player.getName(), price);

			if (price < 2 && price > 0) {
				player.sendMessage(ChatColor.AQUA
						+ plugin.economy.format(price) + " "
						+ plugin.economy.currencyNameSingular()
						+ Tools.getMessage("is-taken-from-account"));
			} else {
				player.sendMessage(ChatColor.AQUA
						+ plugin.economy.format(price) + " "
						+ plugin.economy.currencyNamePlural()
						+ Tools.getMessage("is-taken-from-account"));
			}

		} else {
			return;
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
