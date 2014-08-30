package com.homespawnwarp.tp;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;

public final class TeleportWarmup extends BukkitRunnable {

	private boolean isCancelled = false;

	private final TeleportTicket ticket;

	static volatile public HashMap<UUID, TeleportWarmup> warmups = new HashMap<UUID, TeleportWarmup>();

	public TeleportWarmup(TeleportTicket ticket, HomeSpawnWarp plugin) {

		this.ticket = ticket;
		UUID id = ticket.getPlayer().getUniqueId();

		if (warmups.containsKey(id)) {
			warmups.get(id).cancel();
		}
		warmups.put(id, this);

		MessageSender.timeMessage(Message.TELEPORT_COMMENCE_IN,
				ticket.getPlayer(), (int) (ticket.getWarmup() / 20));

		this.runTaskLater(plugin, ticket.getWarmup());
	}

	@Override
	public void run() {

		Player player = ticket.getPlayer();

		if (!isCancelled && player.isOnline()) {
			ticket.clearWarmup();
			Teleportation.teleportPlayer(ticket);
		}

		if (warmups.containsKey(player.getUniqueId())) {
			warmups.remove(player.getUniqueId());
		}

	}

	public void cancel() {
		isCancelled = true;
	}

	public static void cancel(Player player) {

		warmups.get(player.getUniqueId()).cancel();
	}

	public static boolean contains(Player player) {
		return warmups.containsKey(player.getUniqueId());
	}
}
