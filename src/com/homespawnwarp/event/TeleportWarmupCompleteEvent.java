package com.homespawnwarp.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.homespawnwarp.util.TeleportationType;

public class TeleportWarmupCompleteEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private TeleportationType type;
	private Player player;
	private Location location;
	private double price;

	public TeleportWarmupCompleteEvent(final Player player, final Location l,
			final TeleportationType type, double price) {
		this.player = player;
		this.location = l;
		this.type = type;
		this.price = price;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public TeleportationType getTeleportationType() {
		return type;
	}

	public Player getPlayer() {
		return player;
	}

	public Location getLocation() {
		return location;
	}

	public double getPrice() {
		return price;
	}
}
