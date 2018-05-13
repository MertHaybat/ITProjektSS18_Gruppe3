package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.*;

/**
 * Die Mapper-Klasse KontaktKontaktlisteMapper ermöglicht das Abbilden von Objekten "KontaktKontaktliste"
 * in einer relationalen Datenbank. Dabei sind in der Mapper-Klassen mehrere Methoden wie das erstellen, suchen oder löschen 
 * etc. implementiert. Somit kann ein Objekt für die Datenbank-Struktur umgewandelt, 
 * aber es kann auch von der Datenbank-Struktur als Objekt wieder umgewandelt werden.
 * 
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
	 * @return kontaktkontaktlisteMapper
	 */
	public static KontaktKontaktlisteMapper kontaktkontaktlisteMapper() {
		if (kontaktkontaktlisteMapper == null){
			kontaktkontaktlisteMapper = new KontaktKontaktlisteMapper();
		}
		return kontaktkontaktlisteMapper;	
	}
	
	/**
	 * Ermöglicht das Einfügen von Objekten "KontaktKontaktliste"
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
	
	/**
	 * Das Löschen von einem Objekt "KontaktKontaktlise"
	 * 
	 * @param kontaktkontaktliste
	 */
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
					+ "WHERE id= ?");
			
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
	
	/**
	 * Das Löschen von KontaktKontaktliste mit der KontaktID
	 * 
	 * @param kontaktkontaktliste
	 */
	public void deleteKontaktKontaktlisteByKontaktID(KontaktKontaktliste kontaktkontaktliste) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
			
		try {
			
			/**
			 * Durchführen der Löschoperation
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kontaktkontaktliste WHERE kontaktid= ?");
			
			stmt.setInt(1, kontaktkontaktliste.getKontaktID());
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
	
	/**
	 * Alle KontaktKontaktlisten aus dem Vector<KontaktKontaktliste> die in einer Kontaktliste über die kontaktlisteid im Zusammenhang stehen,
	 * also die in einer n:m Beziehung stehen, werden ausgegeben.
	 * 
	 * @param kontaktlisteid
	 * @return result
	 */
	public Vector<KontaktKontaktliste> findAllKontaktKontaktlisteByKontaktlisteID(int kontaktlisteid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<KontaktKontaktliste> result = new Vector<KontaktKontaktliste>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT `kontaktkontaktliste`.*, `kontaktliste`.`id`, `kontaktliste`.`bezeichnung`"
					+ "FROM `kontaktliste` "
					+ "JOIN `kontaktkontaktliste` "
					+ "ON `kontaktkontaktliste`.`kontaktlisteid` = `kontaktliste`.`id` "
					+ "WHERE `kontaktliste`.`id`= ?");
			
			stmt.setInt(1, kontaktlisteid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag KontaktKontaktliste wird ein KontaktKontaktliste-Objekt erstellt.
			 */
			while(rs.next()) {
				KontaktKontaktliste kontaktkontaktliste = new KontaktKontaktliste();
				
				kontaktkontaktliste.setId(rs.getInt("id"));
				kontaktkontaktliste.setKontaktID(rs.getInt("kontaktid"));
				kontaktkontaktliste.setKontaktlisteID(rs.getInt("kontaktlisteid"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontaktkontaktliste);
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
}