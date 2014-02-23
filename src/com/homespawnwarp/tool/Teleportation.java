package com.homespawnwarp.tool;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

final public class Teleportation {

	private static boolean useWarmups = false;
	private static int homeWarmup;
	private static int spawnWarmup;
	private static int warpWarmup;
	private static int requestWarmup;

	static private HashMap<String, TeleportWarmup> teleportWarmups = new HashMap<String, TeleportWarmup>();
	static private HashMap<String, TeleportRequest> teleportRequests = new HashMap<String, TeleportRequest>();

	public static boolean createTeleportWarmup(final Player player,
			final Location l, final TeleportationType type) {
		if (useWarmups) {
			if (teleportWarmups.containsKey(player.getName())) {
				return true;
			} else {
				switch (type) {
				case HOME:
					if (homeWarmup <= 0) {
						return false;
					} else {
						teleportWarmups
								.put(player.getName(), new TeleportWarmup(
										player, l, type, homeWarmup));
					}
					break;
				case SPAWN:
					if (spawnWarmup <= 0) {
						return false;
					} else {
						teleportWarmups
								.put(player.getName(), new TeleportWarmup(
										player, l, type, spawnWarmup));
					}
					break;
				case WARP:
					if (warpWarmup <= 0) {
						return false;
					} else {
						teleportWarmups
								.put(player.getName(), new TeleportWarmup(
										player, l, type, warpWarmup));
					}
					break;
				case REQUEST:
					if (requestWarmup <= 0) {
						return false;
					} else {
						teleportWarmups.put(player.getName(),
								new TeleportWarmup(player, l, type,
										requestWarmup));
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
		useWarmups = (homeWarmup > 0 || spawnWarmup > 0 || warpWarmup > 0 && requestWarmup > 0);
		Teleportation.homeWarmup = (int) homeWarmup;
		Teleportation.spawnWarmup = (int) spawnWarmup;
		Teleportation.warpWarmup = (int) warpWarmup;
		Teleportation.requestWarmup = (int) requestWarmup;
	}

	public static void createRequest(final Player player,
			final Player targetPlayer) {
		teleportRequests.put(targetPlayer.getName(), new TeleportRequest(
				player, targetPlayer));
		new Thread(teleportRequests.get(targetPlayer.getName())).start();
	}

	public static void removeRequest(final Player targetPlayer) {
		teleportRequests.remove(targetPlayer.getName());
	}

	public static void removeWarmup(final Player player) {
		teleportWarmups.remove(player.getName());
	}

	public static void teleportPlayer(final Player player, final Location l,
			final TeleportationType type, final boolean sendMessage,
			final boolean useWarmup) {

		if ((!player.hasPermission("HomeSpawnWarp.nowarmup") || !player.isOp())
				&& useWarmup) {
			if (createTeleportWarmup(player, l, type)) {
				return;
			}
		}

		if (!l.getChunk().isLoaded()) {
			l.getChunk().load();
		}

		if (sendMessage) {
			switch (type) {
			case HOME:
				player.sendMessage(Tools.getMessage("teleport-to-home"));
				break;
			case SPAWN:
				player.sendMessage(Tools.getMessage("teleport-to-spawn"));
				break;
			case WARP:
				player.sendMessage(Tools.getMessage("teleport-to-warp"));
				break;
			default:
				break;
			}
		}

		player.teleport(l);// TODO HERE1
	}

	public static void teleportPlayer(final Player player, final Location l,// default
																			// teleporting
			final TeleportationType type) {
		teleportPlayer(player, l, type, true, true);
	}

	public static boolean acceptRequest(Player player) {

		if (teleportRequests.containsKey(player.getName())
				&& teleportRequests.get(player.getName()).arePlayersOnline()) {

			player.sendMessage(Tools.getMessage("teleport-to-player")
					+ teleportRequests.get(player.getName()).getSender()
							.getName());

			teleportPlayer(teleportRequests.get(player.getName()).getSender(),
					player.getLocation(), TeleportationType.REQUEST);

			return true;
		} else {
			player.sendMessage(Tools.getMessage("no-request"));
		}
		return false;
	}

	public static boolean warmupsContainsPlayer(Player player) {
		return teleportWarmups.containsKey(player.getName());
	}

	public static void cancel(Player player) {
		teleportWarmups.get(player.getName()).cancel();
	}
}