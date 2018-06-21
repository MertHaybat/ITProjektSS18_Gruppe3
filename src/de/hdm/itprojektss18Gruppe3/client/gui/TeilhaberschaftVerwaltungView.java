package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.MainFrame;

public class TeilhaberschaftVerwaltungView extends MainFrame {

	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel allKontakteCellTableContainer = new HorizontalPanel();
	private FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	
	private Button zurueckButton = new Button("Alle Kontakte");
	private Button teilhaberschaftButton = new Button("Teilhaberschaft löschen");

	
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
		
		
		menuBarContainerFlowPanel.add(zurueckButton);
		menuBarContainerFlowPanel.add(teilhaberschaftButton);
		
		
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungCheckColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungCheckColumn, 20, Unit.PX);
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungKontaktColumn, "Kontaktname");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungKontaktColumn, 10, Unit.EM);
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftEigenschaftColumn, "");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftEigenschaftColumn, 10, Unit.EM);
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungColumn, "");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungColumn, 10, Unit.EM);
		teilhaberschaftAuspraegung.addColumn(teilhaberschaftAuspraegungNutzer, "Eigentümer");
		teilhaberschaftAuspraegung.setColumnWidth(teilhaberschaftAuspraegungNutzer, 10, Unit.EM);
		
		vPanel.add(teilhaberschaftAuspraegung);
		vPanel.add(teilhaberschaftKontakt);
		vPanel.add(teilhaberschaftKontaktliste);
		
		RootPanel.get("menubar").add(menuBarContainerFlowPanel);
		RootPanel.get("content").add(vPanel);
	}

}
