package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;


public class KontaktlistView extends MainFrame {

	private Label menuBarHeadlineLabel = new Label("Kontaktlisten");
	private HorizontalPanel contentViewContainer = new HorizontalPanel();
	private ScrollPanel kontaktlistViewPanel = new ScrollPanel();
	private ScrollPanel allKontaktViewPanel = new ScrollPanel();
	private VerticalPanel menuBarContainerPanel = new VerticalPanel();
	private FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	private Button addKontaktlisteButton = new Button("Neue Kontaktliste");
	private Button deleteKontaktlisteButton = new Button ("Kontaktliste löschen");
	private Button addKontaktToKontaktlisteButton = new Button("Kontakt hinzufügen");
	private Button deleteKontaktFromKontaktlisteButton = new Button("Kontakt entfernen");
	private Button addTeilhaberschaftKontaktButton = new Button("Kontakt teilen");
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private ArrayList<Kontakt> kontakteByKontaktlisteToDisplay = new ArrayList<Kontakt>();
	private static ProvidesKey<Kontakt> keyProvider;
	private CellList<Kontakt> kontaktCellList = new CellList<Kontakt>(new KontaktCell(), CellListResources.INSTANCE);
	private CellTable<Kontakt> kontaktCellTable = new CellTable<Kontakt>(13, CellTableResources.INSTANCE, keyProvider);
	private ArrayList<Kontakt> selectedKontakteInCellTable = new ArrayList<Kontakt>();
	private static Kontaktliste kontaktlisteSelectedInTree = null;
	private Kontakt selectedKontakt;

	private SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();

	public KontaktlistView(Kontaktliste selection) {
		setKontaktlisteSelectedInTree(selection);
	}
	
	public KontaktlistView() {
	}



	public void run () {

		oracle.add("Test 1");
		oracle.add("Test 2");
		oracle.add("Test 3");

		SuggestBox box = new SuggestBox(oracle);
		box.setStylePrimaryName("gwt-SuggestBox");
		/*
		 * Menüleiste mit den Buttons für die Anlage von einer neuen Kontaktliste und dem Löschen einer Kontaktliste erzeugen
		 * und dem Panel zuweisen
		 */
		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		addKontaktlisteButton.setStylePrimaryName("mainButton");
		deleteKontaktlisteButton.setStylePrimaryName("mainButton");
		addKontaktToKontaktlisteButton.setStylePrimaryName("mainButton");
		deleteKontaktFromKontaktlisteButton.setStylePrimaryName("mainButton");
		addTeilhaberschaftKontaktButton.setStylePrimaryName("mainButton");
		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		menuBarContainerFlowPanel.add(addKontaktlisteButton);
		menuBarContainerFlowPanel.add(deleteKontaktlisteButton);
		menuBarContainerFlowPanel.add(addKontaktToKontaktlisteButton);
		menuBarContainerFlowPanel.add(deleteKontaktFromKontaktlisteButton);
		menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktButton);
		menuBarContainerFlowPanel.add(box);
		menuBarContainerFlowPanel.setStylePrimaryName("menuBarContainerFlowPanel");
		menuBarContainerPanel.add(menuBarContainerFlowPanel);


		/*
		 * CellList für die Anzeige der Kontaktlisten eines Users wird umgesetzt
		 */
		kontaktCellList.setEmptyListWidget(new HTML("<b>Du hast keine Kontaktlisten</b>"));
		KontaktDataProvider kontaktDataProvider = new KontaktDataProvider();
		kontaktDataProvider.addDataDisplay(kontaktCellList);	
		kontaktCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);


		/*
		 * SelectionHandler für CellList und CellTable hinzufügen. In der CellList wird damit getrackt,
		 * welche Kontaktliste der Nutzer anklickt. Im CellTable, in dem dann alle Kontakte innerhalb der 
		 * Kontaktliste angezeigt werden, dient der MultiSelectionHandler dazu, die Checkbox Auswahl
		 * des Nutzers zu tracken. Die ausgewählten Kontakt Objekte werden dann in einem ArrayList Objekt 
		 * gespeichert.  
		 */
		kontaktCellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedKontakt = selectionModel.getSelectedObject();
			}});
		




	
		
		kontaktCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		kontaktCellTable.setEmptyTableWidget(new HTML("Bitte Kontaktliste auswählen"));
		kontaktCellTable.setWidth("auto", true);
		kontaktCellTable.setStylePrimaryName("kontaktCellTableView");
		kontaktlistViewPanel.add(kontaktCellList);
		
		allKontaktViewPanel.add(kontaktCellTable);
		allKontaktViewPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		kontaktlistViewPanel.setStylePrimaryName("kontaktlistenViewPanel");
		contentViewContainer.add(kontaktlistViewPanel);
		contentViewContainer.add(allKontaktViewPanel);
		addKontaktlisteButton.addClickHandler(new addKontaktlisteClickHandler());
		deleteKontaktlisteButton.addClickHandler(new deleteKontaktlisteClickHandler(kontaktlisteSelectedInTree));
		addKontaktToKontaktlisteButton.addClickHandler(new addKontaktToKontaktlisteClickHandler());
//		deleteKontaktFromKontaktlisteButton.addClickHandler(new deleteKontaktFromKontaktlisteClickHandler(selectedKontakteInCellTable));
		addTeilhaberschaftKontaktButton.addClickHandler(new addTeilhaberschaftKontaktClickHandler(selectedKontakteInCellTable));

		//new addTeilhaberschaftKontaktClickHandler(selectedKontakteInCellTable)

		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentViewContainer);
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerFlowPanel);
	}

	public static Kontaktliste getKontaktlisteSelectedInTree() {
		return kontaktlisteSelectedInTree;
	}


	public void setKontaktlisteSelectedInTree(Kontaktliste kontaktlisteSelectedInTree) {
		this.kontaktlisteSelectedInTree = kontaktlisteSelectedInTree;
	}

	class addKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			NewKontaktlisteDialogBox dbox = new NewKontaktlisteDialogBox();
			dbox.center();
		}
	}
	
	class deleteKontaktlisteClickHandler implements ClickHandler {

		private Kontaktliste kontaktlisteToDelete = null;
		
		public deleteKontaktlisteClickHandler(Kontaktliste selected) {
				kontaktlisteToDelete = selected;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			DeleteKontaktlisteDialogBox deleteKontakt = new DeleteKontaktlisteDialogBox(kontaktlisteToDelete);
			deleteKontakt.center();
		}
	}
	

	class addKontaktToKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.alert("HI");

		}
	}

//	class deleteKontaktFromKontaktlisteClickHandler implements ClickHandler {
//
//		ArrayList<Kontakt> kontakteToRemoveFromKontaktliste;
//		
//		public deleteKontaktFromKontaktlisteClickHandler(ArrayList<Kontakt> selectedKontakteInCellTable) {
//			kontakteToRemoveFromKontaktliste = selectedKontakteInCellTable;
//		}
//
//		@Override
//		public void onClick(ClickEvent event) {
//			if(kontakteToRemoveFromKontaktliste.size() == 0) {
//				Window.alert("Bitte wähle zuerst mindestens einen Kontakt aus, den du löschen möchtest");
//			} else {
//			KontaktFromKontaktlisteLoeschenDialogBox kontaktFromKKontaktlisteLoeschen = new KontaktFromKontaktlisteLoeschenDialogBox(kontakteToRemoveFromKontaktliste, selectionModel.getSelectedObject());
//			kontaktFromKKontaktlisteLoeschen.center();
//			
//			
//			}
//		}
//	}

	class addTeilhaberschaftKontaktClickHandler implements ClickHandler {

		ArrayList<Kontakt> selectedKontakteInCellTable;
		
		public addTeilhaberschaftKontaktClickHandler(ArrayList<Kontakt> selectedKontakteInCellTable) {
			this.selectedKontakteInCellTable = selectedKontakteInCellTable;
		}

		@Override
		public void onClick(ClickEvent event) {
			if(selectedKontakteInCellTable.size() == 0) {
				Window.alert("Bitte wähle zuerst mindestens einen Kontakt aus, den du teilen möchtest");
			} else {
				TeilhaberschaftDialogBox dialogBox = new TeilhaberschaftDialogBox(selectedKontakteInCellTable);
				dialogBox.center();
			}
		}
	}

	static class KontaktCell extends AbstractCell<Kontakt> {

		@Override
		public void render(Context context, Kontakt value, SafeHtmlBuilder sb) {

			if (value == null) {
				return;
			}
			sb.appendEscaped(value.getName());
		}		
	}



	private static class KontaktDataProvider extends AsyncDataProvider<Kontakt> {

		@Override
		protected void onRangeChanged(HasData<Kontakt> display) {
			Nutzer nutzerKontaktliste = new Nutzer();
			nutzerKontaktliste.setId(Integer.parseInt(Cookies.getCookie("id")));
			final Range range = display.getVisibleRange();

			kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(getKontaktlisteSelectedInTree(), new AsyncCallback<Vector<Kontakt>>() {
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
}

