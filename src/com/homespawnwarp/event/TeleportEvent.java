package com.homespawnwarp.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.homespawnwarp.tool.TeleportationType;

public class TeleportEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private TeleportationType type;
	private Player player;
	private boolean sendMessage;
	private Location l;
	private double price;

	public TeleportEvent(final Player player, final TeleportationType type,
			final Location l, final boolean sendMessage, double price) {
		this.type = type;
		this.player = player;
		this.l = l;
		this.sendMessage = sendMessage;
		this.price = price;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public TeleportationType getTeleportationType() {
		return type;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean sendMessage() {
		return sendMessage;
	}

	public Location getLocation() {
		return l;
	}
	
	public double getPrice() {
		return price;
	}
}
