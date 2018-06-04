package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
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


public class CustomTreeModel extends VerticalPanel implements TreeViewModel {


	private VerticalPanel vPanel = new VerticalPanel();
	private VerticalPanel treeContainer = new VerticalPanel();
//	private Label welcomeMessage = new Label("Willkommen beim Kontaktmanager");
//	private HTML instructionMessage = new HTML("<br>Hier kannst du deine Kontakte verwalten.");
	private Nutzer nutzerKontaktliste = new Nutzer();
	private CellTree navigationCellTree;
	private Label navigationHeadline = new Label("Navigation");
	private VerticalPanel navigationTreePanel = new VerticalPanel();
	private SingleSelectionModel<Kontaktliste> selectionModel = null;
	private Kontaktliste selectedKontaktliste;
	private List<String> menuList;
	private ListDataProvider<String> dataProvider;
	private ListDataProvider<Kontaktliste> kontaktlistenDataProvider = new ListDataProvider<Kontaktliste>();
	private Button kontakte = new Button("Kontakte");
	private Button teilhaberschaften = new Button("Teilhaberschaften");
	private CustomTreeModel customTreeModel;

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();


	public CustomTreeModel() {
		menuList = new ArrayList<String>();
		menuList.add("Alle Kontaktlisten");
		menuList.add("Kontakte");
		menuList.add("Teilhaberschaften");
		dataProvider = new ListDataProvider<String>(menuList);
		selectionModel = new SingleSelectionModel<Kontaktliste>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());

		nutzerKontaktliste.setId(Integer.parseInt(Cookies.getCookie("id")));
		kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(nutzerKontaktliste.getId(), new FindAllKontaktlisteAsyncCallback());

	}


	public void setSelectedKontaktliste(Kontaktliste selection) {
		selectedKontaktliste = selection;
	}


	/*
	 * SelectionChangeEventHandler um die Selektion des Nutzers auf eine Kontaktliste
	 * erfassen zu können und die einzelnen Kontakte innerhalb der angeklickten Kontaktliste
	 * anzeigen zu können. Dies geschieht in der Klasse KontaktlistView, an die die Selektion übergeben wird.
	 */
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


	//BEIDE CLICKHANDLER NUR ZUM ÜBERGANG, DA KA WIE MAN ANDERE PUNKTE IN DEN TREE BEKOMMT
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


	/*
	 * AsyncCallback zum Abfragen aller Kontaktlisten des eingeloggten Nutzers. Das Ergebnis wird dem 
	 * DataListProvider übergeben, wodurch die Anzeige der einzelnen Kontaktlisten im CellTree
	 * ermöglicht wird
	 */

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


	// Get the NodeInfo that provides the children of the specified value.
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if(value.equals("Root")) {

			Cell<String> cell = new AbstractCell<String>() {
				@Override
				public void render(Context context, String value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(value);
					}
				}
			};
			// Return a node info that pairs the data with a cell.
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
			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Kontaktliste>(kontaktlistenDataProvider, kontaktlistenCell, selectionModel, null);

		}
	}

	// Check if the specified value represents a leaf node. Leaf nodes
	// cannot be opened.
	@Override
	public boolean isLeaf(Object value) {
		if(value instanceof Kontaktliste) {
			return true;
		} else {
			return false;
		}
	}
	
	public void onLoad() {

		customTreeModel = new CustomTreeModel();
		navigationCellTree = new CellTree(customTreeModel, "Root");
		navigationCellTree.setAnimationEnabled(true);
		treeContainer.add(navigationHeadline);
		navigationHeadline .setStylePrimaryName("navigationPanelHeadline");
		treeContainer.add(navigationCellTree);
		navigationCellTree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		//KÖNNEN DANN WEG WENN TREE KLAPPT
		kontakte.addClickHandler(new kontakteClickHandler());
		teilhaberschaften.addClickHandler(new teilhaberschaftClickHandler());

		vPanel.add(kontakte);
		vPanel.add(teilhaberschaften);


		RootPanel.get("leftmenutree").clear();
		RootPanel.get("leftmenutree").add(treeContainer);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(vPanel);
	}

}



