package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;

public class KontaktlistView extends MainFrame {

	private Label menuBarHeadlineLabel = new Label("Kontaktlisten");
	private VerticalPanel menuBarContainerPanel = new VerticalPanel();
	private FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	private Button deleteKontaktlisteButton = new Button("Kontaktliste löschen");
	private Button addKontaktToKontaktlisteButton = new Button("Kontakt hinzufügen");
	private Button deleteKontaktFromKontaktlisteButton = new Button("Kontakt entfernen");
	private Button addTeilhaberschaftKontaktButton = new Button("Kontakt teilen");
	private Button addTeilhaberschaftKontaktlisteButton = new Button("Kontaktliste teilen");
	// private CellTable<Kontakt> kontaktCellTable = new CellTable<Kontakt>(13,
	// CellTableResources.INSTANCE, keyProvider);
	// private KontaktForm kontaktForm = null;


	private ArrayList<Kontakt> selectedKontakteInCellTable = new ArrayList<Kontakt>();
	private static Kontaktliste kontaktlisteSelectedInTree = null;
	private Kontakt selectedKontakt;
	private TextBox vornameBox = new TextBox();
	private TextBox nachnameBox = new TextBox();
	private TextBox geburtsdatum = new TextBox();
	private TextBox telefonnummer = new TextBox();
	private TextBox mail = new TextBox();
	private Kontaktliste kontaktliste = new Kontaktliste();
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	public KontaktlistView() {
		run();
	}

	public KontaktlistView(Kontaktliste selection) {
		this.kontaktliste = selection;
		this.setKontaktlisteSelectedInTree(selection);

		run();
	}
	public static Kontaktliste getKontaktlisteSelectedInTree() {
		return kontaktlisteSelectedInTree;
	}

	public void setKontaktlisteSelectedInTree(Kontaktliste kontaktlisteSelectedInTree) {
		this.kontaktlisteSelectedInTree = kontaktlisteSelectedInTree;
	}

	public void run() {
		KontaktCellList kontaktCellList = new KontaktCellList(kontaktliste);
		/*
		 * Menüleiste mit den Buttons für die Anlage von einer neuen
		 * Kontaktliste und dem Löschen einer Kontaktliste erzeugen und dem
		 * Panel zuweisen
		 */
		KontaktDataProvider kontaktDataProvider = new KontaktDataProvider();
		kontaktDataProvider.addDataDisplay(kontaktCellList.getKontaktCell());
		kontaktCellList.getKontaktCell().setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		//-----
		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		deleteKontaktlisteButton.setStylePrimaryName("mainButton");
		addKontaktToKontaktlisteButton.setStylePrimaryName("mainButton");
		deleteKontaktFromKontaktlisteButton.setStylePrimaryName("mainButton");
		addTeilhaberschaftKontaktButton.setStylePrimaryName("mainButton");
		addTeilhaberschaftKontaktlisteButton.setStylePrimaryName("mainButton");
		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		menuBarContainerFlowPanel.add(deleteKontaktlisteButton);
		menuBarContainerFlowPanel.add(addKontaktToKontaktlisteButton);
		menuBarContainerFlowPanel.add(deleteKontaktFromKontaktlisteButton);
		menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktButton);
		menuBarContainerFlowPanel.add(addTeilhaberschaftKontaktlisteButton);
		menuBarContainerFlowPanel.setStylePrimaryName("menuBarContainerFlowPanel");
		menuBarContainerPanel.add(menuBarContainerFlowPanel);
		//----

		/*
		 * CellList für die Anzeige der Kontaktlisten eines Users wird umgesetzt
		 */
		
		//-------//
		
		deleteKontaktlisteButton.addClickHandler(new deleteKontaktlisteClickHandler(kontaktlisteSelectedInTree));
		addKontaktToKontaktlisteButton.addClickHandler(new addKontaktToKontaktlisteClickHandler());
		deleteKontaktFromKontaktlisteButton.addClickHandler(new KontaktAusKontaktlisteDeleteClickHandler());
		addTeilhaberschaftKontaktButton
				.addClickHandler(new addTeilhaberschaftKontaktClickHandler(selectedKontakteInCellTable));
		addTeilhaberschaftKontaktlisteButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			DialogBoxKontaktTeilen dialogbox = new DialogBoxKontaktTeilen(kontaktlisteSelectedInTree);
			dialogbox.center();
			}
			
		});

		// RootPanel.get("content").clear();
		//---/
//		RootPanel.get("content").clear();
		// RootPanel.get("menubar").clear();
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerFlowPanel);
		
	}



	

	class deleteKontaktlisteClickHandler implements ClickHandler {

		private Kontaktliste kontaktlisteToDelete = null;

		public deleteKontaktlisteClickHandler(Kontaktliste selected) {
			kontaktlisteToDelete = selected;
		}

		@Override
		public void onClick(ClickEvent event) {
			DeleteKontaktlisteDialogBox deleteKontakt = new DeleteKontaktlisteDialogBox(kontaktlisteToDelete);
			deleteKontakt.center();
		}
	}

	class addKontaktToKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (kontaktliste == null) {
				KontaktPopup k = new KontaktPopup();
				k.center();
			} else if (kontaktliste != null) {

				KontaktPopup k = new KontaktPopup(kontaktliste);
				k.center();
			}
		}
	}

	class KontaktAusKontaktlisteDeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontaktKontaktliste kliste = new KontaktKontaktliste();
			kliste.setKontaktID(selectedKontakt.getId());
			kliste.setKontaktlisteID(kontaktliste.getId());
			kontaktmanagerVerwaltung.deleteKontaktKontaktliste(kliste, new KontaktausKontaktlisteCallback());
		}

		class KontaktausKontaktlisteCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Kontakt wurde aus der Kontaktliste entfernt");
				KontaktlistView klV = new KontaktlistView(kontaktliste);
				RootPanel.get("content").clear();
				RootPanel.get("content").add(klV);
			}

		}

	}

	class AnsehenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontaktForm kontaktForm = new KontaktForm(selectedKontakt);
		}

	}

	class EigenschaftAuspraegungCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungWrapper> result) {
			for (EigenschaftsAuspraegungWrapper eigenschaftsAuspraegungWrapper : result) {
				switch (eigenschaftsAuspraegungWrapper.getEigenschaftIdValue()){

				case 1:
					vornameBox.setValue(eigenschaftsAuspraegungWrapper.getWertEigenschaftsauspraegungValue());

					break;
				case 2:
					nachnameBox.setValue(eigenschaftsAuspraegungWrapper.getWertEigenschaftsauspraegungValue());
					break;
				case 3:
					geburtsdatum.setValue(eigenschaftsAuspraegungWrapper.getWertEigenschaftsauspraegungValue());
					break;
				case 4:
					telefonnummer.setValue(eigenschaftsAuspraegungWrapper.getWertEigenschaftsauspraegungValue());
					break;
				case 5:
					mail.setValue(eigenschaftsAuspraegungWrapper.getWertEigenschaftsauspraegungValue());
					break;

				default:
					break;
				}

			}
		}

	}

	class AddTeilhaberschaftKontaktlisteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (kontaktliste == null) {
				Window.alert("Bitte wähle zuerst eine Kontaktliste");
			} else {
				
			}
		}
		
	}
	class addTeilhaberschaftKontaktClickHandler implements ClickHandler {

		ArrayList<Kontakt> selectedKontakteInCellTable;

		public addTeilhaberschaftKontaktClickHandler(ArrayList<Kontakt> selectedKontakteInCellTable) {
			this.selectedKontakteInCellTable = selectedKontakteInCellTable;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (selectedKontakt == null) {
				Window.alert("Bitte wähle zuerst mindestens einen Kontakt aus, den du teilen möchtest");
			} else {
//				TeilhaberschaftDialogBox dialogBox = new TeilhaberschaftDialogBox(selectedKontakteInCellTable);
//				dialogBox.center();
			DialogBoxKontaktTeilen dialogbox = new DialogBoxKontaktTeilen(selectedKontakt);
			dialogbox.center();
			}
		}
		
	}

//	static class KontaktCell extends AbstractCell<Kontakt> {
//
//		@Override
//		public void render(Context context, Kontakt value, SafeHtmlBuilder sb) {
//
//			if (value == null) {
//				return;
//			}
//			sb.appendHtmlConstant("<table>");
//			sb.appendHtmlConstant("<td style='font-size:95%;'>");
//			sb.appendEscaped(value.getName());
//			sb.appendHtmlConstant("</td><td>");
//			if (value.getStatus() == 0) {
//				sb.appendHtmlConstant("<img width=\"20\" src=\"images/singleperson.svg\">");
//
//			} else if (value.getStatus() == 1) {
//
//				sb.appendHtmlConstant("<img width=\"20\" src=\"images/group.svg\">");
//			}
//			sb.appendHtmlConstant("</td></table>");
//
//		}
//	}

	private static class KontaktDataProvider extends AsyncDataProvider<Kontakt> {

		@Override
		protected void onRangeChanged(HasData<Kontakt> display) {
			Nutzer nutzerKontaktliste = new Nutzer();
			nutzerKontaktliste.setId(Integer.parseInt(Cookies.getCookie("id")));
			final Range range = display.getVisibleRange();

			kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(getKontaktlisteSelectedInTree(),
					new AsyncCallback<Vector<Kontakt>>() {
						int start = range.getStart();

						ArrayList<Kontakt> kontaktToDisplay = new ArrayList<Kontakt>();

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim Auslesen aller Kontakte");

						}

						@Override
						public void onSuccess(Vector<Kontakt> result) {
							kontaktToDisplay.addAll(result);
							updateRowData(start, kontaktToDisplay);
						}
					});
		}
	}
}
