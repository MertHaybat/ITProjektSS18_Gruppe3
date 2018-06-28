package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftEigenschaftAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktlisteWrapper;
import de.hdm.itprojektss18Gruppe3.client.gui.CellTableTeilhaberschaftAuspraegung.TeilhaberschaftKontakt;
import de.hdm.itprojektss18Gruppe3.client.gui.CellTableTeilhaberschaftKontaktliste.TeilhaberschaftKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

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
	private HTML auspraegungVerwalten = new HTML("Geteilte Eigenschaftsausprägungen");
	private HTML kontakteVerwalten = new HTML("Geteilte Kontakte");
	private HTML kontaktlisteVerwalten = new HTML("Geteilte Kontaktlisten");
	private Anchor nutzerAnchor = new Anchor("Nutzer löschen");
	
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
		super.onLoad();
	}
	
	@Override
	protected void run() {
		
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungCheckColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungCheckColumn, 20, Unit.PX);		
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungKontaktColumn, "Kontaktname");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungKontaktColumn, 10, Unit.EM);		
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftEigenschaftColumn, "");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftEigenschaftColumn, 10, Unit.EM);		
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungColumn, "");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungColumn, 10, Unit.EM);	
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungNutzer, "Teilhabender");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungNutzer, 10, Unit.EM);
		
		
		teilhaberschaftKontaktliste.addColumn(teilhaberschaftKontaktlisteCheckColumn, SafeHtmlUtils.fromSafeConstant("<br/"));
		teilhaberschaftKontaktliste.setColumnWidth(teilhaberschaftKontaktlisteCheckColumn, 20, Unit.PX);
		teilhaberschaftKontaktliste.addColumn(teilhaberschaftKontaktlisteColumn, "Kontaktliste");
		teilhaberschaftKontaktliste.setColumnWidth(teilhaberschaftKontaktlisteNutzerColumn, 10, Unit.PX);
		teilhaberschaftKontaktliste.addColumn(teilhaberschaftKontaktlisteNutzerColumn, "Teilhaber");
		teilhaberschaftKontaktliste.setColumnWidth(teilhaberschaftKontaktlisteNutzerColumn, 10, Unit.PX);
		
		teilhaberschaftKontakt.addColumn(teilhaberschaftKontaktCheckColumn, SafeHtmlUtils.fromSafeConstant("<br/"));
		teilhaberschaftKontakt.setColumnWidth(teilhaberschaftKontaktCheckColumn, 20, Unit.PX);
		teilhaberschaftKontakt.addColumn(teilhaberschaftKontaktColumn, "Kontaktname");
		teilhaberschaftKontakt.setColumnWidth(teilhaberschaftKontaktColumn, 10, Unit.PX);
		teilhaberschaftKontakt.addColumn(teilhaberschaftNutzerColumn, "Teilhabender");
		teilhaberschaftKontakt.setColumnWidth(teilhaberschaftNutzerColumn, 10, Unit.PX);
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		kontaktmanagerVerwaltung.findKontaktlisteTeilhaberschaftEigentuemer(nutzer.getId(), new KontaktlisteCallback());
		kontaktmanagerVerwaltung.findKontaktTeilhaberschaftEigentuemer(nutzer.getId(), new KontaktCallback());
		kontaktmanagerVerwaltung.findAuspraegungTeilhaberschaftEigentuemer(nutzer.getId(), new AuspraegungCallback());
		
		nutzerAnchor.addClickHandler(new DeleteNutzerClickHandler());
		
		headline.setStylePrimaryName("h2");
		nutzerVerwalten.setStylePrimaryName("h2");
		
		vPanel.add(headline);
		vPanel.add(auspraegungVerwalten);
		vPanel.add(teilhaberschaftAuspraegung);
		vPanel.add(kontakteVerwalten);
		vPanel.add(teilhaberschaftKontakt);
		vPanel.add(kontaktlisteVerwalten);
		vPanel.add(teilhaberschaftKontaktliste);
		vPanel.add(nutzerVerwalten);
		vPanel.add(nutzerAnchor);

		RootPanel.get("content").add(vPanel);
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
			Window.alert("Nutzer wurde gelöscht");
			Window.open(GWT.getHostPageBaseURL() + "ITProjektSS18Gruppe3.html", "_self", "");
		}
		
	}
	
	public class AuspraegungCallback implements AsyncCallback<Vector<NutzerTeilhaberschaftEigenschaftAuspraegungWrapper>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
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
			// TODO Auto-generated method stub
			
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<NutzerTeilhaberschaftKontaktlisteWrapper> result) {
			teilhaberschaftKontaktliste.setRowData(0, result);
			teilhaberschaftKontaktliste.setRowCount(result.size(), true);
		}
		
	}

}
