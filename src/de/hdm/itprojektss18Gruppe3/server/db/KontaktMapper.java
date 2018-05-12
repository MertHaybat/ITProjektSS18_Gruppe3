package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.*;

/**
 * Class description noch einfügen!
 * @version 1.10 07 May 2018
 * @author wahidvanaki
 *
 */
public class KontaktMapper {
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Die Klasse KontaktMapper wird nur einmal instantiiert.
	 * Hier spricht man von einem sogenannten Singleton.
	 * Durch static nur einmal vorhanden.
	 * 
	 */
	private static KontaktMapper kontaktMapper = null;
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new"
	 * neue Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected KontaktMapper() {	
	};
	
	/**
	 * Kann aufgerufen werden durch KontaktMapper kontaktMapper.
	 * Sie stellt die Singleton-Eigenschaft sicher.
	 * Methode soll nur über diese statische Methode aufgerufen werden
	 * @return kontaktMapper
	 */
	public static KontaktMapper kontaktMapper() {
		if (kontaktMapper == null){
				kontaktMapper = new KontaktMapper();
			}
		return kontaktMapper;	
	}
	
	/**
	 * Die Methode ermöglicht das Einfügen von Objekten "Kontakt".
	 *
	 *@return kontakt vom Objekt Kontakt
	 */
	public Kontakt createKontakt(Kontakt kontakt) {
		
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();
		java.sql.Date sqlDate = new java.sql.Date(kontakt.getErzeugungsdatum().getTime());
		java.sql.Date sqlDate1 = new java.sql.Date(kontakt.getModifikationsdatum().getTime());
			
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
				kontakt.setId(rs.getInt("maxid") + 1);
				
				/**
				 * Durchführen der Einfüge Operation via Prepared Statement
				 */				
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO kontakt(id, name, erzeugungsdatum, modifikationsdatum, status, nutzerid) "
						+ "VALUES(?, ?, ?, ?, ?, ?) ",
								
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, kontakt.getId());
				stmt1.setString(2, kontakt.getName());
				stmt1.setDate(3, sqlDate);
				stmt1.setDate(4, sqlDate1);
				stmt1.setInt(5, kontakt.getStatus());
				stmt1.setInt(6, kontakt.getNutzerID());
				
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
		return kontakt;
	}
	
	/**
	 * Mit dieser Methode updateKontakt wird das Aktualisieren eines Objektes vom "Kontakt" ermöglicht.
	 * 
	 * @param kontakt 
	 * @return kontakt vom Objekt Kontakt
	 */
	public Kontakt updateKontakt(Kontakt kontakt) {
		String sql = "UPDATE kontakt SET name= ?, erzeugungsdatum= ?, modifikationsdatum= ?, status= ? WHERE id= id ";
		java.sql.Date sqlDate= new java.sql.Date(kontakt.getErzeugungsdatum().getTime());
		java.sql.Date sqlDate1 = new java.sql.Date(kontakt.getModifikationsdatum().getTime());
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
				
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, kontakt.getName());
			stmt.setDate(2, sqlDate);
			stmt.setDate(3, sqlDate1);
			stmt.setInt(4, kontakt.getStatus());
			
			stmt.setInt(5, kontakt.getId());
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
		 * Das Aktualisierte Objekt "Kontakt" wird zurückgegeben.
		 */		
		return kontakt;
	}
	
	/**
	 * Die Methode deleteKontakt ermöglicht das Löschen vom Objekt "Kontakt"
	 * @param kontakt
	 */	
	public void deleteKontakt(Kontakt kontakt) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
			
		try {
			
			/**
			 * Durchführen der Löschoperation
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kontakt " 
					+ "WHERE id= id ");
			
			stmt.setInt(1, kontakt.getId());
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
	 * Die Methode findKontaktByKontaktID ermöglicht das suchen nach einem Kontakt nach kontaktid
	 * 
	 * @param id
	 * @return kontakt vom Objekt Kontakt
	 * @return null falls bisher kein Objekt vom Kontakt erstellt wurde
	 */	
	public Vector<Kontakt> findKontaktByKontaktID(int id) {
		
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();
	
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontakt WHERE id= " + id);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontakt-Objekt erstellt.
			 */		
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */			
				result.addElement(kontakt);
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
	 * Diese Methode durchläuft den kompletten Vector und liefert alle Datensätze die im Vector<Kontakt> gespeichert sind.
	 * @return result
	 * @throws SQLException 
	 */	
	public Vector<Kontakt> findAllKontakt(){
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();
				
		try {
			
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontakt ORDER BY name ASC ");
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Kontakt-Objekt erstellt.
			 */			
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufügen des neuen Objektes zum Ergebnisvektor
				 */				
				result.addElement(kontakt);				
			}
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		} 
		
		/**
		 * Ergebnisvektor zurückgeben
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
		return result;
	}
	
	/**
	 * Die Methode ermöglicht die Ausgabe eines Kontaktes, die im Vekotr<Kontakt> gespeichert sind, anhand der nutzerid.
	 * 
	 * @param nutzerid
	 * @return result
	 */
	public Vector<Kontakt> findAllKontaktByNutzerID(int nutzerid) {
		
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontakt WHERE nutzerid= " + nutzerid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontakt-Objekt erstellt.
			 */		
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */			
				result.addElement(kontakt);
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
	 * Die Methode ermöglicht die Ausgabe eines Kontaktes, die im Vekotr<Kontakt> gespeichert sind, anhand der eigenschaftid.
	 * 
	 * @param eigenschaftid
	 * @return result
	 */
	public Vector<Kontakt> findAllKontaktByEigenschaftID(int eigenschaftid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT kontakt.*, eigenschaft.id FROM eigenschaft, kontakt");
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontakt-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontakt);
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
	 * Alle Kontakte aus dem Vector<Kontakt> in einer Kontaktliste kontaktlisteid ausgeben
	 * 
	 * @param kontaktlisteid
	 * @return result
	 */
	public Vector<Kontakt> findAllKontakteByKontaktliste(int kontaktlisteid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT `kontakt`.`name`, `kontakt`.`erzeugungsdatum`, `kontakt`.`modifikationsdatum`, `kontakt`.`status`, `kontaktliste`.`id` "
					+ "FROM `kontaktliste` "
					+ "LEFT JOIN `kontaktkontaktliste` "
					+ "ON `kontaktkontaktliste`.`kontaktlisteid` = `kontaktliste`.`id` "
					+ "LEFT JOIN `kontakt` "
					+ "ON `kontaktkontaktliste`.`kontaktid` = `kontakt`.`id` WHERE `kontaktliste`.`id` = " + kontaktlisteid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontakt-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontakt);
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
	 * Alle Kontakte aus dem Vector<Kontakt> in einer Teilhaberschaft über die kontaktid ausgeben
	 * 
	 * @param kontaktid
	 * @return result
	 */
	public Vector<Kontakt> findAllKontakteByTeilhaberschaft(int kontaktid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT kontakt.*, teilhaberschaft.kontaktid FROM kontakt JOIN teilhaberschaft ON teilhaberschaft.kontaktid = kontakt.id");


			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontakt-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				teilhaberschaft.setId(rs.getInt("id"));
				teilhaberschaft.setKontaktID(rs.getInt("kontaktid"));
				
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontakt);
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

	public Vector<Kontakt> findAllKontakteByKontaktID(int id) {

		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();
				
		try {
			
			PreparedStatement stmt = con.prepareStatement("SELECT kontakt.*, kontakt.id FROM kontakt");
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Kontakt-Objekt erstellt.
			 */			
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufügen des neuen Objektes zum Ergebnisvektor
				 */				
				result.addElement(kontakt);				
			}
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		} 
		
		/**
		 * Ergebnisvektor zurückgeben
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
		return result;
	}
}