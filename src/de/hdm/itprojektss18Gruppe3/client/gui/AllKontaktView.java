package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * Die Klasse AllKontaktView ist die Ansicht für die Kontaktlisten die angeklickt werden.
 * Hier werden die Kontakte in einer Kontaktliste im Div-Container content angezeigt.
 * Erbt von der Basis-Klasse MainFrame
 * 
 * @version 1.0 30 June 2018
 * @author Mert
 *
 */
public class AllKontaktView extends MainFrame {

	/**
	 * Instanziierung des Proxy Objekts für den Editor
	 */
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	
	/**
	 * Instanziierung der GUI Elemente: VerticalPanel, HorizontalPanel, Button, Anchor, HTML und CheckboxCell
	 */
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel allKontakteCellTableContainer = new HorizontalPanel();
	
	private Button teilhaberschaftButton = new Button("Teilhaberschaft löschen");
	
	private Anchor signOutLink = new Anchor();
	
	private HTML headline = new HTML();
	private CheckboxCell checkBoxCell = new CheckboxCell(true, false);

	/**
	 * Instanziierung und Deklarierung von Listen die für die Verwaltung der BO's notwendig sind.
	 */
	private static ArrayList<Kontakt> allKontakteSelectedArrayList = new ArrayList<>();
	private ArrayList<Kontakt> allKontakteByUserArrayList = new ArrayList<>();
	private List<Kontakt> allSelectedKontakte = new ArrayList<>();
	private static ProvidesKey<Kontakt> keyProvider;

	/**
	 * Deklarierung der BusinessObjects die verwendet werden.
	 */
	private Teilhaberschaft teilhaberschaft = null;
	private Nutzer nutzerausdb = null;
	private static Kontaktliste kontaktliste = null;
	private Kontakt kontakt = null;

	/**
	 * Instanziierung der Cell's
	 */
	private ButtonCell buttonCell = new ButtonCell();
	private TextCell textCell = new TextCell();
	private ClickableTextCell clickCell = new ClickableTextCell();
	
	/**
	 * Instanziierung des CellTables, welches verwendet wird. Damit auch die Columns, die als Innere Klasse in der CellTable Klasse sind.
	 */
	private CellTableKontakt allKontakteCellTable = new CellTableKontakt();
	private CellTableKontakt.KontaktnameColumn kontaktnameColumn = allKontakteCellTable.new KontaktnameColumn(
			clickCell);
	private CellTableKontakt.CheckColumn checkColumn = allKontakteCellTable.new CheckColumn(checkBoxCell);
	private CellTableKontakt.IconColumn iconColumn = allKontakteCellTable.new IconColumn(textCell);


	public AllKontaktView() {
		Kontaktliste dummyKontaktliste = new Kontaktliste();
		dummyKontaktliste.setBezeichnung("Eigene Kontakte");
		this.kontaktliste = dummyKontaktliste;
		headline = new HTML("Alle Kontakte in Ihrem Kontaktmanager");
		super.onLoad();
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AllKontaktByNutzerCallback());
		Menubar mb = new Menubar();
		CustomTreeModel ctm = new CustomTreeModel();
		RootPanel.get("leftmenutree").clear();
		RootPanel.get("leftmenutree").add(ctm);
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
		Menubar mb = new Menubar(kontaktliste, allKontakteSelectedArrayList);
		super.onLoad();
	}

	

	public void run() {

		allKontakteCellTable.setEmptyTableWidget(new Label("Diese Kontaktliste ist leer"));

		allKontakteCellTableContainer.clear();
		allKontakteCellTableContainer.add(allKontakteCellTable);

		allKontakteCellTable.addCellPreviewHandler(new PreviewClickHander());

		iconColumn.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
		allKontakteCellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		allKontakteCellTable.setColumnWidth(checkColumn, 1, Unit.PCT);
		allKontakteCellTable.addColumn(kontaktnameColumn, "Nickname");
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
	
	public static class RenameKontaktlisteCommand implements Command {

		@Override
		public void execute() {
			if (kontaktliste == null) {
				Window.alert("Bitte wählen Sie eine Kontaktliste aus, in welche der  "
						+ "neu zu erstellende Kontakt eingefügt werden soll!");
			} else if (kontaktliste != null) {
				CreateKontaktlisteDialogBox createKL = new CreateKontaktlisteDialogBox(kontaktliste);
				createKL.center();
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
			if (selectedKontakteInCellTable.size() == 1) {
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
			DeleteKontaktDialogBox db = new DeleteKontaktDialogBox(allKontakteSelectedArrayList);
			db.center();

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
			this.setText("Kontakt löschen");
			this.add(vPanel);
			this.setGlassEnabled(true);
			this.setAnimationEnabled(true);
			this.setAutoHideEnabled(true);
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
					KontaktForm kf = new KontaktForm(event.getValue());
//					RootPanel.get("content").clear();
//					RootPanel.get("content").add(kf);
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
