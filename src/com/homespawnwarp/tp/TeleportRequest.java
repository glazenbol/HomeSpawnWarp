package com.homespawnwarp.tp;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.homespawnwarp.util.EconomyManager;

public abstract class TeleportRequest extends BukkitRunnable {

	private final Player player;
	private final Player targetPlayer;
	protected final double borrowedMoney;
	protected Location thenLocation;
	
	
	private static final int maxTime = 10 * 20;
	private boolean isFinished = false;
	private int warmup;

	static volatile public HashMap<UUID, TeleportRequest> requests = new HashMap<UUID, TeleportRequest>();

	public abstract void sendMessage(Player targetPlayer, Player player);

	public TeleportRequest(Player player, Player targetPlayer, double borrowedMoney, JavaPlugin plugin, int warmup) {
		this.player = player;
		this.targetPlayer = targetPlayer;
		this.borrowedMoney = borrowedMoney;
		this.warmup = warmup;
		sendMessage(targetPlayer, player);
		requests.put(targetPlayer.getUniqueId(), this);
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
			EconomyManager.payMoney(player, borrowedMoney);
		}
		if (requests.containsKey(targetPlayer.getUniqueId())) {
			requests.remove(targetPlayer.getUniqueId());
		}
	}

	public static void removeRequest(Player player) {
		requests.remove(player.getUniqueId());
	}

	public int getWarmup() {
		return warmup;
	}
	
	public static TeleportRequest get(Player player) {
		return requests.get(player.getUniqueId());
	}
}
