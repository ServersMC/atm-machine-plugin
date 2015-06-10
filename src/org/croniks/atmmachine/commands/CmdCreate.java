package org.croniks.atmmachine.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.croniks.atmmachine.utils.MemoryUtils;
import org.croniks.atmmachine.utils.SettingsUtils;

public class CmdCreate implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage(ChatColor.GOLD + "Place or Left click " + ChatColor.WHITE + SettingsUtils.getPlate().name() + ChatColor.GOLD + "!");
			MemoryUtils.createAdd(player);
		}
		else {
			sender.sendMessage(ChatColor.RED + "This is a player only command!");
		}
		return true;
	}

}
