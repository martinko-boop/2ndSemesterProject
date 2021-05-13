package databaseLayer;

import java.sql.SQLException;
import java.util.ArrayList;

import modelLayer.Room;

/**
 * This interface defines all the methods for the Room Data Access Object
 * @author Group 1 dmai0920
 */
public interface RoomDBIF
{
	//Room findByNumber(String roomNumber) throws SQLException;
	/**
	 * This method gets and returns all rooms in the database
	 * @return list of all rooms for booking
	 * @throws SQLException
	 */
	ArrayList<Room> getAll() throws SQLException;
}
