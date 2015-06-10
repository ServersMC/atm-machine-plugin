package org.croniks.atmmachine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.croniks.atmmachine.types.GUI;
import org.croniks.atmmachine.types.GUI.GUIAction;
import org.croniks.atmmachine.types.GUI.Menu;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Integer slot = event.getSlot();
		
		String title = event.getInventory().getTitle();
		if (title.equals(GUI.TITLE_MENU) ||
				title.equals(GUI.TITLE_DEPOSIT) ||
				title.equals(GUI.TITLE_WITHDRAWAL)) {
			event.setCancelled(true);
		}
		
		if (slot >= 0) {
			title = event.getClickedInventory().getTitle();
			if (title.equals(GUI.TITLE_MENU)) {
				if (slot == Menu.DEPOSIT) {
					player.openInventory(GUI.getPageEdit(player, GUIAction.DEPOSIT));
				}
				else if (slot == Menu.WITHDRAWAL) {
					player.openInventory(GUI.getPageEdit(player, GUIAction.WITHDRAWAL));
				}
			}
			if (title.equals(GUI.TITLE_DEPOSIT)) {
				
			}
			if (title.equals(GUI.TITLE_WITHDRAWAL)) {
				
			}
		}
	}
	
}
