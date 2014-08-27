package com.homespawnwarp.util;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;

import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

public class EconomyManager {

	private static Economy economy;

	public static void payMoney(Player player, double amount) {
		getEconomy().depositPlayer(player, amount);
	}
	
	public static boolean takeMoney(Player player, double price) {
		return takeMoney(player,price,true);
	}
	
	public static boolean takeMoney(Player player, double price, boolean sendTakenMessage) {
		
		if (canPassWithoutPaying(player, price)) {
			return true;
		}

		if (canAfford(player, price)) {

			getEconomy().withdrawPlayer(player, price);
			if (sendTakenMessage && price > 0) {
				if (price < 2 && price > 0) {
					MessageSender.moneyMessage(Message.TOOK_FROM_ACCOUNT_SINGULAR, player, price);
				} else {
					
					MessageSender.moneyMessage(Message.TOOK_FROM_ACCOUNT_PLURAL, player, price);
				}
			}
			return true;
		} else {
			MessageSender.message(Message.NOT_ENOUGH_MONEY, player);
			return false;
		}
	}

	public static boolean canPassWithoutPaying(Player player, double price) {

		return (getEconomy() == null || price <= 0 || PermissionAgent
				.checkPerm(player, Permission.NOFEE, false));
		
		

	}

	public static boolean canAfford(Player player, double price) {

		return getEconomy().getBalance(player) >= price;

	}

	// Combines canPassWithoutPaying with canAfford
	public static boolean checkMoney(Player player, double price) {
		return canPassWithoutPaying(player, price) || canAfford(player, price);
	}

	public static Economy getEconomy() {
		return economy;
	}

	public static void setEconomy(Economy economy) {
		EconomyManager.economy = economy;
	}

}
