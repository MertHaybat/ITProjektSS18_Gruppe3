package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class AllKontaktView extends MainFrame {

	private VerticalPanel vPanel = new VerticalPanel();
	private ScrollPanel allKontakteCellTableContainer = new ScrollPanel();
	private Label menuBarHeadlineLabel = new Label("Kontakte");
	private FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	private VerticalPanel menuBarContainerPanel = new VerticalPanel();
	private Button addKontaktButton = new Button("Neuer Kontakt");
	private Button deleteKontaktButton = new Button("Kontakt löschen");
	private Button addKontaktToKontaktlistButton = new Button("+ Kontaktliste");
	private Button addTeilhaberschaftKontaktButton = new Button("Kontakt Teilen");
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private CellTable<Kontakt> allKontakteCellTable = new CellTable<Kontakt>(11, CellTableResources.INSTANCE);
	private ArrayList<Kontakt> allKontakteSelectedArrayList = new ArrayList<>();
	private ArrayList<Kontakt> allKontakteByUserArrayList = new ArrayList<>();
	private List<Kontakt> allSelectedKontakte = new ArrayList<>();
	private static ProvidesKey<Kontakt> keyProvider;
	private SuggestBox box = new SuggestBox(oracle);
	private Label contentHeadline = new Label("Die Liste aller deiner Kontakte");
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	private MultiSelectionModel<Kontakt> selectionModelCellTable = new MultiSelectionModel<Kontakt>(keyProvider);
	/**
	 * The list of data to display.
	 */

	private Kontakt kontakt = null;

	// public AllKontaktView(){
	//
	//
	// }
	//
	public AllKontaktView() {
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AllKontaktByNutzerCallback());
		super.onLoad();
	}

	public AllKontaktView(Kontaktliste k) {
		kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(k, new AllKontaktByNutzerCallback());
		run();
	}

	public void run() {

		keyProvider = new ProvidesKey<Kontakt>() {
			public Object getKey(Kontakt item) {
				// Always do a null check.
				return item == null ? null : item.getId();
			}
		};

		
		allKontakteCellTable.setSelectionModel(selectionModelCellTable,
				DefaultSelectionEventManager.<Kontakt>createCheckboxManager());
		selectionModelCellTable.addSelectionChangeHandler(new SelectionChangeHandlerCellTable());

		/*
		 * Menüleiste mit den Buttons für die Anlage von einer neuen
		 * Kontaktliste und dem Löschen einer Kontaktliste erzeugen und dem
		 * Panel zuweisen
		 */
		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");

		addKontaktButton.setStylePrimaryName("mainButton");
		deleteKontaktButton.setStylePrimaryName("mainButton");
		addKontaktToKontaktlistButton.setStylePrimaryName("mainButton");
		addTeilhaberschaftKontaktButton.setStylePrimaryName("mainButton");

		box.setStylePrimaryName("gwt-SuggestBox");
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");
		menuBarContainerPanel.add(menuBarContainerFlowPanel);
		contentHeadline.setStylePrimaryName("h2");

		// Set the message to display when the table is empty.
		allKontakteCellTable.setEmptyTableWidget(new Label("Du hast bisher keine Kontakte angelegt"));

		// Add a selection model so we can select cells.

		/**
		 * Add the columns to the table.
		 */
		// private void initTableColumns(final SelectionModel<Kontakt>
		// selectionModel) {
		// Checkbox column. This table will uses a checkbox column for
		// selection.
		// Alternatively, you can call dataGrid.setSelectionEnabled(true) to
		// enable
		// mouse selection.
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

		ButtonCell visitProfileButton = new ButtonCell();
		Column<Kontakt, String> visitProfileButtonColumn = new Column<Kontakt, String>(visitProfileButton) {
			public String getValue(Kontakt object) {
				return "Ansehen";
			}
		};

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

		allKontakteCellTableContainer.add(allKontakteCellTable);
		allKontakteCellTableContainer.setStylePrimaryName("cellListWidgetContainerPanel");
		vPanel.add(contentHeadline);
		vPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		vPanel.add(allKontakteCellTableContainer);

		deleteKontaktButton.addClickHandler(new KontaktDeleteClickHandler());
		addKontaktToKontaktlistButton.addClickHandler(new AddKontaktToKontaktlisteClickHandler());
		addTeilhaberschaftKontaktButton
				.addClickHandler(new addTeilhaberschaftKontaktClickHandler(allKontakteSelectedArrayList));
		addKontaktButton.addClickHandler(new CreateKontaktClickHandler());

		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		menuBarContainerFlowPanel.add(addKontaktButton);
		menuBarContainerFlowPanel.add(deleteKontaktButton);
		menuBarContainerFlowPanel.add(addKontaktToKontaktlistButton);
		menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktButton);
		menuBarContainerFlowPanel.add(box);
		RootPanel.get("menubar").add(menuBarContainerPanel);
		RootPanel.get("content").add(vPanel);

	}

	// public class ShowKontaktClickHandler implements ClickHandler{
	//
	// @Override
	// public void onClick(ClickEvent event) {
	// // TODO Auto-generated method stub
	// KontaktForm kForm = new KontaktForm(kontakt);
	// RootPanel.get("content").clear();
	// RootPanel.get("content").add(kForm);
	// }
	//
	// }
	public class KontaktDeleteClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			DeleteKontaktDialogBox db = new DeleteKontaktDialogBox(allKontakteSelectedArrayList);
			db.center();
			
		}
		public class DeleteKontaktDialogBox extends DialogBox{
			private VerticalPanel vPanel = new VerticalPanel();
			private HorizontalPanel hPanel = new HorizontalPanel();
			private Label abfrage= new Label("Möchten Sie den Kontakt löschen? Dies führt dazu,"
					+ " dass der Kontakt in allen Kontaktlisten gelöscht wird.");
			private Button ja = new Button("Ja");
			private Button nein = new Button("Nein");
			private ArrayList<Kontakt> kontakt = new ArrayList<>();
			
			public DeleteKontaktDialogBox(ArrayList<Kontakt> k){
				kontakt=k;
				ja.addClickHandler(new DeleteKontaktClickHandler());
				nein.addClickHandler(new AbortDeleteClickHandler());
				vPanel.add(abfrage);
				hPanel.add(ja);
				hPanel.add(nein);
				vPanel.add(hPanel);
				this.setTitle("Kontakt löschen");
				this.add(vPanel);
			}
			public class AbortDeleteClickHandler implements ClickHandler{

				@Override
				public void onClick(ClickEvent event) {
					hide();
				}
				
			}
			public class DeleteKontaktClickHandler implements ClickHandler{

				@Override
				public void onClick(ClickEvent event) {
					for (Kontakt kontakt : kontakt) {
						kontaktmanagerVerwaltung.deleteKontaktByID(kontakt, new DeleteKontaktCallback());
						
					}
				}
				
			}
			public class DeleteKontaktCallback implements AsyncCallback<Void>{

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
	public class SelectionChangeHandlerCellTable implements SelectionChangeEvent.Handler{

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
			new KontaktPopup().center();
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
			for (Kontakt k : result) {
				// k.setId(id);
				allKontakteByUserArrayList.add(k);
				// id++;
			}
			allKontakteCellTable.setRowCount(allKontakteByUserArrayList.size(), true);
			allKontakteCellTable.setRowData(0, allKontakteByUserArrayList);
		}

	}

	// public class PreviewClickHandler implements Handler<Kontakt>{
	// @Override
	// public void onCellPreview(CellPreviewEvent<Kontakt> event) {
	// if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {
	//
	// final Kontakt value = event.getValue();
	// final Boolean state =
	// !event.getDisplay().getSelectionModel().isSelected(value);
	// event.getDisplay().getSelectionModel().setSelected(value, state);
	// event.setCanceled(true);
	// }
	// }
	//
	// }

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

	class addTeilhaberschaftKontaktClickHandler implements ClickHandler {

		ArrayList<Kontakt> selectedKontakteInCellTable;

		public addTeilhaberschaftKontaktClickHandler(ArrayList<Kontakt> selectedKontakteInCellTable) {
			this.selectedKontakteInCellTable = selectedKontakteInCellTable;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (selectedKontakteInCellTable.size() == 0) {
				Window.alert("Bitte wähle zuerst mindestens einen Kontakt aus, den du teilen möchtest");
			} else {
				TeilhaberschaftDialogBox dialogBox = new TeilhaberschaftDialogBox(selectedKontakteInCellTable);
				dialogBox.center();
			}
		}
	}

}