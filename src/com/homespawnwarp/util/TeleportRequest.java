package com.homespawnwarp.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class TeleportRequest extends BukkitRunnable {

	private Player player;
	private Player targetPlayer;
	protected Location thenLocation;
	private static final int maxTime = 10 * 20;
	private double borrowedMoney;
	private boolean isFinished = false;
	
	public abstract void sendMessage(Player player);

	public TeleportRequest(Player player, Player targetPlayer, JavaPlugin plugin) {
		this.player = player;
		this.targetPlayer = targetPlayer;
		sendMessage(targetPlayer);
		runTaskLater(plugin, maxTime);
	}

	public boolean arePlayersOnline() {
		return player.isOnline() && targetPlayer.isOnline();
	}

	public Player getSender() {
		return player;
	}

	public Location getLocation() {
		return thenLocation;
	}

	public void finish() {
		isFinished = true;
	}

	@Override
	public void run() {

		if (!isFinished) {
			MoneyMachine.payMoney(player, borrowedMoney);
		}
	}
}
