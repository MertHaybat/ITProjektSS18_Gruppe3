package de.hdm.itprojektss18Gruppe3.client.gui;


import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
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

public class TeilhaberschaftKontakte extends MainFrame {

	private static Kontaktliste kontaktlisteSelectedInTree = null;
	private Kontakt kontakt = new Kontakt();
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
//	private Kontaktliste kontaktliste = new Kontaktliste();
	
	public TeilhaberschaftKontakte() {
		run();
	}

//	public TeilhaberschaftKontakte(Kontaktliste selection) {
//		this.kontaktliste = selection;
//		this.setKontaktlisteSelectedInTree(selection);
//
//		run();
//	}
	public static Kontaktliste getKontaktlisteSelectedInTree() {
		return kontaktlisteSelectedInTree;
	}

	public void setKontaktlisteSelectedInTree(Kontaktliste kontaktlisteSelectedInTree) {
		this.kontaktlisteSelectedInTree = kontaktlisteSelectedInTree;
	}
	
	@Override
	protected void run() {
		KontaktCellList kontaktCellList = new KontaktCellList();
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
			
			
			kontaktmanagerVerwaltung.findKontakteByTeilhabenderID(nutzerKontaktliste.getId(),
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
	static class KontaktCell extends AbstractCell<Kontakt> {

		@Override
		public void render(Context context, Kontakt value, SafeHtmlBuilder sb) {

			if (value == null) {
				return;
			}
			sb.appendHtmlConstant("<table>");
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped(value.getName());
			sb.appendHtmlConstant("</td><td>");
			if (value.getStatus() == 0) {
				sb.appendHtmlConstant("<img width=\"20\" src=\"images/singleperson.svg\">");

			} else if (value.getStatus() == 1) {

				sb.appendHtmlConstant("<img width=\"20\" src=\"images/group.svg\">");
			}
			sb.appendHtmlConstant("</td></table>");

		}
	}	
}
