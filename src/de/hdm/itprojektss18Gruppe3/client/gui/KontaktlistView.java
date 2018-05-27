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
	private CellTable<Kontakt> kontaktCellTable = new CellTable<Kontakt>(13, CellTableResources.INSTANCE, keyProvider);
	private ArrayList<Kontakt> selectedKontakteInCellTable = new ArrayList<Kontakt>();
	private Kontaktliste selected = null;


	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();

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
		 * CellList für die Anzeige der Kontaktlisten eines Users wird erzeugt
		 */

		CellList<Kontaktliste> kontaktlistenCellList = new CellList<Kontaktliste>(new KontaktlistCell(), CellListResources.INSTANCE);
		kontaktlistenCellList.setEmptyListWidget(new HTML("<b>Du hast keine Kontaktlisten</b>"));
		KontaktlistenDataProvider kontaktlistenDataProvider = new KontaktlistenDataProvider();
		kontaktlistenDataProvider.addDataDisplay(kontaktlistenCellList);	
		kontaktlistenCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);


		// Add a selection model to handle user selection.
		final SingleSelectionModel<Kontaktliste> selectionModel = new SingleSelectionModel<Kontaktliste>();
		kontaktlistenCellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selected = selectionModel.getSelectedObject();
				deleteKontaktlisteButton.addClickHandler(new deleteKontaktlisteClickHandler(selected));
				if (selected != null) {

					kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(selected, new AsyncCallback<Vector<Kontakt>>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(Vector<Kontakt> result) {
							kontakteByKontaktlisteToDisplay.clear();
							int id = 0;
							for(Kontakt k : result)  {
								k.setId(id);
								kontakteByKontaktlisteToDisplay.add(k);
								id++;
							}
							kontaktCellTable.setRowCount(kontakteByKontaktlisteToDisplay.size(), true);
							kontaktCellTable.setRowData(0, kontakteByKontaktlisteToDisplay);		
						}	
					});
				}
			}
		});
		// Wird angezeit, wenn der CellTable keine Daten enthält (Testzweck)

		keyProvider = new ProvidesKey<Kontakt>() {
			public Object getKey(Kontakt item) {
				// Always do a null check.
				return item == null ? null : item.getId();
			}
		};

		final MultiSelectionModel<Kontakt> selectionModelCellTable = new MultiSelectionModel<Kontakt>(keyProvider);
		kontaktCellTable.setSelectionModel(selectionModelCellTable,
				DefaultSelectionEventManager.<Kontakt> createCheckboxManager());
		selectionModelCellTable.addSelectionChangeHandler(new Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedKontakteInCellTable.clear();
				selectedKontakteInCellTable.addAll(((MultiSelectionModel<Kontakt>) selectionModelCellTable).getSelectedSet());
			}
		});


		/*		     * CheckBoxen für die Auswahl mehrerer Kontakte anlegen. Hiermit können mehrere Kontakte gleichzeitig
		 * z.B. aus einer Kontaktliste entfernt werden.
		 * */
		Column<Kontakt, Boolean> checkColumn = new Column<Kontakt, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Kontakt object) {
				// Get the value from the selection model.
				return selectionModelCellTable.isSelected(object);
			}
		};

		kontaktCellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktCellTable.setColumnWidth(checkColumn, 40, Unit.PX);    


		// Add a text column to show the name.
		TextColumn<Kontakt> kontaktnameColumn = new TextColumn<Kontakt>() {
			@Override
			public String getValue(Kontakt object) {
				return object.getName();
			}
		};
		kontaktCellTable.addColumn(kontaktnameColumn, "Kontaktname");
		kontaktCellTable.setColumnWidth(kontaktnameColumn, 25, Unit.EM);


		Column<Kontakt, String> statusIconColumn = new Column<Kontakt, String>(
				new TextCell() 
				{
					public void render(Context context, 
							SafeHtml value, 
							SafeHtmlBuilder sb)
					{
						sb.appendHtmlConstant("<img width=\"20\" src=\"images/" 
								+ value.asString() + "\">");
					}
				})
		{
			@Override
			public String getValue(Kontakt object) {
				if(Integer.toString(object.getStatus()) == "1") {
					return "group.svg";
				} else {
					return "singleperson.svg";
				}
			}
		};
		kontaktCellTable.addColumn(statusIconColumn, "Status");
		statusIconColumn.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);

		ButtonCell visitProfileButton = new ButtonCell();
		Column<Kontakt,String> visitProfileButtonColumn = new Column<Kontakt,String>(visitProfileButton) {
			public String getValue(Kontakt object) {
				return "Ansehen";
			}
		};

		visitProfileButtonColumn.setFieldUpdater(new FieldUpdater<Kontakt, String>() {
			@Override
			public void update(int index, Kontakt object, String value) {
				KontaktCellTable visitProfile = new KontaktCellTable(object);
				RootPanel.get("content").clear();
				RootPanel.get("content").add(visitProfile);
			}
		});
		kontaktCellTable.addColumn(visitProfileButtonColumn, "");
		kontaktCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		kontaktCellTable.setEmptyTableWidget(new HTML("Bitte Kontaktliste auswählen"));
		kontaktCellTable.setWidth("auto", true);
		kontaktCellTable.setStylePrimaryName("kontaktCellTableView");
		kontaktlistViewPanel.add(kontaktlistenCellList);
		allKontaktViewPanel.add(kontaktCellTable);
		allKontaktViewPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		kontaktlistViewPanel.setStylePrimaryName("kontaktlistenViewPanel");
		contentViewContainer.add(kontaktlistViewPanel);
		contentViewContainer.add(allKontaktViewPanel);
		addKontaktlisteButton.addClickHandler(new addKontaktlisteClickHandler());
		deleteKontaktlisteButton.addClickHandler(new deleteKontaktlisteClickHandler(selected));
		addKontaktToKontaktlisteButton.addClickHandler(new addKontaktToKontaktlisteClickHandler());
		deleteKontaktFromKontaktlisteButton.addClickHandler(new deleteKontaktFromKontaktlisteClickHandler());
		addTeilhaberschaftKontaktButton.addClickHandler(new addTeilhaberschaftKontaktClickHandler(selectedKontakteInCellTable));

		//new addTeilhaberschaftKontaktClickHandler(selectedKontakteInCellTable)

		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentViewContainer);
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerFlowPanel);
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

	class deleteKontaktFromKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontaktFromKontaktlisteLoeschenDialogBox kontaktFromKKontaktlisteLoeschen = new KontaktFromKontaktlisteLoeschenDialogBox();
			kontaktFromKKontaktlisteLoeschen.center();
	}
	}

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
				//	TeilhaberschaftDialogBox dialogBox = new TeilhaberschaftDialogBox(selectedKontakteInCellTable);
			}
		}
	}

	static class KontaktlistCell extends AbstractCell<Kontaktliste> {

		@Override
		public void render(Context context, Kontaktliste value, SafeHtmlBuilder sb) {

			if (value == null) {
				return;
			}
			sb.appendEscaped(value.getBezeichnung());
		}		
	}



	private static class KontaktlistenDataProvider extends AsyncDataProvider<Kontaktliste> {

		@Override
		protected void onRangeChanged(HasData<Kontaktliste> display) {
			Nutzer nutzerKontaktliste = new Nutzer();
			nutzerKontaktliste.setId(Integer.parseInt(Cookies.getCookie("id")));
			final Range range = display.getVisibleRange();

			kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(nutzerKontaktliste.getId(), new AsyncCallback<Vector<Kontaktliste>>() {
				int start = range.getStart();

				ArrayList<Kontaktliste> kontaktlistenToDisplay = new ArrayList<Kontaktliste>();
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Fehler beim Auslesen aller Kontakte");

				}

				@Override
				public void onSuccess(Vector<Kontaktliste> result) {
					kontaktlistenToDisplay.addAll(result);
					updateRowData(start, kontaktlistenToDisplay);
				}
			});
		}
	}
}