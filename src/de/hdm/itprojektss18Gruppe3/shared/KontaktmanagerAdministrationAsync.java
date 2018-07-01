package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Vector;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktlisteWrapper;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Person;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * Das asynchrone Gegenstück des Interface {@link KontaktmanagerAdministration}.
 * Es wird semiautomatisch durch das Google Plugin erstellt und gepflegt.
 * 
 * @version 1.30 11 May 2018
 * @author Thomas, Mert
 */
public interface KontaktmanagerAdministrationAsync {

	void init(AsyncCallback<Void> callback);

	void createNutzer(String mail, AsyncCallback<Nutzer> callback);

	void createKontakt(String name, int status, int nutzerID, AsyncCallback<Kontakt> callback);

	void createKontaktliste(String bezeichnung, int nutzerID, int status, AsyncCallback<Kontaktliste> callback);

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

	void findEigenschaftHybrid(Kontakt kontakt,
			  AsyncCallback<Vector<EigenschaftsAuspraegungWrapper>> callback);

	void deleteKontaktByID(Kontakt k, AsyncCallback<Void> callback);

	void deleteKontaktKontaktliste(KontaktKontaktliste kon, AsyncCallback<Void> callback);

	void deleteTeilhaberschaftByKontaktlisteID(Teilhaberschaft t, AsyncCallback<Void> callback);

	void findKontaktlisteByID(int kontaktlisteID, AsyncCallback<Kontaktliste> callback);

	void findAllKontaktlisteByTeilhaberschaft(int teilhabenderID, AsyncCallback<Vector<Kontaktliste>> callback);

	void findKontaktByName(Kontakt k, AsyncCallback<Vector<Kontakt>> callback);

	void findEigeneKontakteBySuche(Nutzer nutzer, Eigenschaftsauspraegung eigenschaftsauspraegung, String eigenschaft,
			AsyncCallback<Vector<Kontakt>> callback);

	void findTeilhaberschaftKontakteBySuche(Nutzer nutzer, Eigenschaftsauspraegung eigenschaftsauspraegung,
			String eigenschaft, AsyncCallback<Vector<Kontakt>> callback);

	void findTeilhaberUndEigeneKontakteBySuche(Nutzer nutzer, Eigenschaftsauspraegung eigenschaftsauspraegung,
			String eigenschaft, AsyncCallback<Vector<Kontakt>> callback);

	void findAllKontakteByEigentuemerID(int eigentuemerID, AsyncCallback<Vector<Kontakt>> callback);

	void findEigenschaftsauspraegungById(int eigenschaftsauspraegungID,
			AsyncCallback<Eigenschaftsauspraegung> callback);

	void findKontakteByTeilhabenderID(int teilhabenderID, AsyncCallback<Vector<Kontakt>> callback);

	void findKontaktlisteByTeilhabenderID(int teilhabenderID, AsyncCallback<Vector<Kontaktliste>> callback);

	void findKontaktlistByName(String bezeichnung, int nutzerid, AsyncCallback<Kontaktliste> callback);

	void findNutzerByKontaktID(int kontaktID, AsyncCallback<Vector<Nutzer>> callback);

	void findTeilhaberschaftByKontaktID(int kontaktID, AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findNutzerTeilhaberschaftKontaktWrapperByTeilhaberschaft(int teilhabenderID,
			AsyncCallback<Vector<NutzerTeilhaberschaftKontaktWrapper>> callback);

	void findAuspraegungTeilhaberschaftKontaktWrapperByTeilhaberschaft(int teilhabenderID,
			AsyncCallback<Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>> callback);

	void findNutzerTeilhaberschaftKontaktlisteWrapper(int teilhabenderID,
			AsyncCallback<Vector<NutzerTeilhaberschaftKontaktlisteWrapper>> callback);

	void findTeilhabendeKontakteAuspraegungen(int teilhabenderID, AsyncCallback<Vector<Kontakt>> callback);

	void deleteTeilhaberschaftByTeilhaberschaft(Teilhaberschaft t, AsyncCallback<Void> callback);

	void findEigenschaftsauspraegungAndKontaktByTeilhaberschaft(int teilhabenderID,
			AsyncCallback<Vector<NutzerTeilhaberschaftKontaktWrapper>> callback);

	void findEigenschaftAndAuspraegungByKontakt(int nutzerID, int kontaktID,
			AsyncCallback<Vector<EigenschaftsAuspraegungWrapper>> callback);

	void findTeilhaberschaftByAuspraegungIDAndNutzerID(int nutzerid, int auspraegungid,
			AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findTeilhaberschaftByKontaktAndTeilhaber(int nutzerid, int kontaktid,
			AsyncCallback<Vector<Teilhaberschaft>> callback);

	void findTeilhaberschaftString(int nutzerid, Vector<Eigenschaftsauspraegung> auspraegung, AsyncCallback<String> callback);

	void findAllKontakteAndTeilhaberschaftenByNutzer(Nutzer nutzer,
			AsyncCallback<Vector<NutzerTeilhaberschaftKontaktWrapper>> callback);

	void findKontaktTeilhaberschaftByEigentuemerAndTeilhaber(int eigentuemerID, int teilhabenderID,
			AsyncCallback<Vector<Kontakt>> callback);

	void findAuspraegungTeilhaberschaftEigentuemer(int eigentuemerID,
			AsyncCallback<Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>> callback);

	void findKontaktlisteTeilhaberschaftEigentuemer(int eigentuemerID,
			AsyncCallback<Vector<NutzerTeilhaberschaftKontaktlisteWrapper>> callback);

	void findKontaktTeilhaberschaftEigentuemer(int eigentuemerID,
			AsyncCallback<Vector<NutzerTeilhaberschaftKontaktWrapper>> callback);

	void findAllKontakteEigenschaftAuspraegung(Nutzer nutzer, Eigenschaft eigenschaft,
			Eigenschaftsauspraegung auspraegung, AsyncCallback<Vector<Kontakt>> callback);

	
	
}
