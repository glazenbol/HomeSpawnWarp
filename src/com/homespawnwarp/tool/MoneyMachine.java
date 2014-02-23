package com.homespawnwarp.tool;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;

public class MoneyMachine {

	public static Economy economy;

	public static boolean takeMoney(Player player, double price, TeleportationType type) {

		if (price <= 0
				|| HomeSpawnWarp.homeCommand.hasPerm(player,
						"homespawnwarp.nofee", false, false)) {

			return true;

		} else {
			if (economy.getBalance(player.getName()) >= price) { // TODO fix plural sentences

				economy.withdrawPlayer(player.getName(), price);// TODO add different cost groups via permissions

				if (price < 2 && price > 0) {
					player.sendMessage(ChatColor.AQUA + economy.format(price)
							+ " " + economy.currencyNameSingular()
							+ Tools.getMessage("is-taken-from-account"));
				} else {
					player.sendMessage(ChatColor.AQUA + economy.format(price)
							+ " " + economy.currencyNamePlural()
							+ Tools.getMessage("is-taken-from-account"));
				}
				return true;

			} else {
				return false;
			}
		}
	}
}
