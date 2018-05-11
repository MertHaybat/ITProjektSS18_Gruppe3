package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.itprojektss18Gruppe3.shared.bo.Person;
/**
 * @version 1.10 07 May 2018
 * @author ersinbarut
 * Die Klasse PersonMapper gilt als Superklasse für die Klassen NutzerMapper und KontaktMapper
 * Sie beinhaltet nur die id als Attribut
 */

public class PersonMapper {
	
	/**
	 * Die Klasse PersonMapper wird nur einmal instantiiert.
	 * Hier spricht man von einem sogenannten Singleton.
	 * Durch static nur einmal vorhanden.
	 */
	
	
	private static PersonMapper personMapper = null;

	/**
	 * Geschuetzter Konstruktor, da nur innerhalbd es packages auf diese Klasse zugegriffen werden soll.
	 */
	protected PersonMapper(){
	}
	
	/**
	 * Kann aufgerufen werden durch PersonMapper personMapper.
	 * Sie stellt die Singleton-Eigenschaft sicher.
	 * Methode soll nur über diese statische Methode aufgerufen werden
	 * @return personMapper 
	 */
	public static PersonMapper personMapper(){
		if(personMapper == null){
			personMapper = new PersonMapper();
		}
		return personMapper;
	}
	
	/**
	 * Die Methode ermoeglicht das Einfuegen von Objekten "Person".
	 * Insert SQL = Erstellen von einem Datensatz und das Einfuegen in die Datenbank einer id.
	 *
	 *@return person
	 *@see createPerson
	 */
	protected Person createPerson(Person person){
		 
	/**
	 * Verbindung zur Datenbank aufbauen 
	 */
	    Connection con = DBConnection.connection();
	    
	    
//	    int id = 0;
	    
	    /**
	     * Try and Catch gehören zum Exception Handling
	     * Try = Den ersten Versuch starten
	     * Catch = Falls der Versuch bei Try fehlschlägt, springt es auf Catch 
	     */
	    try {
	    		
	      Statement stmt = con.createStatement();

	      /*
	       * Zunächst schauen wir nach, welches der momentan höchste
	       * Primärschlüsselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM person");
	      
	      if (rs.next()) {

	    	/**
	    	 * Die Variable erhält den höchsten Primärschlüssel inkrementiert um 1			
	    	 */
	        person.setId(rs.getInt("maxid") + 1);

			/**
			 * Druchfuehren der Einfuege Operation via Prepared Statement
			 */				
			PreparedStatement stmt1 = con.prepareStatement(
					"INSERT INTO person(id) VALUES(?) ",
							
			Statement.RETURN_GENERATED_KEYS);
			stmt1.setInt(1, person.getId());
			
			System.out.println(stmt);
			stmt1.executeUpdate();			
	      }
	    }
		    catch (SQLException e) {
		      e.printStackTrace();
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

		    return person;
		  
	  }
	/**
	 * Die Methode deletePerson ermoeglicht das Loeschen vom Objekt "Person"
	 * @param pers
	 * @see deletePerson
	 */	
	public void deletePerson(Person person){
		 Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("DELETE FROM person" + " WHERE id=" + person.getId());
		      
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
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
