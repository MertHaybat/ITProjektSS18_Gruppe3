package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.EigenschaftsAuspraegungHybrid;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * DialogBox zum Teilen und Betrachten der Teilhaberschaften
 * 
 * @author ersinbarut
 *
 */
public class TeilhaberschaftDialogBox extends DialogBox{

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private final MultiSelectionModel<EigenschaftsAuspraegungHybrid> ssmAuspraegung = new MultiSelectionModel<EigenschaftsAuspraegungHybrid>();
	private final CheckboxCell cbCell = new CheckboxCell(false, true);
	private FlexTable ftTeilhaberschaft = new FlexTable();

	private Button b1 = new Button("Teilen");
	private Button b2 = new Button("Abbrechen");

	private Kontakt kontaktNeu = new Kontakt();
	private KontaktCellTable kt = new KontaktCellTable(kontaktNeu);
	
	private final Handler<EigenschaftsAuspraegungHybrid> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();

	public TeilhaberschaftDialogBox(Kontakt kontakt) {
		kontakt.setId(3);
		kontaktNeu=kontakt;
		run();
	}
		public void run(){


		kt.setSelectionModel(ssmAuspraegung, selectionEventManager);

		Column<EigenschaftsAuspraegungHybrid, Boolean> cbColumn = new Column<EigenschaftsAuspraegungHybrid, Boolean>(
				cbCell) {
			@Override
			public Boolean getValue(EigenschaftsAuspraegungHybrid object) {
				return ssmAuspraegung.isSelected(object);
			}
		};

		kt.addCellPreviewHandler(new PreviewClickHandler());

		ftTeilhaberschaft.setWidget(0, 0, kt);
		ftTeilhaberschaft.setWidget(1, 0, b1);
		ftTeilhaberschaft.setWidget(1, 1, b2);

		this.add(ftTeilhaberschaft);

		b1.addClickHandler(new insertTeilhaberschaftClickHandler());
		b2.addClickHandler(new closeDialogBoxClickHandler());

		kt.insertColumn(0, cbColumn);
		
	
		kontaktmanagerVerwaltung.findEigenschaftHybrid(kontaktNeu, new EigenschaftAuspraegungCallback());
	}
	
	public class EigenschaftAuspraegungCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungHybrid>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungHybrid> result) {
			
			kt.setRowData(0, result);
			kt.setRowCount(result.size(), true);
			
		}
		
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
			// TODO wurde hard gecodet, variablen hinzufügen
			// kverwaltung.createTeilhaberschaft(0, k.getId(),
			// ssmAuspraegung.getSelectedObject().getId(), 2, 1, new
			// createTeilhaberschaftCallback());
		}
	}

	public class createTeilhaberschaftCallback implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			hide();
		}

	}
	public class PreviewClickHandler implements Handler<EigenschaftsAuspraegungHybrid>{

		@Override
		public void onCellPreview(CellPreviewEvent<EigenschaftsAuspraegungHybrid> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final EigenschaftsAuspraegungHybrid value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}
}