package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.TextCell;
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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

public class DialogBoxKontaktTeilen extends DialogBox {

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private Label abfrage = new Label("Wählen Sie die Teilnehmer aus: ");
	private Button sichern = new Button("Sichern");
	private Button abbrechen = new Button("Abbrechen");
	private ArrayList<Kontakt> kontakt = new ArrayList<>();
	private List<Nutzer> nutzerListe = new ArrayList<>();
	private Nutzer nutzerausdb = null;

	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox box = new SuggestBox(oracle);
	private CellTable<Nutzer> selectedNutzerCT = new CellTable<Nutzer>();
	private List<Nutzer> nutzerSuggestbox = new ArrayList<>();
	private Kontakt soloKontakt = new Kontakt();
	private ListDataProvider<Nutzer> nutzerDataProvider = new ListDataProvider<Nutzer>(nutzerSuggestbox);
	private Kontaktliste kontaktliste = new Kontaktliste();

	public DialogBoxKontaktTeilen(Kontakt kontakt) {
		this.soloKontakt = kontakt;
		sichern.addClickHandler(new CreateKontaktTeilhaberschaftClickHandler());
		run();
	}

	public DialogBoxKontaktTeilen(ArrayList<Kontakt> k) {
		kontakt = k;
		sichern.addClickHandler(new CreateKontaktTeilhaberschaftClickHandler());
		run();
	}

	public DialogBoxKontaktTeilen(Kontaktliste kontaktliste) {
		this.kontaktliste = kontaktliste;
		sichern.addClickHandler(new CreateKontaktlisteTeilhaberschaftClickHandler());
		run();
	}

	public void run() {
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
		kontaktmanagerVerwaltung.findAllNutzer(new getAllNutzerCallback());
		selectedNutzerCT.addColumn(nutzertxtColumn, "");
		selectedNutzerCT.addColumn(buttonColumn1, "");
		abbrechen.addClickHandler(new AbortTeilhaberschaftClickHandler());
		nutzerDataProvider.addDataDisplay(selectedNutzerCT);
		vPanel.add(abfrage);
		vPanel.add(box);
		vPanel.add(selectedNutzerCT);
		hPanel.add(sichern);
		hPanel.add(abbrechen);
		vPanel.add(hPanel);
		box.addKeyPressHandler(new NutzerHinzufuegenKeyPressHandler());
		box.setStylePrimaryName("gwt-SuggestBox");
		this.setTitle("Kontakte teilen");
		this.add(vPanel);
	}

	class getAllNutzerCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			for (Nutzer nutzer : result) {
				nutzerListe.add(nutzer);

			}
			for (Nutzer nutzer : nutzerListe) {
				oracle.add(nutzer.getMail());

			}
		}

	}

	public class NutzerHinzufuegenKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				if (box.getValue() == "") {
					Window.alert("Sie müssen eine E-Mail Adresse eingeben.");
				} else {

					Nutzer nutzer = new Nutzer();
					nutzer.setMail(box.getValue());

					nutzerSuggestbox.add(nutzer);
					box.setValue("");
					selectedNutzerCT.setRowCount(nutzerSuggestbox.size(), true);
					selectedNutzerCT.setRowData(0, nutzerSuggestbox);
				}
			}
		}

	}

	public class KontaktlisteFindNutzerByEmail implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Nutzer result) {

			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			kontaktmanagerVerwaltung.createTeilhaberschaft(kontaktliste.getId(), 0, 0, result.getId(), nutzer.getId(),
					new createTeilhaberschaftCallback());
		}

	}

	public class CreateKontaktTeilhaberschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.alert("hallo");
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			for (Nutzer nutzersuggest : nutzerSuggestbox) {
				kontaktmanagerVerwaltung.checkEmail(nutzersuggest.getMail(), new KontaktFindNutzerByMailCallback());

			}

		}

		public class KontaktFindNutzerByMailCallback implements AsyncCallback<Nutzer> {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Nutzer result) {
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				nutzer.setMail(Cookies.getCookie("mail"));
					if (kontakt.size() > 0) {
					for (Kontakt kontakt : kontakt) {
						kontaktmanagerVerwaltung.createTeilhaberschaft(0, kontakt.getId(), 0, result.getId(),
								nutzer.getId(), new createTeilhaberschaftCallback());

					}
				} else {
					kontaktmanagerVerwaltung.createTeilhaberschaft(0, soloKontakt.getId(), 0, result.getId(),
							nutzer.getId(), new createTeilhaberschaftCallback());

				}
			}

		}
	}

	public class CreateKontaktlisteTeilhaberschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			for (Nutzer nutzersuggest : nutzerSuggestbox) {
				kontaktmanagerVerwaltung.checkEmail(nutzersuggest.getMail(), new KontaktlisteFindNutzerByEmail());

			}

		}

	}

	class createTeilhaberschaftCallback implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			Window.alert("Teilhaberschaft erfolgreich erstellt");
			hide();
		}

	}

	public class AbortTeilhaberschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}

	}
}