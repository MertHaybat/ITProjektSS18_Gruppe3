package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
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

	void createNutzer(AsyncCallback<Nutzer> callback);

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

	
}
