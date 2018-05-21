package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * DialogBox zum Teilen und Betrachten der Teilhaberschaften
 * @author ersinbarut
 *
 */
public class TeilhaberschaftDialogBox extends DialogBox{

	private CellTable<Eigenschaftsauspraegung> ctAuspraegung = new CellTable<Eigenschaftsauspraegung>();
	
	private final SingleSelectionModel<Eigenschaftsauspraegung> ssmAuspraegung = new SingleSelectionModel<Eigenschaftsauspraegung>();
	
	private FlexTable ftTeilhaberschaft = new FlexTable();
	
	private Button b1 = new Button ("teilen");
	private Button b2 = new Button ("abbrechen");
	
	private List<String> auspraegungStringList = new ArrayList<>();
	
	private static KontaktmanagerAdministrationAsync kverwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private Kontakt k = new Kontakt();
	
	public TeilhaberschaftDialogBox(final Kontakt kontakt){
		/**
		 * 
		 * Die Anordnung der Textboxen festlegen und dazu deren Bezeichnungen.
		 */
		k=kontakt;
		
		
		ctAuspraegung.setSelectionModel(ssmAuspraegung);
	
		ftTeilhaberschaft.setWidget(0, 0, ctAuspraegung);
		ftTeilhaberschaft.setWidget(1, 0, b1);
		ftTeilhaberschaft.setWidget(1, 1, b2);
		
		
		this.add(ftTeilhaberschaft);
		
		
		Column<Eigenschaftsauspraegung, String> wertAuspraegung = new Column<Eigenschaftsauspraegung, String>(new ClickableTextCell()){

			@Override
			public String getValue(Eigenschaftsauspraegung object) {
				return object.getWert();
			}			
		};
		
//		Column<String, String> wertEigenschaft = new Column<String, String>(new ClickableTextCell()){
//
//			@Override
//			public String getValue(String object) {
//				return object;
//			}			
//		};
		kverwaltung.findAllEigenschaftsauspraegungByPersonID(kontakt, new AllAuspraegungenCallback());
		
		
//		ctAuspraegung.addColumn(wertEigenschaft, "Eigenschaft");
		ctAuspraegung.addColumn(wertAuspraegung, "Eigenschaftsausprägung");
		
		b1.addClickHandler(new insertTeilhaberschaftClickHandler());
		b2.addClickHandler(new closeDialogBoxClickHandler());
		
		
	}
	
	public class closeDialogBoxClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}
	}
	public class insertTeilhaberschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			//TODO wurde hard gecodet, variablen hinzufügen
			kverwaltung.createTeilhaberschaft(0, k.getId(), ssmAuspraegung.getSelectedObject().getId(), 2, 1, new createTeilhaberschaftCallback());
		}
	}

	class AllAuspraegungenCallback implements AsyncCallback<Vector<Eigenschaftsauspraegung>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Eigenschaftsauspraegung> result) {
			// TODO
			
			ctAuspraegung.setRowData(0, result);
			ctAuspraegung.setRowCount(result.size(), true);
			
			
//			for (Eigenschaftsauspraegung eigenschaftsauspraegung : result) {
//				auspraegungStringList.add(eigenschaftsauspraegung.getWert());
//			}
//			ctAuspraegung.setRowData(0, auspraegungStringList);
//			ctAuspraegung.setRowCount(auspraegungStringList.size(), true);
		}
		
//		class AllEigenschaftenCallback implements AsyncCallback<Eigenschaft> {
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onSuccess(Eigenschaft result) {
//				
//				
//			}
//
//			
//
//	}
	}
	class createTeilhaberschaftCallback implements AsyncCallback<Teilhaberschaft>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			hide();
		}
		
	}
}
