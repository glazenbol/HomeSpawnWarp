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
import com.homespawnwarp.cmd.TeleportCommand;
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

		cmds.add(new SetHomeCommand(plugin, Permission.SETHOME, true));
		cmds.add(new HomeCommand(plugin, Permission.HOME, true));
		cmds.add(new HomeListCommand(plugin, Permission.HOMELIST, true));
		cmds.add(new DelHomeCommand(plugin, Permission.DELHOME, true));
		cmds.add(new SetSpawnCommand(plugin, Permission.SETSPAWN, false));
		cmds.add(new SpawnCommand(plugin, Permission.SPAWN, true));
		cmds.add(new SetWarpCommand(plugin, Permission.SETWARP, false));
		cmds.add(new WarpCommand(plugin, Permission.WARP, true));
		cmds.add(new WarpListCommand(plugin, Permission.WARPLIST, true));
		cmds.add(new DelWarpCommand(plugin, Permission.DELWARP, false));
		cmds.add(new WarpToCommand(plugin, Permission.WARPTO, true));
		cmds.add(new WarpAcceptCommand(plugin, Permission.WARPACCEPT, true));

		
		//Setup prices
		for (AbstractCommand cur : cmds) {
			if (cur instanceof TeleportCommand) {
				((TeleportCommand) cur).setupPrices();
			}
		}

	}
}
