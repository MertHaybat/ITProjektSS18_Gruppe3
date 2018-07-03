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


	private Label lbTeilhabender = new Label("Teilhaber: ");

	private VerticalPanel vpanel = new VerticalPanel();

	public TeilnahmenReportForm() {

		reportverwaltung.findNutzer(new NutzerForTeilhaberschaftCallback());

		btAllKontaktTeilhaberschaften.setStylePrimaryName("reportButton");

		this.add(lbTeilhabender);
		this.add(boxTeilhabender);
		this.add(btAllKontaktTeilhaberschaften);

		RootPanel.get("contentReport").add(this);

		btAllKontaktTeilhaberschaften.addClickHandler(new AllKontaktTeilhaberschaftenClickHandler());

		boxTeilhabender.setStylePrimaryName("gwt-SuggestBox");
		nutzerDataProvider.addDataDisplay(selectedNutzerCT);

		this.add(vpanel);

	}

	class NutzerForTeilhaberschaftCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden aus der Datenbank" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			// TODO
			for (Nutzer nutzer : result) {
				oracleNutzerTeilhabender.add(nutzer.getMail());
			}
		}

	}

	class AllKontaktTeilhaberschaftenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(boxTeilhabender.getValue().equals("")){
				Window.alert("Bitte geben Sie eine E-Mail Adresse ein.");
			} else {
				
				vpanel.clear();
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				nutzer.setMail(Cookies.getCookie("email"));
				vpanel.add(new AllKontaktTeilhaberschaften(nutzer.getMail(), boxTeilhabender.getValue()));
				RootPanel.get("contentReport").add(vpanel);
			}

		}
	}
}
