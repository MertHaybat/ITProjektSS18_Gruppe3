package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;


/**
 * @version 1.10 07 May 2018
 * @author ersinbarut
 */

public class TeilhaberschaftMapper {

	/**
	 * Die Klasse TeilhaberschaftMapper wird nur einmal instantiiert.
	 * Hier spricht man von einem sogenannten Singleton.
	 * Durch static nur einmal vorhanden.
	 */
	private static TeilhaberschaftMapper teilhaberschaftMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new"
	 * neue Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected TeilhaberschaftMapper() {	
	};
	
	/**
	 * Kann aufgerufen werden durch TeilhaberschaftMapper kontaktMapper.
	 * Sie stellt die Singleton-Eigenschaft sicher.
	 * Methode soll nur über diese statische Methode aufgerufen werden
	 * @return teilhaberschaftMapper
	 */
	public static TeilhaberschaftMapper teilhaberschaftMapper(){
		if (teilhaberschaftMapper == null){
			teilhaberschaftMapper = new TeilhaberschaftMapper();
		}
		return teilhaberschaftMapper;
	}
	
	/**
	 * Die Methode ermöglicht das Einfügen von Objekten "Teilhaberschaft".
	 *
	 *@return teilhaberschaft vom Objekt Teilhaberschaft
	 */
	public Teilhaberschaft createTeilhaberschaft(Teilhaberschaft teilhaberschaft){
		
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			
		      /*
		       * Zunächst schauen wir nach, welches der momentan höchste
		       * Primärschlüsselwert ist.
		       */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM teilhaberschaft ");
			
			if(rs.next()) {
				/**
				 * Die Variable erhält den höchsten Primärschlüssel inkrementiert um 1
				 */			
				teilhaberschaft.setId(rs.getInt("maxid")+1);
			/**
			 * Durchführen der Einfüge Operation via Prepared Statement
			 */
			PreparedStatement stmt1 = con.prepareStatement(
					"INSERT INTO `teilhaberschaft`"
					+ "(`id`, `kontaktlisteid`, `kontaktid`, `eigenschaftsauspraegungid`, `teilhabenderid`, `eigentuemerid`)"
					+ "VALUES(?,?,?,?,?,?) ",
					
			Statement.RETURN_GENERATED_KEYS);
			stmt1.setInt(1, teilhaberschaft.getId());	
			stmt1.setInt(2, teilhaberschaft.getKontaktlisteID());
			stmt1.setInt(3, teilhaberschaft.getKontaktID());
			stmt1.setInt(4, teilhaberschaft.getEigenschaftsauspraegungID());
			stmt1.setInt(5, teilhaberschaft.getTeilhabenderID());
			stmt1.setInt(6, teilhaberschaft.getEigentuemerID());
			
			//System.out.println(stmt);
			stmt1.executeUpdate();			
			}
			
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		
		/**
		 * Closen der Datenbankverbindung 
		 */
		
		finally {	
			if (con!=null) 
				try {
					con.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		
		return teilhaberschaft;
		
	}
	/**
	 * Mit dieser Methode updateTeilhaberschaft wird das Aktualisieren eines Objektes von "Teilhaberschaft" ermöglicht.
	 * 
	 * @param teilhaberschaft
	 * @return teilhaberschaft vom Objekt Teilhaberschaft
	 */	
	public Teilhaberschaft updateTeilhaberschaft(Teilhaberschaft teilhaberschaft) {
		
		/**
		 * Verbindung zur DB Connection aufbauen
		 */	
		Connection con = DBConnection.connection();
		
		try {
		
			/**
			 * Durchführung der Update-Operation via Prepared Statement
			 */
			PreparedStatement stmt = con.prepareStatement("UPDATE `teilhaberschaft` SET `kontaktlisteid`= ?,"
					+ "`kontaktid`= ?, `eigenschaftsauspraegungid`= ?, "
					+ "`teilhabenderid`= ?, `eigentuemerid`= ? WHERE id= ?");
			stmt.setInt(1, teilhaberschaft.getKontaktlisteID());
			stmt.setInt(2, teilhaberschaft.getKontaktID());
			stmt.setInt(3, teilhaberschaft.getEigenschaftsauspraegungID());
			stmt.setInt(4, teilhaberschaft.getTeilhabenderID());
			stmt.setInt(5, teilhaberschaft.getEigentuemerID());
			stmt.setInt(6, teilhaberschaft.getId());
			
            stmt.executeQuery();
			
			System.out.println("Updated");
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		finally{
			if(con!=null)
				try{
					con.close();
				}
			catch(SQLException e){
				e.printStackTrace();

		}
	}
	
	return teilhaberschaft;
	
	}
	
	
	/**
	 * Methode um alle Teilhaberschaften anhand der von Teilhabern zu finden 
	 * @return result 
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByTeilhabenderID(int teilhabenderID) {
		
		/**
		 * Verbindung zur Datenbank
		 */
		Connection con = DBConnection.connection();
		
		Vector<Teilhaberschaft> result = new Vector <Teilhaberschaft>();
		
		try{
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM teilhaberschaft WHERE teilhabenderid=" + teilhabenderID);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Teilhabender wird ein Teilhaberschaft-Objekt erstellt.
			 */		
			while(rs.next()) {
				Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
				
				teilhaberschaft.setId(rs.getInt("id"));
				teilhaberschaft.setEigenschaftsauspraegungID(rs.getInt("id"));
				teilhaberschaft.setKontaktID(rs.getInt("id"));
				teilhaberschaft.setKontaktlisteID(rs.getInt("id"));
				teilhaberschaft.setTeilhabenderID(rs.getInt("id"));
				teilhaberschaft.setEigentuemerID(rs.getInt("id"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */	
				result.addElement(teilhaberschaft);
			}
		
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		finally {	
			if (con!=null) 
				try {
					con.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		
		/**
		 * Ergebnisvektor zurückgeben
		 */		
		return result;
	}
	
	/**
	 * Methode um alle Teilhaberschaften anhand der von Teilhabern zu finden 
	 * @return result 
	 */
	public Vector<Teilhaberschaft> findTeilhaberschaftByEigentuemerID(int eigentuemerID) {
		
		/**
		 * Verbindung zur Datenbank
		 */
		Connection con = DBConnection.connection();
		
		Vector<Teilhaberschaft> result = new Vector <Teilhaberschaft>();
		
		try{
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM teilhaberschaft WHERE eigentuemerid=" + eigentuemerID);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Teilhabender wird ein Teilhaberschaft-Objekt erstellt.
			 */		
			while(rs.next()) {
				Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
				
				teilhaberschaft.setId(rs.getInt("id"));
				teilhaberschaft.setEigenschaftsauspraegungID(rs.getInt("id"));
				teilhaberschaft.setKontaktID(rs.getInt("id"));
				teilhaberschaft.setKontaktlisteID(rs.getInt("id"));
				teilhaberschaft.setTeilhabenderID(rs.getInt("id"));
				teilhaberschaft.setEigentuemerID(rs.getInt("id"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */	
				result.addElement(teilhaberschaft);
			}
		
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		finally {	
			if (con!=null) 
				try {
					con.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
		
		/**
		 * Ergebnisvektor zurückgeben
		 */		
		return result;
	}

	public void deleteTeilhaberschaftByKontaktID(Teilhaberschaft teilhaberschaft) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
			
		try {
			
			/**
			 * Durchführen der Löschoperation DELETE FROM `teilhaberschaft` WHERE `nutzerid`=2
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM teilhaberschaft WHERE kontaktid=?");
			
			stmt.setInt(1, teilhaberschaft.getKontaktID());
			stmt.executeUpdate();
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		finally {	
		if (con!=null) 
			try {
				con.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteTeilhaberschaftByNutzerID(Teilhaberschaft t) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
			
		try {
			
			/**
			 * Durchführen der Löschoperation DELETE FROM `teilhaberschaft` WHERE `nutzerid`=2
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM teilhaberschaft WHERE eigentuemerid=?");
			
			stmt.setInt(1, t.getEigentuemerID());
			stmt.executeUpdate();
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		finally {	
		if (con!=null) 
			try {
				con.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}