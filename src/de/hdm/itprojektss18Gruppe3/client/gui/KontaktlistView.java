package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.LeftSideFrame;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;


public class KontaktlistView extends MainFrame {

	Label menuBarHeadlineLabel = new Label("Kontaktlisten");
	VerticalPanel contentViewContainer = new VerticalPanel();
	HorizontalPanel kontaktlistViewPanel = new HorizontalPanel();
	VerticalPanel allKontaktViewPanel = new VerticalPanel();
	VerticalPanel menuBarContainerPanel = new VerticalPanel();
	FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	Grid kontaktViewCellTableGrid = new Grid(2,1);
	Button addKontaktlisteButton = new Button("+ Kontaktliste");
	Button deleteKontaktlisteButton = new Button("Loeschen");
	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();

	private static final List<String> KONTAKTLISTEN = Arrays.asList("Familie", "Freunde",
			"Saufkollegen", "Arbeitskollegen", "Bekannte", "Deppen", "Unbekannte", "HdM Crew", "Dealer", "Hotlines", "Nervensägen", "Pizzeria", "Arzt");


	public void run () {

		/*
		 * Menüleiste mit den Buttons für die Anlage von einer neuen Kontaktliste und dem Löschen einer Kontaktliste erzeugen
		 * und dem Panel zuweisen
		 */
		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		//menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");

		addKontaktlisteButton.setStylePrimaryName("mainButton");
		deleteKontaktlisteButton.setStylePrimaryName("mainButton");
		menuBarContainerFlowPanel.add(addKontaktlisteButton);
		menuBarContainerFlowPanel.add(deleteKontaktlisteButton);
		menuBarContainerFlowPanel.setWidth("200%");

		
		oracle.add("Test 1");
		oracle.add("Test 2");
		oracle.add("Test 3");
		   
		SuggestBox box = new SuggestBox(oracle);
	    
	    box.setStylePrimaryName("gwt-SuggestBox");
	    menuBarContainerFlowPanel.add(box);
	    menuBarContainerPanel.setStylePrimaryName("menuBarLabelContainer");
	    menuBarContainerPanel.add(menuBarContainerFlowPanel);

		
		/*
		 * CellList für die Anzeige der Kontaktlisten eines Users wird erzeugt
		 */
		// Create a cell to render each value.
		TextCell textCell = new TextCell();

		// Create a CellList that uses the cell.
		CellList<String> cellList = new CellList<String>(textCell, CellListResources.INSTANCE);
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);



		// Add a selection model to handle user selection.
		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				String selected = selectionModel.getSelectedObject();
				if (selected != null) {
					// Window.alert("You selected: " + selected);
				}
			}
		});

		// Set the total row count. This isn't strictly necessary, but it affects
		// paging calculations, so its good habit to keep the row count up to date.
		cellList.setRowCount(KONTAKTLISTEN.size(), true);

		// Push the data into the widget.
		cellList.setRowData(0, KONTAKTLISTEN);
		
		

		/*
		 * CellTable für die Anzeige der einzelnen Kontakte aus einer Kontaktliste
		 */
		
		CellTable<Contact> table = new CellTable<Contact>(13, CellTableResources.INSTANCE);
	    table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    
	    SimplePager pager;
	    
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(table);
	    
	    /*
	     * CheckBoxen für die Auswahl mehrerer Kontakte anlegen. Hiermit können mehrere Kontakte gleichzeitig
	     * z.B. aus einer Kontaktliste entfernt werden.
	     * 
	     * ERST WENN DB ANBINDUNG STEHT
	     */
	    final SelectionModel<Contact> selectionModelAllKontakteView = new MultiSelectionModel<Contact>();
	        table.setSelectionModel(selectionModelAllKontakteView,
	            DefaultSelectionEventManager.<Contact> createCheckboxManager());

	        
	        Column<Contact, Boolean> checkColumn = new Column<Contact, Boolean>(
	                new CheckboxCell(true, false)) {
	              @Override
	              public Boolean getValue(Contact object) {
	                // Get the value from the selection model.
	                //return selectionModel.isSelected(object);
	            	  return null;
	              }
	            };
	            table.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
	            table.setColumnWidth(checkColumn, 40, Unit.PX);    
	        
	        
	        
	        
	    // Add a text column to show the name.
	    TextColumn<Contact> nameColumn = new TextColumn<Contact>() {
	      @Override
	      public String getValue(Contact object) {
	        return object.name;
	      }
	    };
	    table.addColumn(nameColumn, "Name");

	    // Add a Vorname column to show the birthday.
	    TextColumn<Contact> vornameColumn = new TextColumn<Contact>() {
		      @Override
		      public String getValue(Contact object) {
		        return object.vorname;
		      }
		    };
		    table.addColumn(vornameColumn, "Vorname");	    
		    
	    // Add a text column to show the address.
	    TextColumn<Contact> addressColumn = new TextColumn<Contact>() {
	      @Override
	      public String getValue(Contact object) {
	        return object.address;
	      }
	    };
	    table.addColumn(addressColumn, "Address");

	    // Add a selection model to handle user selection.
	    final SingleSelectionModel<Contact> selectionModelCellTable = new SingleSelectionModel<Contact>();
	    table.setSelectionModel(selectionModelCellTable);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	        Contact selected = selectionModelCellTable.getSelectedObject();
	        if (selected != null) {
	          Window.alert("You selected: " + selected.name);
	        }
	      }
	    });

	    // Set the total row count. This isn't strictly necessary, but it affects
	    // paging calculations, so its good habit to keep the row count up to date.
	    table.setRowCount(CONTACTS.size(), true);

	    // Push the data into the widget.
	    table.setRowData(0, CONTACTS);
	    
	    table.setWidth("auto", true);
	    table.setColumnWidth(nameColumn, 170.0, Unit.PX);
	    table.setColumnWidth(vornameColumn, 170.0, Unit.PX);
	    table.setColumnWidth(addressColumn, 330.0, Unit.PX);
	    
	    table.setStylePrimaryName("kontaktCellTableView");

		
		kontaktlistViewPanel.add(cellList);
		pager.setStylePrimaryName("gwt-SimplePager");
		kontaktViewCellTableGrid.setWidget(0, 0, table);
		kontaktViewCellTableGrid.setWidget(1, 0, pager);

	//	kontaktlistViewPanel.add(table);
		//kontaktlistViewPanel.add(pager);
		kontaktlistViewPanel.add(kontaktViewCellTableGrid);
		kontaktlistViewPanel.setStylePrimaryName("cellListWidgetContainerPanel");

		RootPanel.get("content").add(kontaktlistViewPanel);
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerPanel);
	}
	
	
	
	//TO BE DELETED WENN DIE DB ANBINDUNG STEHT MIT ABFRAGEN
	
		private static class Contact {
		    private final String address;
		    private final String vorname;
		    private final String name;

		    public Contact(String name, String vorname, String address) {
		      this.name = name;
		      this.vorname = vorname;
		      this.address = address;
		    }
		  }

		  /**
		   * The list of data to display.
		   */
		  private static final List<Contact> CONTACTS = Arrays.asList(
		      new Contact("John", "Manfred", "123 Fourth Avenue"),
		      new Contact("Joe", "Hansi", "22 Lance Ln"),
		      new Contact("George", "Werner", "1600 Pennsylvania Avenue"),
		      new Contact("John", "Manfred", "123 Fourth Avenue"),
		      new Contact("Joe", "Hansi", "22 Lance Ln"),
		      new Contact("George", "Werner", "1600 Pennsylvania Avenue"),
		      new Contact("John", "Manfred", "123 Fourth Avenue"),
		      new Contact("Joe", "Hansi", "22 Lance Ln"),
		      new Contact("George", "Werner", "1600 Pennsylvania Avenue"),
		      new Contact("John", "Manfred", "123 Fourth Avenue"),
		      new Contact("George", "Werner", "1600 Pennsylvania Avenue"),
		      new Contact("John", "Manfred", "123 Fourth Avenue"),
		      new Contact("Joe", "Hansi", "22 Lance Ln"),
		      new Contact("George", "Werner", "1600 Pennsylvania Avenue"),
		      new Contact("John", "Manfred", "123 Fourth Avenue"),
		      new Contact("Joe", "Hansi", "22 Lance Ln"),
		      new Contact("George", "Werner", "1600 Pennsylvania Avenue"),
		      new Contact("Joe", "Hansi", "22 Lance Ln"),
		      new Contact("George", "Werner", "1600 Pennsylvania Avenue")
				  );
		  }
