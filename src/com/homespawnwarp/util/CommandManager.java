package com.homespawnwarp.util;

import java.util.HashSet;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.cmd.AbstractCommand;
import com.homespawnwarp.cmd.DelHomeCommand;
import com.homespawnwarp.cmd.DelWarpCommand;
import com.homespawnwarp.cmd.HomeCommand;
import com.homespawnwarp.cmd.HomeListCommand;
import com.homespawnwarp.cmd.SetHomeCommand;
import com.homespawnwarp.cmd.SetSpawnCommand;
import com.homespawnwarp.cmd.SetWarpCommand;
import com.homespawnwarp.cmd.SpawnCommand;
import com.homespawnwarp.cmd.WarpAcceptCommand;
import com.homespawnwarp.cmd.WarpCommand;
import com.homespawnwarp.cmd.WarpHereCommand;
import com.homespawnwarp.cmd.WarpListCommand;
import com.homespawnwarp.cmd.WarpToCommand;
import com.homespawnwarp.util.perm.Permission;

public class CommandManager {

	private HomeSpawnWarp plugin;

	HashSet<AbstractCommand> cmds = new HashSet<AbstractCommand>();

	public CommandManager(HomeSpawnWarp plugin) {
		this.plugin = plugin;
	}

	public void initCommands() {

		cmds.add(new SetHomeCommand(plugin, Permission.SETHOME, "sethome"));
		cmds.add(new HomeCommand(plugin, Permission.HOME, "home"));
		cmds.add(new HomeListCommand(plugin, Permission.HOMELIST,
				"homelist"));
		cmds.add(new DelHomeCommand(plugin, Permission.DELHOME, "delhome"));
		cmds.add(new SetSpawnCommand(plugin, Permission.SETSPAWN,
				"setspawn"));
		cmds.add(new SpawnCommand(plugin, Permission.SPAWN, "spawn"));
		cmds.add(new SetWarpCommand(plugin, Permission.SETWARP,
				"setwarp"));
		cmds.add(new WarpCommand(plugin, Permission.WARP, "warp"));
		cmds.add(new WarpListCommand(plugin, Permission.WARPLIST,
				"warplist"));
		cmds.add(new DelWarpCommand(plugin, Permission.DELWARP,
				"delwarp"));
		cmds.add(new WarpToCommand(plugin, Permission.WARPTO, "warpto"));
		cmds.add(new WarpHereCommand(plugin, Permission.WARPHERE, "warphere"));
		cmds.add(new WarpAcceptCommand(plugin, Permission.WARPACCEPT,
				"warpaccept"));
	}
}
