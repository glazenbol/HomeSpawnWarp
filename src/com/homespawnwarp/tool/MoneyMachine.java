package com.homespawnwarp.tool;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;

public class MoneyMachine {

	private static Economy economy;

	public static boolean takeMoney(Player player, double price) {

		if (canPassWithoutPaying(player, price)) {
			return true;
		}
		
		if (canAfford(player, price)) {

			getEconomy().withdrawPlayer(player.getName(), price);
			if (price > 0) {
				if (price < 2 && price > 0) {
					player.sendMessage(ChatColor.AQUA
							+ getEconomy().format(price) + " "
							+ getEconomy().currencyNameSingular()
							+ Tools.getMessage("is-taken-from-account"));
				} else {
					player.sendMessage(ChatColor.AQUA
							+ getEconomy().format(price) + " "
							+ getEconomy().currencyNamePlural()
							+ Tools.getMessage("are-taken-from-account"));
				}
			}
			return true;
		} else {
			player.sendMessage(Tools.getMessage("not-enough-money"));
			return false;
		}
	}

	public static boolean canPassWithoutPaying(Player player, double price) {
		
		return (getEconomy() == null || price <= 0 || HomeSpawnWarp.homeCommand
				.hasPerm(player, "homespawnwarp.nofee", false, false));

	}

	public static boolean canAfford(Player player, double price) {

		return getEconomy().getBalance(player.getName()) >= price;

	}
	
	
	// Combines canPassWithoutPaying with canAfford
	public static boolean checkMoney(Player player, double price) {
		return canPassWithoutPaying(player, price) || canAfford(player, price);
	}

	public static Economy getEconomy() {
		return economy;
	}

	public static void setEconomy(Economy economy) {
		MoneyMachine.economy = economy;
	}

}
