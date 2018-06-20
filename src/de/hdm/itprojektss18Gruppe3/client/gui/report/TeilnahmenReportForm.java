package de.hdm.itprojektss18Gruppe3.client.gui.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import de.hdm.itprojektss18Gruppe3.client.AllKontaktTeilhaberschaften;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class TeilnahmenReportForm extends HorizontalPanel {

	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	private Button btAllKontaktTeilhaberschaften = new Button("Report Starten");

//	private ListBox lbNutzerEigentuemer = new ListBox();
//	private ListBox lbNutzerTeilhabender = new ListBox();

	/*
	 * SuggestBox Eigentümer
	 */
//	private MultiWordSuggestOracle oracleNutzerEigentümer = new MultiWordSuggestOracle();
//	private SuggestBox boxEigentümer = new SuggestBox(oracleNutzerEigentümer);

	/*
	 * SuggestBox Teilhabender
	 */
	private MultiWordSuggestOracle oracleNutzerTeilhabender = new MultiWordSuggestOracle();
	private SuggestBox boxTeilhabender = new SuggestBox(oracleNutzerTeilhabender);

	private List<Nutzer> nutzerListe = new ArrayList<>();
	private List<Nutzer> nutzerSuggestbox = new ArrayList<>();
	private CellTable<Nutzer> selectedNutzerCT = new CellTable<Nutzer>();
	private ListDataProvider<Nutzer> nutzerDataProvider = new ListDataProvider<Nutzer>(nutzerSuggestbox);
	private Nutzer nutzerausdb = null;

	private Label lbTeilhabender = new Label("Teilhaber: ");

	private VerticalPanel vpanel = new VerticalPanel();

	public TeilnahmenReportForm() {

		reportverwaltung.findNutzer(new NutzerForTeilhaberschaftCallback());

//		lbNutzerEigentuemer.setVisibleItemCount(1);
//		lbNutzerTeilhabender.setVisibleItemCount(1);
//
//		lbNutzerTeilhabender.setStylePrimaryName("listbox-report");
//		lbNutzerEigentuemer.setStylePrimaryName("listbox-report");
		btAllKontaktTeilhaberschaften.setStylePrimaryName("reportButton");
//		lbNutzerTeilhabender.addItem("Alle");

//		this.add(lbEigentuemer);
//		this.add(lbNutzerEigentuemer);
//		this.add(boxEigentümer);
		this.add(lbTeilhabender);
//		this.add(lbNutzerTeilhabender);
		this.add(boxTeilhabender);
		this.add(btAllKontaktTeilhaberschaften);

		RootPanel.get("content").add(this);

		btAllKontaktTeilhaberschaften.addClickHandler(new AllKontaktTeilhaberschaftenClickHandler());

		Column<Nutzer, String> nutzertxtColumn = new Column<Nutzer, String>(new TextCell()) {

			@Override
			public String getValue(Nutzer object) {
				return object.getMail();
			}
		};
		Column<Nutzer, String> buttonColumn1 = new Column<Nutzer, String>(new ButtonCell()) {
			@Override
			public String getValue(Nutzer x) {
				return "x";

			}

			@Override
			public void onBrowserEvent(Context context, Element elem, Nutzer object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if (event.getButton() == NativeEvent.BUTTON_LEFT) {
					nutzerDataProvider.getList().remove(object);
					selectedNutzerCT.setRowCount(nutzerSuggestbox.size(), true);
					selectedNutzerCT.setRowData(0, nutzerSuggestbox);
					selectedNutzerCT.redraw();
				}
			}
		};

		oracleNutzerTeilhabender.add("Alle");
//		boxEigentümer.setStylePrimaryName("gwt-SuggestBox");
		boxTeilhabender.setStylePrimaryName("gwt-SuggestBox");
//		reportverwaltung.findNutzer(new getNutzerCallback());
//		boxEigentümer.addKeyPressHandler(new NutzerHinzufuegenKeyPressHandler());
//		boxTeilhabender.addKeyPressHandler(new NutzerTeilhabenderHinzufuegenKeyPressHandler());
		nutzerDataProvider.addDataDisplay(selectedNutzerCT);

		this.add(vpanel);

	}

	public class FindNutzerByEmail implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Nutzer result) {
			nutzerausdb = result;

		}

	}

//	public class NutzerHinzufuegenKeyPressHandler implements KeyPressHandler {
//
//		@Override
//		public void onKeyPress(KeyPressEvent event) {
//			// TODO Auto-generated method stub
//			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
//				if (boxEigentümer.getValue() == "") {
//					// Window.alert("Sie müssen eine E-Mail Adresse eingeben.");
//				} else {
//
//					Nutzer nutzer = new Nutzer();
//					nutzer.setMail(boxEigentümer.getValue());
//
//					nutzerSuggestbox.add(nutzer);
//					boxEigentümer.setValue("");
//					selectedNutzerCT.setRowCount(nutzerSuggestbox.size(), true);
//					selectedNutzerCT.setRowData(0, nutzerSuggestbox);
//				}
//			}
//
//		}
//
//	}

	public class NutzerTeilhabenderHinzufuegenKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			// TODO Auto-generated method stub
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				if (boxTeilhabender.getValue() == "") {
					// Window.alert("Sie müssen eine E-Mail Adresse eingeben.");
				} else {

					Nutzer nutzer = new Nutzer();
					nutzer.setMail(boxTeilhabender.getValue());

					nutzerSuggestbox.add(nutzer);
					boxTeilhabender.setValue("");
					selectedNutzerCT.setRowCount(nutzerSuggestbox.size(), true);
					selectedNutzerCT.setRowData(0, nutzerSuggestbox);
				}
			}

		}

	}

//	class getNutzerCallback implements AsyncCallback<Vector<Nutzer>> {
//
//		@Override
//		public void onFailure(Throwable caught) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onSuccess(Vector<Nutzer> result) {
//			for (Nutzer nutzer : result) {
//				nutzerListe.add(nutzer);
//
//			}
//			for (Nutzer nutzer : nutzerListe) {
////				oracleNutzerEigentümer.add(nutzer.getMail());
//				oracleNutzerTeilhabender.add(nutzer.getMail());
//
//			}
//		}
//
//	}

	class NutzerForTeilhaberschaftCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden aus der Datenbank" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			// TODO
			for (Nutzer nutzer : result) {
//				oracleNutzerEigentümer.add(nutzer.getMail());
				oracleNutzerTeilhabender.add(nutzer.getMail());
			}
		}

	}

	class AllKontaktTeilhaberschaftenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			vpanel.clear();
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("email"));
			vpanel.add(new AllKontaktTeilhaberschaften(nutzer.getMail(),boxTeilhabender.getValue()));
			RootPanel.get("content").add(vpanel);
//			if (boxTeilhabender.getValue() == "Allen") {
//
//				vpanel.clear();
//				for (Nutzer nutzer : nutzerListe) {
//
//					vpanel.add(new AllKontaktTeilhaberschaften(boxEigentümer.getValue(), nutzer.getMail()));
//
//					RootPanel.get("content").add(vpanel);
//				}
//			} else {
//				if (boxEigentümer.getValue() == null) {
//					Window.alert("Für diesen Nutzer liegen keine gespeicherten Teilhaberschaften vor.");
//
//				} else {
//					if (boxTeilhabender.getValue() == null) {
//						Window.alert("Für diesen Nutzer liegen keine gespeicherten Teilhaberschaften vor.");
//					} else {
//						vpanel.clear();
//						vpanel.add(
//								new AllKontaktTeilhaberschaften(boxEigentümer.getValue(), boxTeilhabender.getValue()));
//						RootPanel.get("content").add(vpanel);
//
//					}
//				}
//			}

		}
	}
}

// if (lbNutzerTeilhabender.getSelectedValue() == "Alle") {
// vpanel.add();
//
// }
// vpanel.add(new
// AllKontaktTeilhaberschaften(lbNutzerEigentuemer.getSelectedValue(),
// lbNutzerTeilhabender.getSelectedValue()));
// RootPanel.get("content").add(vpanel);
//
// }
//
// }
// }
