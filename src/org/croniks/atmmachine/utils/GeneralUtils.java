package org.croniks.atmmachine.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.croniks.atmmachine.types.ATMPlate;

public class GeneralUtils {

	public static void doATMTask(Block block, Player player, ATMTask action) {
		if (block.getType().equals(SettingsUtils.getPlate())) {
			if (!ATMPlate.existsPlate(block.getLocation())) {
				// NOT EXISTS
				if (action.equals(ATMTask.CREATE)) {
					player.sendMessage(ChatColor.GREEN + "This plate is now registered!");
					ATMPlate.createPlate(block.getLocation());
				}
				else if (action.equals(ATMTask.REMOVE)) {
					player.sendMessage(ChatColor.RED + "There is no plate here!");
				}
			}
			else {
				// EXISTS
				ATMPlate plate = ATMPlate.getPlate(block.getLocation());
				if (action.equals(ATMTask.CREATE)) {
					player.sendMessage(ChatColor.RED + "A plate already exists here!");
				}
				else if (action.equals(ATMTask.REMOVE)) {
					player.sendMessage(ChatColor.GREEN + "This plate is no longer registered!");
					ATMPlate.removePlate(plate);
				}
			}
		}
		else {
			player.sendMessage(ChatColor.RED + "This is not a " + ChatColor.WHITE + SettingsUtils.getPlate() + ChatColor.RED + "!");
		}
	}
	
	public static String serializeBlockLocation(Location loc) {
		String output = "";
		output += loc.getWorld().getName() + ",";
		output += loc.getBlockX() + ",";
		output += loc.getBlockY() + ",";
		output += loc.getBlockZ();
		return output;
	}
	
	public static Location parseBlockLocation(String string) {
		String[] vars = string.split(",");
		World world = Bukkit.getWorld(vars[0]);
		Double x = new Double(vars[1]);
		Double y = new Double(vars[2]);
		Double z = new Double(vars[3]);
		Location loc = new Location(world, x, y, z);
		return loc;
	}

	public static void knockbackFromBlock(Block block, Player player) {
		Vector v = new Vector(0, 0, 0);
		Float m = -1.25F;
		if (!block.getRelative(1, 0, 0).isEmpty()) {
			v = new Vector(m, 0, 0);
		}
		if (!block.getRelative(-1, 0, 0).isEmpty()) {
			v = new Vector(-m, 0, 0);
		}
		if (!block.getRelative(0, 0, 1).isEmpty()) {
			v = new Vector(0, 0, m);
		}
		if (!block.getRelative(0, 0, -1).isEmpty()) {
			v = new Vector(0, 0, -m);
		}
		player.setVelocity(v);
	}
	
	public static class ATMTask {

		public static final ATMTask CREATE = new ATMTask(0);
		public static final ATMTask REMOVE = new ATMTask(1);
		
		private Integer ID;
		
		public ATMTask(Integer id) {
			ID = id;
		}
		
		@Override
		public boolean equals(Object obj) {
			Boolean output = false;
			ATMTask task = (ATMTask) obj;
			if (task.ID == ID) {
				output = true;
			}
			return output;
		}
		
	}
	
}