package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

public class CellTableTeilhaberschaftAuspraegung extends CellTable <NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> {

	
	private final MultiSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> ssmAuspraegung 
		= new MultiSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>();
	private final Handler<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();
	
	
	public CellTableTeilhaberschaftAuspraegung(){
		run();
	}
	public void run(){
		this.setSelectionModel(ssmAuspraegung, selectionEventManager);
	}
	
	public class TeilhaberschaftNutzer extends Column <NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String>{
		
		public TeilhaberschaftNutzer(Cell<String> cell){
			super(cell);
		}
		@Override
		public String getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object) {
			// TODO Auto-generated method stub
			return object.getNutzer().getMail();
		}
	}
	
	public class TeilhaberschaftKontakt extends Column <NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String>{

		public TeilhaberschaftKontakt(Cell<String> cell) {
			super(cell);
			// TODO Auto-generated constructor stub
		}
		@Override
		public String getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object) {
			// TODO Auto-generated method stub
			return object.getKontakt().getName();
		}
	}
	
	public class TeilhaberschaftEigenschaft extends Column <NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String>{

		public TeilhaberschaftEigenschaft(Cell<String> cell) {
			super(cell);
			// TODO Auto-generated constructor stub
		}
		@Override
		public String getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object) {
			// TODO Auto-generated method stub
			return object.getEigenschaft().getBezeichnung();
		}
	}
	
	public class TeilhaberschaftAuspraegung extends Column <NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, String>{
		
		public TeilhaberschaftAuspraegung (Cell<String> cell){
			super(cell);
		}
		
		public String getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object){
			return object.getEigenschaftsauspraegung().getWert();
		}
	}
	
	public class CheckColumn extends Column<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper, Boolean>{

		public CheckColumn(Cell<Boolean> cell) {
			super(cell);
		}
		@Override
		public Boolean getValue(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper object) {
			return ssmAuspraegung.isSelected(object);
		}
		
	}
}
