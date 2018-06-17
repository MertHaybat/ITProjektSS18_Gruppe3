package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktForm.DeleteEigenschaftsauspraegung;
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
	private Button teilhaberschaftButton = new Button("Teilhaberschaft löschen");
	private Button addKontaktButton = new Button("Neuer Kontakt");
	private Button deleteKontaktButton = new Button("Kontakt löschen");
	private Button addKontaktToKontaktlistButton = new Button("Kontakt in Kontaktliste");
	private Button addTeilhaberschaftKontaktButton = new Button("Kontakt teilen");
	private Button addKontaktlisteButton = new Button("Neue Kontaktliste");

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
	private CellTableKontakt.KontaktnameColumn kontaktnameColumn = allKontakteCellTable.new KontaktnameColumn(textCell);
	private CellTableKontakt.CheckColumn checkColumn = allKontakteCellTable.new CheckColumn(checkBoxCell);
	private CellTableKontakt.VisitProfileButtonColumn visitProfileButtonColumn  = allKontakteCellTable.new VisitProfileButtonColumn(clickCell);
	private CellTableKontakt.IconColumn iconColumn = allKontakteCellTable.new IconColumn(textCell);
	KontaktlistView klisteView = new KontaktlistView();

	public AllKontaktView() {
		super.onLoad();
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AllKontaktByNutzerCallback());
		

		menuBarContainerFlowPanel.add(addKontaktButton);
		menuBarContainerFlowPanel.add(deleteKontaktButton);
		menuBarContainerFlowPanel.add(addKontaktlisteButton);
		menuBarContainerFlowPanel.add(addKontaktToKontaktlistButton);
		menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktButton);

		RootPanel.get("menubar").add(menuBarContainerPanel);
	
	}
	
//	public AllKontaktView(Kontaktliste k) {
//		this.kontaktliste = k;
//		Nutzer nutzer = new Nutzer();
//		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
//		if(k.getBezeichnung().equals("Empfangene Kontakte")){
//			KontaktlistView klisteView = new KontaktlistView();
//			kontaktmanagerVerwaltung.findAllKontakteByTeilhabenderID(nutzer.getId(), new TeilhaberschaftKontakteCallback());
//		} else {
////			KontaktlistView klisteView = new KontaktlistView(k);
//
//			kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(k, new AllKontaktByNutzerCallback());
//		}
//		super.onLoad();
//	}
	
	
	public AllKontaktView(Kontaktliste k) {
		super.onLoad();
		this.kontaktliste = k;
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		if(k.getBezeichnung().equals("Empfangene Kontakte")){
			klisteView = new KontaktlistView();
			klisteView.getMenuBarContainerFlowPanel().add(teilhaberschaftButton);
			kontaktmanagerVerwaltung.findAllKontakteByTeilhabenderID(nutzer.getId(), new TeilhaberschaftKontakteCallback());
		} else {
			klisteView = new KontaktlistView(k);
			kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(k, new AllKontaktByNutzerCallback());
			
		}
	}


	public HorizontalPanel getAllKontakteCellTableContainer() {
		return allKontakteCellTableContainer;
	}

	public void setAllKontakteCellTableContainer(HorizontalPanel allKontakteCellTableContainer) {
		this.allKontakteCellTableContainer = allKontakteCellTableContainer;
	}

	public void run() {
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");
		addKontaktButton.setStylePrimaryName("mainButton");
		deleteKontaktButton.setStylePrimaryName("mainButton");
		addKontaktToKontaktlistButton.setStylePrimaryName("mainButton");
		addTeilhaberschaftKontaktButton.setStylePrimaryName("mainButton");
		addKontaktlisteButton.setStylePrimaryName("mainButton");
		allKontakteCellTableContainer.setStylePrimaryName("cellListWidgetContainerPanel");
		vPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");
		menuBarContainerPanel.add(menuBarContainerFlowPanel);
		allKontakteCellTable.setEmptyTableWidget(new Label("Du hast bisher keine Kontakte angelegt"));
		teilhaberschaftButton.setStylePrimaryName("mainButton");
		
		
		teilhaberschaftButton.addClickHandler(new TeilhaberschaftButtonClickHandler());
		
		deleteKontaktButton.addClickHandler(new KontaktDeleteClickHandler());
		addKontaktToKontaktlistButton.addClickHandler(new AddKontaktToKontaktlisteClickHandler());
		addTeilhaberschaftKontaktButton
				.addClickHandler(new addTeilhaberschaftKontaktClickHandler(allKontakteSelectedArrayList));
		addKontaktButton.addClickHandler(new CreateKontaktClickHandler());
		addKontaktlisteButton.addClickHandler(new addKontaktlisteClickHandler());
		
		visitProfileButtonColumn.setFieldUpdater(new VisitProfileUpdate());
		iconColumn.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
		allKontakteCellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		allKontakteCellTable.setColumnWidth(checkColumn, 20, Unit.PX);
		allKontakteCellTable.addColumn(kontaktnameColumn, "Kontaktname");
		allKontakteCellTable.setColumnWidth(kontaktnameColumn, 50, Unit.EM);
		allKontakteCellTable.addColumn(iconColumn, "");
		allKontakteCellTable.setColumnWidth(iconColumn, 5, Unit.EM);
		allKontakteCellTable.addColumn(visitProfileButtonColumn, "");

		allKontakteCellTable.getSsmAuspraegung().addSelectionChangeHandler(new SelectionChangeHandlerCellTable());
		
		
		allKontakteCellTableContainer.clear();
		allKontakteCellTableContainer.add(allKontakteCellTable);
		vPanel.add(allKontakteCellTableContainer);
		
		
		RootPanel.get("content").add(vPanel);
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
				}

			}
		}

	}
	public class TeilhaberschaftKontakteCallback implements AsyncCallback<Vector<Kontakt>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			allKontakteCellTable.setRowData(0, result);
			allKontakteCellTable.setRowCount(result.size(), true);
			
		}
		
	}

	public class SelectionChangeHandlerCellTable implements SelectionChangeEvent.Handler {

		public void onSelectionChange(SelectionChangeEvent event) {
			allKontakteSelectedArrayList.clear();
			allKontakteSelectedArrayList
					.addAll(((MultiSelectionModel<Kontakt>) allKontakteCellTable.getSsmAuspraegung()).getSelectedSet());
			
		}

	}

	public class VisitProfileUpdate implements FieldUpdater<Kontakt, String> {

		@Override
		public void update(int index, Kontakt object, String value) {
			KontaktForm kontaktForm = new KontaktForm(object);
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