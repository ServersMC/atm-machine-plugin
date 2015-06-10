package org.croniks.atmmachine.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.croniks.atmmachine.core.DataCon.Plates;

public class ATMPlate {

	// ============================== //
	// =========== STATIC =========== //
	// ============================== //
	
	private static List<ATMPlate> plates = new ArrayList<ATMPlate>();

	public static void registerPlates() {
		plates.clear();
		ResultSet result = Plates.getAll();
		try {
			while (result.next()) {
				plates.add(new ATMPlate(Plates.getId(result), Plates.getLocation(result)));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static Boolean existsPlate(Location loc) {
		Boolean output = false;
		for (ATMPlate plate : plates) {
			if (plate.getLocation().equals(loc)) {
				output = true;
				break;
			}
		}
		return output;
	}
	
	public static ATMPlate getPlate(Location loc) {
		ATMPlate output = null;
		for (ATMPlate plate : plates) {
			if (plate.getLocation().equals(loc)) {
				output = plate;
				break;
			}
		}
		return output;
	}
	
	public static void createPlate(Location location) {
		Plates.insertRow(location);
		registerPlates();
	}
	
	public static void removePlate(ATMPlate plate) {
		Plates.deleteRow(plate.getId());
		registerPlates();
	}
	
	// ============================== //
	// =========== PUBLIC =========== //
	// ============================== //
	
	private Integer ID;
	private Location LOC;
	
	private ATMPlate(Integer id, Location loc) {
		ID = id;
		LOC = loc;
	}
	
	public Integer getId() {
		return ID;
	}
	
	public Location getLocation() {
		return LOC;
	}
	
}
