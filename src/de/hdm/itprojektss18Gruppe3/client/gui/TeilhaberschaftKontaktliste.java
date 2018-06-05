package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

/**
 * 
 * @author ersinbarut
 *
 */
public class TeilhaberschaftKontaktliste extends MainFrame {

	private static Kontaktliste kontaktlisteSelectedInTree = null;
	private Kontaktliste kontaktliste = new Kontaktliste();;

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	public TeilhaberschaftKontaktliste() {
		run();
	}

	public TeilhaberschaftKontaktliste(Kontaktliste selection) {
		this.kontaktliste = selection;
		this.setKontaktlisteSelectedInTree(selection);

		run();
	}
	public static Kontaktliste getKontaktlisteSelectedInTree() {
		return kontaktlisteSelectedInTree;
	}

	public void setKontaktlisteSelectedInTree(Kontaktliste kontaktlisteSelectedInTree) {
		this.kontaktlisteSelectedInTree = kontaktlisteSelectedInTree;
	}
	@Override
	protected void run() {
		KontaktCellList kontaktCellList = new KontaktCellList(kontaktliste);
		KontaktDataProvider kontaktDataProvider = new KontaktDataProvider();
		kontaktDataProvider.addDataDisplay(kontaktCellList.getKontaktCell());
		kontaktCellList.getKontaktCell().setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
	}
	private static class KontaktDataProvider extends AsyncDataProvider<Kontakt> {

		@Override
		protected void onRangeChanged(HasData<Kontakt> display) {
			Nutzer nutzerKontaktliste = new Nutzer();
			nutzerKontaktliste.setId(Integer.parseInt(Cookies.getCookie("id")));
			final Range range = display.getVisibleRange();

			kontaktmanagerVerwaltung.findKontaktlisteByTeilhabenderID(getKontaktlisteSelectedInTree().getId(),
					new AsyncCallback<Vector<Kontakt>>() {
						int start = range.getStart();

						ArrayList<Kontakt> kontaktToDisplay = new ArrayList<Kontakt>();

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim Auslesen aller Kontakte");

						}

						@Override
						public void onSuccess(Vector<Kontakt> result) {
							kontaktToDisplay.addAll(result);
							updateRowData(start, kontaktToDisplay);
						}
					});
		}
	}

}
