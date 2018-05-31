package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;


public class DisclosurePanelSuche extends VerticalPanel {

	private DisclosurePanel disc = new DisclosurePanel("Erweiterte Suche: ");
	
	private String eingabeText = "Geben Sie die Suchkriterien ein";
	private String kontaktLabel = "Kontaktname: ";
	private String eigenschaftLabel = "Eigenschaft: ";
	private String auspraegungLabel = "EigenschaftsausprÃ¤gung: ";

	private ListBox eigenschaftTextbox = new ListBox();
	private TextBox auspraegungTextbox = new TextBox();
	private TextBox kontaktTextbox = new TextBox();
	
	private FlexTable layout = new FlexTable();
	private Grid advancedOptions = new Grid(4, 2);
	
	private CheckBox checkBoxKontakt = new CheckBox("Nur in eigenen Kontakten/Kontaktlisten suchen");
	private CheckBox checkBoxTeilhaber = new CheckBox("Nur in den teilhabenden Kontakten/Kontaktlisten suchen");
	
	private Button startButton = new Button("Start");
	private Button filterLoeschenButton = new Button("Filter lÃ¶schen");

	private Kontakt kontakt = new Kontakt();
	private Eigenschaft eigenschaft = new Eigenschaft();
	private Eigenschaftsauspraegung auspraegung = new Eigenschaftsauspraegung();
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	
	private String checkedCheckbox = "";
	
	public DisclosurePanelSuche() {
		run();
	}

	void run() {
		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("mail"));
		
		
		//----------------------------Hardcoded----------------------//
		
		eigenschaft.setId(1);
		
		//----------------------------Hardcoded----------------------//
		eigenschaft.setBezeichnung(eigenschaftTextbox.getSelectedValue());
		auspraegung.setPersonID(nutzer.getId());
		auspraegung.setWert(auspraegungTextbox.getValue());
		kontakt.setName(kontaktTextbox.getValue());

		if (checkBoxKontakt.getValue() == false && checkBoxTeilhaber.getValue() == false) {
			checkedCheckbox = "";
		} else if (checkBoxKontakt.getValue() == true && checkBoxTeilhaber.getValue() == false) {
			checkedCheckbox = "onlyKontakt";
		} else if (checkBoxKontakt.getValue() == false && checkBoxTeilhaber.getValue() == true) {
			checkedCheckbox = "onlyTeilhaber";
		} else if (checkBoxKontakt.getValue() == true && checkBoxTeilhaber.getValue() == true) {
			checkedCheckbox = "both";
		}
		
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
		
		filterLoeschenButton.addClickHandler(new FilterLoeschenClickHandler());
		
		kontaktmanagerVerwaltung.findAllEigenschaften(new AllEigenschaftenCallback());
		kontaktmanagerVerwaltung.suchFunktion(nutzer, eigenschaft, auspraegung, new suchenCallback());

		this.add(layout);
	}
	public class suchenCallback implements AsyncCallback<Vector<Kontakt>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public class FilterLoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			kontaktTextbox.setValue("");
			eigenschaftTextbox.setSelectedIndex(0);
			auspraegungTextbox.setValue("");
			checkBoxKontakt.setValue(false);
			checkBoxTeilhaber.setValue(false);
			
		}
		
	}
	
	public class AllEigenschaftenCallback implements AsyncCallback<Vector<Eigenschaft>>{

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
