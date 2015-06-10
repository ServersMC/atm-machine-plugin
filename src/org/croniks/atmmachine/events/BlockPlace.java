package org.croniks.atmmachine.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.croniks.atmmachine.utils.GeneralUtils;
import org.croniks.atmmachine.utils.MemoryUtils;
import org.croniks.atmmachine.utils.GeneralUtils.ATMTask;

public class BlockPlace implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();
		
		// CREATE ATM CHECK
		if (MemoryUtils.createContains(player)) {
			GeneralUtils.doATMTask(block, player, ATMTask.CREATE);
			MemoryUtils.createRemove(player);
		}
	}
	
}
