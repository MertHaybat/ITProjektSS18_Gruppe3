package de.hdm.itprojektss18Gruppe3.client.gui.report;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.AllKontaktEigenschaftenAndAuspraegungen;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class EigenschaftenReportForm extends HorizontalPanel {

	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	private Button btAllAuspraegungen = new Button("Report Starten");

//	private ListBox tbEigenschaft = new ListBox();
	private TextBox tbEigenschaft = new TextBox();
	private TextBox tbAuspraegung = new TextBox();

	private Label labelbEigenschaft = new Label("Eigenschaften: ");
	private Label labelAuspraegung = new Label("Eigenschaftsausprägung: ");
	private Vector<Eigenschaftsauspraegung> auspraegungVector = new Vector<Eigenschaftsauspraegung>();
	private VerticalPanel vpanel = new VerticalPanel();

	public EigenschaftenReportForm() {

		Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setMail(Cookies.getCookie("email"));
		reportverwaltung.findAllAuspraegungen(nutzer.getId(), new AllAuspraeungenCallback());
		btAllAuspraegungen.setStylePrimaryName("reportButton");

		this.add(labelbEigenschaft);
		this.add(tbEigenschaft);
		this.add(labelAuspraegung);
		this.add(tbAuspraegung);
		this.add(btAllAuspraegungen);

		RootPanel.get("contentReport").add(this);
		btAllAuspraegungen.addClickHandler(new AllAuspraegungenClickHandler());
	}
	class AllAuspraeungenCallback implements AsyncCallback<Vector<Eigenschaftsauspraegung>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Eigenschaftsauspraegung> result) {
			auspraegungVector = result;
		}
		
	}
	class AllAuspraegungenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			boolean vorhanden = false;
			for (Eigenschaftsauspraegung eigenschaftsauspraegung : auspraegungVector) {
				if(tbAuspraegung.getValue().equals(eigenschaftsauspraegung.getWert())){
					vorhanden = true;
				}
			}
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			nutzer.setMail(Cookies.getCookie("email"));
			if(tbEigenschaft.getValue().equals("") && tbAuspraegung.getValue().equals("")){
				Window.alert("Bitte Eigenschaft oder Ausprägung eingeben");
			} else {
				if(vorhanden == true || tbAuspraegung.getValue().equals("")){
					vpanel.clear();
					vpanel.add(new AllKontaktEigenschaftenAndAuspraegungen(nutzer.getMail(), tbEigenschaft.getValue(),
							tbAuspraegung.getValue()));
					RootPanel.get("contentReport").add(vpanel);
					
				} else {
					Window.alert("Keine Kontakte mit der eingegebenen Eigenschaftsausprägung vorhanden");
				}
				
				
			}
		}
		
	}

//	class EigenschaftenCallback implements AsyncCallback<Vector<Eigenschaft>> {
//
//		@Override
//		public void onFailure(Throwable caught) {
//			Window.alert("Fehler beim Laden aus der Datenbank" + caught.getMessage());
//		}
//
//		@Override
//		public void onSuccess(Vector<Eigenschaft> result) {
//			// TODO Auto-generated method stub
//			for (Eigenschaft eigenschaft : result) {
//				tbEigenschaft.addItem(eigenschaft.getBezeichnung());
//			}
//		}
//
//	}
}