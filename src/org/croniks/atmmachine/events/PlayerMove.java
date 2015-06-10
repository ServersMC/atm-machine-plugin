package org.croniks.atmmachine.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.croniks.atmmachine.types.ATMPlate;
import org.croniks.atmmachine.types.GUI;
import org.croniks.atmmachine.utils.GeneralUtils;
import org.croniks.atmmachine.utils.MemoryUtils;
import org.croniks.atmmachine.utils.SettingsUtils;

public class PlayerMove implements Listener {

	private List<Player> players = new ArrayList<Player>();
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Block block = player.getLocation().getBlock();
		Location loc = block.getLocation();
		
		if (block.getType().equals(SettingsUtils.getPlate())) {
			if (!players.contains(player)) {
				if (ATMPlate.existsPlate(loc)) {
					ATMPlate plate = ATMPlate.getPlate(loc);
					if (MemoryUtils.useContainsPlate(plate)) {
						if (!MemoryUtils.useContainsPlayer(player)) {
							GeneralUtils.knockbackFromBlock(block, player);
							player.sendMessage(ChatColor.RED + "This ATM is in use!");
							players.add(player);
						}
					}
					else {
						MemoryUtils.useAdd(player, plate);
						player.openInventory(GUI.getPageMenu(player));
					}
				}
			}
		}
		else {
			if (players.contains(player)) {
				players.remove(player);
			}
			if (MemoryUtils.useContainsPlayer(player)) {
				MemoryUtils.useRemove(player);
			}
		}
	}
	
}
