package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.gargoylesoftware.htmlunit.javascript.host.Range;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektss18Gruppe3.client.AllKontakteByKontaktliste;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.LeftSideFrame;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.server.KontaktmanagerAdministrationImpl;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministration;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.AbstractCellTableBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;


public class KontaktlistView extends MainFrame {

	Label menuBarHeadlineLabel = new Label("Kontaktlisten");
	HorizontalPanel contentViewContainer = new HorizontalPanel();
	HorizontalPanel kontaktlistViewPanel = new HorizontalPanel();
	VerticalPanel allKontaktViewPanel = new VerticalPanel();
	VerticalPanel menuBarContainerPanel = new VerticalPanel();
	FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	Grid kontaktViewCellTableGrid = new Grid(2,1);
	Button addKontaktlisteButton = new Button("+ Kontaktliste");
	Button deleteKontaktlisteButton = new Button("Loeschen");
	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	ArrayList<Kontakt> kontakteByKontaktlisteToDisplay = new ArrayList<Kontakt>();
	CellTable<Kontakt> kontaktCellTable = new CellTable<Kontakt>(13, CellTableResources.INSTANCE);
	



	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
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
	   
		CellList<Kontaktliste> kontaktlistenCellList = new CellList<Kontaktliste>(new KontaktlistCell(), CellListResources.INSTANCE);
	    
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
					
					kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(selected, new AsyncCallback<Vector<Kontakt>>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(Vector<Kontakt> result) {
							kontakteByKontaktlisteToDisplay.clear();
							kontakteByKontaktlisteToDisplay.addAll(result);	
							Window.alert(Integer.toString(kontakteByKontaktlisteToDisplay.size()));
							kontaktCellTable.setRowCount(kontakteByKontaktlisteToDisplay.size(), true);
							kontaktCellTable.setRowData(0, kontakteByKontaktlisteToDisplay);
							
						}	
					});
				}
			}
		});
		// Wird angezeit, wenn der CellTable keine Daten enthält (Testzweck)
		kontaktlistenCellList.setEmptyListWidget(new HTML("<b>Du hast keine Kontaktlisten</b>"));
		
		
		
		/*
		 * CellTable für die Anzeige der einzelnen Kontakte aus einer Kontaktliste
		 */
		
		kontaktCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		kontaktCellTable.setEmptyTableWidget(addKontaktlisteButton);
		
		//kontakteByKontaktlistenDataProvider.addDataDisplay(kontaktCellTable);
	    
	    SimplePager pager;
	    
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(kontaktCellTable);
	    
   

	    final SelectionModel<Kontakt> selectionModelCellTable = new MultiSelectionModel<Kontakt>();
	    kontaktCellTable.setSelectionModel(selectionModelCellTable,
	            DefaultSelectionEventManager.<Kontakt> createCheckboxManager());
	    

	      
	        
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

	    // Add a selection model to handle user selection.


	    // Set the total row count. This isn't strictly necessary, but it affects
	    // paging calculations, so its good habit to keep the row count up to date.
	    //kontaktCellTable.setRowCount(kontakteByKontaktlisteToDisplay.size(), true);

	    // Push the data into the widget.
	    //kontaktCellTable.setRowData(0, kontakteByKontaktlisteToDisplay);
	    

	    
	    
	    kontaktCellTable.setWidth("auto", true);
	    kontaktCellTable.setColumnWidth(nameColumn, 170.0, Unit.PX);
	    kontaktCellTable.setColumnWidth(vornameColumn, 170.0, Unit.PX);
	    kontaktCellTable.setColumnWidth(addressColumn, 330.0, Unit.PX);
	    
	    kontaktCellTable.setStylePrimaryName("kontaktCellTableView");

		
		kontaktlistViewPanel.add(kontaktlistenCellList);

		allKontaktViewPanel.add(kontaktCellTable);
		allKontaktViewPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		allKontaktViewPanel.add(pager);
		pager.setStylePrimaryName("gwt-SimplePager");
		kontaktlistViewPanel.add(kontaktViewCellTableGrid);
		kontaktlistViewPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		contentViewContainer.add(kontaktlistViewPanel);
		contentViewContainer.add(allKontaktViewPanel);
		addKontaktlisteButton.addClickHandler(new addKontaktlisteClickHandler());
		
		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentViewContainer);
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerPanel);
	
	}
	
	class addKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			KontaktlisteDialogBox dbox = new KontaktlisteDialogBox();
			dbox.center();
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
			 final com.google.gwt.view.client.Range range = display.getVisibleRange();
			 


			    
		    	kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(1, new AsyncCallback<Vector<Kontaktliste>>() {
			          int start = range.getStart();
			          int length = range.getLength();
		    		
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