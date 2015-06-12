package org.croniks.atmmachine.types;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

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
	public static final String TITLE_DEPOSIT = ChatColor.BLUE + "" + ChatColor.BOLD + "ATM - Deposit";
	public static final String TITLE_WITHDRAWAL = ChatColor.RED + "" + ChatColor.BOLD + "ATM - Withdrawal";

	public static final Integer DEPOSIT = 10;
	public static final Integer WITHDRAWAL = 16;
	public static final Integer CASH = 1;
	public static final Integer DEBIT = 7;

	public static Inventory getPageMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, TITLE_MENU);

		inv.setItem(DEPOSIT, getDeposit());
		inv.setItem(WITHDRAWAL, getWithdrawal());

		inv.setItem(13, getWelcome(player));

		inv.setItem(CASH, getCash(player));
		inv.setItem(DEBIT, getDebit(player));

		while (inv.firstEmpty() != -1) {
			inv.setItem(inv.firstEmpty(), getBars());
		}

		return inv;
	}

	public static Inventory getPageEdit(Player player, GUIAction action) {
		Inventory inv = null;
		if (action.equals(GUIAction.DEPOSIT)) {
			inv = Bukkit.createInventory(null, 27, TITLE_DEPOSIT);
		}
		else if (action.equals(GUIAction.WITHDRAWAL)) {
			inv = Bukkit.createInventory(null, 27, TITLE_WITHDRAWAL);
		}

		inv.setItem(3, getCash(player));
		inv.setItem(5, getDebit(player));

		inv.setItem(10, getNote(1.0, player, action));
		inv.setItem(11, getNote(5.0, player, action));
		inv.setItem(12, getNote(10.0, player, action));
		inv.setItem(13, getNote(20.0, player, action));
		inv.setItem(14, getNote(50.0, player, action));
		inv.setItem(15, getNote(100.0, player, action));
		inv.setItem(16, getNote(500.0, player, action));

		while (inv.firstEmpty() >= 0) {
			inv.setItem(inv.firstEmpty(), getBars());
		}

		return inv;
	}

	private static ItemStack getNote(Double num, Player player, GUIAction action) {
		Economy eco = Main.economy;
		ItemStack item = new ItemStack(Material.PAPER);
		if (action.equals(GUIAction.DEPOSIT)) {
			if (!eco.has(player, num)) {
				item.addUnsafeEnchantment(CustomEnchant.VOID, 0);
			}
		}
		else if (action.equals(GUIAction.WITHDRAWAL)) {
			if (!eco.has(player, null, num)) {
				item.addUnsafeEnchantment(CustomEnchant.VOID, 0);
			}
		}
		
		ItemMeta itemM = item.getItemMeta();

		DecimalFormat df = new DecimalFormat("###,##0.00");

		itemM.setDisplayName(ChatColor.AQUA + "$" + df.format(num) + "!");
		item.setItemMeta(itemM);

		return item;
	}
	
	public static Double parseNote(ItemStack item) {
		Double output = null;
		String serialized = item.getItemMeta().getDisplayName();
		serialized = ChatColor.stripColor(serialized);
		output = new Double(serialized.replace("$", "").replace("!", ""));
		return output;
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
		itemL.add(ChatColor.GRAY + "Withdrawal cash from");
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

		Double balance = Main.economy.getBalance(player, null);
		DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");

		List<String> itemL = new ArrayList<String>();
		itemL.add(ChatColor.GRAY + "Debit: $" + ChatColor.BLUE + df.format(balance));

		itemM.setLore(itemL);
		item.setItemMeta(itemM);
		return item;
	}

	public static class GUIAction {

		public static final GUIAction DEPOSIT = new GUIAction(0);
		public static final GUIAction WITHDRAWAL = new GUIAction(1);

		private Integer ID;

		private GUIAction(Integer id) {
			ID = id;
		}
		
		public static GUIAction parse(String str) {
			GUIAction output = null;
			if (str.equals(TITLE_DEPOSIT)) {
				output = DEPOSIT;
			}
			if (str.equals(TITLE_WITHDRAWAL)) {
				output = WITHDRAWAL;
			}
			return output;
		}

		@Override
		public boolean equals(Object obj) {
			Boolean output = false;
			GUIAction action = (GUIAction) obj;
			if (action.ID == ID) {
				output = true;
			}
			return output;
		}

	}

}
