package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktlisteWrapper;
import de.hdm.itprojektss18Gruppe3.client.gui.CellTableTeilhaberschaftKontakt.PreviewClickHander;

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
		this.setEmptyTableWidget(new Label("Keine Teilhaberschaften vorhanden"));
		run();
	}

	public MultiSelectionModel<NutzerTeilhaberschaftKontaktlisteWrapper> getSelectionModel() {
		return ssmAuspraegung;
	}

	private void run() {
		this.setSelectionModel(ssmAuspraegung, selectionEventManager);
		this.addCellPreviewHandler(new PreviewClickHander());
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
	public class PreviewClickHander implements Handler<NutzerTeilhaberschaftKontaktlisteWrapper> {
		@Override
		public void onCellPreview(CellPreviewEvent<NutzerTeilhaberschaftKontaktlisteWrapper> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final NutzerTeilhaberschaftKontaktlisteWrapper value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}
}
