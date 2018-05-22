package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;

/**
 * Die Mapper-Klasse "EigenschaftsauspraegungMapper" ermöglicht das Abbilden von Objekten "Eigenschaftsauspraegung" in einer relationalen Datenbank. 
 * Dabei sind in der Mapper-Klassen mehrere Methoden wie das erstellen, löschen, modifizieren 
 * oder das Suchen nach mehreren Möglichkeiten etc. implementiert. Somit kann ein Objekt für die Datenbank-Struktur umgewandelt, 
 * aber es kann auch von der Datenbank-Struktur als Objekt wieder umgewandelt werden.
 * 
 * @version @version 1.10 14 May 2018
 * @author Giuseppe Galati und wahidvanaki
 *
 */
public class EigenschaftsauspraegungMapper {

	/**
	 * Die Klasse EigenschaftsauspraegungMapper wird nur einmal instantiiert.
	 * Hier spricht man von einem sogenannten Singleton.
	 * Durch static nur einmal vorhanden.
	 * 
	 * @see eigenschaftsauspraegungMapper
	 * */
	
	private static EigenschaftsauspraegungMapper eigenschaftsauspraegungMapper = null;
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new"
	 * neue Instanzen dieser Klasse zu erzeugen.
	 * 
	 * @see EigenschaftsauspraegungMapper
	 */
	
	protected EigenschaftsauspraegungMapper() {
	};
	
	/**
	 * Kann durch EigenschaftsauspraegungMapper eigenschaftsauspraegungMapper aufgerufen werden .
	 * Stellt die Singleton-Eigenschaft sicher.
	 * Methode soll nur über diese statische Methode aufgerufen werden
	 * @return eigenschaftMapper
	 * @see eigenschaftMapper
	 */
	
	public static EigenschaftsauspraegungMapper eigenschaftsauspraegungMapper(){
		if(eigenschaftsauspraegungMapper == null){
			eigenschaftsauspraegungMapper = new EigenschaftsauspraegungMapper();
		}
		return eigenschaftsauspraegungMapper;
		}
	
	/**
	 * Die Methode "createEigenschaftsauspraegung" ermöglicht das Einfügen von Objekten "Eigenschaftsauspraegung".
	 *
	 *@return eigenschaftsauspraegung
	 *@see createEigenschaftsauspraegung
	 */
	
	public Eigenschaftsauspraegung createEigenschaftsauspraegung (Eigenschaftsauspraegung eigenschaftsauspraegung){
	
		/**
		 * Verbindung zur DB Connection aufbauen
		 */	
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			/**
			 * Prüfen, welches der momentan höchste Primärschlüsselwert ist
			 */	
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
					+ "FROM eigenschaftsauspraegung ");
			
			if(rs.next()) {
				
				/**
				 * Die Variable erhält den höchsten Primärschlüssel, um 1 inkrementiert
				 */
				eigenschaftsauspraegung.setId(rs.getInt("maxid")+1);
				
				
				/**
				 * Durchführung der Einfüge-Operation via Prepared Statement
				 */
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO eigenschaftsauspraegung (id, wert, personid, status, eigenschaftid)"
						+ "VALUES (?, ?, ?, ?, ?)",
						
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1,  eigenschaftsauspraegung.getId());
				stmt1.setString(2, eigenschaftsauspraegung.getWert());
				stmt1.setInt(3, eigenschaftsauspraegung.getPersonID());
				stmt1.setInt(4, eigenschaftsauspraegung.getStatus());
				stmt1.setInt(5,  eigenschaftsauspraegung.getEigenschaftID());
				
				System.out.println(stmt);
				stmt1.executeUpdate();
			}
		}
		catch(SQLException e2){
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
		return eigenschaftsauspraegung;	
	}
	
	/**
	 * Mit dieser Methode "updateEigenschaftsauspraegung" wird das Aktualisieren eines Objektes von "Eigenschaftsauspraegung" ermöglicht.
	 * 
	 * @param eigenschaftsauspraegung - Objekt, der Klasse Eigenschaftsauspraegung
	 * @return eigenschaftsauspraegung vom Objekt Eigenschaftsauspraegung
	 */
	public Eigenschaftsauspraegung updateEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung) {
		
		/**
		 * Verbindung zur DB Connection aufbauen
		 */	
		Connection con = DBConnection.connection();
		
		try {
			
			/**
			 * Durchführung der Update-Operation via Prepared Statement
			 */
			PreparedStatement stmt = con.prepareStatement("UPDATE `eigenschaftsauspraegung` SET `wert`= ?, `personid`= ?,"
					+ " `status`= ?, `eigenschaftid`= ? WHERE id= ?");
			
			stmt.setString(1, eigenschaftsauspraegung.getWert());
			stmt.setInt(2, eigenschaftsauspraegung.getPersonID());
			stmt.setInt(3, eigenschaftsauspraegung.getStatus());
			stmt.setInt(4, eigenschaftsauspraegung.getEigenschaftID());
			stmt.setInt(5, eigenschaftsauspraegung.getId());
			
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

		return eigenschaftsauspraegung;
	}
	
	
	/**
	 * Die Methode "deleteEigenschaftsauspraegung" ermöglicht das Löschen vom Objekt "Eigenschaftsauspraegung"
	 * @param eigenschaftsauspraegung
	 * @see deleteEigenschaftsauspraegung
	 */	
	
	public void deleteEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung) {
	
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		try {
			/**
			 * Durchführen der Löschoperation
			 */
			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM eigenschaftsauspraegung WHERE id= ? ");
			
			stmt.setInt(1, eigenschaftsauspraegung.getId());
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
	 * Die Methode "deleteEigenschaftsauspraegungByPersonID" ermöglicht das Löschen vom Objekt "Eigenschaftsauspraegung" anhand der PersonID
	 * @param eigenschaftsauspraegung
	 * @see deleteEigenschaftsauspraegungByPersonID
	 */	
	
	public void deleteEigenschaftsauspraegungByPersonID(Eigenschaftsauspraegung eigenschaftsauspraegung) {
	
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		try {
			/**
			 * Durchführen der Löschoperation
			 */
			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM eigenschaftsauspraegung WHERE personid= ? ");
			
			stmt.setInt(1, eigenschaftsauspraegung.getPersonID());
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
	 * Die Methode "findAllEigenschaftsauspraegungenByEigenschaftID" ermöglicht das Ausgeben aller Eigenschaftsauspraegungen
	 * zu einer Eigenschaft
	 * @param eigenschaftID - Fremdschlüssel aus der Tabelle Eigenschaft
	 * @return null - gibt in der catch-Methode null zurück um später Fehler besser zu lösen
	 * @return result - gibt als Vector alle Eigenschaftsauspraegungen zu einer EigenschaftID zurück
	 */
	
	public Vector <Eigenschaftsauspraegung> findAllEigenschaftsauspraegungenByEigenschaftID(int eigenschaftID) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Vector<Eigenschaftsauspraegung> result = new Vector<Eigenschaftsauspraegung>();

		try{
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM eigenschaftsauspraegung WHERE eigenschaftid = ?");
			stmt.setInt(1, eigenschaftID);
			
			/**
			 * Statement ausfüllen und an die DB senden
			 */
			
			ResultSet rs = stmt.executeQuery();
			

			while(rs.next()){
				
				Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();
				
				eigenschaftsauspraegung.setId(rs.getInt("id"));
				eigenschaftsauspraegung.setWert(rs.getString("wert"));
				eigenschaftsauspraegung.setPersonID(rs.getInt("personid"));
				eigenschaftsauspraegung.setStatus(rs.getInt("status"));
				eigenschaftsauspraegung.setEigenschaftID(rs.getInt("eigenschaftid"));
				
				/**
				 * Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(eigenschaftsauspraegung);
			}
		}
		
		catch(SQLException e2) {
			e2.printStackTrace();
			return null;
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
		return result;
	}
	
	/**
	 * Mit der Methode "findAllEigenschaftsauspraegungByPersonID" wird das Suchen einer Eigenschaftsausprägung im Vector<Eigenschaftsauspraegung> anhand der "personid" ermöglicht.
	 * @param personID - Fremdschlüssel aus der Tabelle Person
	 * @return result - hier wird ein Vector ausgegeben mit allen Eigenschaftsauspraegungen einer bestimmten Person
	 */
	
	public Vector <Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByPersonID (int personID) {
		
		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();
		
		Vector<Eigenschaftsauspraegung> result = new Vector<Eigenschaftsauspraegung>();
		
		try {
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM eigenschaftsauspraegung WHERE personid = ? ");
		stmt.setInt(1, personID);
		
		ResultSet rs = stmt.executeQuery();
		/**
		* Für jeden Eintrag Eigenschaftsauspraegung ein Eigenschaftsauspraegung-Objekt erstellt.
		*/	
				while(rs.next()) {
					Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();
					
					eigenschaftsauspraegung.setId(rs.getInt("id"));
					eigenschaftsauspraegung.setWert(rs.getString("wert"));
					eigenschaftsauspraegung.setPersonID(rs.getInt("personid"));
					eigenschaftsauspraegung.setStatus(rs.getInt("status"));
					eigenschaftsauspraegung.setEigenschaftID(rs.getInt("eigenschaftid"));
					
					/**
					 * Hinzufügen des neuen Objekts zum Ergebnisvektor
					 */
					
					result.addElement(eigenschaftsauspraegung);
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
	 * Die Methode "findAllEigeschaftauspraegungByWert" ist für die Suchfunktion. Es wird nach dem Wert der Eigenschaftsauspraegung geschaut.
	 * 
	 * @param wert - Ist die Eigenschaftsauspraegung die eingegeben wird. Spalte "wert".
	 * @return result - Vector des Objekts Eigenschaftsauspraegung
	 */
	public Vector<Eigenschaftsauspraegung> findAllEigenschaftsauspraegungByWert(Eigenschaftsauspraegung eig) {

		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();

		Vector<Eigenschaftsauspraegung> result = new Vector<Eigenschaftsauspraegung>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM `eigenschaftsauspraegung` WHERE `wert` LIKE '"+eig.getWert()+"' AND `eigenschaftid` = "+eig.getEigenschaftID()+"");
				
			ResultSet rs = stmt.executeQuery();
			/**
			 * Für jeden Eintrag Eigenschaftsauspraegung ein
			 * Eigenschaftsauspraegung-Objekt erstellt.
			 */
			while (rs.next()) {
				Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();

				eigenschaftsauspraegung.setId(rs.getInt("id"));
				eigenschaftsauspraegung.setWert(rs.getString("wert"));
				eigenschaftsauspraegung.setPersonID(rs.getInt("personid"));
				eigenschaftsauspraegung.setStatus(rs.getInt("status"));
				eigenschaftsauspraegung.setEigenschaftID(rs.getInt("eigenschaftid"));

				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */

				result.addElement(eigenschaftsauspraegung);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		/**
		 * Ergebnisvektor zurückgeben
		 */
		return result;
	}
}
	


