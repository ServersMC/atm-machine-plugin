package org.croniks.atmmachine.utils;

import java.io.File;

import org.bukkit.Material;
import org.croniks.atmmachine.core.Main;

public class SettingsUtils {

	public static Material getPlate() {
		return Material.IRON_PLATE;
	}
	
	public static File getSQLPlates() {
		return new File(getDataFolder(), "plates.db");
	}
	
	public static File getDataFolder() {
		return Main.getFolder();
	}
	
	public static String getSQLHost() {
		return "127.0.0.1";
	}
	
	public static String getSQLDataBase() {
		return "minecraft";
	}
	
	public static String getSQLUsername() {
		return "root";
	}
	
	public static String getSQLPassword() {
		return "";
	}
	
}
