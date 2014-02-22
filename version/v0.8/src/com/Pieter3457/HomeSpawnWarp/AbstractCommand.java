package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractCommand implements CommandExecutor {

	HomeSpawnWarp plugin;

	String commandPermission;
	boolean isDefaultPermitted;

	boolean isConsoleSendable;

	boolean commandRequiresItems;

	ItemStack item;
	int amount;

	abstract boolean doCommand(Player player, CommandSender sender,
			Command cmd, String commandLabel, String[] args);

	public AbstractCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {

		commandRequiresItems = false;

		this.plugin = plugin;
		this.commandPermission = commandPermission;
		this.isDefaultPermitted = isDefaultPermitted;
		this.isConsoleSendable = isConsoleSendable;
	}

	public AbstractCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable,
			ItemStack item, int amount) {

		commandRequiresItems = true;

		this.plugin = plugin;
		this.commandPermission = commandPermission;
		this.isDefaultPermitted = isDefaultPermitted;
		this.isConsoleSendable = isConsoleSendable;

		this.item = item;
		this.amount = amount;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player player = null;
		if (isConsoleSendable) {

			if (!plugin.leightWeightMode) {
				if (doCommand(player, sender, cmd, commandLabel, args)) {
					plugin.saveLocations();
				}
			} else {
				doCommand(player, sender, cmd, commandLabel, args);
			}

		} else {
			if (sender instanceof Player) {

				player = (Player) sender;

				if (hasPerm(sender, commandPermission, isDefaultPermitted, true)) {
					if (commandRequiresItems && takeItem(player, item, amount)
							|| !commandRequiresItems) {

						if (!plugin.leightWeightMode) {
							if (doCommand(player, sender, cmd, commandLabel,
									args)) {
								plugin.saveLocations();
							}
						} else {
							doCommand(player, sender, cmd, commandLabel, args);
						}

					}
				}
			} else {
				sender.sendMessage(plugin.getMessage("cannot-perform"));
			}
		}

		return false;
	}

	boolean takeItem(Player player, ItemStack item, int amount) {

		if (item == null && amount == 0) {
			return true;
		}

		Inventory i = player.getInventory();
		if (i.contains(item, amount)) {
			i.remove(item);
			return true;
		} else {
			player.sendMessage(plugin.getMessage("not-enough-items"));
			return false;
		}
	}

	boolean hasPerm(CommandSender sender, String permission,
			boolean isDefaultPermitted, boolean sendMessages) {
		if (isDefaultPermitted) {
			if (sender.hasPermission(permission)
					|| !sender.isPermissionSet(permission)) {
				return true;
			} else {
				if (sendMessages) {
					sender.sendMessage(plugin.getMessages()
							.getString("no-permission"));
				}
				return false;
			}
		} else {
			if (sender.hasPermission(permission)) {
				return true;
			} else {
				if (sendMessages) {
					sender.sendMessage(plugin.getMessages()
							.getString("no-permission"));
				}
				return false;
			}
		}
	}
}
