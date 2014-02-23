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

	public TeleportEvent(final Player player, final TeleportationType type,
			final Location l, final boolean sendMessage) {
		this.type = type;
		this.player = player;
		this.l = l;
		this.sendMessage = sendMessage;
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
}
