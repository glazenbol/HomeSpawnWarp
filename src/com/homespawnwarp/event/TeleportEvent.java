package com.homespawnwarp.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.homespawnwarp.tool.TeleportationType;

public class TeleportEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private TeleportationType type;

	public TeleportEvent(TeleportationType type) {
		this.type = type;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public TeleportationType getTeleportationType() {
		return type;
	}
}
