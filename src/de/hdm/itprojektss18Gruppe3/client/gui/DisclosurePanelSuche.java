package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class DisclosurePanelSuche extends VerticalPanel {

	private DisclosurePanel disc = new DisclosurePanel("Eigenschaftsausprägung Suche: ");
	private VerticalPanel suchErgebnisPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();

	private String eingabeText = "Geben Sie die Suchkriterien ein";
	private String kontaktLabel = "Kontaktname: ";
	private String eigenschaftLabel = "Eigenschaft: ";
	private String auspraegungLabel = "Eigenschaftsausprägung: ";
	private String textboxValue = "";
	
	private ListBox eigenschaftTextbox = new ListBox();
	private TextBox auspraegungTextbox = new TextBox();
	private TextBox kontaktTextbox = new TextBox();

	private FlexTable layout = new FlexTable();
	private Grid advancedOptions = new Grid(4, 2);

	private CheckBox checkBoxKontakt = new CheckBox("In eigenen Kontakten/Kontaktlisten suchen");
	private CheckBox checkBoxTeilhaber = new CheckBox("In den teilhabenden Kontakten/Kontaktlisten suchen");

	private Button startButton = new Button("Start");
	private Button filterLoeschenButton = new Button("Filter löschen");
	private Button zurueck = new Button("Alle Kontakte");
	
	private Kontakt kontakt = new Kontakt();
	private Eigenschaft eigenschaft = new Eigenschaft();
	private Eigenschaftsauspraegung auspraegung = new Eigenschaftsauspraegung();

	private CellTable<Kontakt> suchErgebnisCellTable = new CellTable<Kontakt>();

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	private String checkedCheckbox = "";
	private Kontaktliste kontaktliste = new Kontaktliste();
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
		zurueck.setStylePrimaryName("mainButton");
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("mail"));

		kontakt.setName(kontaktTextbox.getValue());

		checkBoxKontakt.getValue();
		checkBoxTeilhaber.getValue();

		kontaktTextbox.setStylePrimaryName("suchTextbox");
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
		this.setSpacing(6);
		layout.setCellSpacing(6);

		layout.setHTML(0, 0, eingabeText);
		layout.setHTML(1, 0, kontaktLabel);
		layout.setWidget(1, 1, kontaktTextbox);

		eigenschaftTextbox.setVisibleItemCount(1);
		advancedOptions.setCellSpacing(6);
		// advancedOptions.setHTML(0, 0, kontaktlisteLabel);
		// advancedOptions.setWidget(0, 1, kontaktlisteTextbox);
		advancedOptions.setHTML(0, 0, eigenschaftLabel);
		advancedOptions.setWidget(0, 1, eigenschaftTextbox);
		advancedOptions.setHTML(1, 0, auspraegungLabel);
		advancedOptions.setWidget(1, 1, auspraegungTextbox);
		advancedOptions.setWidget(2, 0, checkBoxKontakt);
		advancedOptions.setWidget(3, 0, checkBoxTeilhaber);
		disc.setContent(advancedOptions);
		layout.setWidget(3, 0, disc);
		layout.setWidget(4, 1, startButton);
		layout.setWidget(4, 2, filterLoeschenButton);

		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		Column<Kontakt, String> kontaktnameColumn = new Column<Kontakt, String>(new TextCell()) {
			@Override
			public String getValue(Kontakt object) {
				return object.getName();
			}
		};
		Column<Kontakt, String> iconColumn = new Column<Kontakt, String>(new TextCell() {
			public void render(Context context, SafeHtml value, SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<img width=\"20\" src=\"images/" + value.asString() + "\">");
			}
		}) {
			@Override
			public String getValue(Kontakt object) {
				if (object.getStatus() == 1) {
					return "group.svg";
				} else {
					return "singleperson.svg";
				}
			}
		};

		filterLoeschenButton.addClickHandler(new FilterLoeschenClickHandler());
		startButton.addClickHandler(new StartClickHandler());
		zurueck.addClickHandler(new ZuruckClickHandler());
		
		disc.addOpenHandler(new DiscOpenHandler());
		disc.addCloseHandler(new DiscCloseHandler());

		
	
		kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(nutzer.getId(), new AllKontaktlisteCallback());
		kontaktmanagerVerwaltung.findAllEigenschaften(new AllEigenschaftenCallback());


		suchErgebnisCellTable.addColumn(kontaktnameColumn, "Kontaktname");
		suchErgebnisCellTable.addColumn(iconColumn, "");
		hPanel.add(zurueck);
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(hPanel);
		this.add(layout);
		this.add(suchErgebnisPanel);
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
			suchErgebnisCellTable.setRowData(0, result);
			suchErgebnisCellTable.setRowCount(result.size(), true);
			suchErgebnisCellTable.redraw();

			suchErgebnisPanel.add(suchErgebnisCellTable);

		}

	}
	
	public class KontaktVollSuche implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("mail"));
			Eigenschaftsauspraegung eigenschaftsauspraegung = new Eigenschaftsauspraegung();
			eigenschaftsauspraegung.setWert(textboxValue);
			if (result.size()>=1){
				
				suchErgebnisCellTable.setRowData(0, result);
				suchErgebnisCellTable.setRowCount(result.size(), true);
				suchErgebnisCellTable.redraw();
				
				suchErgebnisPanel.add(suchErgebnisCellTable);
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
			suchErgebnisCellTable.setRowData(0, result);
			suchErgebnisCellTable.setRowCount(result.size(), true);
			suchErgebnisCellTable.redraw();
			
			suchErgebnisPanel.add(suchErgebnisCellTable);
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
}
