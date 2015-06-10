package org.croniks.atmmachine.events;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.croniks.atmmachine.utils.GeneralUtils;
import org.croniks.atmmachine.utils.GeneralUtils.ATMTask;
import org.croniks.atmmachine.utils.MemoryUtils;

public class PlayerInteract implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Action action = event.getAction();
		
		// CREATE ATM CHECK
		if (player.getGameMode().equals(GameMode.SURVIVAL)) {
			if (MemoryUtils.createContains(player)) {
				if (action.equals(Action.LEFT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
					GeneralUtils.doATMTask(block, player, ATMTask.CREATE);
					event.setCancelled(true);
					MemoryUtils.createRemove(player);
				}
			}
		}
	}
	
}
