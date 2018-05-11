package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	
	
	
	
}
