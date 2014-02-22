package com.homespawnwarp.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.homespawnwarp.tool.TeleportationType;

public class TimerCompleteEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private TeleportationType type;
	private Player player;
	private Location location;

	public TimerCompleteEvent(final Player player, final Location l,
			final TeleportationType type) {
		this.player = player;
		this.location = l;
		this.type = type;
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
}
