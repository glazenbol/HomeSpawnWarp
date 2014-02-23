package com.homespawnwarp.tool;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;

public class MoneyMachine {// TODO make this the only money taking device
	
	public static Economy economy;
	public static double sethome, home, homelist, delhome, setspawn, spawn,
			setwarp, warp, warplist, delwarp, warpto, warpaccept;
	
	
	public static boolean takeMoney(Player player,
			TeleportationType type) {
		if player(HomeSpawnWarp.)// just a random command
		switch (type) {
		case HOME:
			break;
		case REQUEST:
			break;
		case SPAWN:
			break;
		case WARP:
			economy.withdrawPlayer()
			break;
		}
		
		return false;
	}
}
