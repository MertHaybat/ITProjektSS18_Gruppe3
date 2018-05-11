package de.hdm.itprojektss18Gruppe3.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;
/**
 * Verwalten eienr Verbindung zur Datenbank.
 * @author Mert; In Anlehnung an das Bankprojekt
 *
 */
public class DBConnection {
	/**
	 * Die Klasse DBConnection wird nur einmal instantiiert. 
	 * Dies nennt sich Singleton. Diese Variable ist durch den Bezeichner
	 * static nur einmal für sämtliche eventuellen Instanzen dieser Klasse
	 * vorhanden.
	 * 
	 * @see TeilhaberschaftMapper.teilhaberschaftMapper()
	 * @see KontaktMapper.kontaktMapper()
	 * @see KontaktlisteMapper.kontaktlisteMapper()
	 * @see EigenschaftMapper.eigenschaftMapper()
	 * @see EigenschaftsauspraegungMapper.eigenschaftsauspraegungMapper()
	 * @see PersonMapper.personMapper()
	 * @see NutzerMapper.nutzerMapper()
	 * @see KontaktKontaktliste.kontaktKontaktlisteMapper()
	 */
	private static Connection con = null;
	
	/**
	 * Die URL, mit deren Hilfe die Datenbank angesprochen wird.
	 */
    private static String googleUrl = "jdbc:google:mysql://bankproject-154007:bankproject/bankproject?user=demo&password=demo";
    private static String localUrl = "jdbc:mysql://127.0.0.1:3306/itprojektgruppe3?user=root&password=";

    /**
     * Diese statische Methode kann aufgerufen werden durch 
     * DBConnection.connection(). 
     * Sie stellt die Singleton-Eigenschaft sicher.
     * 
     * @return DBConnection Objekt mit "con"
     * @see con
     */
	public static Connection connection() {
		if (con == null){
			String url = null;
			try{
				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production){
					// Wenn das SystemUmfeld in der GoogleCloud ist wird diese Bedingung erfüllt
					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleUrl;
				} else {
					// Bei der Lokalen MySQL Instanz wird diese Bedingung erfüllt.
					Class.forName("com.mysql.jdbc.Driver");
					url = localUrl;
				}
				/**
				 * Nun wird mit den Verbindungsinformation in der Variable url 
				 * eine Verbindung von dem DriverManager aufgebaut.
				 */
				con = DriverManager.getConnection(url);
			} catch (Exception e){
				con = null;
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		
		// Zurückgeben der Verbindung
		return con;	
	}
	
}