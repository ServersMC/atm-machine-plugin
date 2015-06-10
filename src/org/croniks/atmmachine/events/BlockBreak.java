package org.croniks.atmmachine.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.croniks.atmmachine.types.ATMPlate;
import org.croniks.atmmachine.utils.GeneralUtils;
import org.croniks.atmmachine.utils.MemoryUtils;
import org.croniks.atmmachine.utils.GeneralUtils.ATMTask;

public class BlockBreak implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		// CREATE ATM CHECK
		if (player.getGameMode().equals(GameMode.CREATIVE)) {
			if (MemoryUtils.createContains(player)) {
				GeneralUtils.doATMTask(block, player, ATMTask.CREATE);
				event.setCancelled(true);
				MemoryUtils.createRemove(player);
			}
		}
		
		// ATM EXISTS CHECK
		if (ATMPlate.existsPlate(block.getLocation())) {
			if (!event.isCancelled()) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You cannot break this block!");
			}
		}
	}
	
}
