package com.homespawnwarp.tool;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.event.TimerCompleteEvent;
import com.homespawnwarp.plugin.HomeSpawnWarp;

public final class TeleportWarmup implements Runnable {

	private int warmup;
	private TeleportationType type;
	private Player player;
	private Location l;
	private boolean isCancelled = false;
	private double price;

	public TeleportWarmup(Player player, Location l, TeleportationType type,
			int warmup, double price) {
		this.warmup = warmup;
		this.player = player;
		this.l = l;
		this.type = type;
		this.price = price;
	}

	@Override
	public void run() {
		player.sendMessage(Tools.getMessage("teleport-commence-in")
				+ (int) (warmup / 1000) + Tools.getMessage("seconds"));

		try {
			Thread.sleep(warmup);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (!isCancelled) {
			HomeSpawnWarp.plugin.getServer().getPluginManager()
					.callEvent(new TimerCompleteEvent(player, l, type, price));
		}
		Teleportation.removeWarmup(player);
	}
	
	public TeleportationType getType() {
		return type;
	}

	public void cancel() {
		isCancelled = true;
	}
}
