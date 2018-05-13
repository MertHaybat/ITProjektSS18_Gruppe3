package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Date;
import java.util.Vector;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
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

	void findTeilhaberschaftByTeilhabenderID(int teilhabenderID, AsyncCallback<Teilhaberschaft> callback);

	void findTeilhaberschaftByEigentuemerID(int eigentuemerID, AsyncCallback<Teilhaberschaft> callback);

	void findAllEigenschaften(AsyncCallback<Vector<Eigenschaft>> callback);

	void findAllKontakte(AsyncCallback<Vector<Kontakt>> callback);

	void checkEmail(String email, AsyncCallback<Nutzer> callback);

	void createEigenschaftsauspraegung(String wert, int personID, int status, String eigenschaft,
			AsyncCallback<Eigenschaftsauspraegung> callback);

	void createEigenschaft(String bezeichnung, AsyncCallback<Eigenschaft> callback);

	void createKontaktKontaktliste(int kontaktID, int kontaktlisteID, AsyncCallback<KontaktKontaktliste> callback);

	void findAllKontakteByKontaktlisteID(Kontaktliste k, AsyncCallback<Vector<Kontakt>> callback);

	void findKontaktByID(Kontakt k, AsyncCallback<Kontakt> callback);

	void findAllEigenschaftsauspraegungByPersonID(Person p, AsyncCallback<Vector<Eigenschaftsauspraegung>> callback);

	void findAllEigenschaftByEigenschaftIDFromPerson(Eigenschaft e, AsyncCallback<Vector<Eigenschaft>> callback);

	void findAllKontaktByEigenschaftsauspraegung(Eigenschaftsauspraegung e, AsyncCallback<Vector<Kontakt>> callback);

	void findAllEigenschaftsauspraegungByEigenschaftID(Eigenschaft e,
			AsyncCallback<Vector<Eigenschaftsauspraegung>> callback);

	void findAllEigenschaftsauspraegungByWert(Eigenschaftsauspraegung e,
			AsyncCallback<Vector<Eigenschaftsauspraegung>> callback);

	void deletePerson(Person p, AsyncCallback<Void> callback);

	void deleteKontaktKontaktliste(KontaktKontaktliste k, AsyncCallback<Void> callback);

	void deleteTeilhaberschaftByPersonID(Teilhaberschaft t, AsyncCallback<Void> callback);

	void deleteKontaktlisteByNutzerID(Kontaktliste k, AsyncCallback<Void> callback);

	void deleteEigenschaftsauspraegungByPersonID(Eigenschaftsauspraegung e, AsyncCallback<Void> callback);

	void findEigenschaftByBezeichnung(Eigenschaft e, AsyncCallback<Eigenschaft> callback);

	void findEigenschaftByEigenschaftID(Eigenschaft e, AsyncCallback<Eigenschaft> callback);

	void deleteKontaktByNutzerID(Kontakt k, AsyncCallback<Void> callback);

	void deleteKontaktKontaktlisteByKontaktID(KontaktKontaktliste k, AsyncCallback<Void> callback);
	
}
