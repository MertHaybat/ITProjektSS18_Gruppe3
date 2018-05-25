package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.EigenschaftsAuspraegungHybrid;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * DialogBox zum Teilen und Betrachten der Teilhaberschaften
 * 
 * @author ersinbarut, giuseppeggalati
 *
 */
public class TeilhaberschaftDialogBox extends DialogBox{

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	private final MultiSelectionModel<EigenschaftsAuspraegungHybrid> ssmAuspraegung = new MultiSelectionModel<EigenschaftsAuspraegungHybrid>();
	private final CheckboxCell cbCell = new CheckboxCell(false, true);
	private FlexTable ftTeilhaberschaft = new FlexTable();
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox box = new SuggestBox(oracle); 
	private List <Nutzer> nutzerListe = new ArrayList<>();
	private List <Nutzer> nutzerSuggestbox = new ArrayList<>();
	
	private CellTable<Nutzer> ausgewaehlteNutzerCt = new CellTable<Nutzer>();
	
	private Label lb1 = new Label("Wählen Sie die Eigenschaften aus, die Sie teilen möchten: ");
	private Label lb2 = new Label("Mit wem möchten Sie diese Eigenschaften teilen: ");
	private Button b1 = new Button("Teilen");
	private Button b2 = new Button("Abbrechen");
	private Button nutzerHinzufuegenBt = new Button("+");
    private ButtonCell buttonCell1 = new ButtonCell();
	

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
			
			
			box.setStylePrimaryName("gwt-SuggestBox");
			
			kontaktmanagerVerwaltung.findAllNutzer(new getAllNutzerCallback());
			
		kt.setSelectionModel(ssmAuspraegung, selectionEventManager);

		Column<EigenschaftsAuspraegungHybrid, Boolean> cbColumn = new Column<EigenschaftsAuspraegungHybrid, Boolean>(
				cbCell) {
			@Override
			public Boolean getValue(EigenschaftsAuspraegungHybrid object) {
				return ssmAuspraegung.isSelected(object);
			}
		};
		Column<Nutzer, String> nutzertxtColumn = new Column<Nutzer, String>(new TextCell()) {

			@Override
			public String getValue(Nutzer object) {
				// TODO Auto-generated method stub
				return object.getMail();
			}
		};
		Column<Nutzer, String> buttonColumn1 = new Column<Nutzer, String>(new ButtonCell()) {
	        @Override
	        public String getValue(Nutzer x) {
	            return "x";
	        }
	    };
	    
	    /**
	     * Field Updater noch einfügen 
	     */
	    
	  
		kt.addCellPreviewHandler(new PreviewClickHander());

		ftTeilhaberschaft.setWidget(0, 0, lb1);
		ftTeilhaberschaft.setWidget(1, 0, kt);
		ftTeilhaberschaft.setWidget(2, 0, lb2);
		ftTeilhaberschaft.setWidget(3, 0, box);
		ftTeilhaberschaft.setWidget(4, 1, nutzerHinzufuegenBt);
		ftTeilhaberschaft.setWidget(4, 0, ausgewaehlteNutzerCt);
		ftTeilhaberschaft.setWidget(5, 1, b1);
		ftTeilhaberschaft.setWidget(5, 2, b2);

		this.add(ftTeilhaberschaft);

		b1.addClickHandler(new insertTeilhaberschaftClickHandler());
		b2.addClickHandler(new closeDialogBoxClickHandler());
		nutzerHinzufuegenBt.addClickHandler(new NutzerHinzufuegenClickHandler());
		kt.insertColumn(0, cbColumn);
		
		ausgewaehlteNutzerCt.setRowCount(nutzerSuggestbox.size(), true);
		ausgewaehlteNutzerCt.setRowData(0, nutzerSuggestbox);
		ausgewaehlteNutzerCt.addColumn(nutzertxtColumn, "");
		ausgewaehlteNutzerCt.addColumn(buttonColumn1, "");
		
		kontaktmanagerVerwaltung.findEigenschaftHybrid(kontaktNeu, new EigenschaftAuspraegungCallback());
	}
	public class NutzerHinzufuegenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setMail(box.getValue());;
			nutzerSuggestbox.add(nutzer);
		
			ausgewaehlteNutzerCt.setRowCount(nutzerSuggestbox.size(), true);
			ausgewaehlteNutzerCt.setRowData(0, nutzerSuggestbox);
			ausgewaehlteNutzerCt.redraw();
		}
		
	}
	public class PreviewClickHander implements Handler<EigenschaftsAuspraegungHybrid>{
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

	class createTeilhaberschaftCallback implements AsyncCallback<Teilhaberschaft> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Teilhaberschaft result) {
			hide();
		}

	}
	class getAllNutzerCallback implements AsyncCallback<Vector<Nutzer>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			for (Nutzer nutzer : result) {
				nutzerListe.add(nutzer);
				
			}
			for (Nutzer nutzer : nutzerListe) {
				oracle.add(nutzer.getMail());
				
			}
		}
		
	}

}