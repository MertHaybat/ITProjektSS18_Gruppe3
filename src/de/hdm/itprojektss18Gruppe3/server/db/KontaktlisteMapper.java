package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.*;

/**
  * Die Mapper-Klasse "KontaktlisteMapper" ermöglicht das Abbilden von Objekten "Kontaktliste" in einer relationalen Datenbank. 
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
	 * Die Methode "createKontaktliste" ermöglicht das Einfügen von Objekten "Kontaktliste".
	 * 
	 *@return kontaktliste vom Objekt Kontaktliste
	 *@see createKontaktliste
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
						"INSERT INTO kontaktliste(id, bezeichnung, nutzerid, status) "
						+ "VALUES(?, ?, ?, ?) ",
								
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, kontaktliste.getId());
				stmt1.setString(2, kontaktliste.getBezeichnung());
				stmt1.setInt(3, kontaktliste.getNutzerID());
				stmt1.setInt(4, kontaktliste.getStatus());
				
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
	 * Mit dieser Methode "updateKontaktliste" wird das Aktualisieren eines Objektes vom "Kontaktliste" ermöglicht.
	 * 
	 * @param kontaktliste
	 * @return kontaktliste vom Objekt Kontaktliste
	 */
	public Kontaktliste updateKontaktliste(Kontaktliste kontaktliste) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
			
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE kontaktliste SET bezeichnung= ?, status= ? WHERE id= ? ");
			
			stmt.setString(1, kontaktliste.getBezeichnung());
			stmt.setInt(2, kontaktliste.getStatus());
			stmt.setInt(3, kontaktliste.getId());
			
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
	 * Die Methode "deleteKontaktliste" ermöglicht das Löschen vom Objekt "Kontaktliste"
	 * @param kontaktliste
	 * @see deleteKontaktliste
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
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kontaktliste WHERE id= ?");
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
	 * @see deleteKontaktlisteByNutzerID
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
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kontaktliste WHERE nutzerid= ?");
			stmt.setInt(1, kontaktliste.getNutzerID());
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
	 * Diese Methode "findAllKontaktliste" durchläuft den kompletten Vector und liefert alle Datensätze die im Vector<Kontaktliste> gespeichert sind.
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
				kontaktliste.setStatus(rs.getInt("status"));
				
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
	 * Die Methode "findAllKontaktlisteByNutzerID" ermöglicht die Ausgabe einer Kontaktliste, die im Vektor<Kontaktliste> gespeichert sind, anhand der nutzerid.
	 * @param nutzerID - NutzerID aus der Kontaktliste
	 * @return result - gibt als Vektor alle Kontaktlisten eines bestimmten Nutzers zurück
	 */
	public Vector<Kontaktliste> findAllKontaktlisteByNutzerID(int nutzerID) {
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste WHERE nutzerid= ?");
			
			stmt.setInt(1, nutzerID);
			
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontaktliste-Objekt erstellt.
			 */		
			while(rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				kontaktliste.setNutzerID(rs.getInt("nutzerid"));
				kontaktliste.setStatus(rs.getInt("status"));
				
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
	
//	/**
//	 * Das Suchen einer Kontaktliste im Vector<Kontaktliste> anhand der kontaktid.
//	 * @param kontaktid
//	 * @return result
//	 */
//	public Vector<Kontaktliste> findAllKontaktlisteByKontaktID(int kontaktid) {
//		
//		/**
//		 * Verbindung zur DB Connection
//		 */
//		Connection con = DBConnection.connection();
//		
//		Vector<Kontaktliste> result = new Vector<Kontaktliste>();
//
//		try {
//			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste WHERE nutzerid= ?");
//			stmt.setInt(1, kontaktid);
//			
//			ResultSet rs = stmt.executeQuery();
//			
//			/**
//			 * Für jeden Eintrag Kontaktliste ein Kontaktliste-Objekt erstellt.
//			 */
//			while(rs.next()) {
//				Kontaktliste kontaktliste = new Kontaktliste();
//				
//				kontaktliste.setId(rs.getInt("id"));
//				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
//				kontaktliste.setNutzerID(rs.getInt("nutzerid"));
//				
//				/**
//				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
//				 */
//				result.addElement(kontaktliste);
//			}
//		}
//		catch(SQLException e2) {
//			e2.printStackTrace();
//		}
//		finally {	
//			if (con!=null) 
//				try {
//					con.close();
//				}
//				catch(SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		/**
//		 * Ergebnisvektor zurückgeben
//		 */
//		return result;
//	}
	
	/**
	 * Die Methode "findAllKontaktlisteByTeilhaberschaft" ermöglicht
	 * alle Kontaktlisten aus dem Vector<Kontaktliste> die in einer Teilhaberschaft sind, über die kontaktlisteid ausgeben.
	 * 
	 * @param kontaktliste - Objekt, der Klasse Kontaktliste - hier wird die KontaktlisteID entnommen 
	 * @return result - gibt als Vektor alle Kontaktlisten einer Teilhaberschaft zurück
	 */
	public Vector<Kontaktliste> findAllKontaktlisteByTeilhaberschaft(Kontaktliste kontaktliste2) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste Where id= ?");
			
			stmt.setInt(1, kontaktliste2.getId());
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontaktliste-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				kontaktliste.setNutzerID(rs.getInt("nutzerid"));
				kontaktliste.setStatus(rs.getInt("status"));
				
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

	public Kontaktliste findKontaktlisteByID(int kontaktlisteID) {
		// TODO Auto-generated method stub
		

		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();

		Kontaktliste k = new Kontaktliste();

		try {
			
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste WHERE `id` = ?");

			stmt.setInt(1, kontaktlisteID);
			ResultSet rs = stmt.executeQuery();

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Kontaktlisten-Objekt
			 * erstellt.
			 */
			if (rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setNutzerID(rs.getInt("nutzerid"));
				kontaktliste.setStatus(rs.getInt("status"));
				k = kontaktliste;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/**
		 * Ergebnisvektor zurückgeben
		 */
		finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return k;
	}
	public Kontaktliste findKontaktlisteByBezeichnung(String bezeichnung, int nutzerID) {
		// TODO Auto-generated method stub
		

		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();

		Kontaktliste k = new Kontaktliste();

		try {
			
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste WHERE `bezeichnung` = ? AND `nutzerid` = ?");

			stmt.setString(1, bezeichnung);
			stmt.setInt(2, nutzerID);
			ResultSet rs = stmt.executeQuery();

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Kontaktlisten-Objekt
			 * erstellt.
			 */
			if (rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setNutzerID(rs.getInt("nutzerid"));
				kontaktliste.setStatus(rs.getInt("status"));
				k = kontaktliste;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/**
		 * Ergebnisvektor zurückgeben
		 */
		finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return k;
	}

//	/**
//	 * Die Methode "findAllKontakteByKontaktlisteID" ermöglicht
//	 * alle Kontaktlisten aus dem Vector<Kontaktliste> über die kontaktlisteid ausgeben.
//	 * 
//	 * @param kontakt2 - Objekt, der Klasse Kontaktliste 
//	 * @return 
//	 */
//	public Vector<Kontakt> findAllKontakteByKontaktlisteID(Kontakt kontakt2) {
//		
//		/**
//		 * Verbindung zur DB Connection
//		 */
//		Connection con = DBConnection.connection();
//		
//		Vector<Kontakt> result = new Vector<Kontakt>();
//
//		try {
//			PreparedStatement stmt = con.prepareStatement("SELECT `kontakt`.`name`, `kontakt`.`erzeugungsdatum`, `kontakt`.`modifikationsdatum`, `kontakt`.`status`, `kontaktliste`.`id` "
//					+ "FROM `kontaktliste` "
//					+ "LEFT JOIN `kontaktkontaktliste` "
//					+ "ON `kontaktkontaktliste`.`kontaktlisteid` = `kontaktliste`.`id` "
//					+ "LEFT JOIN `kontakt` "
//					+ "ON `kontaktkontaktliste`.`kontaktid` = `kontakt`.`id` WHERE `kontaktliste`.`id` = ?");
//			
//			stmt.setInt(1, kontakt2.getId());
//			ResultSet rs = stmt.executeQuery();
//			
//			/**
//			 * Für jeden Eintrag Kontakt ein Kontakt-Objekt erstellt.
//			 */
//			while(rs.next()) {
//				Kontakt kontakt = new Kontakt();
//				
//				kontakt.setId(rs.getInt("id"));
//				kontakt.setName(rs.getString("name"));
//				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
//				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
//				kontakt.setStatus(rs.getInt("status"));
//				
//				/**
//				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
//				 */
//				result.addElement(kontakt);
//			}
//		}
//		catch(SQLException e2) {
//			e2.printStackTrace();
//		}
//		finally {	
//			if (con!=null) 
//				try {
//					con.close();
//				}
//				catch(SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		
//		/**
//		 * Ergebnisvektor zurückgeben
//		 */
//		return result;
//	}
}