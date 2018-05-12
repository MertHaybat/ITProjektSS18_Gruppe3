package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.*;

/**
 * Class-Description noch einfügen!
 * @version 1.10 12 May 2018
 * @author wahidvanaki
 *
 */
public class KontaktKontaktlisteMapper {

	/**
	 * Die Klasse KontaktlisteMapper wird nur einmal instantiiert.
	 * Hier spricht man von einem sogenannten Singleton.
	 * Durch static nur einmal vorhanden.
	 * 
	 */
	private static KontaktKontaktlisteMapper kontaktkontaktlisteMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new"
	 * neue Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected KontaktKontaktlisteMapper() {	
	};
	
	/**
	 * Kann aufgerufen werden durch KontaktKontaktlisteMapper kontaktkontaktlisteMapper.
	 * Sie stellt die Singleton-Eigenschaft sicher.
	 * Methode soll nur über diese statische Methode aufgerufen werden
	 * @return kontaktlisteMapper
	 */
	public static KontaktKontaktlisteMapper kontaktkontaktlisteMapper() {
		if (kontaktkontaktlisteMapper == null){
			kontaktkontaktlisteMapper = new KontaktKontaktlisteMapper();
		}
		return kontaktkontaktlisteMapper;	
	}
	
	/**
	 * 
	 * @param kontaktkontaktliste
	 * @return kontaktkontaktliste
	 */
	public KontaktKontaktliste createKontaktKontaktliste(KontaktKontaktliste kontaktkontaktliste) {
		
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();
			
		try {
			Statement stmt = con.createStatement();
			
			/**
			 * Was ist der momentan höchste Primärschlüssel
			 */		
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM kontakt ");
			
			if(rs.next()) {
				
				/**
				 * Die Variable erhält den höchsten Primärschlüssel inkrementiert um 1
				 */			
				kontaktkontaktliste.setId(rs.getInt("maxid") + 1);
				
				/**
				 * Durchführen der Einfüge Operation via Prepared Statement
				 */				
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO kontaktkontaktliste(id, kontaktid, kontaktlisteid) "
						+ "VALUES(?, ?, ?) ",
								
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, kontaktkontaktliste.getId());
				stmt1.setInt(2, kontaktkontaktliste.getKontaktID());
				stmt1.setInt(3, kontaktkontaktliste.getKontaktlisteID());
				
				System.out.println(stmt);
				stmt1.executeUpdate();			
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
		return kontaktkontaktliste;
	}
	
	public void deleteKontaktKontaktliste(KontaktKontaktliste kontaktkontaktliste) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
			
		try {
			
			/**
			 * Durchführen der Löschoperation
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kontaktkontaktliste " 
					+ "WHERE id= id ");
			
			stmt.setInt(1, kontaktkontaktliste.getId());
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