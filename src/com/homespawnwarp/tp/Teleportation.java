package com.homespawnwarp.tp;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.EconomyManager;
import com.homespawnwarp.util.FireworkEffectGenerator;
import com.homespawnwarp.util.FireworkEffectPlayer;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

final public class Teleportation {

	private static HomeSpawnWarp plugin;
	private static FireworkEffectPlayer fwep;

	static volatile public HashMap<UUID, TeleportWarmup> teleportWarmups = new HashMap<UUID, TeleportWarmup>();
	static volatile public HashMap<UUID, TeleportRequest> teleportRequests = new HashMap<UUID, TeleportRequest>();

	public static void init(HomeSpawnWarp plugin, boolean useFireworkEffects) {

		Teleportation.plugin = plugin;

		if (useFireworkEffects) {
			fwep = new FireworkEffectPlayer();
		}
	}

	public static boolean createTeleportWarmup(final Player player,
			final Location l, final TeleportationType type, double price,
			int warmup) {
		if (warmup > 0) {
			if (teleportWarmups.containsKey(player.getUniqueId())) {
			} else {
				teleportWarmups.put(player.getUniqueId(), new TeleportWarmup(
						plugin, player, l, type, warmup, price));
				new Thread(teleportWarmups.get(player.getUniqueId())).start();
			}
			return true;
		} else {
			return false;
		}
	}

	public static void createWarpToRequest(final Player player,
			final Player targetPlayer, double borrowedMoney) {

		teleportRequests.put(targetPlayer.getUniqueId(), new WarpToRequest(
				player, targetPlayer, plugin));
	}

	public static void createWarpHereRequest(final Player player,
			final Player targetPlayer, double borrowedMoney) {

		teleportRequests.put(targetPlayer.getUniqueId(), new WarpHereRequest(
				player, targetPlayer, plugin));
	}

	public static void removeRequest(final Player targetPlayer) {
		if (teleportRequests.containsKey(targetPlayer.getUniqueId())) {
			teleportRequests.remove(targetPlayer.getUniqueId());
		}
	}

	public static void removeWarmup(final Player player) {
		teleportWarmups.remove(player.getUniqueId());
	}

	public static void teleportPlayer(final Player player, final Location l,
			final TeleportationType type, double price,
			final boolean sendMessage, final boolean useFirework, int warmup) {

		if (warmup > 0
				&& !PermissionAgent.checkPerm(player, Permission.NOWARMUP,
						false)
				&& createTeleportWarmup(player, l, type, price, warmup)) {

			return;

		}

		finalizeTeleport(player, type, l, sendMessage, price, useFirework);
	}

	public static void teleportPlayer(final Player player, final Location l,
			final TeleportationType type, double price, int warmup) {
		teleportPlayer(player, l, type, price, true, true, warmup);
	}

	public static void finalizeTeleport(Player player, TeleportationType type,
			Location l, boolean sendMessage, double price, boolean useFirework) {
		if (EconomyManager.takeMoney(player, price)) {

			if (!l.getChunk().isLoaded()) {
				l.getChunk().load();
			}

			if (sendMessage) {
				switch (type) {
				case HOME:
					MessageSender.message(Message.TP_TO_HOME, player);
					break;
				case SPAWN:
					MessageSender.message(Message.TP_TO_SPAWN, player);
					break;
				case WARP:
					MessageSender.message(Message.TP_TO_WARP, player);
					break;
				case REQUEST:
					MessageSender.message(Message.TP_TO_REQUEST, player);
				default:
					break;
				}
			}

			if (useFirework
					&& fwep != null
					&& PermissionAgent.checkPerm(player,
							Permission.TELEPORTEFFECT, false)) {
				try {
					fwep.playFirework(player.getWorld(), player.getLocation(),
							FireworkEffectGenerator.randomBurstEffect());
				} catch (Exception e) {
				}
			}

			player.teleport(l); // ALL teleports go thru here
		}
	}

	public static boolean warmupsContainsPlayer(Player player) {
		return teleportWarmups.containsKey(player.getUniqueId());
	}

	public static void cancel(Player player) {
		teleportWarmups.get(player.getUniqueId()).cancel();
		removeRequest(player);// TODO Move, the Teleportation class is too big
	}
}