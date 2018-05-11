package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.*;

/**
 * Class description noch einfuegen!
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
	 * @see kontaktlisteMapper
	 */
	private static KontaktlisteMapper kontaktlisteMapper = null;
	
	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit "new"
	 * neue Instanzen dieser Klasse zu erzeugen.
	 * 
	 * @see KontaktlisteMapper
	 */
	protected KontaktlisteMapper() {	
	};
	
	/**
	 * Kann aufgerufen werden durch KontaktlisteMapper kontaktlisteMapper.
	 * Sie stellt die Singleton-Eigenschaft sicher.
	 * Methode soll nur über diese statische Methode aufgerufen werden
	 * @return kontaktlisteMapper
	 * @see kontaktlisteMapper
	 */
	public static KontaktlisteMapper kontaktlisteMapper() {
		if (kontaktlisteMapper == null){
			kontaktlisteMapper = new KontaktlisteMapper();
		}
		return kontaktlisteMapper;	
	}
	
	/**
	 * Die Methode ermoeglicht das Einfuegen von Objekten "Kontaktliste".
	 * Insert SQL = Erstellen und das Einfuegen in die Datenbank.
	 *
	 *@return kontaktliste
	 *@see insertKontaktliste
	 */
	public Kontaktliste insertKontaktliste(Kontaktliste kontaktliste) {
		
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
			Statement stmt = con.createStatement();
			
			/**
			 * Was ist der momentan hoechste Primaerschluessel
			 */		
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM kontaktliste ");
			
			if(rs.next()) {
				
				/**
				 * Die Variable erhaelt den hoechsten Primaerschluessel inkrementiert um 1
				 */			
				kontaktliste.setId(rs.getInt("maxid") + 1);
				
				/**
				 * Druchfuehren der Einfuege Operation via Prepared Statement
				 */				
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO kontaktliste(id, bezeichnung, nutzerid) "
						+ "VALUES(?, ?, ?) ",
								
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, kontaktliste.getId());
				stmt1.setString(2, kontaktliste.getBezeichnung());
				//stmt1.setInt(3, kontaktliste.getKontaktID());
				
				System.out.println(stmt);
				stmt1.executeUpdate();			
			}
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		return kontaktliste;
	}
	
	/**
	 * Mit dieser Methode updateKontaktliste wird das Aktualisieren eines Objektes vom "Kontaktliste" ermoeglicht.
	 * 
	 * @param kontaktliste
	 * @return kontaktliste
	 * @see updateKontaktliste
	 */
	public Kontaktliste updateKontaktliste(Kontaktliste kontaktliste) {
		String sql = "UPDATE kontaktliste SET bezeichnung= ? WHERE id= ? ";
		
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
			
			stmt.setString(1, kontaktliste.getBezeichnung());
			
			
			stmt.setInt(2, kontaktliste.getId());
			stmt.executeUpdate();
			
			System.out.println("Update complete");
		}
		catch(SQLException e2){
			e2.printStackTrace();
		}
		
		/**
		 * Das Aktualisierte Objekt "Kontaktliste" wird zurueckgegeben.
		 */		
		return kontaktliste;
	}
	
	/**
	 * Die Methode deleteKontaktliste ermoeglicht das Loeschen vom Objekt "Kontaktliste"
	 * @param kontaktliste
	 * @see deleteKontaktliste
	 */	
	public void deleteKontaktliste(Kontaktliste kontaktliste) {
		
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
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kontaktliste " 
					+ "WHERE id= ? ");
			stmt.setInt(1, kontaktliste.getId());
			stmt.executeUpdate();
			
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * Diese Methode durchlaeuft den kompletten Vector und liefert alle Datensätze die im Vector<Kontaktliste> gespeichert sind.
	 * @return result
	 * @see findAllKontaktliste
	 */	
	public Vector<Kontaktliste> findAllKontaktliste() {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();
		
		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */		
		try {
			
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste ");
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Fuer jeden Eintrag im Suchergebnis wird nun ein Kontaktliste-Objekt erstellt.
			 */			
			while(rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				//kontaktliste.setKontaktID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufuegen des neuen Objektes zum Ergebnisvektor
				 */				
				result.addElement(kontaktliste);
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
	 * Die Methode ermoeglicht die Ausgabe einer Kontaktliste, die im Vekotr<Kontaktliste> gespeichert sind, anhand der nutzerID.
	 * 
	 * @param nutzerid
	 * @return result
	 * @see findKontaktlisteByNutzerID
	 */
	public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerid) {
		
		/**
		 * Verbindung zur DB Connection
		 */	
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */	
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
				//kontaktliste.setKontaktID(rs.getInt("nutzerid"));
				
				/**
				 * Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 */			
				result.addElement(kontaktliste);
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
	 * Das suchen einer Kontaktliste im Vector<Kontaktliste> anhand der kontaktID
	 * @param kontaktid
	 * @return result
	 */
	public Vector<Kontaktliste> findKontaktlisteByKontaktID(int kontaktid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste WHERE id= " + kontaktid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontaktliste ein Kontaktliste-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				//kontaktliste.setKontaktID(rs.getInt("nutzerid"));
				
				
				/**
				 * Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontaktliste);
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
	 * Alle Kontaktlisten aus dem Vector<Kontaktliste> in einer Teilhaberschaft über die kontaktlisteID ausgeben
	 * 
	 * @param kontaktlisteid
	 * @return result
	 */
	public Vector<Kontaktliste> getAllKontaktlisteByTeilhaberschaft(int kontaktlisteid) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Kontaktliste> result = new Vector<Kontaktliste>();

		/**
		 * Try and Catch gehoeren zum Exception Handling
		 * Try = Versuch erst dies
		 * Catch = Wenn Try fehlschlaegt, versuch es so
		 */
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM kontaktliste WHERE id= " + kontaktlisteid);
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Kontakt ein Kontaktliste-Objekt erstellt.
			 */
			while(rs.next()) {
				Kontaktliste kontaktliste = new Kontaktliste();
				//Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
				
				kontaktliste.setId(rs.getInt("id"));
				kontaktliste.setBezeichnung(rs.getString("bezeichnung"));
				//kontaktliste.setKontaktID(rs.getInt("nutzerid"));
				//teilhaberschaft.setKontaktID(rs.getInt("kontaktid"));
				
				/**
				 * Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(kontaktliste);
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
