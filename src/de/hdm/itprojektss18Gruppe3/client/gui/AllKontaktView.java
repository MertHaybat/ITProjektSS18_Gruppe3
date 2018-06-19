package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.client.params.AllClientPNames;

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
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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
	private FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	private VerticalPanel menuBarContainerPanel = new VerticalPanel();
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

	private HTML headline = new HTML();

	private Teilhaberschaft teilhaberschaft = null;

	private ArrayList<Kontakt> allKontakteSelectedArrayList = new ArrayList<>();
	private ArrayList<Kontakt> allKontakteByUserArrayList = new ArrayList<>();
	private List<Kontakt> allSelectedKontakte = new ArrayList<>();
	private static ProvidesKey<Kontakt> keyProvider;

	private Anchor signOutLink = new Anchor();
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	private Nutzer nutzerausdb = null;
	private Kontaktliste kontaktliste = new Kontaktliste();


	private Kontakt kontakt = null;

	private CellTableKontakt allKontakteCellTable = new CellTableKontakt();

	private CheckboxCell checkBoxCell = new CheckboxCell(true, false);
	private ButtonCell buttonCell = new ButtonCell();
	private TextCell textCell = new TextCell();
	private ClickableTextCell clickCell = new ClickableTextCell();
	private CellTableKontakt.KontaktnameColumn kontaktnameColumn = allKontakteCellTable.new KontaktnameColumn(clickCell);
	private CellTableKontakt.CheckColumn checkColumn = allKontakteCellTable.new CheckColumn(checkBoxCell);
	private CellTableKontakt.IconColumn iconColumn = allKontakteCellTable.new IconColumn(textCell);

	private KontaktlistView klisteView = new KontaktlistView();

	public AllKontaktView() {
		headline = new HTML("Alle Kontakte in Ihrem Kontaktmanager");
		super.onLoad();
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AllKontaktByNutzerCallback());

		deleteKontaktButton.addClickHandler(new KontaktDeleteClickHandler());
		addKontaktToKontaktlistButton.addClickHandler(new AddKontaktToKontaktlisteClickHandler());

		addKontaktButton.addClickHandler(new CreateKontaktClickHandler());
		addKontaktlisteButton.addClickHandler(new addKontaktlisteClickHandler());
		addTeilhaberschaftKontaktlisteButton.addClickHandler(new AddTeilhaberschaftKontaktlisteClickHandler());

		menuBarContainerFlowPanel.add(addKontaktButton);
		menuBarContainerFlowPanel.add(deleteKontaktButton);
		menuBarContainerFlowPanel.add(addKontaktlisteButton);
		menuBarContainerFlowPanel.add(addKontaktToKontaktlistButton);
		menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktButton);


		RootPanel.get("menubar").add(menuBarContainerPanel);

	}

	public AllKontaktView(final Kontaktliste k) {

		this.kontaktliste = k;

		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		if(k.getBezeichnung().equals("Empfangene Kontakte")){
			headline = new HTML("Alle Kontakte, die Sie als Empfänger geteilt bekommen haben");
			//			klisteView = new KontaktlistView();
			//			klisteView.getMenuBarContainerFlowPanel().add(teilhaberschaftButton);
			menuBarContainerFlowPanel.add(teilhaberschaftButton);
			teilhaberschaftButton.addClickHandler(new TeilhaberschaftButtonClickHandler());
			kontaktmanagerVerwaltung.findEigenschaftsauspraegungAndKontaktByTeilhaberschaft(nutzer.getId(), new TeilhaberschaftKontakteCallback());
		} else {
			headline = new HTML("Alle Kontakte in der Kontaktliste " + k.getBezeichnung());
			kontaktmanagerVerwaltung.findKontaktlisteByTeilhabenderID(nutzer.getId(), new TeilhaberschaftKontaktlisteCallback());
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
		//visitColumn.setFieldUpdater(new VisitProfileUpdate());
		menuBarContainerFlowPanel.add(zurueckButton);
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");
		zurueckButton.setStylePrimaryName("mainButton");
		addTeilhaberschaftKontaktlisteButton.setStylePrimaryName("mainButton");
		addTeilhaberschaftKontaktButton.setStylePrimaryName("mainButton");
		deleteKontaktfromListeButton.setStylePrimaryName("mainButton");
		kontaktlisteLoeschen.setStylePrimaryName("mainButton");
		kontaktHinzufuegenButton.setStylePrimaryName("mainButton");
		addKontaktButton.setStylePrimaryName("mainButton");
		deleteKontaktButton.setStylePrimaryName("mainButton");
		addKontaktToKontaktlistButton.setStylePrimaryName("mainButton");
		addTeilhaberschaftKontaktButton.setStylePrimaryName("mainButton");
		addKontaktlisteButton.setStylePrimaryName("mainButton");
		teilhaberschaftButton.setStylePrimaryName("mainButton");
		allKontakteCellTableContainer.setStylePrimaryName("cellListWidgetContainerPanel");
		vPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");
		menuBarContainerPanel.add(menuBarContainerFlowPanel);

		allKontakteCellTable.setEmptyTableWidget(new Label("Diese Kontaktliste ist leer"));

		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerPanel);
		allKontakteCellTableContainer.clear();
		allKontakteCellTableContainer.add(allKontakteCellTable);

		zurueckButton.addClickHandler(new ZurueckButtonClickHandler());
		addTeilhaberschaftKontaktButton
		.addClickHandler(new addTeilhaberschaftKontaktClickHandler(allKontakteSelectedArrayList));

		allKontakteCellTable.addCellPreviewHandler(new PreviewClickHander());

		iconColumn.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
		allKontakteCellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		allKontakteCellTable.setColumnWidth(checkColumn, 20, Unit.PX);
		allKontakteCellTable.addColumn(kontaktnameColumn, "Kontaktname");
		allKontakteCellTable.setColumnWidth(kontaktnameColumn, 50, Unit.EM);
		allKontakteCellTable.addColumn(iconColumn, "");
		allKontakteCellTable.setColumnWidth(iconColumn, 5, Unit.EM);

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
	class ZurueckButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			AllKontaktView akw = new AllKontaktView();
		}

	}
	class AddTeilhaberschaftKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktTeilen dialogbox = new DialogBoxKontaktTeilen(kontaktliste);
			dialogbox.center();
		}
	}
	class TeilhaberschaftButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(allKontakteSelectedArrayList.isEmpty()){
				Window.alert("Sie müssen mindestens eine Teilhaberschaft auswählen");
			} else {
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
				teilhaberschaft.setTeilhabenderID(nutzer.getId());

				for (Kontakt kontakt : allKontakteSelectedArrayList) {

					teilhaberschaft.setKontaktID(kontakt.getId());
					kontaktmanagerVerwaltung.deleteTeilhaberschaftByTeilhaberschaft(teilhaberschaft, new DeleteTeilhaberschaftCallback());
				}
			}
		}

	}
	class TeilhaberschaftKontaktlisteCallback implements AsyncCallback<Vector<Kontaktliste>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Kontaktliste> result) {
			int o = result.size();

			if (result.size() == 0){
				menuBarContainerFlowPanel.add(deleteKontaktButton);
				menuBarContainerFlowPanel.add(kontaktlisteLoeschen);
				menuBarContainerFlowPanel.add(kontaktHinzufuegenButton);
				menuBarContainerFlowPanel.add(deleteKontaktfromListeButton);
				menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktButton);
				menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktlisteButton);
				deleteKontaktfromListeButton.addClickHandler(new KontaktAusKontaktlisteDeleteClickHandler());
				kontaktHinzufuegenButton.addClickHandler(new AddNewKontaktToKontaktlisteClickHandler());
				kontaktlisteLoeschen.addClickHandler(new deleteKontaktlisteClickHandler());
			}

			for (int i = 0; i < result.size(); i++) {
				if (result.elementAt(i).getId() == kontaktliste.getId()) {
					menuBarContainerFlowPanel.add(teilhaberschaftButton);
					menuBarContainerFlowPanel.add(kontaktHinzufuegenButton);
					teilhaberschaftButton.addClickHandler(new DeleteTeilhaberschaftKontaktlisteClickHandler());
					kontaktHinzufuegenButton.addClickHandler(new AddNewKontaktToKontaktlisteClickHandler());

				}

				if (i + 1 == result.size() && result.elementAt(i).getId() != kontaktliste.getId()) {
					menuBarContainerFlowPanel.add(deleteKontaktButton);
					menuBarContainerFlowPanel.add(kontaktlisteLoeschen);
					menuBarContainerFlowPanel.add(kontaktHinzufuegenButton);
					menuBarContainerFlowPanel.add(deleteKontaktfromListeButton);
					menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktButton);
					menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktlisteButton);
					deleteKontaktfromListeButton.addClickHandler(new KontaktAusKontaktlisteDeleteClickHandler());
					kontaktHinzufuegenButton.addClickHandler(new AddNewKontaktToKontaktlisteClickHandler());
					kontaktlisteLoeschen.addClickHandler(new deleteKontaktlisteClickHandler());

				}

			}

		}
	}
	class KontaktAusKontaktlisteDeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			KontaktFromKontaktlisteLoeschenDialogBox deleteKontakt = new KontaktFromKontaktlisteLoeschenDialogBox(
					allKontakteSelectedArrayList, kontaktliste);
			deleteKontakt.center();
		}

	}
	class deleteKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteKontaktlisteDialogBox deleteKontakt = new DeleteKontaktlisteDialogBox(kontaktliste);
			deleteKontakt.center();
		}
	}
	class AddNewKontaktToKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (kontaktliste == null) {
				KontaktPopup k = new KontaktPopup();
				k.center();
			} else if (kontaktliste != null) {
				KontaktPopup k = new KontaktPopup(kontaktliste);
				k.center();
			}
		}
	}
	class DeleteTeilhaberschaftKontaktlisteClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
			teilhaberschaft.setKontaktlisteID(kontaktliste.getId());
			teilhaberschaft.setTeilhabenderID(nutzer.getId());
			kontaktmanagerVerwaltung.deleteTeilhaberschaftByTeilhaberschaft(teilhaberschaft, new DeleteTeilhaberschaftKontaktlisteCallback());
		}
		class DeleteTeilhaberschaftKontaktlisteCallback implements AsyncCallback<Void>{

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

	class DeleteTeilhaberschaftCallback implements AsyncCallback<Void>{

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
	class addTeilhaberschaftKontaktClickHandler implements ClickHandler {

		ArrayList<Kontakt> selectedKontakteInCellTable;

		public addTeilhaberschaftKontaktClickHandler(ArrayList<Kontakt> selectedKontakteInCellTable) {
			this.selectedKontakteInCellTable = selectedKontakteInCellTable;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (selectedKontakteInCellTable.size() == 0) {
				Window.alert("Bitte wähle zuerst mindestens einen Kontakt aus, den du teilen möchtest");
			} else if (selectedKontakteInCellTable.size() == 1) {
				TeilhaberschaftDialogBox dialogBox = new TeilhaberschaftDialogBox(allKontakteSelectedArrayList);
				dialogBox.center();
			} else if (selectedKontakteInCellTable.size() > 1) {
				DialogBoxKontaktTeilen dialogbox = new DialogBoxKontaktTeilen(allKontakteSelectedArrayList);
				dialogbox.center();
			}
		}
	}



	class addKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
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

	public class KontaktDeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteKontaktDialogBox db = new DeleteKontaktDialogBox(allKontakteSelectedArrayList);
			db.center();

		}

		public class DeleteKontaktDialogBox extends DialogBox {
			private VerticalPanel vPanel = new VerticalPanel();
			private HorizontalPanel hPanel = new HorizontalPanel();
			private Label abfrage = new Label("Möchten Sie den Kontakt löschen? Dies führt dazu,"
					+ " dass der Kontakt in allen Kontaktlisten gelöscht wird.");
			private Button ja = new Button("Ja");
			private Button nein = new Button("Nein");
			private ArrayList<Kontakt> kontakt = new ArrayList<>();

			public DeleteKontaktDialogBox(ArrayList<Kontakt> k) {
				kontakt = k;
				ja.addClickHandler(new DeleteKontaktClickHandler());
				nein.addClickHandler(new AbortDeleteClickHandler());
				vPanel.add(abfrage);
				hPanel.add(ja);
				hPanel.add(nein);
				vPanel.add(hPanel);
				this.setTitle("Kontakt löschen");
				this.add(vPanel);
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

	}
	public class TeilhaberschaftKontakteCallback implements AsyncCallback<Vector<NutzerTeilhaberschaftKontaktWrapper>>{

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

		}

	}

	public class PreviewClickHander implements Handler<Kontakt> {

		long initialClick=-1000;

		@Override
		public void onCellPreview(CellPreviewEvent<Kontakt> event) {

			long clickedAt = System.currentTimeMillis();

			if(event.getNativeEvent().getType().contains("click")){

				/*
				 * Wenn nicht mehr als 300ms zwischen zwei Klicks liegen,
				 * so wird ein Doppelklick ausgelöst und die Profilansicht
				 * des angeklickten Kontaktes geöffnet. Andernfalls wird der 
				 * Kontakt lediglich selektiert.
				 */
				
				if(clickedAt-initialClick < 300) {
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

	public class CreateKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
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

	class AddKontaktToKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktlisteHinzufuegen db = new DialogBoxKontaktlisteHinzufuegen(allKontakteSelectedArrayList);
			db.center();
		}

	}

}
