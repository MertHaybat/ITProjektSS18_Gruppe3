package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
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
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

public class AllKontaktView extends MainFrame {

	private VerticalPanel vPanel = new VerticalPanel();
	private ScrollPanel allKontakteCellTableContainer = new ScrollPanel();
	private FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	private VerticalPanel menuBarContainerPanel = new VerticalPanel();
//	private Button logoutButton = new Button("Ausloggen");
	private Button addKontaktButton = new Button("Neuer Kontakt");
	private Button deleteKontaktButton = new Button("Kontakt löschen");
	private Button addKontaktToKontaktlistButton = new Button("+ Kontaktliste");
	private Button addTeilhaberschaftKontaktButton = new Button("Kontakt teilen");
	private Button addKontaktlisteButton = new Button("Neue Kontaktliste");
	
	private Teilhaberschaft teilhaberschaft = null;
	private NoSelectionModel<Kontaktliste> ssmKontaktliste = new NoSelectionModel<Kontaktliste>();
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private CellTable<Kontakt> allKontakteCellTable = new CellTable<Kontakt>(11, CellTableResources.INSTANCE);
	private ArrayList<Kontakt> allKontakteSelectedArrayList = new ArrayList<>();
	private ArrayList<Kontakt> allKontakteByUserArrayList = new ArrayList<>();
	private List<Kontakt> allSelectedKontakte = new ArrayList<>();
	private CellTable<Kontaktliste> kontaktlisteCelltable = new CellTable<Kontaktliste>();
	private static ProvidesKey<Kontakt> keyProvider;
	
	// private SuggestBox box = new SuggestBox(oracle);
	private Anchor signOutLink = new Anchor();
//	private Label contentHeadline = new Label("Die Liste aller deiner Kontakte");
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	private MultiSelectionModel<Kontakt> selectionModelCellTable = new MultiSelectionModel<Kontakt>(keyProvider);
	private Nutzer nutzerausdb = null;
	private Kontaktliste kontaktliste = new Kontaktliste();
	/**
	 * The list of data to display.
	 */
	private KontaktDataProvider kontaktDataProvider = new KontaktDataProvider();
	private static Kontaktliste kontaktlisteSelectedInTree = null;

	private Kontakt kontakt = null;

	private RadioButton rb0 = new RadioButton("auswahlRadio", "Alle eigenen Kontakte");
	private RadioButton rb1 = new RadioButton("auswahlRadio", "Alle geteilten Kontakte");
	private RadioButton rb2 = new RadioButton("auswahlRadio", "Alle geteilten Kontaktlisten");
	private RadioButton rb3 = new RadioButton("auswahlRadio", "Alle geteilten Eigenschaftsausprägungen");
	private FlowPanel radioFlowPanel = new FlowPanel();
	private ButtonCell visitProfileButton = new ButtonCell();
	// public AllKontaktView(){
	//
	//
	// }
	//
	public AllKontaktView() {
		RootPanel.get("content").clear();
//		Nutzer nutzer = new Nutzer();
//		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
//		kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AllKontaktByNutzerCallback());
		run();
	}

	public AllKontaktView(Kontaktliste k) {
		this.kontaktliste = k;
		kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(k, new AllKontaktByNutzerCallback());
		run();
	}
	public void setKontaktlisteSelectedInTree(Kontaktliste kontaktlisteSelectedInTree) {
		this.kontaktlisteSelectedInTree = kontaktlisteSelectedInTree;
	}
	public static Kontaktliste getKontaktlisteSelectedInTree() {
		return kontaktlisteSelectedInTree;
	}
	public void run() {
		radioFlowPanel.add(rb0);
		radioFlowPanel.add(rb1);
		radioFlowPanel.add(rb2);
		radioFlowPanel.add(rb3);
	
		
		KontaktDataProvider kontaktDataProvider = new KontaktDataProvider();

		kontaktlisteCelltable.setSelectionModel(ssmKontaktliste);
		allKontakteCellTable.setSelectionModel(selectionModelCellTable,
		DefaultSelectionEventManager.<Kontakt>createCheckboxManager());
		selectionModelCellTable.addSelectionChangeHandler(new SelectionChangeHandlerCellTable());

		/*
		 * Menüleiste mit den Buttons für die Anlage von einer neuen
		 * Kontaktliste und dem Löschen einer Kontaktliste erzeugen und dem
		 * Panel zuweisen
		 */
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");

		
//		logoutButton.setStylePrimaryName("mainButton");
		addKontaktButton.setStylePrimaryName("mainButton");
		deleteKontaktButton.setStylePrimaryName("mainButton");
		addKontaktToKontaktlistButton.setStylePrimaryName("mainButton");
		addTeilhaberschaftKontaktButton.setStylePrimaryName("mainButton");
		addKontaktlisteButton.setStylePrimaryName("mainButton");

		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");
		menuBarContainerPanel.add(menuBarContainerFlowPanel);
		allKontakteCellTable.setEmptyTableWidget(new Label("Du hast bisher keine Kontakte angelegt"));

		
		Column<Kontakt, Boolean> checkColumn = new Column<Kontakt, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Kontakt object) {
				// Get the value from the selection model.
				allSelectedKontakte.add(object);
				return selectionModelCellTable.isSelected(object);
			}
		};

		// First name.
		Column<Kontakt, String> kontaktnameColumn = new Column<Kontakt, String>(new TextCell()) {
			@Override
			public String getValue(Kontakt object) {
				return object.getName();
			}
		};

		Column<Kontakt, String> iconColumn = new Column<Kontakt, String>(new TextCell() {
			public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
			}
		}) {
			@Override
			public String getValue(Kontakt object) {
				if (object.getStatus() == 1) {
					return "group.svg";
				} else {
					return "singleperson.svg";
				}
			}
		};

		
		Column<Kontakt, String> visitProfileButtonColumn = new Column<Kontakt, String>(visitProfileButton) {
			public String getValue(Kontakt object) {
				return "Ansehen";
			}
		};
		Column<Kontaktliste, String> kontaktlisteNameColumn = new Column<Kontaktliste, String>(new TextCell()) {
			@Override
			public String getValue(Kontaktliste object) {
				return object.getBezeichnung();
			}
		};	
		
		String cookieCB0 = Cookies.getCookie("checkbox0Cookie");
		String cookieCB1 = Cookies.getCookie("checkbox1Cookie");
		String cookieCB2 = Cookies.getCookie("checkbox2Cookie");
		String cookieCB3 = Cookies.getCookie("checkbox3Cookie");
		rb0.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("menubar").clear();
				RootPanel.get("menubar").add(menuBarContainerPanel);
				allKontakteCellTableContainer.clear();
				allKontakteCellTableContainer.add(allKontakteCellTable);
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AllKontaktByNutzerCallback());
				Cookies.setCookie("checkbox0Cookie", "1");
				Cookies.setCookie("checkbox1Cookie", "0");
				Cookies.setCookie("checkbox2Cookie", "0");
				Cookies.setCookie("checkbox3Cookie", "0");
			}
			
		});
		rb1.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				KontaktTeilhaberschaftCellTable ktc = new KontaktTeilhaberschaftCellTable();
				allKontakteCellTableContainer.clear();
				RootPanel.get("menubar").clear();
				RootPanel.get("menubar").add(ktc.getMenubar());
				allKontakteCellTableContainer.add(ktc.getCellTableTeilhaberschaft());
//				Nutzer nutzer = new Nutzer();
//				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
//				kontaktmanagerVerwaltung.findKontakteByTeilhabenderID(nutzer.getId(), new AllKontaktByNutzerCallback());
				Cookies.setCookie("checkbox0Cookie", "0");
				Cookies.setCookie("checkbox1Cookie", "1");
				Cookies.setCookie("checkbox2Cookie", "0");
				Cookies.setCookie("checkbox3Cookie", "0");
			}
		});
		rb2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				allKontakteCellTableContainer.clear();
				RootPanel.get("menubar").clear();
				allKontakteCellTableContainer.add(kontaktlisteCelltable);
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				kontaktmanagerVerwaltung.findKontaktlisteByTeilhabenderID(nutzer.getId(), new AllKontaktlisteCallback());
				Cookies.setCookie("checkbox0Cookie", "0");
				Cookies.setCookie("checkbox1Cookie", "1");
				Cookies.setCookie("checkbox2Cookie", "0");
				Cookies.setCookie("checkbox3Cookie", "0");
			}
		});
		rb3.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				EigenschaftsauspraegungTeilhaberschaftCellTable etc = new EigenschaftsauspraegungTeilhaberschaftCellTable();
				allKontakteCellTableContainer.clear();
				RootPanel.get("menubar").clear();
				RootPanel.get("menubar").add(etc.getMenubar());
				allKontakteCellTableContainer.clear();
				allKontakteCellTableContainer.add(etc.getCellTableTeilhaberschaft());
				// Nutzer nutzer = new Nutzer();
				// nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				// kontaktmanagerVerwaltung.findKontakteByTeilhabenderID(nutzer.getId(),
				// new AllKontaktByNutzerCallback());
				Cookies.setCookie("checkbox0Cookie", "0");
				Cookies.setCookie("checkbox1Cookie", "0");
				Cookies.setCookie("checkbox2Cookie", "0");
				Cookies.setCookie("checkbox3Cookie", "1");

			}
			
		});

		
		
		if(cookieCB0 == "0" && cookieCB1 == "0" && cookieCB2 =="0" && cookieCB3 =="0"){
			rb0.setValue(true);
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AllKontaktByNutzerCallback());
			RootPanel.get("menubar").clear();
			RootPanel.get("menubar").add(menuBarContainerPanel);
			allKontakteCellTableContainer.clear();
			allKontakteCellTableContainer.add(allKontakteCellTable);
		} else if (cookieCB0 == "0" && cookieCB1 == "1" && cookieCB2 =="0" && cookieCB3 =="0"){
			rb1.setValue(true);
			KontaktTeilhaberschaftCellTable ktc = new KontaktTeilhaberschaftCellTable();
			RootPanel.get("menubar").clear();
			RootPanel.get("menubar").add(ktc.getMenubar());
			allKontakteCellTableContainer.clear();
			allKontakteCellTableContainer.add(ktc.getCellTableTeilhaberschaft());
			
		} else if (cookieCB0 == "0" && cookieCB1 == "0" && cookieCB2 =="1" && cookieCB3 =="0"){
			rb2.setValue(true);
			allKontakteCellTableContainer.clear();
			RootPanel.get("menubar").clear();
			allKontakteCellTableContainer.add(kontaktlisteCelltable);
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			kontaktmanagerVerwaltung.findKontaktlisteByTeilhabenderID(nutzer.getId(), new AllKontaktlisteCallback());
		} else if (cookieCB0 == "1" && cookieCB1 == "0" && cookieCB2 =="0" && cookieCB3 =="0"){
			rb0.setValue(true);
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AllKontaktByNutzerCallback());
			RootPanel.get("menubar").clear();
			RootPanel.get("menubar").add(menuBarContainerPanel);
			allKontakteCellTableContainer.clear();
			allKontakteCellTableContainer.add(allKontakteCellTable);

		} else if (cookieCB0 == "0" && cookieCB1 == "0" && cookieCB2 =="0" && cookieCB3 =="1"){
			rb3.setValue(true);
			EigenschaftsauspraegungTeilhaberschaftCellTable etc = new EigenschaftsauspraegungTeilhaberschaftCellTable();
			RootPanel.get("menubar").clear();
			RootPanel.get("menubar").add(etc.getMenubar());
			allKontakteCellTableContainer.clear();
			allKontakteCellTableContainer.add(etc.getCellTableTeilhaberschaft());
		}
//		ssmKontaktliste.addSelectionChangeHandler(new Handler(){
//
//			@Override
//			public void onSelectionChange(SelectionChangeEvent event) {
//				// TODO Auto-generated method stub
//				KontaktCellList klv = new KontaktCellList(ssmKontaktliste.getLastSelectedObject(), teilhaberschaft);
//				allKontakteCellTableContainer.add(klv);
//				
//			}
//			
//		});
		visitProfileButtonColumn.setFieldUpdater(new VisitProfileUpdate());
		iconColumn.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
		// allKontakteCellTable.addCellPreviewHandler(new
		// PreviewClickHandler());
		allKontakteCellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		allKontakteCellTable.setColumnWidth(checkColumn, 20, Unit.PX);
		allKontakteCellTable.addColumn(kontaktnameColumn, "Kontaktname");
		allKontakteCellTable.setColumnWidth(kontaktnameColumn, 50, Unit.EM);
		allKontakteCellTable.addColumn(iconColumn, "Status");
		allKontakteCellTable.setColumnWidth(iconColumn, 5, Unit.EM);
		allKontakteCellTable.addColumn(visitProfileButtonColumn, "");
		
		kontaktlisteCelltable.addColumn(kontaktlisteNameColumn, "Kontaktliste");
		kontaktlisteCelltable.setColumnWidth(kontaktlisteNameColumn, 10, Unit.EM);
	
		allKontakteCellTableContainer.setStylePrimaryName("cellListWidgetContainerPanel");
//		vPanel.add(contentHeadline);
		vPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		

//		logoutButton.addClickHandler(new LogoutClickHandler());
		deleteKontaktButton.addClickHandler(new KontaktDeleteClickHandler());
		addKontaktToKontaktlistButton.addClickHandler(new AddKontaktToKontaktlisteClickHandler());
		addTeilhaberschaftKontaktButton
				.addClickHandler(new addTeilhaberschaftKontaktClickHandler(allKontakteSelectedArrayList));
		addKontaktButton.addClickHandler(new CreateKontaktClickHandler());
		
		addKontaktlisteButton.addClickHandler(new addKontaktlisteClickHandler());
		
		menuBarContainerFlowPanel.add(addKontaktButton);
		menuBarContainerFlowPanel.add(deleteKontaktButton);
		menuBarContainerFlowPanel.add(addKontaktlisteButton);
		menuBarContainerFlowPanel.add(addKontaktToKontaktlistButton);
		menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktButton);
		// menuBarContainerFlowPanel.add(box);
		
//		menuBarContainerFlowPanel.add(logoutButton);
		vPanel.add(radioFlowPanel);
		vPanel.add(allKontakteCellTableContainer);
		RootPanel.get("content").add(vPanel);
	}
	
	class AllKontaktlisteCallback implements AsyncCallback<Vector<Kontaktliste>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten aus der Datenbank");
			
		}

		@Override
		public void onSuccess(Vector<Kontaktliste> result) {
			Range range = new Range(0, result.size());
			kontaktlisteCelltable.setVisibleRangeAndClearData(range, true);
			kontaktlisteCelltable.setRowCount(result.size(), true);
			kontaktlisteCelltable.setRowData(0, result);
		}
		
	}
	
	class addKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			NewKontaktlisteDialogBox dbox = new NewKontaktlisteDialogBox();
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

	
	public class SelectionChangeHandlerCellTable implements SelectionChangeEvent.Handler {

		public void onSelectionChange(SelectionChangeEvent event) {
			allKontakteSelectedArrayList.clear();
			allKontakteSelectedArrayList
					.addAll(((MultiSelectionModel<Kontakt>) selectionModelCellTable).getSelectedSet());
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

	/*
	 * Diverse ClickHandler um die Klicks auf die Menübuttons zu erfassen und zu
	 * händeln.
	 */
	class AddKontaktToKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DialogBoxKontaktlisteHinzufuegen db = new DialogBoxKontaktlisteHinzufuegen(allKontakteSelectedArrayList);
			db.center();
		}

	}

	private static class KontaktDataProvider extends AsyncDataProvider<Kontakt> {

		@Override
		protected void onRangeChanged(HasData<Kontakt> display) {
			Nutzer nutzerKontaktliste = new Nutzer();
			nutzerKontaktliste.setId(Integer.parseInt(Cookies.getCookie("id")));
			final Range range = display.getVisibleRange();
			kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(getKontaktlisteSelectedInTree(),
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
				TeilhaberschaftDialogBox dialogBox = new TeilhaberschaftDialogBox(selectedKontakteInCellTable);
				dialogBox.center();
			} else if (selectedKontakteInCellTable.size() > 1) {
				DialogBoxKontaktTeilen dialogbox = new DialogBoxKontaktTeilen(selectedKontakteInCellTable);
				dialogbox.center();
			}
		} 
	}
	public class EigenschaftsauspraegungTeilhaberschaftCellTable {
		
		private SingleSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> ssmModel = new SingleSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>();
		private CellTable<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> cellTableTeilhaberschaft = new CellTable<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>();
		private FlowPanel menubar = new FlowPanel();
		private Button teilhaberschaftButton = new Button("Teilhaberschaft Löschen");
		
		
		public EigenschaftsauspraegungTeilhaberschaftCellTable(){
			run();
		}
		
		public SingleSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> getSsmModel() {
			return ssmModel;
		}
		public void setSsmModel(SingleSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> ssmModel) {
			this.ssmModel = ssmModel;
		}
		public CellTable<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> getCellTableTeilhaberschaft() {
			return cellTableTeilhaberschaft;
		}
		public void setCellTableTeilhaberschaft(
				CellTable<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> cellTableTeilhaberschaft) {
			this.cellTableTeilhaberschaft = cellTableTeilhaberschaft;
		}
		public FlowPanel getMenubar() {
			return menubar;
		}
		public void setMenubar(FlowPanel menubar) {
			this.menubar = menubar;
		}
		void run(){
			menubar.add(teilhaberschaftButton);
			teilhaberschaftButton.addClickHandler(new DeleteTeilhaberschaftClickHandler());
			Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String> nutzerColumn = new Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String>(new TextCell()) {
				@Override
				public String getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object) {
					return object.getNutzer().getMail();
				}
			};
		
			Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String> kontaktnameColumn = new Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String>(new TextCell()) {
				@Override
				public String getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object) {
					return object.getKontakt().getName();
				}
			};
			Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String> eigenschaftColumn = new Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String>(new TextCell()) {
				@Override
				public String getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object) {
					return object.getEigenschaft().getBezeichnung();
				}
			};
			Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String> auspraegungColumn = new Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String>(new TextCell()) {
				@Override
				public String getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object) {
					return object.getEigenschaftsauspraegung().getWert();
				}
			};
			Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String> modifikationsColumn = new Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String>(new TextCell()) {
				@Override
				public String getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object) {
					return String.valueOf(object.getKontakt().getModifikationsdatum());
				}
			};
			cellTableTeilhaberschaft.setSelectionModel(ssmModel);
			
			cellTableTeilhaberschaft.addColumn(nutzerColumn, "Eigentümer");
			cellTableTeilhaberschaft.addColumn(kontaktnameColumn, "Kontaktname");
			cellTableTeilhaberschaft.addColumn(eigenschaftColumn, "");
			cellTableTeilhaberschaft.addColumn(auspraegungColumn, "");

			cellTableTeilhaberschaft.addColumn(modifikationsColumn, "Modifikationsdatum");
			
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			kontaktmanagerVerwaltung.findAuspraegungTeilhaberschaftKontaktWrapperByTeilhaberschaft(nutzer.getId(), new TeilhaberschaftCallback());
		}
		private class DeleteTeilhaberschaftClickHandler implements ClickHandler{

			@Override
			public void onClick(ClickEvent event) {
				if(ssmModel == null){
					Window.alert("Sie müssen eine Teilhaberschaft auswählen");
				} else {
					
					kontaktmanagerVerwaltung.deleteTeilhaberschaftById(ssmModel.getSelectedObject().getTeilhaberschaft(), new DeleteTeilhaberschaftCallback());
				}
			}
			private class DeleteTeilhaberschaftCallback implements AsyncCallback<Void>{

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Die Teilhaberschaft konnte nicht gelöscht werden");
				}

				@Override
				public void onSuccess(Void result) {
					Window.alert("Die Teilhaberschaft wurde erfolgreich gelöscht");
				}
				
			}
		}
		private class TeilhaberschaftCallback implements AsyncCallback<Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>>{

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Laden der Teilhaberschaften");
				
			}

			@Override
			public void onSuccess(Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> result) {
				cellTableTeilhaberschaft.setRowCount(result.size(), true);
				cellTableTeilhaberschaft.setRowData(0, result);
			}
			
		}
	}
	public class KontaktTeilhaberschaftCellTable {
		
		private SingleSelectionModel<NutzerTeilhaberschaftKontaktWrapper> ssmModel = new SingleSelectionModel<NutzerTeilhaberschaftKontaktWrapper>();
		private CellTable<NutzerTeilhaberschaftKontaktWrapper> cellTableTeilhaberschaft = new CellTable<NutzerTeilhaberschaftKontaktWrapper>();
		private FlowPanel menubar = new FlowPanel();
		private Button teilhaberschaftButton = new Button("Kontakt Verwalten");
		
		public KontaktTeilhaberschaftCellTable(){
			run();
		}
		
		public CellTable<NutzerTeilhaberschaftKontaktWrapper> getCellTableTeilhaberschaft() {
			return cellTableTeilhaberschaft;
		}

		public void setCellTableTeilhaberschaft(CellTable<NutzerTeilhaberschaftKontaktWrapper> cellTableTeilhaberschaft) {
			this.cellTableTeilhaberschaft = cellTableTeilhaberschaft;
		}
		
		public FlowPanel getMenubar() {
			return menubar;
		}

		public void setMenubar(FlowPanel menubar) {
			this.menubar = menubar;
		}
		void run() {
			menubar.add(teilhaberschaftButton);
			teilhaberschaftButton.addClickHandler(new KontaktFormClickHandler());
			Column<NutzerTeilhaberschaftKontaktWrapper, String> nutzerColumn = new Column<NutzerTeilhaberschaftKontaktWrapper, String>(new TextCell()) {
				@Override
				public String getValue(NutzerTeilhaberschaftKontaktWrapper object) {
					return object.getNutzerMail();
				}
			};
		
			Column<NutzerTeilhaberschaftKontaktWrapper, String> kontaktnameColumn = new Column<NutzerTeilhaberschaftKontaktWrapper, String>(new TextCell()) {
				@Override
				public String getValue(NutzerTeilhaberschaftKontaktWrapper object) {
					return object.getKontaktName();
				}
			};
			Column<NutzerTeilhaberschaftKontaktWrapper, String> modifikationsColumn = new Column<NutzerTeilhaberschaftKontaktWrapper, String>(new TextCell()) {
				@Override
				public String getValue(NutzerTeilhaberschaftKontaktWrapper object) {
					return String.valueOf(object.getKontakt().getModifikationsdatum());
				}
			};
			cellTableTeilhaberschaft.setSelectionModel(ssmModel);
			
			cellTableTeilhaberschaft.addColumn(nutzerColumn, "Eigentümer");
			cellTableTeilhaberschaft.addColumn(kontaktnameColumn, "Kontaktname");
			cellTableTeilhaberschaft.addColumn(modifikationsColumn, "Modifikationsdatum");
			
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			kontaktmanagerVerwaltung.findNutzerTeilhaberschaftKontaktWrapperByTeilhaberschaft(nutzer.getId(), new TeilhaberschaftenCallback());
		}
	
		public class KontaktFormClickHandler implements ClickHandler{

			@Override
			public void onClick(ClickEvent event) {
				if(ssmModel.getSelectedObject() == null){
					Window.alert("Bitte wählen Sie eine Teilhaberschaft aus");
				} else {
					
					KontaktForm kontaktform= new KontaktForm(ssmModel.getSelectedObject().getKontakt(),ssmModel.getSelectedObject().getTeilhaberschaft());
				}
			}
			
		}
		public class TeilhaberschaftenCallback implements AsyncCallback <Vector<NutzerTeilhaberschaftKontaktWrapper>>{
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Laden der Teilhaberschaften");
				
			}
			
			@Override
			public void onSuccess(Vector<NutzerTeilhaberschaftKontaktWrapper> result) {
				cellTableTeilhaberschaft.setRowCount(result.size(), true);
				cellTableTeilhaberschaft.setRowData(0, result);
			}
			
		}
	}

}