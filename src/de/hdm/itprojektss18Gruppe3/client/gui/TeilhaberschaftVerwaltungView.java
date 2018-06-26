package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	private HTML headline = new HTML();
	
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
		teilhaberschaftKontakt.addColumn(teilhaberschaftNutzerColumn, "Nutzer");
		teilhaberschaftKontakt.setColumnWidth(teilhaberschaftNutzerColumn, 10, Unit.PX);
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		kontaktmanagerVerwaltung.findKontaktlisteTeilhaberschaftEigentuemer(nutzer.getId(), new KontaktlisteCallback());
		kontaktmanagerVerwaltung.findKontaktTeilhaberschaftEigentuemer(nutzer.getId(), new KontaktCallback());
		kontaktmanagerVerwaltung.findAuspraegungTeilhaberschaftEigentuemer(nutzer.getId(), new AuspraegungCallback());
		
		vPanel.add(teilhaberschaftAuspraegung);
		vPanel.add(teilhaberschaftKontakt);
		vPanel.add(teilhaberschaftKontaktliste);

		RootPanel.get("content").add(vPanel);
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
