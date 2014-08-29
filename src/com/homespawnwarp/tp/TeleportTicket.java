package com.homespawnwarp.tp;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

public class TeleportTicket {

	private final Player player;
	private final Message message;
	private final Location location;
	private final boolean useFirework;
	private final double price;
	private int warmup;

	public TeleportTicket(Player p, Message m, Location l, boolean useFirework,
			double price, int warmup) {

		this.player = p;
		this.message = m;
		this.location = l;
		this.useFirework = useFirework;
		this.price = price;
		this.warmup = warmup;
	}

	public void sendFinishMessage() {
		if (message != null) {
			MessageSender.message(message, player);
		}
	}

	public boolean canPlayFirework() {
		return useFirework
				&& PermissionAgent.checkPerm(player, Permission.TELEPORTEFFECT,
						false);
	}

	public void clearWarmup() {
		warmup = 0;
	}

	public int getWarmup() {
		return warmup;
	}

	public Player getPlayer() {
		return player;
	}

	public Location getLocation() {
		return location;
	}

	public Message getMessage() {
		return message;
	}

	public double getPrice() {
		return price;
	}
}
