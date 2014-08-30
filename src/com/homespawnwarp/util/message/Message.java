package com.homespawnwarp.util.message;

public enum Message {

	//TELEPORTATION
	TP_TO_HOME("teleport-to-home"),
	TP_TO_SPAWN("teleport-to-spawn"),
	TP_TO_WARP("teleport-to-warp"),
	TP_TO_REQUEST("teleporting"),
	//ECONOMY
	NOT_ENOUGH_MONEY("not-enough-money"),
	TOOK_FROM_ACCOUNT_PLURAL("are-taken-from-account"),
	TOOK_FROM_ACCOUNT_SINGULAR("is-taken-from-account"),
	
	WARPTO_REQUEST("warpto-request"),
	WARPHERE_REQUEST("warphere-request"),
	TELEPORT_COMMENCE_IN("teleport-commence-in"),
	NO_PERMISSION("no-permission"),
	TP_CANCELLED_BY_MOVEMENT("teleportation-cancelled-by-move"),
	CANNOT_USE_DOT("cannot-use-dot"),
	CANNOT_PERFORM("cannot-perform"),
	HOME_DELETED("home-deleted"),
	WRONG_HOMENAME("wrong-homename"),
	TOO_FEW_ARGUMENTS("too-few-arguments"),
	WRONG_WARPNAME("wrong-warpname"),
	WARP_DELETED("warp-deleted"),
	NO_HOME_SET("no-home-set"),
	PLAYER_NOT_ONLINE("player-not-online"),
	HOME_SET("home-set"),
	MAX_HOMES_REACHED("max-homes-reached"),
	SET_SPAWN("set-spawn"),
	SET_WARP("set-warp"),
	SET_PRICED_WARP("set-priced-warp"),
	NO_SPAWN_SET("no-spawn-set"),
	ACCEPTED_REQUEST_FROM("you-accepted-request-of"),
	ACCEPTED_YOUR_REQUEST("accepted-your-request"),
	NO_REQUEST("no-request"),
	SENT_WARPTO_REQUEST("sent-warpto-request"),
	SENT_WARPHERE_REQUEST("sent-warphere-request"),
	DOESNT_ALLOW_WARPHERE("player-doesnt-allow-warphere"),
	DOESNT_ALLOW_WARPTO("player-doesnt-allow-warpto");
	
	
	
	private final String path;
	
	private Message(final String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return path;
		
	}
}
