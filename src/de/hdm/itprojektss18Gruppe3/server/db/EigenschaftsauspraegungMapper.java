package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;

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
	 * Die Methode ermöglicht das Einfügen von Objekten "Eigenschaftsauspraegung".
	 * Insert SQL = Erstellen von einem Datensatz und das Einfügen in die Datenbank.
	 *
	 *@return eigenschaftsauspraegung
	 *@see insertEigenschaftsauspraegung
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
	 * An dieser Stelle wird noch die Update und Delete -methode eigefügt
	 */
	
	public Vector <Eigenschaftsauspraegung> findAllEigenschaftsauspraegungenByEigenschaftID(int id) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Vector<Eigenschaftsauspraegung> result = new Vector<Eigenschaftsauspraegung>();

		try{
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM eigenschaftsauspraegung WHERE eigenschaftid =" +id);
			stmt.setInt(1, id);
			
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
		return null;
		
	}
}


