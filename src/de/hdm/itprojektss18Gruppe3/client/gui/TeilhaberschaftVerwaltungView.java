package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktlisteWrapper;
import de.hdm.itprojektss18Gruppe3.client.gui.CellTableTeilhaberschaftAuspraegung.TeilhaberschaftKontakt;
import de.hdm.itprojektss18Gruppe3.client.gui.CellTableTeilhaberschaftKontaktliste.TeilhaberschaftKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/**
 * Klasse, um Teilhaberschaften (Eigenschaften, Ausprägungen, Kontakte und Kontaktlisten) zu verwalten 
 * @author ersinbarut
 *
 */
public class TeilhaberschaftVerwaltungView extends MainFrame {

	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel allKontakteCellTableContainer = new HorizontalPanel();

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	private HTML headline = new HTML("Teilhaberschaften verwalten");
	private HTML nutzerVerwalten = new HTML("Nutzer verwalten");
	private HTML auspraegungVerwalten = new HTML("Geteilte Eigenschaftsausprägungen:");
	private HTML kontakteVerwalten = new HTML("Geteilte Kontakte:");
	private HTML kontaktlisteVerwalten = new HTML("Geteilte Kontaktlisten:");
	private Anchor nutzerAnchor = new Anchor("Nutzer löschen");
	private Label emptyLabel;

	private static MultiSelectionModel<NutzerTeilhaberschaftKontaktWrapper> selectionKontaktAuspraegung;
	private static MultiSelectionModel<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> selectionEigenschaftAuspraegung; 
	private static MultiSelectionModel<NutzerTeilhaberschaftKontaktlisteWrapper> selectionKontaktlisteAuspraegung;

	private CellTableTeilhaberschaftAuspraegung teilhaberschaftAuspraegung = new CellTableTeilhaberschaftAuspraegung();
	private CellTableTeilhaberschaftKontakt	teilhaberschaftKontakt = new CellTableTeilhaberschaftKontakt();
	private CellTableTeilhaberschaftKontaktliste teilhaberschaftKontaktliste = new CellTableTeilhaberschaftKontaktliste();

	private ClickableTextCell clickCell = new ClickableTextCell();
	private CheckboxCell checkBoxCell = new CheckboxCell(true, false);

	private CellTableTeilhaberschaftKontakt.TeilhaberschaftKontakt teilhaberschaftKontaktColumn = 
			teilhaberschaftKontakt.new TeilhaberschaftKontakt(clickCell);
	private CellTableTeilhaberschaftKontakt.TeilhaberschaftNutzer teilhaberschaftNutzerColumn = 
			teilhaberschaftKontakt.new TeilhaberschaftNutzer(clickCell);
	private CellTableTeilhaberschaftKontakt.CheckColumn teilhaberschaftKontaktCheckColumn =
			teilhaberschaftKontakt.new CheckColumn(checkBoxCell);

	private CellTableTeilhaberschaftKontaktliste.TeilhaberschaftKontaktliste teilhaberschaftKontaktlisteColumn = 
			teilhaberschaftKontaktliste.new TeilhaberschaftKontaktliste(clickCell);
	private CellTableTeilhaberschaftKontaktliste.TeilhaberschaftNutzer teilhaberschaftKontaktlisteNutzerColumn = 
			teilhaberschaftKontaktliste.new TeilhaberschaftNutzer(clickCell);
	private CellTableTeilhaberschaftKontaktliste.CheckColumn teilhaberschaftKontaktlisteCheckColumn =
			teilhaberschaftKontaktliste.new CheckColumn(checkBoxCell);

	private CellTableTeilhaberschaftAuspraegung.TeilhaberschaftAuspraegung teilhaberschaftAuspraegungColumn = 
			teilhaberschaftAuspraegung.new TeilhaberschaftAuspraegung(clickCell);
	private CellTableTeilhaberschaftAuspraegung.TeilhaberschaftEigenschaft teilhaberschaftEigenschaftColumn = 
			teilhaberschaftAuspraegung.new TeilhaberschaftEigenschaft(clickCell);
	private CellTableTeilhaberschaftAuspraegung.TeilhaberschaftKontakt teilhaberschaftAuspraegungKontaktColumn = 
			teilhaberschaftAuspraegung.new TeilhaberschaftKontakt(clickCell);
	private CellTableTeilhaberschaftAuspraegung.TeilhaberschaftNutzer teilhaberschaftAuspraegungNutzer = 
			teilhaberschaftAuspraegung.new TeilhaberschaftNutzer (clickCell);
	private CellTableTeilhaberschaftAuspraegung.CheckColumn teilhaberschaftAuspraegungCheckColumn = 
			teilhaberschaftAuspraegung.new CheckColumn (checkBoxCell);

	public TeilhaberschaftVerwaltungView() {
		selectionKontaktAuspraegung = teilhaberschaftKontakt.getSelectionModel();
		selectionEigenschaftAuspraegung = teilhaberschaftAuspraegung.getSelectionModel();
		selectionKontaktlisteAuspraegung = teilhaberschaftKontaktliste.getSelectionModel();
		super.onLoad();
	}

	@Override
	protected void run() {

		Menubar mb = new Menubar(selectionKontaktAuspraegung);

		auspraegungVerwalten.setStylePrimaryName("teilhaberschaftLabel");
		kontakteVerwalten.setStylePrimaryName("teilhaberschaftLabel");
		kontaktlisteVerwalten.setStylePrimaryName("teilhaberschaftLabel");


		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungCheckColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungCheckColumn, 5, Unit.PCT);		
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungKontaktColumn, "Nickname");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungKontaktColumn, 23.75, Unit.PCT);		
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftEigenschaftColumn, "Eigenschaft");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftEigenschaftColumn, 23.75, Unit.PCT);		
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungColumn, "Wert");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungColumn, 23.75, Unit.PCT);	
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungNutzer, "Teilhabender");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungNutzer, 23.75, Unit.PCT);


		teilhaberschaftKontaktliste.addColumn(teilhaberschaftKontaktlisteCheckColumn, SafeHtmlUtils.fromSafeConstant("<br/"));
		teilhaberschaftKontaktliste.setColumnWidth(teilhaberschaftKontaktlisteCheckColumn, 5, Unit.PCT);
		teilhaberschaftKontaktliste.addColumn(teilhaberschaftKontaktlisteColumn, "Nickname");
		teilhaberschaftKontaktliste.setColumnWidth(teilhaberschaftKontaktlisteNutzerColumn, 55, Unit.PCT);
		teilhaberschaftKontaktliste.addColumn(teilhaberschaftKontaktlisteNutzerColumn, "Teilhaber");
		teilhaberschaftKontaktliste.setColumnWidth(teilhaberschaftKontaktlisteNutzerColumn, 40, Unit.PCT);


		teilhaberschaftKontakt.addColumn(teilhaberschaftKontaktCheckColumn, SafeHtmlUtils.fromSafeConstant("<br/"));
		teilhaberschaftKontakt.setColumnWidth(teilhaberschaftKontaktCheckColumn, 5, Unit.PCT);
		teilhaberschaftKontakt.addColumn(teilhaberschaftKontaktColumn, "Nickname");
		teilhaberschaftKontakt.setColumnWidth(teilhaberschaftKontaktColumn, 55, Unit.PCT);
		teilhaberschaftKontakt.addColumn(teilhaberschaftNutzerColumn, "Teilhabender");
		teilhaberschaftKontakt.setColumnWidth(teilhaberschaftNutzerColumn, 40, Unit.PCT);


		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));

		kontaktmanagerVerwaltung.findKontaktlisteTeilhaberschaftEigentuemer(nutzer.getId(), new KontaktlisteCallback());
		kontaktmanagerVerwaltung.findKontaktTeilhaberschaftEigentuemer(nutzer.getId(), new KontaktCallback());
		kontaktmanagerVerwaltung.findAuspraegungTeilhaberschaftEigentuemer(nutzer.getId(), new AuspraegungCallback());

		nutzerAnchor.addClickHandler(new DeleteNutzerClickHandler());

		headline.setStylePrimaryName("h3");
		nutzerVerwalten.setStylePrimaryName("h3");

		vPanel.add(headline);
		vPanel.add(new HTML("<br><br>"));
		vPanel.add(auspraegungVerwalten);
		vPanel.add(teilhaberschaftAuspraegung);
		vPanel.add(new HTML("<br><br><br>"));
		vPanel.add(kontakteVerwalten);
		vPanel.add(teilhaberschaftKontakt);
		vPanel.add(new HTML("<br><br><br>"));
		vPanel.add(kontaktlisteVerwalten);
		vPanel.add(teilhaberschaftKontaktliste);
		vPanel.add(new HTML("<br><br><br>"));
		vPanel.add(nutzerVerwalten);
		vPanel.add(nutzerAnchor);
		vPanel.setStylePrimaryName("teilhaberschaftVerwaltungPanel");

		RootPanel.get("content").add(vPanel);
	}

	public class TeilhaberschaftDeleteCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			TeilhaberschaftVerwaltungView teilhaberschaftVerwaltung = new TeilhaberschaftVerwaltungView();
		}

	}

	public static class DeleteTeilhaberschaft implements Command {

		@Override
		public void execute() {
			if(selectionKontaktAuspraegung.getSelectedSet().size() > 0) {
				for(NutzerTeilhaberschaftKontaktWrapper t : selectionKontaktAuspraegung.getSelectedSet()) {
					kontaktmanagerVerwaltung.deleteTeilhaberschaftByID(t.getTeilhaberschaft(), new DeleteTeilhaberschaftCallback());
				}
			}
			if(selectionEigenschaftAuspraegung.getSelectedSet().size() > 0) {
				for(NutzerTeilhaberschaftEigenschaftAuspraegungWrapper t : selectionEigenschaftAuspraegung.getSelectedSet()) {
					kontaktmanagerVerwaltung.deleteTeilhaberschaftByEigenschaftsauspraegungID(t.getTeilhaberschaft(), new DeleteTeilhaberschaftCallback());
				}
			}
			if(selectionKontaktlisteAuspraegung.getSelectedSet().size() > 0) {
				for(NutzerTeilhaberschaftKontaktlisteWrapper t : selectionKontaktlisteAuspraegung.getSelectedSet()) {
					kontaktmanagerVerwaltung.deleteTeilhaberschaftByKontaktlisteID(t.getTeilhaberschaft(), new DeleteTeilhaberschaftCallback());
				}
			}

			if((selectionKontaktAuspraegung.getSelectedSet().size() == 0 && selectionEigenschaftAuspraegung.getSelectedSet().size() == 0 && selectionKontaktlisteAuspraegung.getSelectedSet().size() == 0)) {
				Window.alert("Kein Löschen möglich!");
			}
		}
	}

	public static class DeleteTeilhaberschaftCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Löschen der Teilhaberschaft " + caught.getMessage());

		}

		@Override
		public void onSuccess(Void result) {
			TeilhaberschaftVerwaltungView tvv = new TeilhaberschaftVerwaltungView();
		}
	}

	public class DeleteNutzerClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			boolean deleteNutzer = Window.confirm("Möchten Sie den Nutzer löschen?");
			if(deleteNutzer == true){
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				nutzer.setMail(Cookies.getCookie("email"));
				kontaktmanagerVerwaltung.deleteNutzer(nutzer, new DeleteNutzerCallback());	
			} 
		}

	}
	public class DeleteNutzerCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Löschen des Nutzers: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			Anchor signOutLink = new Anchor();
			Window.alert("Nutzer wurde gelöscht");
			signOutLink.setHref(Cookies.getCookie("signout"));
			Window.open(signOutLink.getHref(), "_self", "");
		}

	}

	public class AuspraegungCallback implements AsyncCallback<Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper> result) {
			teilhaberschaftAuspraegung.setRowCount(result.size(), true);
			teilhaberschaftAuspraegung.setRowData(0, result);
		}

	}
	public class KontaktCallback implements AsyncCallback<Vector<NutzerTeilhaberschaftKontaktWrapper>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<NutzerTeilhaberschaftKontaktWrapper> result) {
			teilhaberschaftKontakt.setRowData(0, result);
			teilhaberschaftKontakt.setRowCount(result.size(), true);
		}

	}
	public class KontaktlisteCallback implements AsyncCallback<Vector<NutzerTeilhaberschaftKontaktlisteWrapper>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<NutzerTeilhaberschaftKontaktlisteWrapper> result) {
			teilhaberschaftKontaktliste.setRowData(0, result);
			teilhaberschaftKontaktliste.setRowCount(result.size(), true);
		}

	}


}
