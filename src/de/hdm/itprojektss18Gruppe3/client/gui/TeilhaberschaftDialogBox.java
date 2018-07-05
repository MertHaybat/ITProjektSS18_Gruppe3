package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.gargoylesoftware.htmlunit.javascript.host.Event;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.BrowserEvents;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.gui.DialogBoxKontaktTeilen.createTeilhaberschaftCallback;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * DialogBox zum Teilen und Betrachten der Teilhaberschaften
 * 
 * @author ersinbarut, giuseppeggalati, merthaybat
 *
 */
public class TeilhaberschaftDialogBox extends DialogBox {

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	private final MultiSelectionModel<EigenschaftsAuspraegungWrapper> ssmAuspraegung = new MultiSelectionModel<EigenschaftsAuspraegungWrapper>();
	private final MultiSelectionModel<Eigenschaft> eigenschaftModel = new MultiSelectionModel<Eigenschaft>();

	private FlexTable ftTeilhaberschaft = new FlexTable();

	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox box = new SuggestBox(oracle);
	private List<Nutzer> nutzerListe = new ArrayList<>();
	private List<Nutzer> nutzerSuggestbox = new ArrayList<>();
	private CheckBox kontaktTeilenCB = new CheckBox("Gesamten Kontakt teilen");


	private Nutzer nutzerausdb = null;
	private Label lb1 = new Label("Wählen Sie die Eigenschaften aus, die Sie teilen möchten: ");
	private Label lb2 = new Label("Mit wem möchten Sie diese Eigenschaften teilen: ");
	private Button b1 = new Button("Teilen");
	private Button b2 = new Button("Abbrechen");

	private Kontakt kontaktNeu = new Kontakt();

	private final Handler<EigenschaftsAuspraegungWrapper> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();

	private final Handler<Eigenschaft> eigenschaftSelectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();

	private CellTable<Eigenschaft> eigenschaftCT = new CellTable<Eigenschaft>();
	private ListDataProvider<Nutzer> nutzerDataProvider = new ListDataProvider<Nutzer>(nutzerSuggestbox);

	private CheckboxCell cbCell = new CheckboxCell(false, true);
	private ButtonCell btCell = new ButtonCell();
	private TextCell textCell = new TextCell();
	private ClickableTextCell clickableCell = new ClickableTextCell();

	private CellTableNutzer selectedNutzerCT = new CellTableNutzer();
	private CellTableNutzer.ButtonColumn buttonBox = selectedNutzerCT.new ButtonColumn(btCell){
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

	private CellTableNutzer.NutzerColumn nutzerBox = selectedNutzerCT.new NutzerColumn(textCell);

	private CellTableAuspraegungWrapper kt = new CellTableAuspraegungWrapper(ssmAuspraegung);
	private CellTableAuspraegungWrapper.CheckBoxBolumn checkBoxAuspraegung = kt.new CheckBoxBolumn(cbCell);
	private CellTableAuspraegungWrapper.WertEigenschaftColumn eigenschaftColumn = kt.new WertEigenschaftColumn(clickableCell);
	private CellTableAuspraegungWrapper.WertAuspraegungColumn auspraegungColumn = kt.new WertAuspraegungColumn(clickableCell);

	public TeilhaberschaftDialogBox(Kontakt kontakt) {
		kontaktNeu = kontakt;
		kontaktmanagerVerwaltung.findEigenschaftHybrid(kontaktNeu, new EigenschaftAuspraegungCallback());
		run();
	}

	public TeilhaberschaftDialogBox(List<Kontakt> kontakt) {

		for (Kontakt kontakte : kontakt) {

			kontaktmanagerVerwaltung.findEigenschaftHybrid(kontakte, new EigenschaftAuspraegungCallback());
		}
		kontaktNeu = kontakt.get(kontakt.size() - 1);
		run();
	}

	public void run() {
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.setAutoHideEnabled(true);

		this.setText("Kontakt/Kontakteigenschaften teilen");
		ftTeilhaberschaft.setWidget(0, 0, lb1);
		ftTeilhaberschaft.setWidget(1, 0, kt);
		ftTeilhaberschaft.setWidget(2, 0, new HTML("<br>"));
		ftTeilhaberschaft.setWidget(3, 0, kontaktTeilenCB);
		ftTeilhaberschaft.setWidget(4, 0, lb2);
		ftTeilhaberschaft.setWidget(5, 0, box);
		ftTeilhaberschaft.setWidget(6, 0, selectedNutzerCT);
		ftTeilhaberschaft.setWidget(7, 1, b1);
		ftTeilhaberschaft.setWidget(7, 2, b2);

		kontaktmanagerVerwaltung.findAllNutzer(new getAllNutzerCallback());

		eigenschaftCT.setSelectionModel(eigenschaftModel, eigenschaftSelectionEventManager);

		box.addKeyPressHandler(new NutzerHinzufuegenKeyPressHandler());
		b1.addClickHandler(new insertTeilhaberschaftClickHandler());
		b2.addClickHandler(new closeDialogBoxClickHandler());
		box.addKeyPressHandler(new NutzerHinzufuegenKeyPressHandler());

		ssmAuspraegung.addSelectionChangeHandler(new SelectionHandlerAuspraegung());

		kontaktTeilenCB.addClickHandler(new CheckBoxClickHandler());

		nutzerDataProvider.addDataDisplay(selectedNutzerCT);
		selectedNutzerCT.addColumn(nutzerBox, "");
		selectedNutzerCT.addColumn(buttonBox, "");



		kt.addColumn(checkBoxAuspraegung, ""); 
		kt.addColumn(eigenschaftColumn, "");
		kt.addColumn(auspraegungColumn, "");

		this.add(ftTeilhaberschaft);
	}
	public class CheckBoxClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			ssmAuspraegung.clear();
		}

	}
	public class SelectionHandlerAuspraegung implements SelectionChangeEvent.Handler{

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			kontaktTeilenCB.setValue(false);
		}

	}
	public class FindNutzerByEmail implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten" + caught.getMessage());
		}

		@Override
		public void onSuccess(Nutzer result) {
			nutzerausdb = result;

		}

	}

	public class NutzerHinzufuegenKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				boolean nutzerVorhanden = false;
				if (box.getValue() == "") {
				} else {
					for (Nutzer nutzerListe : nutzerListe) {
						if(nutzerListe.getMail() == box.getValue()){
							nutzerVorhanden = true;
						}
					}
					if(nutzerVorhanden == true){
						
						Nutzer nutzer = new Nutzer();
						nutzer.setMail(box.getValue());
						
						nutzerSuggestbox.add(nutzer);
						box.setValue("");
						selectedNutzerCT.setRowCount(nutzerSuggestbox.size(), true);
						selectedNutzerCT.setRowData(0, nutzerSuggestbox);
					} else {
						Window.alert("Angegebene E-Mail Adresse ist ungültig.");
						box.setValue("");
					}
				}
			}

		}

	}

	public class EigenschaftPreviewClickHander implements Handler<Eigenschaft> {
		@Override
		public void onCellPreview(CellPreviewEvent<Eigenschaft> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final Eigenschaft value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}

	public class PreviewClickHander implements Handler<EigenschaftsAuspraegungWrapper> {
		@Override
		public void onCellPreview(CellPreviewEvent<EigenschaftsAuspraegungWrapper> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final EigenschaftsAuspraegungWrapper value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}

	public class EigenschaftAuspraegungCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungWrapper> result) {

			kt.setRowData(0, result);
			kt.setRowCount(result.size(), true);

		}

	}

	public class closeDialogBoxClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}
	}

	public class insertTeilhaberschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if(nutzerSuggestbox.size() == 0){
				Window.alert("Sie müssen mindestens eine E-Mail Adresse angeben.");
			} else {
				if(ssmAuspraegung.getSelectedSet().size()>=1 || kontaktTeilenCB.getValue() == true){
					for (Nutzer nutzersuggest : nutzerSuggestbox) {
						kontaktmanagerVerwaltung.checkEmail(nutzersuggest.getMail(), new KontaktFindNutzerByMailCallback());
						
					}
					
				} else {
					Window.alert("Sie müssen eine Auswahl treffen");
				}
			}

		}

		public class KontaktFindNutzerByMailCallback implements AsyncCallback<Nutzer> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Laden der Daten" + caught.getMessage());
			}

			@Override
			public void onSuccess(Nutzer result) {
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				nutzer.setMail(Cookies.getCookie("mail"));

				if (kontaktTeilenCB.getValue() == true){
					kontaktmanagerVerwaltung.createTeilhaberschaft(0, kontaktNeu.getId(), 0, result.getId(),
							nutzer.getId(), new createTeilhaberschaftCallback());
				} else {
					List<EigenschaftsAuspraegungWrapper> eListe = new ArrayList<>();

					for (EigenschaftsAuspraegungWrapper auspraegung : ssmAuspraegung.getSelectedSet()) {
						eListe.add(auspraegung);
					}
					for (int i = 0; i < eListe.size(); i++) {

						kontaktmanagerVerwaltung.createTeilhaberschaft(0, kontaktNeu.getId(),
								eListe.get(i).getIDEigenschaftsauspraegungValue(), result.getId(), nutzer.getId(),
								new createTeilhaberschaftCallback());

					}

				}


			}
		}
	}

	class createTeilhaberschaftCallback implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten" + caught.getMessage());
		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			if (result == null) {
				Window.alert("Teilhaberschaft existiert bereits!");
			} else if (result.getTeilhabenderID() == result.getEigentuemerID()) {
				Window.alert("Sie können nichts mit sich selbst teilen!");
			} else {
				Window.alert("Teilhaberschaft erfolgreich erstellt");
			}
			hide();
			CustomTreeModel ctm = new CustomTreeModel();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);
			KontaktForm kf = new KontaktForm(kontaktNeu);

		}
	}


	class getAllNutzerCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten" + caught.getMessage());
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

}