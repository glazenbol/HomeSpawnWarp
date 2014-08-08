package com.homespawnwarp.util;

import java.util.HashSet;

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
import com.homespawnwarp.cmd.WarpListCommand;
import com.homespawnwarp.cmd.WarpToCommand;
import com.homespawnwarp.plugin.HomeSpawnWarp;

public class CommandManager {

	private HomeSpawnWarp plugin;

	HashSet<AbstractCommand> cmds = new HashSet<AbstractCommand>();

	public CommandManager(HomeSpawnWarp plugin) {
		this.plugin = plugin;
	}

	public void initCommands() {

		cmds.add(new SetHomeCommand(plugin, Permission.SETHOME, true, "sethome"));
		cmds.add(new HomeCommand(plugin, Permission.HOME, true, "home"));
		cmds.add(new HomeListCommand(plugin, Permission.HOMELIST, true,
				"homelist"));
		cmds.add(new DelHomeCommand(plugin, Permission.DELHOME, true, "delhome"));
		cmds.add(new SetSpawnCommand(plugin, Permission.SETSPAWN, false,
				"setspawn"));
		cmds.add(new SpawnCommand(plugin, Permission.SPAWN, true, "spawn"));
		cmds.add(new SetWarpCommand(plugin, Permission.SETWARP, false,
				"setwarp"));
		cmds.add(new WarpCommand(plugin, Permission.WARP, true, "warp"));
		cmds.add(new WarpListCommand(plugin, Permission.WARPLIST, true,
				"warplist"));
		cmds.add(new DelWarpCommand(plugin, Permission.DELWARP, false,
				"delwarp"));
		cmds.add(new WarpToCommand(plugin, Permission.WARPTO, true, "warpto"));
		cmds.add(new WarpAcceptCommand(plugin, Permission.WARPACCEPT, true,
				"warpaccept"));
	}
}
