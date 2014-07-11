package com.homespawnwarp.tool;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.event.TeleportEvent;

final public class Teleportation extends Tool {

	private static boolean useWarmups = false;
	private static int homeWarmup;
	private static int spawnWarmup;
	private static int warpWarmup;
	private static int requestWarmup;

	static volatile public HashMap<String, TeleportWarmup> teleportWarmups = new HashMap<String, TeleportWarmup>();
	static volatile public HashMap<String, TeleportRequest> teleportRequests = new HashMap<String, TeleportRequest>();

	public static boolean createTeleportWarmup(final Player player,
			final Location l, final TeleportationType type, double price) {
		if (useWarmups) {
			if (teleportWarmups.containsKey(player.getName())) {
				return true;
			} else {
				switch (type) {
				case HOME:
					if (homeWarmup <= 0) {
						return false;
					} else {
						teleportWarmups.put(player.getName(),
								new TeleportWarmup(plugin, player, l, type, homeWarmup,
										price));
					}
					break;
				case SPAWN:
					if (spawnWarmup <= 0) {
						return false;
					} else {
						teleportWarmups.put(player.getName(),
								new TeleportWarmup(plugin, player, l, type,
										spawnWarmup, price));
					}
					break;
				case WARP:
					if (warpWarmup <= 0) {
						return false;
					} else {
						teleportWarmups.put(player.getName(),
								new TeleportWarmup(plugin, player, l, type, warpWarmup,
										price));
					}
					break;
				case REQUEST:
					if (requestWarmup <= 0) {
						return false;
					} else {
						teleportWarmups.put(player.getName(),
								new TeleportWarmup(plugin, player, l, type,
										requestWarmup, price));
					}
					break;
				}
				new Thread(teleportWarmups.get(player.getName())).start();
				return true;
			}
		} else {
			return false;
		}
	}

	public static boolean usesWarmup() {
		return useWarmups;
	}

	public static void setWarmups(double homeWarmup, double spawnWarmup,
			double warpWarmup, double requestWarmup) {
		useWarmups = (homeWarmup > 0 || spawnWarmup > 0 || warpWarmup > 0
				&& requestWarmup > 0);
		Teleportation.homeWarmup = (int) homeWarmup;
		Teleportation.spawnWarmup = (int) spawnWarmup;
		Teleportation.warpWarmup = (int) warpWarmup;
		Teleportation.requestWarmup = (int) requestWarmup;
	}

	public static void createRequest(final Player player,
			final Player targetPlayer, double price) {
		teleportRequests.put(targetPlayer.getName(), new TeleportRequest(
				player, targetPlayer));
		new Thread(teleportRequests.get(targetPlayer.getName())).start();
	}

	public static void removeRequest(final Player targetPlayer) {
		if (teleportRequests.containsKey(targetPlayer.getName())) {
			teleportRequests.remove(targetPlayer.getName());
		}
	}

	public static void removeWarmup(final Player player) {
		teleportWarmups.remove(player.getName());
	}

	public static void teleportPlayer(final Player player, final Location l,
			final TeleportationType type, double price,
			final boolean sendMessage, final boolean useWarmup) {

		if ((!player.hasPermission("HomeSpawnWarp.nowarmup") || !player.isOp())
				&& useWarmup) {
			if (createTeleportWarmup(player, l, type, price)) {
				return;
			}
		}

		plugin.getServer()
				.getPluginManager()
				.callEvent(
						new TeleportEvent(player, type, l, sendMessage, price));
	}

	public static void teleportPlayer(final Player player, final Location l,// default
																			// teleporting
			final TeleportationType type, double price) {
		teleportPlayer(player, l, type, price, true, true);
	}

	public static boolean warmupsContainsPlayer(Player player) {
		return teleportWarmups.containsKey(player.getName());
	}

	public static void cancel(Player player) {
		teleportWarmups.get(player.getName()).cancel();
		removeRequest(player);// THIS IS A BIT TRICKY
	}
}