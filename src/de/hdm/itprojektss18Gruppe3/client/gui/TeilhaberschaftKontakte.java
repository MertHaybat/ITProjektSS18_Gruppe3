package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;


/**
 * 
 * @author ersinbarut
 *
 */
public class TeilhaberschaftKontakte extends TeilhaberschaftenAlle {

	VerticalPanel vPanel = new VerticalPanel();
	Label menuBarHeadlineLabel = new Label("Kontakte aus Teilhaberschaften");
	FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	VerticalPanel menuBarContainerPanel = new VerticalPanel();
	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();

	@Override
	protected void run() {

		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);

		oracle.add("Test 1");

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
		allKontakteCellTable.setEmptyTableWidget(new Label("Du hast bisher keine geteilten Kontakte"));

		// Create a Pager to control the table.
		SimplePager pager;
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(allKontakteCellTable);

		// Add a selection model so we can select cells.
		final SelectionModel<Contact> selectionModel = new MultiSelectionModel<Contact>();
		allKontakteCellTable.setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<Contact>createCheckboxManager());

		/**
		 * Add the columns to the table.
		 */
		// private void initTableColumns(final SelectionModel<Contact>
		// selectionModel) {
		// Checkbox column. This table will uses a checkbox column for
		// selection.
		// Alternatively, you can call dataGrid.setSelectionEnabled(true) to
		// enable
		// mouse selection.
		Column<Contact, Boolean> checkColumn = new Column<Contact, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Contact object) {
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};
		allKontakteCellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		allKontakteCellTable.setColumnWidth(checkColumn, 40, Unit.PX);

		// First name.
		Column<Contact, String> firstNameColumn = new Column<Contact, String>(new TextCell()) {
			@Override
			public String getValue(Contact object) {
				return object.vorname;
			}
		};

		allKontakteCellTable.addColumn(firstNameColumn, "Vorname");
		allKontakteCellTable.setColumnWidth(firstNameColumn, 80, Unit.PX);

		// Last name.
		Column<Contact, String> lastNameColumn = new Column<Contact, String>(new TextCell()) {
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
//		Column<Contact, String> kontaktlisteColumn = new Column<Contact, String>(new TextCell()) {
//			@Override
//			public String getValue(Contact object) {
//				return object.kontaktliste;
//			}
//		};
//
//		allKontakteCellTable.addColumn(kontaktlisteColumn, "Kontaktliste");
//		allKontakteCellTable.setColumnWidth(kontaktlisteColumn, 80, Unit.PX);

		// Address.
		Column<Contact, String> addressColumn = new Column<Contact, String>(new TextCell()) {
			@Override
			public String getValue(Contact object) {
				return object.address;
			}
		};

		allKontakteCellTable.addColumn(addressColumn, "Adresse");
		allKontakteCellTable.setColumnWidth(addressColumn, 150, Unit.PX);

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

	private static class Contact {
		private final String address;
		private final String vorname;
		private final String name;
//		private final String kontaktliste;
		private final int age;
		// private final String status;

		public Contact(String name, String vorname, String address, int age) {
			this.name = name;
			this.vorname = vorname;
			this.address = address;
			this.age = age;
//			this.kontaktliste = kontaktliste;
			// this.status = status;
		}
	}

	/**
	 * The list of data to display.
	 */
	private static final List<Contact> CONTACTS = Arrays.asList(
			new Contact("Thomas", "Burkhardt", "123 Fourth Avenue", 24),
			new Contact("Joe", "Hansi", "22 Lance Ln", 11),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 85),
			new Contact("John", "Manfred", "123 Fourth Avenue", 22),
			new Contact("Joe", "Hansi", "22 Lance Ln", 9),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 12),
			new Contact("John", "Manfred", "123 Fourth Avenue", 75),
			new Contact("Joe", "Hansi", "22 Lance Ln", 31),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 21),
			new Contact("John", "Manfred", "123 Fourth Avenue", 75),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 102),
			new Contact("John", "Manfred", "123 Fourth Avenue", 25),
			new Contact("Joe", "Hansi", "22 Lance Ln", 30),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 41),
			new Contact("John", "Manfred", "123 Fourth Avenue", 81),
			new Contact("Joe", "Hansi", "22 Lance Ln", 94),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 14),
			new Contact("Joe", "Hansi", "22 Lance Ln", 4),
			new Contact("George", "Werner", "1600 Pennsylvania Avenue", 51));
}
