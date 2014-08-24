package com.homespawnwarp.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportRequest implements Runnable {

	public enum RequestType {
		WARPTO, WARPHERE;
	}

	private Player player;
	private Player targetPlayer;
	private Location thenLocation;
	private RequestType type;
	private static final int maxTime = 60;
	private double borrowedMoney;
	private boolean isFinished = false;

	public TeleportRequest(Player player, Player targetPlayer, RequestType type) {
		this.player = player;
		this.targetPlayer = targetPlayer;
		this.thenLocation = targetPlayer.getLocation();
		this.type = type;
		new Thread(this).run();
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

	public RequestType getType() {
		return type;
	}

	public void finish() {
		isFinished = true;
	}

	@Override
	public void run() {

		if (type == RequestType.WARPHERE) {
			player.sendMessage(Tools.getMessage("warphere-request"));
		}
		if (type == RequestType.WARPTO) {
			player.sendMessage(Tools.getMessage("warpto-request"));
		}

		try {
			Thread.sleep(maxTime * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (!isFinished) {
			MoneyMachine.payMoney(player, borrowedMoney);
		}
	}
}
