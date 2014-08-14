package com.homespawnwarp.util;

public enum Permission {
	
	// CommandPermissions
	SETHOME("HomeSpawnWarp.sethome", true),
	HOME("HomeSpawnWarp.home", true),
	HOMELIST("HomeSpawnWarp.homelist", true),
	DELHOME("HomeSpawnWarp.delhome", true),
	SETSPAWN("HomeSpawnWarp.setspawn", false),
	SPAWN("HomeSpawnWarp.spawn", true),
	SETWARP("HomeSpawnWarp.setwarp", false),
	SETPRICEDWARP("HomeSpawnWarp.setwarp.priced", false),
	WARP("HomeSpawnWarp.warp", true),
	WARPLIST("HomeSpawnWarp.warplist", true),
	DELWARP("HomeSpawnWarp.delwarp", false),
	WARPTO("HomeSpawnWarp.warpto", true),
	WARPACCEPT("HomeSpawnWarp.warpaccept", true),
	// Misc permissions
	UNLIMITED_HOMES("HomeSpawnWarp.home.UNLIMITED", false),
	HOMEGROUP("HomeSpawnWarp.home.GROUP",false),
	PRICES("HomeSpawnWarp.prices.",false),
	
	TELEPORTEFFECT("HomeSpawnWarp.teleporteffect", true),
	
	NOFEE("HomeSpawnWarp.nofee", false),
	NOWARMUP("HomeSpawnWarp.nowarmup", false);
	
	private final String node;
	private final boolean isDefaultPermitted;

	private Permission(final String node, final boolean isDefaultPermitted) {
		
		this.node = node;
		this.isDefaultPermitted = isDefaultPermitted;
	}

	public String getNode() {
		return node;
	}

	public boolean isDefaultPermitted() {
		return isDefaultPermitted;
	}
}
