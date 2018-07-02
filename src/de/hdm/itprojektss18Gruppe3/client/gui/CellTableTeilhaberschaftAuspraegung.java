package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.gui.CellTableTeilhaberschaftKontakt.PreviewClickHander;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

/**
 * Klasse, um eine Teilhaberschaft und ihre Eigenschaften und Ausprägungen zu verwalten innerhalb einer Tabelle
 * @author ersinbarut
 *
 */
public class CellTableTeilhaberschaftAuspraegung extends CellTable <NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> {

	
	private final MultiSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> ssmAuspraegung 
		= new MultiSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>();
	private final Handler<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();

	public CellTableTeilhaberschaftAuspraegung(){
		run();
	}
	
	public MultiSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> getSelectionModel() {
		return ssmAuspraegung;
	}
	
	public void run(){
		this.setEmptyTableWidget(new Label("Keine Teilhaberschaften vorhanden"));
		this.setSelectionModel(ssmAuspraegung, selectionEventManager);
		this.addCellPreviewHandler(new PreviewClickHander());
		
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
	public class PreviewClickHander implements Handler<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> {
		@Override
		public void onCellPreview(CellPreviewEvent<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final NutzerTeilhaberschaftEigenschaftAuspraegungWrapper value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}
}
