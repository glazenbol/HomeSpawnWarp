package com.homespawnwarp.tp;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.EconomyManager;
import com.homespawnwarp.util.FireworkEffectGenerator;
import com.homespawnwarp.util.FireworkEffectPlayer;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

final public class Teleportation {

	private static HomeSpawnWarp plugin;
	private static FireworkEffectPlayer fwep;


	public static void init(HomeSpawnWarp plugin, boolean useFireworkEffects) {

		Teleportation.plugin = plugin;

		if (useFireworkEffects) {
			fwep = new FireworkEffectPlayer();
		}
	}

	private static boolean createTeleportWarmup(TeleportTicket ticket) {
		if (ticket.getWarmup() > 0
				&& !PermissionAgent.checkPerm(ticket.getPlayer(),
						Permission.NOWARMUP, false)) {
			
			new TeleportWarmup(ticket, plugin);
			return true;
		} else {
			return false;
		}
	}



	public static void teleportPlayer(TeleportTicket ticket) {

		if (createTeleportWarmup(ticket)) {

			return;

		} else {

			finalizeTeleport(ticket);
		}
	}

	public static void finalizeTeleport(TeleportTicket ticket) {

		Player player = ticket.getPlayer();
		Location location = ticket.getLocation();

		if (EconomyManager.takeMoney(player, ticket.getPrice())) {

			if (!location.getChunk().isLoaded()) {
				location.getChunk().load();
			}

			ticket.sendFinishMessage();

			if (ticket.canPlayFirework() && fwep != null) {
				try {
					fwep.playFirework(player.getWorld(), player.getLocation(),
							FireworkEffectGenerator.randomBurstEffect());
				} catch (Exception e) {
				}
			}

			player.teleport(location);
		}
	}
}