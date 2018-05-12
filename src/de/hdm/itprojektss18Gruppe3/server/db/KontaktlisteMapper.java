package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.*;

/**
  * Die Mapper-Klasse KontaktlisteMapper ermöglicht das Abbilden von Objekten "Kontaktliste" in einer relationalen Datenbank. 
 * Dabei sind in der Mapper-Klassen mehrere Methoden wie das erstellen, löschen, modifizieren 
 * oder das Suchen nach mehreren Möglichkeiten etc. implementiert. Somit kann ein Objekt für die Datenbank-Struktur umgewandelt, 
 * aber es kann auch von der Datenbank-Struktur als Objekt wieder umgewandelt werden.
 * 
 * @version 1.10 08 May 2018
 * @author wahidvanaki
 *
 */
public class KontaktlisteMapper {
	
	/**
	 * Die Klasse KontaktlisteMapper wird nur einmal instantiiert.
	 * Hier spricht man von einem sogenannten Singleton.
	 * Durch static nur einmal vorhanden.
	 * 
	 */
	private static KontaktlisteMapper kontaktlisteMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new"
	 * neue Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected KontaktlisteMapper() {	
	};
	
	/**
	 * Kann aufgerufen werden durch KontaktlisteMapper kontaktlisteMapper.
	 * Sie stellt die Singleton-Eigenschaft sicher.
	 * Methode soll nur über diese statische Methode aufgerufen werden
	 * @return kontaktlisteMapper
	 */
	public static KontaktlisteMapper kontaktlisteMapper() {
		if (kontaktlisteMapper == null){
			kontaktlisteMapper = new KontaktlisteMapper();
		}
		return kontaktlisteMapper;	
	}
	
	/**
	 * Die Methode ermöglicht das Einfügen von Objekten "Kontaktliste".
	 * 
	 *@return kontaktliste vom Objekt Kontaktliste
	 */
	public Kontaktliste createKontaktliste(Kontaktliste kontaktliste) {
		
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
					+ "FROM kontaktliste ");
			
			if(rs.next()) {
				
				/**
				 * Die Variable erhält den höchsten Primärschlüssel inkrementiert um 1
				 */			
				kontaktliste.setId(rs.getInt("maxid") + 1);
				
				/**
				 * Durchführen der Einfüge Operation via Prepared Statement
				 */				
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO kontaktliste(id, bezeichnung, nutzerid) "
						+ "VALUES(?, ?, ?) ",
								
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, kontaktliste.getId());
				stmt1.setString(2, kontaktliste.getBezeichnung());
				stmt1.setInt(3, kontaktliste.getNutzerID());
				
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
		return kontaktliste;
	}
	
	/**
	 * Mit dieser Methode updateKontaktliste wird das Aktualisieren eines Objektes vom "Kontaktliste" ermöglicht.
	 * 
	 * @param kontaktliste
	 * @return kontaktliste vom Objekt Kontaktliste
	 */
	public Kontaktliste updateKontaktliste(Kontaktliste kontaktliste) {
		String sql = "UPDATE kontaktliste SET bezeichnung= ? WHERE id= id ";
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
			
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, kontaktliste.getBezeichnung());
			
			
			stmt.setInt(2, kontaktliste.getId());
			stmt.executeUpdate();
			
			System.out.println("Update complete");
		}
		catch(SQLException e2){
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
		 * Das Aktualisierte Objekt "Kontaktliste" wird zurückgegeben.
		 */		
		return kontaktliste;
	}
	
	/**
	 * Die Methode deleteKontaktliste ermöglicht das Löschen vom Objekt "Kontaktliste"
	 * @param kontaktliste
	 */	
	public void deleteKontaktliste(Kontaktliste kontaktliste) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
				
		try {
			/**
			 * Durchführen der Löschoperation
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kontaktliste " 
					+ "WHERE id= id ");
			stmt.setInt(1, kontaktliste.getId());
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
	 * Die Methode deleteKontaktlisteByNutzerID ermöglicht das Löschen vom Objekt "Kontaktliste" anhand der nutzerid
	 * @param kontaktliste
	 */
	public void deleteKontaktlisteByNutzerID(Kontaktliste kontaktliste) {
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		try {
			/**
			 * Durchführen der Löschoperation
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kontaktliste " 
					+ "WHERE id= nutzerid ");
			stmt.setInt(1, kontaktliste.getId());
			stmt.setInt(2, kontaktliste.getNutzerID());
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
	 * Diese Methode durchläuft den kompletten Vector und liefert alle Datensätze die im Vector<Kontaktliste> gespeichert sind.
	 * @return result 
	 */	
	public Vector<Kontaktliste> findAllKontaktliste() {	
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();
			
		try {
			/**
			 * Durchführen der Suchfunktion
			 */
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste ");
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Kontaktliste-Objekt erstellt.
			 */			
			while(rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				kontaktliste.setNutzerID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufügen des neuen Objektes zum Ergebnisvektor
				 */				
				result.addElement(kontaktliste);
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
	 * Die Methode ermöglicht die Ausgabe einer Kontaktliste, die im Vekotr<Kontaktliste> gespeichert sind, anhand der nutzerid.
	 * 
	 * @param nutzerid
	 * @return result
	 */
	public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerid) {
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste WHERE id= " + nutzerid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontaktliste-Objekt erstellt.
			 */		
			while(rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				kontaktliste.setNutzerID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */			
				result.addElement(kontaktliste);
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
	 * Das Suchen einer Kontaktliste im Vector<Kontaktliste> anhand der kontaktid.
	 * @param kontaktid
	 * @return result
	 */
	public Vector<Kontaktliste> findAllKontaktlisteByKontaktID(int kontaktid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT `kontaktliste`.`bezeichnung`, `kontaktliste`.`nutzerid`, `kontakt`.`id`"
					+ "FROM `kontaktliste`"
					+ "LEFT JOIN `kontaktkontaktliste` "
					+ "ON `kontaktkontaktliste`.`kontaktlisteid` = `kontaktliste`.`id`"
					+ "LEFT JOIN `kontakt` "
					+ "ON `kontaktkontaktliste`.`kontaktid` = `kontakt`.`id` "
					+ "WHERE `kontaktkontaktliste`.`id`= " + kontaktid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontaktliste ein Kontaktliste-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				kontaktliste.setNutzerID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontaktliste);
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
	 * Alle Kontaktlisten aus dem Vector<Kontaktliste> in einer Teilhaberschaft über die kontaktlisteid ausgeben.
	 * 
	 * @param kontaktlisteid
	 * @return result
	 */
	public Vector<Kontaktliste> findAllKontaktlisteByTeilhaberschaft(int kontaktlisteid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste Where id= " + kontaktlisteid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontaktliste-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				kontaktliste.setNutzerID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontaktliste);
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
