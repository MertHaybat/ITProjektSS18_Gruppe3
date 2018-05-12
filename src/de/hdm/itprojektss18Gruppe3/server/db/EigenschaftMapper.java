package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;

public class EigenschaftMapper {
	
	/**
	 * Die Klasse EigenschaftMapper wird nur einmal instantiiert.
	 * Hier spricht man von einem sogenannten Singleton.
	 * Durch static nur einmal vorhanden.
	 * 
	 * @see eigenschaftMapper
	 * */
	private static EigenschaftMapper eigenschaftMapper = null;
	
	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new"
	 * neue Instanzen dieser Klasse zu erzeugen.
	 * 
	 * @see EigenschaftMapper
	 */
	
	protected EigenschaftMapper() {
	};
	
	/**
	 * Kann durch EigenschaftMapper eigenschaftMapper  aufgerufen werden.
	 * Stellt die Singleton-Eigenschaft sicher.
	 * Methode soll nur über diese statische Methode aufgerufen werden
	 * @return eigenschaftMapper
	 * @see eigenschaftMapper
	 */
	
	public static EigenschaftMapper eigenschaftMapper() {
		if (eigenschaftMapper == null){
			eigenschaftMapper = new EigenschaftMapper();
		}
		return eigenschaftMapper;
		}
	
	/**
	 * Die Methode ermöglicht das Einfügen von Objekten "Eigenschaft".
	 * Insert SQL = Erstellen von einem Datensatz und das Einfügen in die Datenbank.
	 *
	 *@return eigenschaft
	 *@see insertEigenschaft
	 */
	
	public Eigenschaft createEigenschaft (Eigenschaft eigenschaft) {
		
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
					+ "FROM eigenschaft ");
			
			if(rs.next()) {
				
				/**
				 * Die Variable erhält den höchsten Primärschlüssel, um 1 inkrementiert
				 */
				eigenschaft.setId(rs.getInt("maxid")+1);
				
				/**
				 * Durchführung der Update-Operation via Prepared Statement
				 */
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO eigenschaft(id, bezeichnung)" 
						+ "VALUES(?, ?) ",
						
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1,  eigenschaft.getId());
				stmt1.setString(2, eigenschaft.getBezeichnung());
				
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
		return eigenschaft;	
	}
	
	/**
	 * Mit dieser Methode updateEigenschaft wird das Aktualisieren eines Objektes vom "Eigenschaft" ermöglicht.
	 * 
	 * @param eigenschaft
	 * @return eigenschaft vom Objekt Eigenschaft
	 */
	public Eigenschaft updateEigenschaft(Eigenschaft eig) {
	
		/**
		 * Verbindung zur DB Connection aufbauen
		 */	
		Connection con = DBConnection.connection();
		
		try {

			/**
			 * Durchführung der Einfüge-Operation via Prepared Statement
			 */
			PreparedStatement stmt = con.prepareStatement("UPDATE `eigenschaft` SET `bezeichnung`= ? WHERE id = ?");

			stmt.setString(1, eig.getBezeichnung());
			stmt.setInt(2, eig.getId());

			stmt.executeUpdate();

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

		return eig;
	}
	
	
	/**
	 * Diese Methode durchläuft den kompletten Vektor und liefert alle Datensätze, die im Vector<Eigenschaft> gespeichert sind.
	 * @return result ist ein Vektor des Typs Eigenschaft
	 * @see findAllEigenschaft
	 */
	
	public Vector<Eigenschaft> findAllEigenschaften(){
	
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		/**
		 * Try and Catch beschreibung einfügen
		 */
		
		try{
			
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM eigenschaft ORDER BY id ASC ");
			
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Fuer jeden Eintrag im Suchergebnis wird nun ein Eigenschafts-Objekt erstellt.
			 */	
			while(rs.next()){
				Eigenschaft eigenschaft = new Eigenschaft();
			eigenschaft.setId(rs.getInt("id"));
			eigenschaft.setBezeichnung(rs.getString("bezeichnung"));
			
			/**
			 * Hinzufuegen des neuen Objektes zum Ergebnisvektor
			 */	
			result.addElement(eigenschaft);
			}
		}
		catch(SQLException e2) {
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
		/**
		 * Ergebnisvektor zurueckgeben
		 */	
		return result;
		
	}
	
	/**
	 * Die Methode findEigenschaftByEigenschaftID ermöglicht das Suchen nach einer Eigenschaft über den Primärschlüssel
	 * 
	 * @param id
	 * @return eigenschaft
	 * @return null
	 * @see findByEigenschaftID
	 */
	
	public Vector <Eigenschaft> findAllEigenschaftByEigenschaftID(int id) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Vector<Eigenschaft> result = new Vector<Eigenschaft>();


		try{
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM eigenschaft WHERE id = " +id);
			stmt.setInt(1, id);
			
			/**
			 * Statement ausfüllen und an die DB senden
			 */	
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				Eigenschaft eigenschaft = new Eigenschaft();
				
				eigenschaft.setId(rs.getInt("id"));
				eigenschaft.setBezeichnung(rs.getString("bezeichnung"));
				
				/**
				 * Hinzufuegen des neuen Objektes zum Ergebnisvektor
				 */	
				result.addElement(eigenschaft);
			}
		}
		catch(SQLException e2){
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
	
