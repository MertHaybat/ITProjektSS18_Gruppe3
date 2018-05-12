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
	protected int createPerson(Person person){
		 
	/**
	 * Verbindung zur Datenbank aufbauen 
	 */
	    Connection con = DBConnection.connection();
	    
	    
	    int id = 0;
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
	        id=person.getId();

			/**
			 * Druchfuehren der Einfuege Operation via Prepared Statement
			 */				
			PreparedStatement stmt1 = con.prepareStatement(
					"INSERT INTO person(id) VALUES(?)",
							
			Statement.RETURN_GENERATED_KEYS);
			stmt1.setInt(1, person.getId());
			
			System.out.println(stmt);
			stmt1.executeUpdate();			
	      }
	    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		    return id;
		  
	  }
	/**
	 * Die Methode deletePerson ermoeglicht das Loeschen vom Objekt "Person"
	 * @param pers
	 * @see deletePerson
	 */	
	public void deletePerson(Person person){
		 Connection con = DBConnection.connection();

		    try {
		      PreparedStatement stmt = con.prepareStatement("DELETE FROM person WHERE id= ?");
		      stmt.setInt(1, person.getId());
		      stmt.executeUpdate();
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
	}
}
