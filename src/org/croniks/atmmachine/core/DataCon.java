package org.croniks.atmmachine.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;
import org.croniks.atmmachine.utils.GeneralUtils;
import org.croniks.atmmachine.utils.SettingsUtils;

public class DataCon {

	public static Connection PLATE;
	public static final String TABLE_PLATES = "plates";
	
	public static class Plates {
		
		public static final String COL_ID = "id";
		public static final String COL_LOCATION = "location";

		public static void deleteRow(Integer id) {
			DataCon.execute("DELETE FROM `" + TABLE_PLATES + "` WHERE (`" + COL_ID + "` = '" + id + "')");
			System.out.println("DELETE FROM `" + TABLE_PLATES + "` WHERE (`" + COL_ID + "` = '" + id + "')");
		}

		public static void insertRow(Location location) {
			String sql = "INSERT INTO `" + TABLE_PLATES + "` (";
			String temp = ") VALUES (";
			if (location != null) { sql = sql + ", `" + COL_LOCATION + "`"; temp = temp + ", '" + GeneralUtils.serializeBlockLocation(location) + "'"; }
			sql = sql.replaceFirst(", ", "") + temp.replaceFirst(", ", "") + ");";
			System.out.println(sql);
			execute(sql);
		}
		
		public static ResultSet getAll() {
			return query("SELECT * FROM `" + TABLE_PLATES + "`");
		}
		
		public static Integer getId(ResultSet result) throws SQLException {
			return result.getInt(COL_ID);
		}
		
		public static Location getLocation(ResultSet result) throws SQLException {
			return GeneralUtils.parseBlockLocation(result.getString(COL_LOCATION));
		}
		
		public class Update {
			
			private Integer ID;
			private String LOCATION;
			
			public Update(Integer id) {
				ID = id;
			}
			
			public void setLocation(Location loc) {
				LOCATION = GeneralUtils.serializeBlockLocation(loc);
			}
			
			public void executeUpdate() {
				String sql = "UPDATE `" + TABLE_PLATES + "` SET ";
				if (LOCATION != null) { sql = sql + ", `" + COL_LOCATION + "`='" + LOCATION + "'"; }
				sql = sql.replaceFirst(", ", "");
				sql = sql + " WHERE (`" + COL_ID + "` = '" + ID + "');";
				execute(sql);
			}
			
		}
		
	}
	
	public static void setupConnection() {
		try {
			PLATE = DriverManager.getConnection("jdbc:mysql://" + SettingsUtils.getSQLHost() + "/" + SettingsUtils.getSQLDataBase(),
					SettingsUtils.getSQLUsername(),
					SettingsUtils.getSQLPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection() {
		try {
			PLATE.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setupTables() {
		execute("CREATE TABLE IF NOT EXISTS `" + TABLE_PLATES + "` ("
				+ "`" + Plates.COL_ID + "` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "`" + Plates.COL_LOCATION + "` VARCHAR(50)"
				+ ");");
	}
	
	private static synchronized ResultSet query(String sql) {
		ResultSet output = null;
		try {
			output = PLATE.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	private static synchronized void execute(String sql) {
		try {
			PLATE.createStatement().execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
