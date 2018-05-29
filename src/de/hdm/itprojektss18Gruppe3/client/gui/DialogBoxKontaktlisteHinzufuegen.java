package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.EigenschaftsAuspraegungHybrid;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class DialogBoxKontaktlisteHinzufuegen extends DialogBox{
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private Label abfrage= new Label("In welche Kontaktlisten möchten Sie diese Kontakte hinzufügen?");
	private Button speichern = new Button("Speichern");
	private Button abbrechen = new Button("Abbrechen");
	private ArrayList<Kontakt> kontakte = new ArrayList<>();
	private CellTable<Kontaktliste> kontaktliste = new CellTable<Kontaktliste>();
	private MultiSelectionModel<Kontaktliste> selectionModel = new MultiSelectionModel<Kontaktliste>();
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	private final Handler<Kontaktliste> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();
	
	public DialogBoxKontaktlisteHinzufuegen(ArrayList<Kontakt> k){
		kontakte=k;
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("mail"));
		kontaktliste.setSelectionModel(selectionModel, selectionEventManager);
		speichern.addClickHandler(new AddClickHandler());
		abbrechen.addClickHandler(new AbortClickHandler());
		kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(nutzer.getId(), new AllKontaktlisteByNutzerCallback());
		Column<Kontaktliste, String> kontaktnameColumn = new Column<Kontaktliste, String>(new TextCell()) {
			@Override
			public String getValue(Kontaktliste object) {
				return object.getBezeichnung();
			}
		};
		
		kontaktliste.addCellPreviewHandler(new PreviewClickHander());
		kontaktliste.addColumn(kontaktnameColumn, "Kontaktliste:");
		vPanel.add(abfrage);
		vPanel.add(kontaktliste);
		hPanel.add(speichern);
		hPanel.add(abbrechen);
		vPanel.add(hPanel);
		this.add(vPanel);
	}
	public class AllKontaktlisteByNutzerCallback implements AsyncCallback<Vector<Kontaktliste>>{

		@Override
		public void onFailure(Throwable caught) {
		Window.alert("Fehler beim Laden der Kontaktlisten" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Kontaktliste> result) {
			kontaktliste.setRowCount(result.size(), true);
			kontaktliste.setRowData(0, result);
		}
		
	}
	public class AddClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			for (Kontakt kontakt : kontakte) {
				for (Kontaktliste kontaktliste : selectionModel.getSelectedSet()) {
					kontaktmanagerVerwaltung.createKontaktKontaktliste(kontakt.getId(), kontaktliste.getId(), new CreateKontaktKontaktlisteCallback());
					
				}
			}
		}
		
	}
	public class AbortClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			hide();
		}
		
	}
	public class CreateKontaktKontaktlisteCallback implements AsyncCallback<KontaktKontaktliste>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Einfügen in die Kontaktliste: " + caught.getMessage());
			hide();
		}

		@Override
		public void onSuccess(KontaktKontaktliste result) {
			if(result != null){
				Window.alert("Kontakt erfolgreich in die Kontaktliste hinzugefügt.");	
			} else {
				Window.alert("Kontakt ist bereits in dieser Kontaktliste");
			}
			hide();
		}
		
	}
	public class PreviewClickHander implements Handler<Kontaktliste> {
		@Override
		public void onCellPreview(CellPreviewEvent<Kontaktliste> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final Kontaktliste value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}
	
	
}
