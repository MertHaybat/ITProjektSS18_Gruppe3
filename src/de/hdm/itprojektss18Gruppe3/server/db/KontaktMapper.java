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
 * Class description noch einfuegen!
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
	 * @see kontaktMapper
	 */
	private static KontaktMapper kontaktMapper = null;
	
	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit "new"
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
	 * @see kontaktMapper
	 */
	public static KontaktMapper kontaktMapper() {
		if (kontaktMapper == null){
			kontaktMapper = new KontaktMapper();
		}
		return kontaktMapper;	
	}
	
	/**
	 * Die Methode ermoeglicht das Einfuegen von Objekten "Kontakt".
	 * Insert SQL = Erstellen von einem Datensatz und das Einfuegen in die Datenbank.
	 *
	 *@return kontakt
	 *@see insertKontakt
	 */
	public Kontakt insertKontakt(Kontakt kontakt) {
		
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();
		java.sql.Date sqlDate = new java.sql.Date(kontakt.getErzeugungsdatum().getTime());
		java.sql.Date sqlDate1 = new java.sql.Date(kontakt.getModifikationsdatum().getTime());
		
		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */	
		try {
			Statement stmt = con.createStatement();
			
			/**
			 * Was ist der momentan hoechste Primaerschluessel
			 */		
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM kontakt ");
			
			if(rs.next()) {
				
				/**
				 * Die Variable erhaelt den hoechsten Primaerschluessel inkrementiert um 1
				 */			
				kontakt.setId(rs.getInt("maxid") + 1);
				
				/**
				 * Druchfuehren der Einfuege Operation via Prepared Statement
				 */				
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO kontakt(id, name, erzeugungsdatum, modifikationsdatum, status, nutzerid) "
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?) ",
								
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
		return kontakt;
	}
	
	/**
	 * Mit dieser Methode updateKontakt wird das Aktualisieren eines Objektes vom "Kontakt" ermoeglicht.
	 * 
	 * @param kontakt
	 * @return kontakt
	 * @see updateKontakt
	 */
	public Kontakt updateKontakt(Kontakt kontakt) {
		String sql = "UPDATE kontakt SET name= ?, erzeugungsdatum= ?, modifikationsdatum= ?, status= ? WHERE id= ? ";
		java.sql.Date sqlDate= new java.sql.Date(kontakt.getErzeugungsdatum().getTime());
		java.sql.Date sqlDate1 = new java.sql.Date(kontakt.getModifikationsdatum().getTime());
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */		
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
		
		/**
		 * Das Aktualisierte Objekt "Kontakt" wird zurueckgegeben.
		 */		
		return kontakt;
	}
	
	/**
	 * Die Methode deleteKontakt ermoeglicht das Loeschen vom Objekt "Kontakt"
	 * @param kontakt
	 * @see deleteKontakt
	 */	
	public void deleteKontakt(Kontakt kontakt) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */		
		try {
			
			/**
			 * Durchfuehren der Loeschoperation
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kontakt " 
					+ "WHERE id= id ");
			
			stmt.setInt(1, kontakt.getId());
			stmt.executeUpdate();
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * Diese Methode durchlaeuft den kompletten Vector und liefert alle Datensätze die im Vector<Kontakt> gespeichert sind.
	 * @return result
	 * @see findAllKontakt
	 */	
	public Vector<Kontakt> findAllKontakt() {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();
		
		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */		
		try {
			
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontakt ORDER BY name ASC ");
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Fuer jeden Eintrag im Suchergebnis wird nun ein Kontakt-Objekt erstellt.
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
				 * Hinzufuegen des neuen Objektes zum Ergebnisvektor
				 */				
				result.addElement(kontakt);
			}
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		
		/**
		 * Ergebnisvektor zurueckgeben
		 */		
		return result;
	}
	
	/**
	 * Die Methode findKontaktByKontaktID ermoeglicht das suchen nach einem Kontakt nach kontaktID
	 * 
	 * @param id
	 * @return kontakt
	 * @return null
	 * @see findByKontaktID
	 */	
	public Kontakt findKontaktByKontaktID(int id) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */	
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontakt WHERE id= id ");
			stmt.setInt(1, id);
			
			/**
			 * Statement ausfuellen und an die DB senden
			 */			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				Kontakt kontakt = new Kontakt();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				
				return kontakt;
			}
		}
		catch(SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * Die Methode ermoeglicht die Ausgabe eines Kontaktes, die im Vekotr<Kontakt> gespeichert sind, anhand der nutzerid.
	 * 
	 * @param nutzerid
	 * @return result
	 * @see findKontaktByNutzerID
	 */
	public Vector<Kontakt> findKontaktByNutzerID(int nutzerid) {
		
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();

		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */	
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
				 * Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 */			
				result.addElement(kontakt);
		}
	}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		
		/**
		 * Ergebnisvektor zurueckgeben
		 */		
		return result;
	}
	
	/**
	 * Die Methode ermoeglicht die Ausgabe eines Kontaktes, die im Vekotr<Kontakt> gespeichert sind, anhand der eigenschaftID.
	 * 
	 * @param eigenschaftid
	 * @return result
	 * @see findKontaktByEigenschaftID
	 */
	public Vector<Kontakt> findKontaktByEigenschaftID(int eigenschaftid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();

		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontakt WHERE id= " + eigenschaftid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontakt-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				//Eigenschaft eigenschaft = new Eigenschaft();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				//eigenschaft.setId(rs.getInt("id"));
				
				/**
				 * Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontakt);
			}
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		
		/**
		 * Ergebnisvektor zurueckgeben
		 */
		return result;
	}
	
	/**
	 * Alle Kontakte aus dem Vector<Kontakt> in einer Kontaktliste kontaktlisteID ausgeben
	 * 
	 * @param kontaktlisteid
	 * @return result
	 */
	public Vector<Kontakt> getAllKontakteByKontaktliste(int kontaktlisteid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();

		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontakt WHERE kontaktlisteid= " + kontaktlisteid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontakt-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				//Kontaktliste kontaktliste = new Kontaktliste ();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				//kontaktliste.setId(rs.getInt("id"));
				//kontaktliste.setKontaktlisteID(rs.getInt("kontaktlisteid"));
				//kontaktliste.setKontaktID(rs.getInt("kontaktid"));
				
				/**
				 * Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontakt);
		}
	}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		
		/**
		 * Ergebnisvektor zurueckgeben
		 */
		return result;
	}
		
	/**
	 * Alle Kontakte aus dem Vector<Kontakt> in einer Teilhaberschaft über die kontaktID ausgeben
	 * 
	 * @param kontaktid
	 * @return result
	 */
	public Vector<Kontakt> getAllKontakteByTeilhaberschaft(int kontaktid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontakt> result = new Vector<Kontakt>();

		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontakt WHERE id= " + kontaktid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontakt-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontakt kontakt = new Kontakt();
				//Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
				
				kontakt.setId(rs.getInt("id"));
				kontakt.setName(rs.getString("name"));
				kontakt.setErzeugungsdatum(rs.getDate("erzeugungsdatum"));
				kontakt.setModifikationsdatum(rs.getDate("modifikationsdatum"));
				kontakt.setStatus(rs.getInt("status"));
				kontakt.setNutzerID(rs.getInt("nutzerid"));
				//teilhaberschaft.setId(rs.getInt("ID"));
				//teilhaberschaft.setKontaktID(rs.getInt("Kontakt_ID"));
				
				/**
				 * Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontakt);
		}
	}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		
		/**
		 * Ergebnisvektor zurueckgeben
		 */
		return result;
	}
}
	












