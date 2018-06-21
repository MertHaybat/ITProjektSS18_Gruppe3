package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;

public class CellTableTeilhaberschaftKontakt extends CellTable<NutzerTeilhaberschaftKontaktWrapper>{
	
	private final MultiSelectionModel<NutzerTeilhaberschaftKontaktWrapper> ssmAuspraegung = 
			new MultiSelectionModel<NutzerTeilhaberschaftKontaktWrapper>();
	private final Handler <NutzerTeilhaberschaftKontaktWrapper> selectionEventManager =
			DefaultSelectionEventManager.createCheckboxManager();
	
	public class TeilhaberschaftNutzer extends Column<NutzerTeilhaberschaftKontaktWrapper, String>{
		
		public TeilhaberschaftNutzer (Cell<String>cell){
			super(cell);
		}
		public String getValue(NutzerTeilhaberschaftKontaktWrapper object){
			return object.getNutzer().getMail();
		}

	}

	public class TeilhaberschaftKontakt extends Column <NutzerTeilhaberschaftKontaktWrapper, String>{
		
		public TeilhaberschaftKontakt(Cell <String> cell){
			super(cell);
			
		}
		
		public String getValue(NutzerTeilhaberschaftKontaktWrapper object){
			return object.getKontakt().getName();
		}
		
	}
	
	public class CheckColumn extends Column<NutzerTeilhaberschaftKontaktWrapper, Boolean>{

		public CheckColumn(Cell<Boolean> cell) {
			super(cell);
		}
		@Override
		public Boolean getValue(NutzerTeilhaberschaftKontaktWrapper object) {
			return ssmAuspraegung.isSelected(object);
		}
		
	}
}
