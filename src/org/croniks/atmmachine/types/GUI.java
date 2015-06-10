package org.croniks.atmmachine.types;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.croniks.atmmachine.core.Main;

public class GUI {

	public static final String TITLE_MENU = ChatColor.BOLD + "ATM - Menu";
	
	public static final Integer DEPOSIT = 1;
	public static final Integer WITHDRAWAL = 7;
	public static final Integer CASH = 10;
	public static final Integer DEBIT = 16;
	
	public static Inventory getMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, TITLE_MENU);

		inv.setItem(DEPOSIT, getDeposit());
		inv.setItem(WITHDRAWAL, getWithdrawal());
		
		inv.setItem(13, getWelcome(player));

		inv.setItem(CASH, getCash(player));
		inv.setItem(DEBIT, getDebit(player));
		
		while (inv.firstEmpty() >= 0) {
			inv.setItem(inv.firstEmpty(), getBars());
		}
		
		return inv;
	}
	
	private static ItemStack getWelcome(Player player) {
		ItemStack item = new ItemStack(Material.BOOK);
		item.addUnsafeEnchantment(CustomEnchant.VOID, 0);
		ItemMeta itemM = item.getItemMeta();
		
		List<String> itemL = new ArrayList<String>();
		itemL.add(ChatColor.GRAY + "This is the ATM Machine. Use");
		itemL.add(ChatColor.GRAY + "this to transfer funds from");
		itemL.add(ChatColor.GRAY + "cash to debit!");

		itemM.setDisplayName(ChatColor.AQUA + "Welcome " + player.getName() + "!");
		itemM.setLore(itemL);
		item.setItemMeta(itemM);
		
		return item;
	}
	
	private static ItemStack getBars() {
		ItemStack item = new ItemStack(Material.IRON_FENCE);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(ChatColor.RESET + "");
		item.setItemMeta(itemM);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	private static ItemStack getDeposit() {
		ItemStack item = new ItemStack(Material.WOOL, 1, (short) 0, (byte) 3);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(ChatColor.AQUA + "Deposit");
		
		List<String> itemL = new ArrayList<String>();
		itemL.add(ChatColor.GRAY + "Deposit money into");
		itemL.add(ChatColor.GRAY + "you bank account!");
		
		itemM.setLore(itemL);
		item.setItemMeta(itemM);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	private static ItemStack getWithdrawal() {
		ItemStack item = new ItemStack(Material.WOOL, 1, (short) 0, (byte) 14);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(ChatColor.AQUA + "Withdrawal");
		
		List<String> itemL = new ArrayList<String>();
		itemL.add(ChatColor.GRAY + "Withdrawal crash from");
		itemL.add(ChatColor.GRAY + "your bank account!");
		
		itemM.setLore(itemL);
		item.setItemMeta(itemM);
		return item;
	}
	
	private static ItemStack getCash(Player player) {
		ItemStack item = new ItemStack(Material.EMERALD);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(ChatColor.AQUA + "Cash");
		
		Double balance = Main.economy.getBalance(player);
		DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
		
		List<String> itemL = new ArrayList<String>();
		itemL.add(ChatColor.GRAY + "Cash: $" + ChatColor.BLUE + df.format(balance));
		
		itemM.setLore(itemL);
		item.setItemMeta(itemM);
		return item;
	}
	
	private static ItemStack getDebit(Player player) {
		ItemStack item = new ItemStack(Material.DIAMOND);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(ChatColor.AQUA + "Debit");
		
		Double balance = Main.economy.bankBalance(player.getName()).balance;
		DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
		
		List<String> itemL = new ArrayList<String>();
		itemL.add(ChatColor.GRAY + "Debit: $" + ChatColor.BLUE + df.format(balance));
		
		itemM.setLore(itemL);
		item.setItemMeta(itemM);
		return item;
	}
	
}
