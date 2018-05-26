package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

/**
 * 
 * @author ersinbarut
 *
 */
public class TeilhaberschaftKontaktliste extends MainFrame {

	private Label menuBarHeadlineLabel = new Label("Kontaktlisten");
	private HorizontalPanel contentViewContainer = new HorizontalPanel();
	private HorizontalPanel kontaktlistViewPanel = new HorizontalPanel();
	private VerticalPanel allKontaktViewPanel = new VerticalPanel();
	private VerticalPanel menuBarContainerPanel = new VerticalPanel();
	private FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	private Button deleteKontaktlisteButton = new Button("Teilhaberschaft löschen");
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private ArrayList<Kontakt> kontakteByKontaktlisteToDisplay = new ArrayList<Kontakt>();
	private static ProvidesKey<Kontakt> keyProvider;
	private CellTable<Kontakt> kontaktCellTable = new CellTable<Kontakt>(13, CellTableResources.INSTANCE, keyProvider);
	private SimplePager pager;
	private ArrayList<Kontakt> selectedKontakteInCellTable = new ArrayList<Kontakt>();

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	@Override
	protected void run() {
		// TODO Auto-generated method stub

		/**
		 * hart gecodet um testen zu können
		 */
		oracle.add("Test 1");
		oracle.add("Test 2");
		oracle.add("Test 3");

		SuggestBox box = new SuggestBox(oracle);
		box.setStylePrimaryName("gwt-SuggestBox");

		/*
		 * Menüleiste mit den Buttons für die Anlage von einer neuen
		 * Kontaktliste und dem Löschen einer Kontaktliste erzeugen und dem
		 * Panel zuweisen
		 */
		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		// addKontaktlisteButton.setStylePrimaryName("mainButton");
		deleteKontaktlisteButton.setStylePrimaryName("mainButton");
		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		// menuBarContainerFlowPanel.add(addKontaktlisteButton);
		menuBarContainerFlowPanel.add(deleteKontaktlisteButton);
		menuBarContainerFlowPanel.add(box);
		menuBarContainerFlowPanel.setStylePrimaryName("menuBarContainerFlowPanel");
		menuBarContainerPanel.add(menuBarContainerFlowPanel);

		/*
		 * CellList für die Anzeige der Kontaktlisten eines Users wird erzeugt
		 */

		CellList<Kontaktliste> kontaktlistenCellList = new CellList<Kontaktliste>(new KontaktlistCell(),
				CellListResources.INSTANCE);
		kontaktlistenCellList.setEmptyListWidget(new HTML("<b>Du hast keine Teilhaberschaft in Kontaktlisten</b>"));
		KontaktlistenDataProvider kontaktlistenDataProvider = new KontaktlistenDataProvider();
		kontaktlistenDataProvider.addDataDisplay(kontaktlistenCellList);
		kontaktlistenCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a selection model to handle user selection.
		final SingleSelectionModel<Kontaktliste> selectionModel = new SingleSelectionModel<Kontaktliste>();
		kontaktlistenCellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Kontaktliste selected = selectionModel.getSelectedObject();
				if (selected != null) {

					kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(selected,
							new AsyncCallback<Vector<Kontakt>>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(Vector<Kontakt> result) {
									kontakteByKontaktlisteToDisplay.clear();

									// !!!!!!!!!!Kontakte haben alle die selbe
									// ID, daher wird die ganze Tabelle
									// selektiert wenn man eine CheckBox klickt.
									// !!!!!!!!!!Daher hier manuelles setzen von
									// unterschiedlichen ID's für ProvidesKey
									int id = 0;
									for (Kontakt k : result) {
										k.setId(id);
										id++;
										kontakteByKontaktlisteToDisplay.add(k);
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

		final SelectionModel<Kontakt> selectionModelCellTable = new MultiSelectionModel<Kontakt>(keyProvider);
		kontaktCellTable.setSelectionModel(selectionModelCellTable,
				DefaultSelectionEventManager.<Kontakt>createCheckboxManager());
		selectionModelCellTable.addSelectionChangeHandler(new Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedKontakteInCellTable.clear();
				selectedKontakteInCellTable
						.addAll(((MultiSelectionModel<Kontakt>) selectionModelCellTable).getSelectedSet());
				Window.alert(Integer.toString(selectedKontakteInCellTable.size()));
			}
		});

		/*
		 * * CheckBoxen für die Auswahl mehrerer Kontakte anlegen. Hiermit
		 * können mehrere Kontakte gleichzeitig z.B. aus einer Kontaktliste
		 * entfernt werden.
		 */
		Column<Kontakt, Boolean> checkColumn = new Column<Kontakt, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Kontakt object) {
				// Get the value from the selection model.
				return selectionModelCellTable.isSelected(object);
			}
		};

		kontaktCellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		kontaktCellTable.setColumnWidth(checkColumn, 40, Unit.PX);

		// Add a text column to show the name.
		TextColumn<Kontakt> nameColumn = new TextColumn<Kontakt>() {
			@Override
			public String getValue(Kontakt object) {
				return object.getName();
			}
		};
		kontaktCellTable.addColumn(nameColumn, "Name");

		// Add a Vorname column to show the birthday.
		TextColumn<Kontakt> vornameColumn = new TextColumn<Kontakt>() {
			@Override
			public String getValue(Kontakt object) {
				return object.getName();
			}
		};
		kontaktCellTable.addColumn(vornameColumn, "Vorname");

		// Add a text column to show the address.
		TextColumn<Kontakt> addressColumn = new TextColumn<Kontakt>() {
			@Override
			public String getValue(Kontakt object) {
				return object.getName();
			}
		};
		kontaktCellTable.addColumn(addressColumn, "Address");

		// Column<Kontakt, String> statusIconColumn = new Column<Kontakt,
		// String>(
		// new TextCell()
		// {
		// public void render(Context context,
		// SafeHtml value,
		// SafeHtmlBuilder sb)
		// {
		// sb.appendHtmlConstant("<img width=\"20\" src=\"images/"
		// + value.asString() + "\">");
		// }
		// })
		// {
		// @Override
		// public String getValue(Kontakt object) {
		// if(Integer.toString(object.getStatus()) == "1") {
		// return "group.svg";
		// } else {
		// return "singleperson.svg";
		// }
		// }
		// };
		// kontaktCellTable.addColumn(statusIconColumn, "Status");
		// statusIconColumn.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);

		ButtonCell visitProfileButton = new ButtonCell();
		Column<Kontakt, String> visitProfileButtonColumn = new Column<Kontakt, String>(visitProfileButton) {
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

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(kontaktCellTable);
		pager.setPageSize(13);

		// Add a selection model to handle user selection.

		// Set the total row count. This isn't strictly necessary, but it
		// affects
		// paging calculations, so its good habit to keep the row count up to
		// date.
		// kontaktCellTable.setRowCount(kontakteByKontaktlisteToDisplay.size(),
		// true);

		// Push the data into the widget.
		// kontaktCellTable.setRowData(0, kontakteByKontaktlisteToDisplay);

		kontaktCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		kontaktCellTable.setEmptyTableWidget(new HTML("Bitte Kontaktliste auswählen"));
		kontaktCellTable.setWidth("auto", true);
		kontaktCellTable.setColumnWidth(nameColumn, 12, Unit.EM);
		kontaktCellTable.setColumnWidth(vornameColumn, 12, Unit.EM);
		kontaktCellTable.setColumnWidth(addressColumn, 18, Unit.EM);

		kontaktCellTable.setStylePrimaryName("kontaktCellTableView");
		kontaktlistViewPanel.add(kontaktlistenCellList);

		allKontaktViewPanel.add(kontaktCellTable);
		allKontaktViewPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		allKontaktViewPanel.add(pager);
		pager.setStylePrimaryName("gwt-SimplePager");
		kontaktlistViewPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		contentViewContainer.add(kontaktlistViewPanel);
		contentViewContainer.add(allKontaktViewPanel);
		// addKontaktlisteButton.addClickHandler(new
		// addKontaktlisteClickHandler());
		deleteKontaktlisteButton.addClickHandler(new deleteKontaktlisteClickHandler());

		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentViewContainer);
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerFlowPanel);
	}

	// class addKontaktlisteClickHandler implements ClickHandler {
	//
	// @Override
	// public void onClick(ClickEvent event) {
	// // TODO Auto-generated method stub
	// KontaktlisteDialogBox dbox = new KontaktlisteDialogBox();
	// dbox.center();
	// }
	// }

	/**
	 * 
	 * @author ersinbarut Die Methode deleteKontaktlisteByTeilhaberschaftID muss
	 *         noch implementiert werden !!!
	 */

	class deleteKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			for (Kontakt k : selectedKontakteInCellTable) {
				kontaktmanagerVerwaltung.deleteKontaktByID(k, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Fehler beim Löschen");

					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Kontakt wurde gelöscht!");
					}
				});
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

			/**
			 * findAllKontaktlisteByNutzerID muss ergänzt werden durch die noch
			 * zu implementierende Methode
			 * findAllKontaktlisteByTeilhaberschaftID
			 */
			kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(nutzerKontaktliste.getId(),
					new AsyncCallback<Vector<Kontaktliste>>() {
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
