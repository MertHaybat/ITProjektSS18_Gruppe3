package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;

import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;

/**
 * 
 * @author ersinbarut
 *
 */
public class TeilhaberschaftKontakte extends MainFrame {

	private VerticalPanel vPanel = new VerticalPanel();
	private Label menuBarHeadlineLabel = new Label("Teilhaberschaft");
	private FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	private VerticalPanel menuBarContainerPanel = new VerticalPanel();
	private Button deleteKontaktButton = new Button("Teilhaberschaft loeschen");
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private CellTable<Kontakt> allKontakteCellTable = new CellTable<Kontakt>(11, CellTableResources.INSTANCE);
	private final MultiSelectionModel<Kontakt> ssmAuspraegung = new MultiSelectionModel<Kontakt>();
	private final CheckboxCell cbCell = new CheckboxCell(false, true);
	private SuggestBox box = new SuggestBox(oracle);
	private Label contentHeadline = new Label("Die Liste aller deiner Kontakte aus Teilhaberschaften");
	private SimplePager pager;
	private final Handler<Kontakt> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager();
	private Button showKontaktButton = new Button("Anzeigen");
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	// VerticalPanel vPanel = new VerticalPanel();
	// Label menuBarHeadlineLabel = new Label("Kontakte aus Teilhaberschaften");
	// FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	// VerticalPanel menuBarContainerPanel = new VerticalPanel();
	// MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();

	private Kontakt kontakt = new Kontakt();

	protected void run() {

		allKontakteCellTable.setSelectionModel(ssmAuspraegung, selectionEventManager);

		/*
		 * Menüleiste mit den Buttons für die Anlage von einer neuen
		 * Kontaktliste und dem Löschen einer Kontaktliste erzeugen und dem
		 * Panel zuweisen
		 */

		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");

		// addKontaktButton.setStylePrimaryName("mainButton");
		deleteKontaktButton.setStylePrimaryName("mainButton");
		showKontaktButton.setStylePrimaryName("mainButton");

		menuBarContainerFlowPanel.setWidth("200%");

		box.setStylePrimaryName("gwt-SuggestBox");
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");
		menuBarContainerPanel.add(menuBarContainerFlowPanel);

		contentHeadline.setStylePrimaryName("h2");

		allKontakteCellTable.setHeight("600px");
		allKontakteCellTable.setWidth("1000px");

		// Set the message to display when the table is empty.
		allKontakteCellTable.setEmptyTableWidget(new Label("Du hast bisher keine Kontakt in Teilhaberschaften"));

		// Create a Pager to control the table.

		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(allKontakteCellTable);

		// Add a selection model so we can select cells.

		/**
		 * Add the columns to the table.
		 */
		// private void initTableColumns(final SelectionModel<Kontakt>
		// selectionModel) {
		// Checkbox column. This table will use a checkbox column for selection.
		// Alternatively, you can call dataGrid.setSelectionEnabled(true) to
		// enable
		// mouse selection.

		Column<Kontakt, Boolean> cbColumn = new Column<Kontakt, Boolean>(cbCell) {
			@Override
			public Boolean getValue(Kontakt object) {
				kontakt = object;
				return ssmAuspraegung.isSelected(object);
			}
		};
		allKontakteCellTable.addCellPreviewHandler(new PreviewClickHandler());

		allKontakteCellTable.addColumn(cbColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		allKontakteCellTable.setColumnWidth(cbColumn, 40, Unit.PX);

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

		allKontakteCellTable.addColumn(kontaktnameColumn, "Kontaktname");
		allKontakteCellTable.setColumnWidth(kontaktnameColumn, 80, Unit.PX);
		allKontakteCellTable.addColumn(iconColumn);
		allKontakteCellTable.setColumnWidth(iconColumn, 5, Unit.PX);

		// allKontakteCellTable.setRowCount(CONTACTS.size(), true);

		// Push the data into the widget.
		kontaktmanagerVerwaltung.findAllKontakteByTeilhabenderID(2, new AllKontakteByTeilhabenderCallback());
		// .findAllKontaktByNutzerID(2, new AllKontaktByNutzerCallback());

		vPanel.add(contentHeadline);
		vPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		vPanel.add(allKontakteCellTable);
		pager.setStylePrimaryName("gwt-SimplePager");
		vPanel.add(pager);
		// RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerPanel);
		RootPanel.get("content").add(vPanel);
		// this.add(vPanel);

		showKontaktButton.addClickHandler(new ShowKontaktClickHandler());

		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		menuBarContainerFlowPanel.add(box);

		menuBarContainerFlowPanel.add(deleteKontaktButton);
		menuBarContainerFlowPanel.add(showKontaktButton);

	}

	public class ShowKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			KontaktForm kForm = new KontaktForm(kontakt);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kForm);
		}

	}

	public class AllKontakteByTeilhabenderCallback implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Daten ist ein Fehler aufgetreten" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			allKontakteCellTable.setRowCount(result.size(), true);
			allKontakteCellTable.setRowData(0, result);
		}

	}

	public class PreviewClickHandler implements Handler<Kontakt> {
		@Override
		public void onCellPreview(CellPreviewEvent<Kontakt> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final Kontakt value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}

	}
}
