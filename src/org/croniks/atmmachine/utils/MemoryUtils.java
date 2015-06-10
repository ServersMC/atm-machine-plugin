package org.croniks.atmmachine.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.croniks.atmmachine.types.ATMPlate;

public class MemoryUtils {

	private static List<Player> createPlayers = new ArrayList<Player>();
	private static List<Player> deletePlayers = new ArrayList<Player>();
	private static HashMap<Player, ATMPlate> usePlayers = new HashMap<Player, ATMPlate>();
	
	public static void createAdd(Player player) {
		createPlayers.add(player);
	}
	
	public static void createRemove(Player player) {
		createPlayers.remove(player);
	}
	
	public static Boolean createContains(Player player) {
		return createPlayers.contains(player);
	}
	
	public static void deleteAdd(Player player) {
		deletePlayers.add(player);
	}
	
	public static void deleteRemove(Player player) {
		deletePlayers.remove(player);
	}
	
	public static Boolean deleteContains(Player player) {
		return deletePlayers.contains(player);
	}
	
	public static void useAdd(Player player, ATMPlate plate) {
		usePlayers.put(player, plate);
	}
	
	public static void useRemove(Player player) {
		usePlayers.remove(player);
	}
	
	public static ATMPlate useGet(Player player) {
		return usePlayers.get(player);
	}
	
	public static Boolean useContainsPlayer(Player player) {
		return usePlayers.containsKey(player);
	}
	
	public static Boolean useContainsPlate(ATMPlate plate) {
		return usePlayers.containsValue(plate);
	}
	
}
