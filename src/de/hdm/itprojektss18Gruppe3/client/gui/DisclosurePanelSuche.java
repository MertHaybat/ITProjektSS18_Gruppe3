package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class DisclosurePanelSuche extends VerticalPanel {

	private DisclosurePanel disc = new DisclosurePanel("Eigenschaftsausprägung Suche: ");
	private VerticalPanel suchErgebnisZweiPanel = new VerticalPanel();
	private VerticalPanel suchErgebnisPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private HorizontalPanel hButtonPanel = new HorizontalPanel();
			
	private Label eingabeText = new Label("Geben Sie die Suchkriterien ein");
	private Label kontaktLabel = new Label("Kontaktname:");
	private String eigenschaftLabel = "Eigenschaft: ";
	private String auspraegungLabel = "Eigenschaftsausprägung: ";
	private String textboxValue = "";
	private Label emptyListMessage = new Label("Diese Suche ergab keinen Treffer");
	
	private ListBox eigenschaftTextbox = new ListBox();
	private TextBox auspraegungTextbox = new TextBox();
	private TextBox kontaktTextbox = new TextBox();

	private FlexTable layout = new FlexTable();
	private Grid advancedOptions = new Grid(4, 2);
	private HorizontalPanel kontaktLabelTextBox = new HorizontalPanel();

	private CheckBox checkBoxKontakt = new CheckBox("In eigenen Kontakten/Kontaktlisten suchen");
	private CheckBox checkBoxTeilhaber = new CheckBox("In den teilhabenden Kontakten/Kontaktlisten suchen");

	private Button startButton = new Button("Start");
	private Button filterLoeschenButton = new Button("Filter löschen");
	private Button zurueck = new Button("Alle Kontakte");
	
	private Kontakt kontakt = new Kontakt();
	private Eigenschaftsauspraegung auspraegung = new Eigenschaftsauspraegung();
	private NoSelectionModel<EigenschaftsAuspraegungWrapper> selection = new NoSelectionModel<EigenschaftsAuspraegungWrapper>();
	private SingleSelectionModel<Kontakt> ssmKontakt = new SingleSelectionModel<Kontakt>();
	private CellTableKontakt kontaktCellTable = new CellTableKontakt(ssmKontakt);
	private ClickableTextCell clickCell = new ClickableTextCell();
	private TextCell textCell = new TextCell();
	private CellTableKontakt.KontaktnameColumn kontaktnameColumn = kontaktCellTable.new KontaktnameColumn(clickCell);
	private CellTableKontakt.IconColumn iconColumn = kontaktCellTable.new IconColumn(clickCell);
	private ClickableTextCell clickEigenschaft = new ClickableTextCell();

	private CellTableAuspraegungWrapper celltable = new CellTableAuspraegungWrapper(selection);
	private CellTableAuspraegungWrapper.WertEigenschaftColumn wertEigenschaftColumn = celltable.new WertEigenschaftColumn(clickEigenschaft);
	private CellTableAuspraegungWrapper.WertAuspraegungColumn wertAuspraegungColumn = celltable.new WertAuspraegungColumn(clickEigenschaft);
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	private Kontakt kontaktNeu = new Kontakt();
	
	private Button zurStartseiteButton = new Button("Startseite");


	public DisclosurePanelSuche() {
		run();
	}
	
	public DisclosurePanelSuche(Kontakt kontakt){
		kontaktNeu=kontakt;
		
		run();
	}
	
	public DisclosurePanelSuche(String textboxValue){
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("mail"));
		
		this.textboxValue=textboxValue;
		
		Kontakt k = new Kontakt();
		k.setName(textboxValue);
		k.setNutzerID(nutzer.getId());
		
		kontaktmanagerVerwaltung.findKontaktByName(k, new KontaktVollSuche());
		run();
	}

	void run() {
		eingabeText.setStylePrimaryName("h2");
		zurueck.setStylePrimaryName("mainButton");
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("mail"));

		kontakt.setName(kontaktTextbox.getValue());

		hButtonPanel.add(startButton);
		hButtonPanel.add(filterLoeschenButton);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
		this.setSpacing(6);
		layout.setCellSpacing(6);

		kontaktTextbox.setStylePrimaryName("detailSearchTextbox");
		kontaktLabel.setStylePrimaryName("labelSize1");
		kontaktLabelTextBox.add(kontaktLabel);
		kontaktLabelTextBox.add(kontaktTextbox);
		layout.setWidget(1, 0, kontaktLabelTextBox);
		
		eigenschaftTextbox.setVisibleItemCount(1);
		advancedOptions.setCellSpacing(6);
		advancedOptions.setHTML(0, 0, eigenschaftLabel);
		advancedOptions.setWidget(0, 1, eigenschaftTextbox);
		advancedOptions.setHTML(1, 0, auspraegungLabel);
		advancedOptions.setWidget(1, 1, auspraegungTextbox);
		advancedOptions.setWidget(2, 0, checkBoxKontakt);
		advancedOptions.setWidget(3, 0, checkBoxTeilhaber);
		disc.setContent(advancedOptions);
		layout.setWidget(3, 0, disc);
		layout.setWidget(4, 0, hButtonPanel);
		

		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		filterLoeschenButton.addClickHandler(new FilterLoeschenClickHandler());
		startButton.addClickHandler(new StartClickHandler());
		zurueck.addClickHandler(new ZuruckClickHandler());
		
		disc.addOpenHandler(new DiscOpenHandler());
		disc.addCloseHandler(new DiscCloseHandler());


		kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(nutzer.getId(), new AllKontaktlisteCallback());
		kontaktmanagerVerwaltung.findAllEigenschaften(new AllEigenschaftenCallback());
		
		ssmKontakt.addSelectionChangeHandler(new SelectionHandlerAuspraegung());
//		kontaktCellTable.getSsmAuspraegung().addSelectionChangeHandler(new SelectionHandlerAuspraegung());
		kontaktCellTable.setSelectionModel(ssmKontakt);
		ssmKontakt.addSelectionChangeHandler(new SelectionHandlerAuspraegung());
		kontaktCellTable.addColumn(kontaktnameColumn, "Kontaktname");
		kontaktCellTable.addColumn(iconColumn, "");
		kontaktCellTable.setColumnWidth(iconColumn, 5, Unit.EM);
		kontaktCellTable.setEmptyTableWidget(emptyListMessage);
//		kontaktCellTable.addCellPreviewHandler(new PreviewClickHander());
		
		kontaktCellTable.addDomHandler(new KontaktFormDoubleClickHandler(), DoubleClickEvent.getType());
		celltable.setSelectionModel(selection);

		
		celltable.addColumn(wertEigenschaftColumn, "");
		celltable.setColumnWidth(wertEigenschaftColumn, 7, Unit.EM);
		celltable.addColumn(wertAuspraegungColumn, "");
		celltable.setColumnWidth(wertAuspraegungColumn, 14, Unit.EM);
		
	//	suchErgebnisPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		hPanel.add(suchErgebnisPanel);
		hPanel.add(suchErgebnisZweiPanel);
		this.add(eingabeText);
		this.add(layout);
		this.add(hPanel);
		this.setStylePrimaryName("disclosureSearchPanel");
//		this.add(suchErgebnisZweiPanel);
	}
	public class KontaktFormDoubleClickHandler implements DoubleClickHandler{

		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			KontaktForm kontaktform = new KontaktForm(ssmKontakt.getSelectedObject());
		}
		
	}
	
	public class SelectionHandlerAuspraegung implements SelectionChangeEvent.Handler{

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			suchErgebnisZweiPanel.clear();
			suchErgebnisZweiPanel.add(celltable);
			kontaktmanagerVerwaltung.findEigenschaftAndAuspraegungByKontakt(nutzer.getId(), ssmKontakt.getSelectedObject().getId(), new WrapperCallback());

		}
		
	}
	
	public class WrapperCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungWrapper>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungWrapper> result) {
			celltable.setRowData(0, result);
			celltable.setRowCount(result.size(), true);
		}
		
	}
	
	public class ZuruckClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("content").clear();
			AllKontaktView allKontaktView = new AllKontaktView();
		}
		
	}
		
	public class DiscOpenHandler implements OpenHandler<DisclosurePanel> {

		@Override
		public void onOpen(OpenEvent<DisclosurePanel> event) {
			kontaktTextbox.setValue("");
			kontaktTextbox.setEnabled(false);
		}

	}

	public class DiscCloseHandler implements CloseHandler<DisclosurePanel> {

		@Override
		public void onClose(CloseEvent<DisclosurePanel> event) {
			kontaktTextbox.setEnabled(true);

		}

	}

	public class StartClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			suchErgebnisPanel.clear();
			suchErgebnisZweiPanel.clear();
			int checked = 0;
			
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			Kontakt k = new Kontakt();
			k.setName(kontaktTextbox.getValue());
			k.setNutzerID(nutzer.getId());
			// eigenschaft.setBezeichnung(eigenschaftTextbox.getSelectedValue());
			auspraegung.setWert(auspraegungTextbox.getValue());

			if (disc.isOpen() == false) {
				if (kontaktTextbox.getValue() == "") {
					Window.alert("Sie müssen einen Kontaktnamen eingeben");
				} else {
					kontaktmanagerVerwaltung.findKontaktByName(k, new FindKontaktByNameCallback());

				}
			} else if (disc.isOpen() == true) {
				if (checkBoxKontakt.getValue() == false && checkBoxTeilhaber.getValue() == false) {
					kontaktmanagerVerwaltung.findTeilhaberUndEigeneKontakteBySuche(nutzer, auspraegung, eigenschaftTextbox.getSelectedValue(), new FindKontaktByNameCallback());
				} else if (checkBoxKontakt.getValue() == true && checkBoxTeilhaber.getValue() == false) {
					kontaktmanagerVerwaltung.findEigeneKontakteBySuche(nutzer, auspraegung, eigenschaftTextbox.getSelectedValue(), new FindKontaktByNameCallback());
				} else if (checkBoxKontakt.getValue() == false && checkBoxTeilhaber.getValue() == true) {
					kontaktmanagerVerwaltung.findTeilhaberschaftKontakteBySuche(nutzer, auspraegung, eigenschaftTextbox.getSelectedValue(), new FindKontaktByNameCallback());
				} else if (checkBoxKontakt.getValue() == true && checkBoxTeilhaber.getValue() == true) {
					kontaktmanagerVerwaltung.findTeilhaberUndEigeneKontakteBySuche(nutzer, auspraegung, eigenschaftTextbox.getSelectedValue(), new FindKontaktByNameCallback());
				}
			}
			
		}

	}

	public class FindKontaktByNameCallback implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			kontaktCellTable.setRowData(0, result);
			kontaktCellTable.setRowCount(result.size(), true);
			kontaktCellTable.redraw();

			suchErgebnisPanel.add(kontaktCellTable);

		}

	}
	
	public class KontaktVollSuche implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Suchen aufgetreten: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();
			eigenschaftsauspraegung.setWert(textboxValue);
			if (result.size()>=1){
				
				kontaktCellTable.setRowData(0, result);
				kontaktCellTable.setRowCount(result.size(), true);
				kontaktCellTable.redraw();
				
				suchErgebnisPanel.add(kontaktCellTable);
			} else if (result.size() == 0){
				kontaktmanagerVerwaltung.findTeilhaberUndEigeneKontakteBySuche(nutzer, eigenschaftsauspraegung, "", new VollSucheCallback());	}
			

		}

	}
	public class VollSucheCallback implements AsyncCallback<Vector<Kontakt>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			kontaktCellTable.setRowData(0, result);
			kontaktCellTable.setRowCount(result.size(), true);
			kontaktCellTable.redraw();
			
			suchErgebnisPanel.add(kontaktCellTable);
		}
		
	}
	public class AllKontaktlisteCallback implements AsyncCallback<Vector<Kontaktliste>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Kontaktliste> result) {
			// kontaktlisteTextbox.addItem("");
			// for (Kontaktliste kontaktliste : result) {
			// kontaktlisteTextbox.addItem(kontaktliste.getBezeichnung());
			// }

		}

	}

	public class suchenCallback implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			// TODO Auto-generated method stub

		}

	}

	public class FilterLoeschenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			kontaktTextbox.setValue("");
			auspraegungTextbox.setValue("");
			eigenschaftTextbox.setSelectedIndex(0);
			// kontaktlisteTextbox.setSelectedIndex(0);
			checkBoxKontakt.setValue(false);
			checkBoxTeilhaber.setValue(false);

		}

	}

	public class AllEigenschaftenCallback implements AsyncCallback<Vector<Eigenschaft>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Eigenschaft> result) {
			eigenschaftTextbox.addItem("");
			for (Eigenschaft eigenschaft : result) {
				eigenschaftTextbox.addItem(eigenschaft.getBezeichnung());

			}
		}
	}
	
//	public class PreviewClickHander implements CellPreviewEvent.Handler<Kontakt> {
//
//		@Override
//		public void onCellPreview(CellPreviewEvent<Kontakt> event) {
//			
//			if(Event.getTypeInt(event.getNativeEvent().getType()) == Event.ONCLICK){
//					
//					KontaktForm kf = new KontaktForm(event.getValue());
//				}
//		}
//	}
}
