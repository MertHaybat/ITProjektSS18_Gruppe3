package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

/**
 * @version 1.10 07 May 2018
 * @author ersinbarut
 */

	/**
	 * Die Klasse NutzerMapper erbt von ihrer Superklasse PersonMapper
	 */
public class NutzerMapper extends PersonMapper{
	
	/**
	 * Die Klasse NutzerMapper wird nur einmal instantiiert.
	 * Hier spricht man von einem sogenannten Singleton.
	 * Durch static nur einmal vorhanden.
	 */
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
	public static NutzerMapper nutzerMapper(){
		if (nutzerMapper == null){
			nutzerMapper = new NutzerMapper();
		}
		return nutzerMapper;
	}
	
	
	public Nutzer createNutzer(Nutzer nutzer){
		
		/**
		 * Verbindung zur Datenbank wird aufgebaut
		 */
		Connection con = DBConnection.connection();
	
		
		try {
			
			Statement stmt = con.createStatement();
			
			/**
			 * Um die NutzerID zu erstellen, wird eine Person angelegt und später die maxID als NutzerID genommen
			 */
			nutzer.setId(super.createPerson(nutzer));
			
			/**
			* Zunächst schauen wir nach, welches der momentan höchste
			* Primärschlüsselwert ist in der Tabelle person.
			*/		
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM person");	
			if(rs.next()) {
		
			
			/**
			 * Druchfuehren der Einfuege Operation via Prepared Statement
			 */			
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO nutzer (id, mail) VALUES (?, ?) ",
								
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, nutzer.getId());
				stmt1.setString(2, nutzer.getMail());
				
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
	
	/**
	 * Mit dieser Methode updateNutzer wird das Aktualisieren eines Objektes von "Nutzer" ermöglicht.
	 * 
	 * @param nutzer
	 * @return nutzer vom Objekt Nutzer
	 */
	public Nutzer updateNutzer(Nutzer nutzer) {
		
		/**
		 * Verbindung zur DB Connection aufbauen
		 */	
		Connection con = DBConnection.connection();
		
		try {
			
			/**
			 * Durchführung der Update-Operation via Prepared Statement
			 */
			PreparedStatement stmt = con.prepareStatement("UPDATE `nutzer` SET `mail`= ? WHERE id= ?");
			
			stmt.setString(1, nutzer.getMail());
			stmt.setInt(2, nutzer.getId());
			
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

		return nutzer;
	}
	
	
	public void deleteNutzer(Nutzer nutzer) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		try {
			
			/**
			 * Durchfuehren der Loeschoperation
			 */			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM nutzer WHERE id= ?");
			
			stmt.setInt(1, nutzer.getId());
			stmt.executeUpdate();
			
			super.deletePerson(nutzer);
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
	 * Methode um alle Nutzer zurückzugeben
	 * @param nutzerid
	 * @return result 
	 * gibt als Result alle Nutzer zurück
	 */
	public Vector<Nutzer> findAllNutzer() {
		
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
				nutzer.setMail(rs.getString("mail"));

				
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
	public Nutzer findNutzerByEmail(String email){
		return null;
		
	}
	

}
