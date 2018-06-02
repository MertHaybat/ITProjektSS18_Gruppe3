package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.gui.DialogBoxKontaktTeilen.CreateKontaktTeilhaberschaftClickHandler.KontaktFindNutzerByMailCallback;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.EigenschaftsAuspraegungHybrid;
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

	private final MultiSelectionModel<EigenschaftsAuspraegungHybrid> ssmAuspraegung = new MultiSelectionModel<EigenschaftsAuspraegungHybrid>();
	private final MultiSelectionModel<Eigenschaft> eigenschaftModel = new MultiSelectionModel<Eigenschaft>();

	private final CheckboxCell cbCell = new CheckboxCell(false, true);
	private FlexTable ftTeilhaberschaft = new FlexTable();

	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox box = new SuggestBox(oracle);
	private List<Nutzer> nutzerListe = new ArrayList<>();
	private List<Nutzer> nutzerSuggestbox = new ArrayList<>();

	private Nutzer nutzerausdb = null;
	private Label lb1 = new Label("Wählen Sie die Eigenschaften aus, die Sie teilen möchten: ");
	private Label lb2 = new Label("Mit wem möchten Sie diese Eigenschaften teilen: ");
	private Button b1 = new Button("Teilen");
	private Button b2 = new Button("Abbrechen");
	private CellTable<Nutzer> selectedNutzerCT = new CellTable<Nutzer>();
	private Kontakt kontaktNeu = new Kontakt();

	private KontaktCellTable kt = new KontaktCellTable(kontaktNeu);

	private final Handler<EigenschaftsAuspraegungHybrid> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();

	private final Handler<Eigenschaft> eigenschaftSelectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();

	private CellTable<Eigenschaft> eigenschaftCT = new CellTable<Eigenschaft>();
	private ListDataProvider<Nutzer> nutzerDataProvider = new ListDataProvider<Nutzer>(nutzerSuggestbox);

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

		setText("Wählen Sie die zu teilenden Eigenschaften aus sowie die Person, mit der Sie den Kontakt teilen möchten");
		ftTeilhaberschaft.setWidget(0, 0, lb1);
		ftTeilhaberschaft.setWidget(1, 0, kt);
		ftTeilhaberschaft.setWidget(2, 0, lb2);
		ftTeilhaberschaft.setWidget(3, 0, box);
		ftTeilhaberschaft.setWidget(4, 0, selectedNutzerCT);
		ftTeilhaberschaft.setWidget(5, 1, b1);
		ftTeilhaberschaft.setWidget(5, 2, b2);

		Column<EigenschaftsAuspraegungHybrid, Boolean> cbColumn = new Column<EigenschaftsAuspraegungHybrid, Boolean>(
				cbCell) {
			@Override
			public Boolean getValue(EigenschaftsAuspraegungHybrid object) {
				return ssmAuspraegung.isSelected(object);
			}
		};
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

		box.setStylePrimaryName("gwt-SuggestBox");

		kontaktmanagerVerwaltung.findAllNutzer(new getAllNutzerCallback());

		eigenschaftCT.setSelectionModel(eigenschaftModel, eigenschaftSelectionEventManager);
		kt.setSelectionModel(ssmAuspraegung, selectionEventManager);

		eigenschaftCT.addCellPreviewHandler(new EigenschaftPreviewClickHander());
		kt.addCellPreviewHandler(new PreviewClickHander());
		box.addKeyPressHandler(new NutzerHinzufuegenKeyPressHandler());
		b1.addClickHandler(new insertTeilhaberschaftClickHandler());
		b2.addClickHandler(new closeDialogBoxClickHandler());
		kt.insertColumn(0, cbColumn);
		box.addKeyPressHandler(new NutzerHinzufuegenKeyPressHandler());

		nutzerDataProvider.addDataDisplay(selectedNutzerCT);
		// eigenschaftCT.addColumn(EigenschaftcbColumn, "");
		// eigenschaftCT.addColumn(eigenschaftColumn, "Eigenschaft");
		selectedNutzerCT.addColumn(nutzertxtColumn, "");
		selectedNutzerCT.addColumn(buttonColumn1, "");

		this.add(ftTeilhaberschaft);

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

	public class NutzerHinzufuegenKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			// TODO Auto-generated method stub
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				if (box.getValue() == "") {
					// Window.alert("Sie müssen eine E-Mail Adresse eingeben.");
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

	public class PreviewClickHander implements Handler<EigenschaftsAuspraegungHybrid> {
		@Override
		public void onCellPreview(CellPreviewEvent<EigenschaftsAuspraegungHybrid> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final EigenschaftsAuspraegungHybrid value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}

	public class EigenschaftAuspraegungCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungHybrid>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungHybrid> result) {

			// TODO
			// for (EigenschaftsAuspraegungHybrid eigenschaftsAuspraegungHybrid
			// : result) {
			//
			// hybridListe.add(eigenschaftsAuspraegungHybrid);
			// }
			kt.setRowData(0, result);
			kt.setRowCount(result.size(), true);
			// kt.redraw();

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

				List<EigenschaftsAuspraegungHybrid> eListe = new ArrayList<>();

				for (EigenschaftsAuspraegungHybrid auspraegung : ssmAuspraegung.getSelectedSet()) {
					eListe.add(auspraegung);
				}
				for (int i = 0; i < eListe.size(); i++) {

					kontaktmanagerVerwaltung.createTeilhaberschaft(0, 0, eListe.get(i).getAuspraegungid(),
							result.getId(), nutzer.getId(), new createTeilhaberschaftCallback());

				}

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

}