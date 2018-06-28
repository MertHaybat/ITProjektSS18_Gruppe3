package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.gui.CellTableAuspraegungWrapper.PreviewClickHander;

/**
 * Klasse, um eine Teilhaberschaft und ihre Kontakte zu verwalten innerhalb einer Tabelle
 * @author ersinbarut
 *
 */
public class CellTableTeilhaberschaftKontakt extends CellTable<NutzerTeilhaberschaftKontaktWrapper>{
	
	private final MultiSelectionModel<NutzerTeilhaberschaftKontaktWrapper> ssmAuspraegung = 
			new MultiSelectionModel<NutzerTeilhaberschaftKontaktWrapper>();
	private final Handler <NutzerTeilhaberschaftKontaktWrapper> selectionEventManager =
			DefaultSelectionEventManager.createCheckboxManager();
	
	public CellTableTeilhaberschaftKontakt(){
		this.setSelectionModel(ssmAuspraegung, selectionEventManager);
		this.addCellPreviewHandler(new PreviewClickHander());
	}
	
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
	public class PreviewClickHander implements Handler<NutzerTeilhaberschaftKontaktWrapper> {
		@Override
		public void onCellPreview(CellPreviewEvent<NutzerTeilhaberschaftKontaktWrapper> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final NutzerTeilhaberschaftKontaktWrapper value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}
}
