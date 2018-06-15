package de.hdm.itprojektss18Gruppe3.client.gui.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.itprojektss18Gruppe3.client.AllKontakte;
import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.ReportGeneratorAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class KontakteReportForm extends HorizontalPanel{

	ReportGeneratorAsync reportverwaltung = ClientsideSettings.getReportGenerator();

	private Button btAllNutzer = new Button("Report Starten");
	
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox box = new SuggestBox(oracle);
	private List<Nutzer> nutzerListe = new ArrayList<>();
	private List<Nutzer> nutzerSuggestbox = new ArrayList<>();
	private CellTable<Nutzer> selectedNutzerCT = new CellTable<Nutzer>();
	private ListDataProvider<Nutzer> nutzerDataProvider = new ListDataProvider<Nutzer>(nutzerSuggestbox);
	private Nutzer nutzerausdb = null;


	
//	private ListBox listboxNutzer = new ListBox();
	private Label labelbNutzer = new Label("Kontakte von: ");
	
	private VerticalPanel vpanel = new VerticalPanel();

	public KontakteReportForm() {
				
//		listboxNutzer.setVisibleItemCount(1);
//		listboxNutzer.setStylePrimaryName("listbox-report");
		btAllNutzer.setStylePrimaryName("reportButton");
		
		this.add(labelbNutzer);
//		this.add(listboxNutzer);
		this.add(box);
		this.add(btAllNutzer);
		
		RootPanel.get("content").add(this);

		btAllNutzer.addClickHandler(new AllKontakteClickHandler());

		Column<Nutzer, String> nutzertxtColumn = new Column<Nutzer, String>(new TextCell()) {

			@Override
			public String getValue(Nutzer object) {
				return object.getMail();
			}
		};
		Column<Nutzer, String> buttonColumn1 = new Column<Nutzer, String>(new ButtonCell()) {
			@Override
			public String getValue(Nutzer x) {
				return "x";

			}

			@Override
			public void onBrowserEvent(Context context, Element elem, Nutzer object, NativeEvent event) {
				super.onBrowserEvent(context, elem, object, event);
				if (event.getButton() == NativeEvent.BUTTON_LEFT) {
					nutzerDataProvider.getList().remove(object);
					selectedNutzerCT.setRowCount(nutzerSuggestbox.size(), true);
					selectedNutzerCT.setRowData(0, nutzerSuggestbox);
					selectedNutzerCT.redraw();
				}
			}
		};
		oracle.add("Allen");
		box.setStylePrimaryName("gwt-SuggestBox");
		reportverwaltung.findNutzer(new getNutzerCallback());
		box.addKeyPressHandler(new NutzerHinzufuegenKeyPressHandler());
		nutzerDataProvider.addDataDisplay(selectedNutzerCT);

		this.add(vpanel);

	}
	
	public class FindNutzerByEmail implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Nutzer result) {
			nutzerausdb = result;

		}

	}
	
	public class NutzerHinzufuegenKeyPressHandler implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {
			// TODO Auto-generated method stub
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
				if (box.getValue() == "") {
					// Window.alert("Sie müssen eine E-Mail Adresse eingeben.");
				} else {

					Nutzer nutzer = new Nutzer();
					nutzer.setMail(box.getValue());

					nutzerSuggestbox.add(nutzer);
					box.setValue("");
					selectedNutzerCT.setRowCount(nutzerSuggestbox.size(), true);
					selectedNutzerCT.setRowData(0, nutzerSuggestbox);
				}
			}

		}

	}
	
	class getNutzerCallback implements AsyncCallback<Vector<Nutzer>> {

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
	
	
	
	class AllKontakteReport implements AsyncCallback <Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden aus der Datenbank" + caught.getMessage());

		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			// TODO Auto-generated method stub
			for (Nutzer nutzer : result) {

				oracle.add(nutzer.getMail());
			}
		}


		
	}
	
	class AllKontakteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			if (box.getValue() =="Allen") {
				vpanel.clear();
				for (Nutzer nutzer : nutzerListe) {
				
				
				vpanel.add(new AllKontakte(nutzer.getMail()));

//				vpanel.add(new AllKontakte(box.getValue()));
				RootPanel.get("content").add(vpanel);
				}


			}
			else{
				if (box.getValue() == null){
					Window.alert("Für diesen Nutzer liegen keine gespeicherten Kontakte vor.");
				}
					else{
						
					
			
				vpanel.clear();
				vpanel.add(new AllKontakte(box.getValue()));
				RootPanel.get("content").add(vpanel);
				
					}
			}
		}
	
	}
}