package org.croniks.atmmachine.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.croniks.atmmachine.commands.CmdCreate;
import org.croniks.atmmachine.events.BlockBreak;
import org.croniks.atmmachine.events.BlockPlace;
import org.croniks.atmmachine.events.InventoryClick;
import org.croniks.atmmachine.events.PlayerInteract;
import org.croniks.atmmachine.events.PlayerMove;
import org.croniks.atmmachine.types.ATMPlate;

public class Main extends JavaPlugin {
	
	private static File folder;
    public static Economy economy = null;
	
	@Override
	public void onEnable() {
		folder = getDataFolder();
		setupFiles();
		
		DataCon.setupConnection();
		DataCon.setupTables();
		
		setupEconomy();
		ATMPlate.registerPlates();
		
		// Event Register
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new BlockPlace(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new InventoryClick(), this);
		
		// Command Register
		getCommand("atm-create").setExecutor(new CmdCreate());
	}
	
	@Override
	public void onDisable() {
		DataCon.closeConnection();
	}
	
	public static File getFolder() {
		return folder;
	}
	
	private void setupFiles() {
		List<File> folders = new ArrayList<File>();
		folders.add(getFolder());
		for (File folder : folders) {
			if (!folder.exists()) {
				folder.mkdir();
			}
		}
		List<String> files = new ArrayList<String>();
		files.add("config.yml");
		for (String file : files) {
			if (!new File(getDataFolder(), file).exists()) {
				saveResource(file, false);
			}
		}
	}

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

}
