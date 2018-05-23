package de.hdm.itprojektss18Gruppe3.client;

import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;

public class AllKontakteByKontaktliste {

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	public AllKontakteByKontaktliste(Kontaktliste k) {
		kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(k, new FindAllKontakteByKontaktlisteCallback());
}

	class FindAllKontakteByKontaktlisteCallback implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler");
			
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			Window.alert("Es wurden so viele Kontakte gefunden: " + result.size());
		}
		
	}


}