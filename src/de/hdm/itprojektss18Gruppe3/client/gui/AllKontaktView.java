package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.MainFrame;

public class AllKontaktView extends MainFrame {


	VerticalPanel vPanel = new VerticalPanel();
	Label menuBarHeadlineLabel = new Label("Kontakte");
	FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	VerticalPanel menuBarContainerPanel = new VerticalPanel();
	Button addKontaktButton = new Button("+ Kontakt");
	Button deleteKontaktButton = new Button("Loeschen");
	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();



	/**
	 * The list of data to display.
	 */


	public void run() {

		/*
		 * Menüleiste mit den Buttons für die Anlage von einer neuen Kontaktliste und dem Löschen einer Kontaktliste erzeugen
		 * und dem Panel zuweisen
		 */
		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		//menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");

		addKontaktButton.setStylePrimaryName("mainButton");
		deleteKontaktButton.setStylePrimaryName("mainButton");
		menuBarContainerFlowPanel.add(addKontaktButton);
		menuBarContainerFlowPanel.add(deleteKontaktButton);
		menuBarContainerFlowPanel.setWidth("200%");


		oracle.add("Test 1");
		oracle.add("Test 2");
		oracle.add("Test 3");

		SuggestBox box = new SuggestBox(oracle);

		box.setStylePrimaryName("gwt-SuggestBox");
		menuBarContainerFlowPanel.add(box);
		menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");
		menuBarContainerPanel.add(menuBarContainerFlowPanel);


		Label contentHeadline = new Label("Die Liste aller deiner Kontakte");
		contentHeadline.setStylePrimaryName("h2");


		CellTable<Contact> allKontakteCellTable = new CellTable<Contact>(11, CellTableResources.INSTANCE);

		allKontakteCellTable.setHeight("600px");
		allKontakteCellTable.setWidth("1000px");

		// Set the message to display when the table is empty.
		allKontakteCellTable.setEmptyTableWidget(new Label("Du hast bisher keine Kontakte angelegt"));  

		// Create a Pager to control the table.
		SimplePager pager;
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(allKontakteCellTable);

		// Add a selection model so we can select cells.
		final SelectionModel<Contact> selectionModel =
				new MultiSelectionModel<Contact>();
		allKontakteCellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager
				.<Contact> createCheckboxManager());

		/**
		 * Add the columns to the table.
		 */
		// private void initTableColumns(final SelectionModel<Contact> selectionModel) {
		// Checkbox column. This table will uses a checkbox column for selection.
		// Alternatively, you can call dataGrid.setSelectionEnabled(true) to enable
		// mouse selection.
		Column<Contact, Boolean> checkColumn =
				new Column<Contact, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Contact object) {
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};
		allKontakteCellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		allKontakteCellTable.setColumnWidth(checkColumn, 40, Unit.PX);


		// First name.
		Column<Contact, String> firstNameColumn =
				new Column<Contact, String>(new TextCell()) {
			@Override
			public String getValue(Contact object) {
				return object.vorname;
			}
		};

		allKontakteCellTable.addColumn(firstNameColumn, "Vorname");
		allKontakteCellTable.setColumnWidth(firstNameColumn, 80, Unit.PX);


		// Last name.
		Column<Contact, String> lastNameColumn =
				new Column<Contact, String>(new TextCell()) {
			@Override
			public String getValue(Contact object) {
				return object.name;
			}
		};

		allKontakteCellTable.addColumn(lastNameColumn, "Nachname");
		allKontakteCellTable.setColumnWidth(lastNameColumn, 80, Unit.PX);


		// Age.
		Column<Contact, Number> ageColumn = new Column<Contact, Number>(new NumberCell()) {
			@Override
			public Number getValue(Contact object) {
				return object.age;
			}
		};

		allKontakteCellTable.addColumn(ageColumn, "Alter");
		allKontakteCellTable.setColumnWidth(ageColumn, 5, Unit.PX);


		// Category.
		Column<Contact, String> kontaktlisteColumn =
				new Column<Contact, String>(new TextCell()) {
			@Override
			public String getValue(Contact object) {
				return object.kontaktliste;
			}
		};

		allKontakteCellTable.addColumn(kontaktlisteColumn, "Kontaktliste");
		allKontakteCellTable.setColumnWidth(kontaktlisteColumn, 80, Unit.PX);


		// Address.
		Column<Contact, String> addressColumn = new Column<Contact, String>(new TextCell()) {
			@Override
			public String getValue(Contact object) {
				return object.address;
			}
		};

		allKontakteCellTable.addColumn(addressColumn, "Adresse");
		allKontakteCellTable.setColumnWidth(addressColumn, 150, Unit.PX);

		// Status		
		Column<Contact, String> statusColumn = new Column<Contact, String>(new TextCell()) {
			@Override
			public String getValue(Contact object) {
				return object.status;
			}
		};


		Column<Contact, String> iconColumn = 
				new Column<Contact, String>(
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
			public String getValue(Contact object) {
				if(object.status.equals("Geteilt")) {
					return "group.svg";
				} else {
					return "singleperson.svg";
				}
			}
		};



		allKontakteCellTable.addColumn(statusColumn, "Status");
		allKontakteCellTable.setColumnWidth(statusColumn, 80, Unit.PX);
		allKontakteCellTable.addColumn(iconColumn);
		allKontakteCellTable.setColumnWidth(iconColumn, 5, Unit.PX);


		//   allKontakteCellTable.setRowCount(CONTACTS.size(), true);

		// Push the data into the widget.
		allKontakteCellTable.setRowData(0, CONTACTS);

		vPanel.add(contentHeadline);
		vPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		vPanel.add(allKontakteCellTable);
		pager.setStylePrimaryName("gwt-SimplePager");
		vPanel.add(pager);
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerPanel);
		RootPanel.get("content").add(vPanel);
	}


	/*
	 * TO BE DELETED
	 */

	private static class Contact {
		private final String address;
		private final String vorname;
		private final String name;
		private final String kontaktliste;
		private final int age;
		private final String status;

		public Contact(String name, String vorname, String address, int age, String kontaktliste, String status) {
			this.name = name;
			this.vorname = vorname;
			this.address = address;
			this.age = age;
			this.kontaktliste = kontaktliste;
			this.status = status;
		}
	}


	/**
	 * The list of data to display.
	 */
	private static final List<Contact> CONTACTS = Arrays.asList(
			new Contact("John", "Manfred", "123 Fourth Avenue", 24, "AB", "Geteilt"),
			new Contact("Joe", "Hansi", "22 Lance Ln", 11, "TEST", "Geteilt"),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 85, "BXI", "Nicht Geteilt"),
			new Contact("John", "Manfred", "123 Fourth Avenue", 22, "JSAD", "Geteilt"),
			new Contact("Joe", "Hansi", "22 Lance Ln", 9, "BMASD", "Geteilt"),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 12, "MVUDSF", "Nicht Geteilt"),
			new Contact("John", "Manfred", "123 Fourth Avenue", 75, "JDOKEF", "Geteilt"),
			new Contact("Joe", "Hansi", "22 Lance Ln", 31, "JVUNS", "Nicht Geteilt"),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 21, "GUJDSF", "Nicht Geteilt"),
			new Contact("John", "Manfred", "123 Fourth Avenue", 75, "VNSAD", "Geteilt"),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 102, "JBUNDSF", "Geteilt"),
			new Contact("John", "Manfred", "123 Fourth Avenue", 25, "JUIJSDIF", "Nicht Geteilt"),
			new Contact("Joe", "Hansi", "22 Lance Ln", 30, "JFIA", "Nicht Geteilt"),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 41, "KFSMDIF", "Nicht Geteilt"),
			new Contact("John", "Manfred", "123 Fourth Avenue", 81, "ASD", "Nicht Geteilt"),
			new Contact("Joe", "Hansi", "22 Lance Ln", 94, "LSASDF", "Nicht Geteilt"),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 14, "BFSUIDSF", "Geteilt"),
			new Contact("Joe", "Hansi", "22 Lance Ln", 4, "IDSJF", "Nicht Geteilt"),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 51, "JDSFI", "Nicht Geteilt")
			);
}

