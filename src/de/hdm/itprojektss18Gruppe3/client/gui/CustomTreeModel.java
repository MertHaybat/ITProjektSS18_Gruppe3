package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;


public class CustomTreeModel implements TreeViewModel{


//	private VerticalPanel vPanel = new VerticalPanel();
	DecoratorPanel treeContainer = new DecoratorPanel();
//	private Label welcomeMessage = new Label("Willkommen beim Kontaktmanager");
//	private HTML instructionMessage = new HTML("<br>Hier kannst du deine Kontakte verwalten.");
	Nutzer nutzerKontaktliste = new Nutzer();
	Tree navigationTree = new Tree();
	Label navigationHeadline = new Label("Navigation");
//	VerticalPanel navigationTreePanel = new VerticalPanel();
	private SingleSelectionModel<Kontaktliste> selectionModel = null;
	private BusinessObjectKeyProvider kontaktlistenKeyProvider = null;
	private Kontaktliste selectedKontaktliste;
	private List<String> menuList;
	ListDataProvider<String> dataProvider;
	ListDataProvider<Kontaktliste> kontaktlistenDataProvider = new ListDataProvider<Kontaktliste>();

	private Button kontakte = new Button("Kontakte");
	private Button teilhaberschaften = new Button("Teilhaberschaften");
	

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();


	private class BusinessObjectKeyProvider implements
	ProvidesKey<Kontaktliste> {
		@Override
		public Integer getKey(Kontaktliste kontaktliste) {
			if (kontaktliste == null) {
				return null;
			} else {
				return new Integer(kontaktliste.getId());
			}
		}
	};






	private class SelectionChangeEventHandler implements
	SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Kontaktliste selection = selectionModel.getSelectedObject();
			setSelectedKontaktliste((Kontaktliste) selection);
			KontaktlistView klV = new KontaktlistView(selection);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(klV);
		}
	}
	
	public class kontakteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			AllKontaktView akv = new AllKontaktView();

			
		}	
	}

	public class teilhaberschaftClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			TeilhaberschaftenAlle teilhaberschaftenAlle = new TeilhaberschaftenAlle();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(teilhaberschaftenAlle);
			
		}
		
	}

	
	

	public CustomTreeModel() {
		
		kontakte.addClickHandler(new kontakteClickHandler());
		teilhaberschaften.addClickHandler(new teilhaberschaftClickHandler());

		menuList = new ArrayList<String>();
		menuList.add("Alle Kontaktlisten");

		nutzerKontaktliste.setId(Integer.parseInt(Cookies.getCookie("id")));
		kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(nutzerKontaktliste.getId(), new FindAllKontaktlisteAsyncCallback());

		kontaktlistenKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<Kontaktliste>(kontaktlistenKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());


//		welcomeMessage.addStyleName("headline");
//		vPanel.setStylePrimaryName("headlinePanel");
//		vPanel.add(welcomeMessage);
//		instructionMessage.setStylePrimaryName("landingpageText");
//		vPanel.add(instructionMessage);
//		vPanel.add(kontakte);
//		vPanel.add(teilhaberschaften);
//
//
//		RootPanel.get("content").clear();
//		RootPanel.get("content").add(vPanel);


	}

	public void setSelectedKontaktliste(Kontaktliste selection) {
		selectedKontaktliste = selection;
	}




	public class FindAllKontaktlisteAsyncCallback implements AsyncCallback<Vector<Kontaktliste>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("ERROR");

		}

		@Override
		public void onSuccess(Vector<Kontaktliste> result) {
			kontaktlistenDataProvider.getList().addAll(result);
		}

	}


	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if(value.equals("Root")) {

			dataProvider = new ListDataProvider<String>(menuList);

			Cell<String> cell = new AbstractCell<String>() {
				@Override
				public void render(Context context, String value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(value);
					}
				}
			};
			return new DefaultNodeInfo<String>(dataProvider, cell);   

		} else {

			Cell<Kontaktliste> kontaktlistenCell = new AbstractCell<Kontaktliste>() {
				@Override
				public void render(Context context, Kontaktliste value, SafeHtmlBuilder sb) {
					if (value != null) {
						
						sb.appendHtmlConstant("<table><td>");
						sb.appendEscaped(value.getBezeichnung());
						sb.appendHtmlConstant("</td><td>");
						sb.appendHtmlConstant("<img width=\"20\" src=\"images/singleperson.svg\">");
						sb.appendHtmlConstant("</td></table>");
						}

//						if (value.getStatus() == 0) {
//							sb.appendHtmlConstant("<img width=\"20\" src=\"images/singleperson.svg\">");
//
//						} else if (value.getStatus() == 1) {
//
//							sb.appendHtmlConstant("<img width=\"20\" src=\"images/group.svg\">");
//						}

					
				}
			};

			return new DefaultNodeInfo<Kontaktliste>(kontaktlistenDataProvider, kontaktlistenCell, selectionModel, null);

		}
	}


	@Override
	public boolean isLeaf(Object value) {
		if(value instanceof Kontaktliste) {
			return true;
		}
		return false;
	}

}
