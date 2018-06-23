package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktlisteWrapper;

/**
 * Klasse, um eine Teilhaberschaft und ihre Kontaktliste(-n) zu verwalten - innerhalb einer Tabelle
 * @author ersinbarut
 *
 */
public class CellTableTeilhaberschaftKontaktliste extends CellTable <NutzerTeilhaberschaftKontaktlisteWrapper>{
	
	private final MultiSelectionModel<NutzerTeilhaberschaftKontaktlisteWrapper> ssmAuspraegung =
			new MultiSelectionModel<NutzerTeilhaberschaftKontaktlisteWrapper>();
	private final Handler <NutzerTeilhaberschaftKontaktlisteWrapper> selectionEventManager = 
			DefaultSelectionEventManager.createCheckboxManager();

	public CellTableTeilhaberschaftKontaktliste (){
		run();
	}

	private void run() {
		// TODO Auto-generated method stub
		this.setSelectionModel(ssmAuspraegung, selectionEventManager);
	}
	
	public class TeilhaberschaftKontaktliste extends Column <NutzerTeilhaberschaftKontaktlisteWrapper, String>{
		
		public TeilhaberschaftKontaktliste(Cell<String>cell){
			super(cell);
		}
		
		public String getValue (NutzerTeilhaberschaftKontaktlisteWrapper object){
		return object.getKontaktliste().getBezeichnung();
		}
	}
	
	public class TeilhaberschaftNutzer extends Column <NutzerTeilhaberschaftKontaktlisteWrapper, String>{
		
		public TeilhaberschaftNutzer(Cell<String>cell){
			super(cell);
		}
		public String getValue(NutzerTeilhaberschaftKontaktlisteWrapper object){
			return object.getNutzer().getMail();
		}
	}
	
	public class CheckColumn extends Column<NutzerTeilhaberschaftKontaktlisteWrapper, Boolean>{

		public CheckColumn(Cell<Boolean> cell) {
			super(cell);
		}
		@Override
		public Boolean getValue(NutzerTeilhaberschaftKontaktlisteWrapper object) {
			return ssmAuspraegung.isSelected(object);
		}
		
	}
}
