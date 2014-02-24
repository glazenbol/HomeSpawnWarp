package com.homespawnwarp.tool;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;

public class MoneyMachine {

	private static Economy economy;

	public static boolean takeMoney(Player player, double price) {

	

		if(checkMoney(player, price, false)){
			// TODO fix
			// plural
			// sentences

			getEconomy().withdrawPlayer(player.getName(), price);// TODO add
			// different
			// cost groups
			// via
			// permissions

			if (price < 2 && price > 0) {
				player.sendMessage(ChatColor.AQUA + getEconomy().format(price)
						+ " " + getEconomy().currencyNameSingular()
						+ Tools.getMessage("is-taken-from-account"));
			} else {
				player.sendMessage(ChatColor.AQUA + getEconomy().format(price)
						+ " " + getEconomy().currencyNamePlural()
						+ Tools.getMessage("is-taken-from-account"));
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkMoney(Player player, double price) {
		return checkMoney(player, price, true);
	}

	public static boolean checkMoney(Player player, double price,boolean sendMessage) {
		boolean canAfford = (getEconomy() == null
				|| price <= 0
				|| HomeSpawnWarp.homeCommand.hasPerm(player,
						"homespawnwarp.nofee", false, false) || getEconomy()
				.getBalance(player.getName()) >= price);
		return canAfford;

	}

	public static Economy getEconomy() {
		return economy;
	}

	public static void setEconomy(Economy economy) {
		MoneyMachine.economy = economy;
	}
}
