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
import com.google.gwt.user.client.ui.ScrollPanel;
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
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;


public class CustomTreeModel extends VerticalPanel implements TreeViewModel {

	private VerticalPanel treeContainer = new VerticalPanel();
	private Nutzer nutzerKontaktliste = new Nutzer();
	private CellTree navigationCellTree;
	private Label navigationHeadline = new Label("Navigation");
	private ScrollPanel navigationTreePanel = new ScrollPanel();
	private SingleSelectionModel<Kontaktliste> selectionModel = null;
	private SingleSelectionModel<Kontakt> kontaktSelectionModel = null;
	private Kontaktliste selectedKontaktliste;
	private Kontakt selectedKontakt;
	private ListDataProvider<Kontakt> kontaktDataProvider = new ListDataProvider<Kontakt>();
	private ListDataProvider<Kontaktliste> kontaktlistenDataProvider = new ListDataProvider<Kontaktliste>();
	private CustomTreeModel customTreeModel;

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();


	public CustomTreeModel() {

		selectionModel = new SingleSelectionModel<Kontaktliste>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		
		kontaktSelectionModel = new SingleSelectionModel<Kontakt>();
		kontaktSelectionModel.addSelectionChangeHandler(new KontaktSelectionChangeEventHandler());

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
			Kontaktliste selection = new Kontaktliste();
			selection = selectionModel.getSelectedObject();
			setSelectedKontaktliste((Kontaktliste) selection);
			kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID(selection, new FindAllKontakteByKontaktlisteAsyncCallback());
			KontaktlistView klV = new KontaktlistView(selection);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(klV);
		}
	}
	
	private class KontaktSelectionChangeEventHandler implements
	SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			Kontakt selection = new Kontakt();
			selection = kontaktSelectionModel.getSelectedObject();
			KontaktForm kontaktForm = new KontaktForm(selection);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kontaktForm);
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
	
	
	public class FindAllKontakteByKontaktlisteAsyncCallback implements AsyncCallback<Vector<Kontakt>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Kontakt> result) {
			kontaktDataProvider.getList().clear();
			kontaktDataProvider.getList().addAll(result);
			Window.alert(Integer.toString(kontaktDataProvider.getList().size()));
		}
		
	}


	// Get the NodeInfo that provides the children of the specified value.
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if(value.equals("Root")) {

			Cell<Kontaktliste> kontaktlistenCell = new AbstractCell<Kontaktliste>() {
				@Override
				public void render(Context context, Kontaktliste value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(value.getBezeichnung());
					}
				}
			};
			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Kontaktliste>(kontaktlistenDataProvider, kontaktlistenCell, selectionModel, null);   

		} else {

			Cell<Kontakt> kontaktCell = new AbstractCell<Kontakt>() {
				@Override
				public void render(Context context, Kontakt value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendHtmlConstant("<table><td>");
						sb.appendEscaped(value.getName());
						sb.appendHtmlConstant("</td><td>");
						if (value.getStatus() == 0) {
							sb.appendHtmlConstant("<img width=\"20\" src=\"images/singleperson.svg\">");

						} else if (value.getStatus() == 1) {

							sb.appendHtmlConstant("<img width=\"20\" src=\"images/group.svg\">");
						}
						sb.appendHtmlConstant("</td></table>");
						}
				}
			};
			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Kontakt>(kontaktDataProvider, kontaktCell, kontaktSelectionModel, null);

		}
	}

	// Check if the specified value represents a leaf node. Leaf nodes
	// cannot be opened.
	@Override
	public boolean isLeaf(Object value) {
		return value instanceof Kontakt; 
	}
	
	public void onLoad() {

		customTreeModel = new CustomTreeModel();
		navigationCellTree = new CellTree(customTreeModel, "Root");
		navigationCellTree.setAnimationEnabled(true);
		navigationTreePanel.add(navigationCellTree);
		
		treeContainer.add(navigationHeadline);
		navigationTreePanel.setStylePrimaryName("treeContainerPanel");
		navigationHeadline .setStylePrimaryName("navigationPanelHeadline");
		treeContainer.add(navigationTreePanel);
		navigationCellTree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);


		RootPanel.get("leftmenutree").clear();
		RootPanel.get("leftmenutree").add(treeContainer);
	//		RootPanel.get("content").clear();
	//		RootPanel.get("content").add(vPanel);
	}

}



