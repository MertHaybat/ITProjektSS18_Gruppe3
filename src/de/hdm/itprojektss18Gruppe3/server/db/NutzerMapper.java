package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

/**
 * 
 * @author ersinbarut
 *
 */

	/**
	 * Die Klasse NutzerMapper erbt von ihrer Superklasse PersonMapper
	 */
public class NutzerMapper extends PersonMapper{
	

	private static NutzerMapper nutzerMapper = null;
	
	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit "new"
	 * neue Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected NutzerMapper(){	
	};
	
	/**
	 * Kann aufgerufen werden durch NutzerMapper nutzerMapper.
	 * Sie stellt die Singelton-Eigenschaft sicher. 
	 * Methode soll nur über diese statische Methode aufgerufen werden
	 * @return nutzerMapper
	 * @see createNutzer
	 */
	
	public Nutzer createNutzer(Nutzer nutzer){
		
		/**
		 * Verbindung zur Datenbank wird aufgebaut
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
			* Zunächst schauen wir nach, welches der momentan höchste
			* Primärschlüsselwert ist.
			*/		
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM nutzer ");	
			if(rs.next()) {
			/**
			 * Die Variable erhaelt den hoechsten Primaerschluessel inkrementiert um 1
			 */		
				nutzer.setId(rs.getInt("maxid") + 1);
			
			/**
			 * Druchfuehren der Einfuege Operation via Prepared Statement
			 */			
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO nutzer(id, mail VALUES(?) ",
								
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, nutzer.getId());
				
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
		return nutzer;
	}
	
	public void deleteNutzer(Nutzer nutzer) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
	    /**
	     * Try and Catch gehören zum Exception Handling
	     * Try = Den ersten Versuch starten
	     * Catch = Falls der Versuch bei Try fehlschlägt, springt es auf Catch 
	     */
		try {
			
			/**
			 * Durchfuehren der Loeschoperation
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM nutzer " 
					+ "WHERE id = id ");
			
			stmt.setInt(1, nutzer.getId());
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
	
	public Vector<Nutzer> findAllNutzer(int nutzerid) {
		
		/**
		 * Verbindung zur Datenbank aufbauen
		 */
		Connection con = DBConnection.connection();

		/**
		 * Es wird ein Vector um Nutzer darin zu speichern
		 */
		Vector<Nutzer> result = new Vector<Nutzer>();
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM `nutzer` ORDER BY `mail` ASC");
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Nutzer ein Nutzer-Objekt erstellt.
			 */
			while(rs.next()) {
				Nutzer nutzer = new Nutzer();
				
				nutzer.setId(rs.getInt("id"));

				
				/**
				 * Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(nutzer);
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
