package org.croniks.atmmachine.events;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.croniks.atmmachine.core.Main;
import org.croniks.atmmachine.types.CustomEnchant;
import org.croniks.atmmachine.types.GUI;
import org.croniks.atmmachine.types.GUI.GUIAction;

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
				if (slot == GUI.DEPOSIT) {
					player.openInventory(GUI.getPageEdit(player, GUIAction.DEPOSIT));
				}
				else if (slot == GUI.WITHDRAWAL) {
					player.openInventory(GUI.getPageEdit(player, GUIAction.WITHDRAWAL));
				}
			}
			if (event.getCurrentItem().getType().equals(Material.PAPER)) {
				ItemStack item = event.getCurrentItem();
				Double money = GUI.parseNote(item);
				Economy eco = Main.economy;

				if (title.equals(GUI.TITLE_DEPOSIT)) {
					if (!item.containsEnchantment(CustomEnchant.VOID)) {
						eco.depositPlayer(player, null, money);
						eco.withdrawPlayer(player, money);
					}
				}
				if (title.equals(GUI.TITLE_WITHDRAWAL)) {
					if (!item.containsEnchantment(CustomEnchant.VOID)) {
						eco.depositPlayer(player, money);
						eco.withdrawPlayer(player, null, money);
					}
				}

				event.getInventory().setContents(GUI.getPageEdit(player, GUIAction.parse(title)).getContents());
			}
		}
	}

}
