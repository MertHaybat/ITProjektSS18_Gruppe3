package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;

/**
 * Die Mapper-Klasse "EigenschaftMapper" ermöglicht das Abbilden von Objekten "Eigenschaft" in einer relationalen Datenbank. 
 * Dabei sind in der Mapper-Klassen mehrere Methoden wie das erstellen, löschen, modifizieren 
 * oder das Suchen nach mehreren Möglichkeiten etc. implementiert. Somit kann ein Objekt für die Datenbank-Struktur umgewandelt, 
 * aber es kann auch von der Datenbank-Struktur als Objekt wieder umgewandelt werden.
 * 
 * @version @version 1.10 14 May 2018
 * @author Giuseppe Galati und wahidvanaki
 *
 */
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
	 * Die Methode "createEigenschaft" ermöglicht das Einfügen von Objekten "Eigenschaft".
	 *
	 *@return eigenschaft 
	 *@see createEigenschaft
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
	 * Mit dieser Methode "updateEigenschaft" wird das Aktualisieren eines Objektes vom "Eigenschaft" ermöglicht.
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
	 * Diese Methode "findAllEigenschaften" durchläuft den kompletten Vektor und liefert alle Datensätze, die im Vector<Eigenschaft> gespeichert sind.
	 * @return result - ist ein Vektor des Typs Eigenschaft - gibt alle Eigenschaften zurück, die in der Datenbank hinterlegt wurden
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
	 * Die Methode "findEigenschaftByEigenschaftID" ermöglicht das Suchen nach einer Eigenschaft über den Primärschlüssel
	 * @param eig: Objekt der Klasse Eigenschaft: hier wird die EigenschaftID rausgenommen
	 * @return eigenschaft2: gibt die Eigenschaft zurück
	 * @see findByEigenschaftID
	 */
	
	public Eigenschaft findEigenschaftByEigenschaftID(Eigenschaft eig) {
		
		/**
		 * Verbindung zur DB Connection
		 */		
		Connection con = DBConnection.connection();
		
		Eigenschaft eigenschaft2 = new Eigenschaft();


		try{
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM eigenschaft WHERE id = ?");
			stmt.setInt(1, eig.getId());
			
			/**
			 * Statement ausfüllen und an die DB senden
			 */	
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				
				Eigenschaft eigenschaft = new Eigenschaft();
				
				eigenschaft.setId(rs.getInt("id"));
				eigenschaft.setBezeichnung(rs.getString("bezeichnung"));
				
				eigenschaft2=eigenschaft;
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
		return eigenschaft2;
		
	}
	
	/**
	 * Die Methode "findEigenschaftByBezeichnung" sucht die Eigenschaft anhand der Bezeichnung 
	 * @param eigenschaft: Objekt der Klasse Eigenschaft: hier wird die Bezeichnung rausgenommen
	 * @return eig: gibt die Bezeichnung der Eigenschaft zurück
	 */
	public Eigenschaft findEigenschaftByBezeichnung(Eigenschaft eigenschaft){
		
		
		Connection con = DBConnection.connection();
		
		Eigenschaft eig = new Eigenschaft();

		try {

			/**
			 * Durchführung der Einfüge-Operation via Prepared Statement
			 */
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM `eigenschaft` WHERE `bezeichnung` LIKE ?");

			stmt.setString(1, eigenschaft.getBezeichnung());

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				Eigenschaft eigenschaft2 = new Eigenschaft();

				eigenschaft2.setId(rs.getInt("id"));
				eigenschaft2.setBezeichnung(rs.getString("bezeichnung"));
				
				eig=eigenschaft2;
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

		return eig;

	}
}