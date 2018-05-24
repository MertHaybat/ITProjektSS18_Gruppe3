package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Date;
import java.util.Vector;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.EigenschaftsAuspraegungHybrid;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Person;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
<<<<<<< HEAD
 * Das asynchrone Gegenstück des Interface {@link KontaktmanagerAdministration}.
=======
 * Das asynchrone Gegenst�ck des Interface {@link KontaktmanagerAdministration}.
>>>>>>> refs/heads/Mert
 * Es wird semiautomatisch durch das Google Plugin erstellt und gepflegt.
 * 
 * @version 1.30 11 May 2018
 * @author Thomas, Mert
 */
public interface KontaktmanagerAdministrationAsync {

	void init(AsyncCallback<Void> callback);

	void createNutzer(String mail, AsyncCallback<Nutzer> callback);

	void createKontakt(String name, Date erzeugungsdatum, Date modifikationsdatum, int status, int nutzerID,
			AsyncCallback<Kontakt> callback);

	void createKontaktliste(String bezeichnung, int nutzerID, AsyncCallback<Kontaktliste> callback);

	void findAllKontaktlisteByNutzerID(int nutzerID, AsyncCallback<Vector<Kontaktliste>> callback);

	void createTeilhaberschaft(int kontaktlisteID, int kontaktID, int eigenschaftsauspraegungID, int teilhabenderID,
			int eigentuemerID, AsyncCallback<Teilhaberschaft> callback);

	void findTeilhaberschaftByEigentuemerID(int eigentuemerID, AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findAllEigenschaften(AsyncCallback<Vector<Eigenschaft>> callback);

	void findAllKontakte(AsyncCallback<Vector<Kontakt>> callback);

	void checkEmail(String email, AsyncCallback<Nutzer> callback);

	void createEigenschaftsauspraegung(String wert, int personID, int status, String eigenschaft,
			AsyncCallback<Eigenschaftsauspraegung> callback);

	void createEigenschaft(String bezeichnung, AsyncCallback<Eigenschaft> callback);

	void createKontaktKontaktliste(int kontaktID, int kontaktlisteID, AsyncCallback<KontaktKontaktliste> callback);

	void findAllKontakteByKontaktlisteID(Kontaktliste k, AsyncCallback<Vector<Kontakt>> callback);

	void findKontaktByID(int kontaktID, AsyncCallback<Kontakt> callback);

	void findAllEigenschaftsauspraegungByPersonID(Person p, AsyncCallback<Vector<Eigenschaftsauspraegung>> callback);

	void findEigenschaftByEigenschaftIDFromPerson(Eigenschaftsauspraegung e, AsyncCallback<Eigenschaft> callback);

	void findAllKontaktByEigenschaftsauspraegung(Eigenschaftsauspraegung e, AsyncCallback<Vector<Kontakt>> callback);

	void findAllEigenschaftsauspraegungByEigenschaftID(Eigenschaft e,
			AsyncCallback<Vector<Eigenschaftsauspraegung>> callback);

	void deleteKontaktKontaktlisteByKontaktlisteID(KontaktKontaktliste k, AsyncCallback<Void> callback);

	void deleteKontaktlisteByNutzerID(Kontaktliste k, AsyncCallback<Void> callback);

	void deleteEigenschaftsauspraegungByPersonID(Eigenschaftsauspraegung e, AsyncCallback<Void> callback);

	void findEigenschaftByBezeichnung(String e, AsyncCallback<Eigenschaft> callback);

	void findEigenschaftByEigenschaftID(int eigenschaftID, AsyncCallback<Eigenschaft> callback);

	void deleteKontaktByOwner(Kontakt k, AsyncCallback<Void> callback);

	void deleteKontaktKontaktlisteByKontaktID(KontaktKontaktliste k, AsyncCallback<Void> callback);

	void findAllKontakteByEigenschaftUndEigenschaftsauspraegungen(Eigenschaft e, Eigenschaftsauspraegung auspraegung, AsyncCallback<Vector<Kontakt>> callback);
	
	void deleteTeilhaberschaftByID(Teilhaberschaft t, AsyncCallback<Void> callback);

	void saveEigenschaft(Eigenschaft eig, AsyncCallback<Void> callback);
	
	void saveEigenschaftsauspraegung(Eigenschaftsauspraegung aus, AsyncCallback<Void> callback);
	
	void saveKontakt(Kontakt k, AsyncCallback<Void> callback);
	
	void saveKontaktliste(Kontaktliste kliste, AsyncCallback<Void> callback);
			
	void saveNutzer(Nutzer n, AsyncCallback<Void> callback);
			
	void savePerson(Person p, AsyncCallback<Void> callback);
			
	void saveTeilhaberschaft(Teilhaberschaft t, AsyncCallback<Void> callback);

	void deleteAllTeilhaberschaftByOwner(Nutzer n, AsyncCallback<Void> callback);

	void deleteAllEigenschaftsauspraegungByNutzer(Nutzer n, AsyncCallback<Void> callback);

	void deleteAllKontaktKontaktlisteByOwner(Nutzer n, AsyncCallback<Void> callback);

	void deleteAllKontaktByOwner(Nutzer n, AsyncCallback<Void> callback);

	void deleteAllKontaktlisteByOwner(Nutzer n, AsyncCallback<Void> callback);

	void deleteNutzer(Nutzer n, AsyncCallback<Void> callback);

	void deleteTeilhaberschaftByKontakt(Kontakt k, AsyncCallback<Void> callback);

	void deleteEigenschaftsauspraegungByKontakt(Kontakt k, AsyncCallback<Void> callback);

	void deleteKontaktKontaktlisteByKontakt(Kontakt k, AsyncCallback<Void> callback);

	void suchFunktion(Nutzer nutzer, Eigenschaft eigenschaft, Eigenschaftsauspraegung auspraegung,
			AsyncCallback<Vector<Kontakt>> callback);

	void findAllEigenschaftsauspraegungByWertAndEigenschaft(Eigenschaftsauspraegung e, Eigenschaft eigenschaft,
			AsyncCallback<Vector<Eigenschaftsauspraegung>> callback);

	void findAllKontaktByNutzerID(int nutzerID, AsyncCallback<Vector<Kontakt>> callback);

	void findAllKontakteByTeilhabenderID(int teilhabenderID, AsyncCallback<Vector<Kontakt>> callback);

	void deleteEigenschaftsauspraegungById(Eigenschaftsauspraegung e, AsyncCallback<Void> callback);

	void deleteTeilhaberschaftByEigenschaftsauspraegungID(Teilhaberschaft t, AsyncCallback<Void> callback);

	void deleteTeilhaberschaftById(Teilhaberschaft t, AsyncCallback<Void> callback);

	void findAllTeilhaberschaftenByTeilhabenderID(int teilhaberschaftID, AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findAllKontaktByTeilhaberschaften(int teilhabenderID, int eigentuemerID,
			AsyncCallback<Vector<Kontakt>> callback);

	void findNutzerByID(int nutzerID, AsyncCallback<Nutzer> callback);

	void deleteKontaktlisteByID(Kontaktliste k, AsyncCallback<Void> callback);

	void deleteKontaktKontaktlisteByID(KontaktKontaktliste kk, AsyncCallback<Void> callback);

	void findAllNutzer(AsyncCallback<Vector<Nutzer>> callback);

	void findEigenschaftHybrid(Person person,
			  AsyncCallback<Vector<EigenschaftsAuspraegungHybrid>> callback);

	void deleteKontaktByID(Kontakt k, AsyncCallback<Void> callback);

	
	
}
