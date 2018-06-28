package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.client.Menubar;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.gui.DialogBoxKontaktlisteHinzufuegen.AddClickHandler;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

public class AllKontaktView extends MainFrame {

	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel allKontakteCellTableContainer = new HorizontalPanel();
	private Button kontaktlisteLoeschen = new Button("Kontaktliste löschen");
	private Button teilhaberschaftButton = new Button("Teilhaberschaft löschen");
	private Button kontaktHinzufuegenButton = new Button("Kontakt hinzufügen");
	private Button addKontaktButton = new Button("Neuer Kontakt");
	private Button deleteKontaktButton = new Button("Kontakt löschen");
	private Button addKontaktToKontaktlistButton = new Button("Kontakt in Kontaktliste");
	private Button addTeilhaberschaftKontaktButton = new Button("Kontakt teilen");
	private Button addKontaktlisteButton = new Button("Neue Kontaktliste");
	private Button deleteKontaktfromListeButton = new Button("Kontakt entfernen");
	private Button addTeilhaberschaftKontaktlisteButton = new Button("Kontaktliste teilen");
	private Button zurueckButton = new Button("Alle Kontakte");
	private Button teilhaberschaftVerwaltenButton = new Button("Teilhaberschaft verwalten");

	private HTML headline = new HTML();

	private Teilhaberschaft teilhaberschaft = null;

	private static ArrayList<Kontakt> allKontakteSelectedArrayList = new ArrayList<>();
	private ArrayList<Kontakt> allKontakteByUserArrayList = new ArrayList<>();
	private List<Kontakt> allSelectedKontakte = new ArrayList<>();
	private static ProvidesKey<Kontakt> keyProvider;

	private Anchor signOutLink = new Anchor();
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	private Nutzer nutzerausdb = null;
	private static Kontaktliste kontaktliste = null;

	private Kontakt kontakt = null;

	private CellTableKontakt allKontakteCellTable = new CellTableKontakt();

	private CheckboxCell checkBoxCell = new CheckboxCell(true, false);
	private ButtonCell buttonCell = new ButtonCell();
	private TextCell textCell = new TextCell();
	private ClickableTextCell clickCell = new ClickableTextCell();
	private CellTableKontakt.KontaktnameColumn kontaktnameColumn = allKontakteCellTable.new KontaktnameColumn(
			clickCell);
	private CellTableKontakt.CheckColumn checkColumn = allKontakteCellTable.new CheckColumn(checkBoxCell);
	private CellTableKontakt.IconColumn iconColumn = allKontakteCellTable.new IconColumn(textCell);

	private KontaktlistView klisteView = new KontaktlistView();

	public AllKontaktView() {
		Kontaktliste dummyKontaktliste = new Kontaktliste();
		dummyKontaktliste.setBezeichnung("Eigene Kontakte");
		this.kontaktliste = dummyKontaktliste;
		headline = new HTML("Alle Kontakte in Ihrem Kontaktmanager");
		super.onLoad();
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AllKontaktByNutzerCallback());

	}

	public AllKontaktView(Kontaktliste k) {

		kontaktliste = k;

		allKontakteSelectedArrayList.clear();

		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		if (k.getBezeichnung().equals("Empfangene Kontakte")) {
			headline = new HTML("Alle Kontakte, die Sie als Empfänger geteilt bekommen haben");
			teilhaberschaftButton.addClickHandler(new TeilhaberschaftButtonClickHandler());
			kontaktmanagerVerwaltung.findEigenschaftsauspraegungAndKontaktByTeilhaberschaft(nutzer.getId(),
					new TeilhaberschaftKontakteCallback());
		} else {
			headline = new HTML("Alle Kontakte in der Kontaktliste " + k.getBezeichnung());
			kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(k, new AllKontaktByNutzerCallback());

		}
		super.onLoad();
	}

	public HorizontalPanel getAllKontakteCellTableContainer() {
		return allKontakteCellTableContainer;
	}

	public void setAllKontakteCellTableContainer(HorizontalPanel allKontakteCellTableContainer) {
		this.allKontakteCellTableContainer = allKontakteCellTableContainer;
	}

	public void run() {
		
		Menubar mb = new Menubar(kontaktliste, allKontakteSelectedArrayList);

		allKontakteCellTable.setEmptyTableWidget(new Label("Diese Kontaktliste ist leer"));

		allKontakteCellTableContainer.clear();
		allKontakteCellTableContainer.add(allKontakteCellTable);

		allKontakteCellTable.addCellPreviewHandler(new PreviewClickHander());

		iconColumn.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
		allKontakteCellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		allKontakteCellTable.setColumnWidth(checkColumn, 1, Unit.PCT);
		allKontakteCellTable.addColumn(kontaktnameColumn, "Kontaktname");
		allKontakteCellTable.setColumnWidth(kontaktnameColumn, 100, Unit.PCT);
		allKontakteCellTable.addColumn(iconColumn, "");
		allKontakteCellTable.setColumnWidth(iconColumn, 1, Unit.PCT);

		allKontakteCellTableContainer.setStylePrimaryName("cellListWidgetContainerPanel");
		vPanel.setStylePrimaryName("cellListWidgetContainerPanel");

		headline.setStylePrimaryName("h2");
		vPanel.add(headline);

		allKontakteCellTable.getSsmAuspraegung().addSelectionChangeHandler(new SelectionChangeHandlerCellTable());

		allKontakteCellTableContainer.clear();
		allKontakteCellTableContainer.add(allKontakteCellTable);

		vPanel.add(allKontakteCellTableContainer);

		RootPanel.get("content").add(vPanel);
	}

	public static class AddTeilhaberschaftKontaktlisteCommand implements Command {

		@Override
		public void execute() {
			if (kontaktliste != null) {
				DialogBoxKontaktTeilen dialogbox = new DialogBoxKontaktTeilen(kontaktliste);
				dialogbox.center();
			} else {
				Window.alert("Bitte wählen Sie zunächst die Kontaktliste aus, die geteilt werden soll!");
			}
		}
	}

	class TeilhaberschaftButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (allKontakteSelectedArrayList.isEmpty()) {
				Window.alert("Sie müssen mindestens eine Teilhaberschaft auswählen");
			} else {
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
				teilhaberschaft.setTeilhabenderID(nutzer.getId());

				for (Kontakt kontakt : allKontakteSelectedArrayList) {

					teilhaberschaft.setKontaktID(kontakt.getId());
					kontaktmanagerVerwaltung.deleteTeilhaberschaftByTeilhaberschaft(teilhaberschaft,
							new DeleteTeilhaberschaftCallback());
				}
			}
		}

	}

	public static class DeleteKontaktAusKontaktlisteCommand implements Command {

		@Override
		public void execute() {
			if (allKontakteSelectedArrayList.size() != 0) {
				KontaktFromKontaktlisteLoeschenDialogBox deleteKontakt = new KontaktFromKontaktlisteLoeschenDialogBox(
						allKontakteSelectedArrayList, kontaktliste);
				deleteKontakt.center();
			} else {
				Window.alert("Es muss zuerst mindestens ein Kontakt aus einer Kontaktliste ausgewählt werden!");
			}

		}

	}

	public static class DeleteKontaktlisteCommand implements Command {

		@Override
		public void execute() {
			if (kontaktliste != null) {
				DeleteKontaktlisteDialogBox deleteKontakt = new DeleteKontaktlisteDialogBox(kontaktliste);
				deleteKontakt.center();
				kontaktliste = null;
			} else {
				Window.alert("Bitte wählen Sie zunächst die Kontaktliste aus, die gelöscht werden soll!");
			}
		}
	}

	public static class AddNewKontaktToKontaktlisteCommand implements Command {

		@Override
		public void execute() {
			if (kontaktliste == null) {
				Window.alert("Bitte wählen Sie eine Kontaktliste aus, in welche der  "
						+ "neu zu erstellende Kontakt eingefügt werden soll!");
			} else if (kontaktliste != null) {
				KontaktPopup k = new KontaktPopup(kontaktliste);
				k.center();
			}
		}
	}

	class DeleteTeilhaberschaftKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
			teilhaberschaft.setKontaktlisteID(kontaktliste.getId());
			teilhaberschaft.setTeilhabenderID(nutzer.getId());
			kontaktmanagerVerwaltung.deleteTeilhaberschaftByTeilhaberschaft(teilhaberschaft,
					new DeleteTeilhaberschaftKontaktlisteCallback());
		}

		class DeleteTeilhaberschaftKontaktlisteCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Löschen" + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				AllKontaktView allkontaktview = new AllKontaktView();
			}

		}
	}

	class DeleteTeilhaberschaftCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Löschen: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			Kontaktliste kontaktliste = new Kontaktliste();
			kontaktliste.setBezeichnung("Empfangene Kontakte");
			AllKontaktView allkontaktView = new AllKontaktView(kontaktliste);
			CustomTreeModel ctm = new CustomTreeModel();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(ctm);
		}

	}

	public static class AddTeilhaberschaftKontaktCommand implements Command {

		@Override
		public void execute() {
			ArrayList<Kontakt> selectedKontakteInCellTable = new ArrayList<Kontakt>();
			selectedKontakteInCellTable = allKontakteSelectedArrayList;
			if (selectedKontakteInCellTable.size() == 0) {
				Window.alert("Es muss zuerst ein Kontakt ausgewählt werden!");
			} else if (selectedKontakteInCellTable.size() == 1) {
				TeilhaberschaftDialogBox dialogBox = new TeilhaberschaftDialogBox(allKontakteSelectedArrayList);
				dialogBox.center();
			} else if (selectedKontakteInCellTable.size() > 1) {
				DialogBoxKontaktTeilen dialogbox = new DialogBoxKontaktTeilen(allKontakteSelectedArrayList);
				dialogbox.center();
			}
		}
	}

	public static class AddKontaktlisteCommand implements Command {

		@Override
		public void execute() {
			CreateKontaktlisteDialogBox dbox = new CreateKontaktlisteDialogBox();
			dbox.center();
		}
	}

	public class LogoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String logoutInfo = Cookies.getCookie("logout");

			signOutLink.setHref(logoutInfo);
			Window.open(signOutLink.getHref(), "_self", "");
		}

	}

	public static class KontaktDeleteCommand implements Command {

		@Override
		public void execute() {
			if (allKontakteSelectedArrayList.size() != 0) {
				DeleteKontaktDialogBox db = new DeleteKontaktDialogBox(allKontakteSelectedArrayList);
				db.center();
			} 
			else {
				Window.alert("Es muss zuerst ein Kontakt ausgewählt werden!");
			}
		}
	}

	public static class DeleteKontaktDialogBox extends DialogBox {
		private VerticalPanel vPanel = new VerticalPanel();
		private VerticalPanel hPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();
		private Label abfrage = new Label("Soll dieser Kontakt wirklich gelöscht und aus allen Kontaktlisten"
				+ " entfernt werden?");
		private Button jaButton = new Button("Löschen");
		private Button neinButton = new Button("Abbrechen");
		private ArrayList<Kontakt> kontakt = new ArrayList<>();

		public DeleteKontaktDialogBox(ArrayList<Kontakt> k) {
			kontakt = k;
			jaButton.addClickHandler(new DeleteKontaktClickHandler());
			neinButton.addClickHandler(new AbortDeleteClickHandler());
			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			hPanel.add(abfrage);
			hPanel.add(new HTML("<br>"));
			hPanel.add(buttonPanel);
			vPanel.add(hPanel);
			this.setTitle("Kontakt löschen");
			this.add(vPanel);
			this.setGlassEnabled(true);
		}

		public class AbortDeleteClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}

		}

		public class DeleteKontaktClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				for (Kontakt kontakt : kontakt) {
					kontaktmanagerVerwaltung.deleteKontaktByID(kontakt, new DeleteKontaktCallback());
				}
			}

		}

		public class DeleteKontaktCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Der Kontakt konnte nicht gelöscht werden: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Der Kontakt wurde erfolgreich gelöscht.");
				hide();
				AllKontaktView akw = new AllKontaktView();
				CustomTreeModel ctm = new CustomTreeModel();
				RootPanel.get("leftmenutree").clear();
				RootPanel.get("leftmenutree").add(ctm);
			}
		}
	}

	public class TeilhaberschaftKontakteCallback implements AsyncCallback<Vector<NutzerTeilhaberschaftKontaktWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<NutzerTeilhaberschaftKontaktWrapper> result) {
			Vector<Kontakt> kontakt = new Vector<Kontakt>();
			for (NutzerTeilhaberschaftKontaktWrapper nutzerTeilhaberschaftKontaktWrapper : result) {
				kontakt.add(nutzerTeilhaberschaftKontaktWrapper.getKontakt());
			}
			allKontakteCellTable.setRowData(0, kontakt);
			allKontakteCellTable.setRowCount(kontakt.size(), true);

		}

	}

	public class SelectionChangeHandlerCellTable implements SelectionChangeEvent.Handler {

		public void onSelectionChange(SelectionChangeEvent event) {
			allKontakteSelectedArrayList.clear();
			allKontakteSelectedArrayList
					.addAll(((MultiSelectionModel<Kontakt>) allKontakteCellTable.getSsmAuspraegung()).getSelectedSet());

			Menubar mb = new Menubar(kontaktliste, allKontakteSelectedArrayList);
		}

	}

	public class PreviewClickHander implements Handler<Kontakt> {

		long initialClick = -1000;

		@Override
		public void onCellPreview(CellPreviewEvent<Kontakt> event) {

			long clickedAt = System.currentTimeMillis();

			if (event.getNativeEvent().getType().contains("click")) {

				/*
				 * Wenn nicht mehr als 300ms zwischen zwei Klicks liegen, so
				 * wird ein Doppelklick ausgelöst und die Profilansicht des
				 * angeklickten Kontaktes geöffnet. Andernfalls wird der Kontakt
				 * lediglich selektiert.
				 */

				if (clickedAt - initialClick < 300) {
					allKontakteSelectedArrayList.add(event.getValue());
					KontaktForm kf = new KontaktForm(event.getValue());

				}

				initialClick = System.currentTimeMillis();

				final Kontakt value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}

	public static class CreateKontaktCommand implements Command {

		@Override
		public void execute() {
			KontaktPopup k = new KontaktPopup();
			k.center();
		}
	}

	public class AllKontaktByNutzerCallback implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Daten ist ein Fehler aufgetreten" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			// int id = 0;
			Range range = new Range(0, result.size());
			allKontakteCellTable.setVisibleRangeAndClearData(range, true);
			for (Kontakt k : result) {
				// k.setId(id);
				allKontakteByUserArrayList.add(k);
				// id++;
			}
			allKontakteCellTable.setRowCount(allKontakteByUserArrayList.size(), true);
			allKontakteCellTable.setRowData(0, allKontakteByUserArrayList);
		}

	}

	public static class AddKontaktToKontaktlisteCommand implements Command {

		@Override
		public void execute() {
			if (allKontakteSelectedArrayList.size() != 0) {
				DialogBoxKontaktlisteHinzufuegen db = new DialogBoxKontaktlisteHinzufuegen(
						allKontakteSelectedArrayList);
				db.center();
			} else {
				Window.alert("Es muss zuerst ein Kontakt ausgewählt werden!");
			}
		}
	}

	public static class TeilhaberschaftVerwaltenCommand implements Command {

		@Override
		public void execute() {
			TeilhaberschaftVerwaltungView teilhaberschaftVerwaltung = new TeilhaberschaftVerwaltungView();
		}

	}

}
