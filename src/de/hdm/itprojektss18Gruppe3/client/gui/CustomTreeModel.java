package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.BusinessObject;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;


public class CustomTreeModel extends VerticalPanel implements TreeViewModel {

	private VerticalPanel treeContainer = new VerticalPanel();
	private Nutzer nutzerKontaktliste = new Nutzer();
	private CellTree navigationCellTree;
	private Label navigationHeadline = new Label("Navigation");
	private ScrollPanel navigationTreePanel = new ScrollPanel();
	private SingleSelectionModel<BusinessObject> selectionModel = null;
	private Kontaktliste selectedKontaktliste = null;
	private Kontakt selectedKontakt = null;
	private ListDataProvider<Kontaktliste> kontaktlistenDataProvider = null;
	private CustomTreeModel customTreeModel;
	private Map<Kontaktliste, ListDataProvider<Kontakt>> kontaktDataProvider = null;


	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();


	public CustomTreeModel() {
		selectionModel = new SingleSelectionModel<BusinessObject>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		kontaktDataProvider = new HashMap<Kontaktliste, ListDataProvider<Kontakt>>();
		nutzerKontaktliste.setId(Integer.parseInt(Cookies.getCookie("id")));
	}


	void setSelectedKontaktliste(Kontaktliste selection) {
		selectedKontaktliste = selection;
	}

	void setSelectedKontakt(Kontakt selection) {
			selectedKontakt = selection;
	}

	Kontaktliste getSelectedKontaktliste() {
		return selectedKontaktliste;
	}

	Kontakt getSelectedKontakt() {
		return selectedKontakt;
	}


	/*
	 * SelectionChangeEventHandler um die Selektion des Nutzers auf eine Kontaktliste
	 * erfassen zu können und die einzelnen Kontakte innerhalb der angeklickten Kontaktliste
	 * anzeigen zu können. Dies geschieht in der Klasse KontaktlistView, an die die Selektion übergeben wird.
	 *
	 *
	 * Nested Class für die Reaktion auf Selektionsereignisse. Als Folge einer
	 * Baumknotenauswahl wird je nach Typ des Business-Objekts der
	 * "selectedKontaktliste" bzw. das "selectedKontakt" gesetzt.
	 */
	private class SelectionChangeEventHandler implements
	SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			KontaktlistView kontaktlistView = new KontaktlistView();
			kontaktlistView.getMenuBarContainerFlowPanel();
			if (selection instanceof Kontaktliste) {
				setSelectedKontaktliste((Kontaktliste) selection);
			} else if (selection instanceof Kontakt) {
				setSelectedKontakt((Kontakt) selection);
			}
			KontaktForm kontaktForm = new KontaktForm(getSelectedKontakt(), getSelectedKontaktliste());
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
			for(Kontaktliste kL : result) {
				kontaktlistenDataProvider.getList().add(kL);
			}
		}
	}


	// Get the NodeInfo that provides the children of the specified value.
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if(value.equals("Root")) {

			kontaktlistenDataProvider = new ListDataProvider<Kontaktliste>();
			kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(nutzerKontaktliste.getId(), new FindAllKontaktlisteAsyncCallback());



			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Kontaktliste>(kontaktlistenDataProvider, new KontaktlistenCell(), selectionModel, null);   

		} else if(value instanceof Kontaktliste){

			final ListDataProvider<Kontakt> kontaktProvider = new ListDataProvider<Kontakt>();
			kontaktDataProvider.put((Kontaktliste) value, kontaktProvider); 

			kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID((Kontaktliste) value, 
					new AsyncCallback<Vector<Kontakt>>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
					for(Kontakt k : result) {
						kontaktProvider.getList().add(k);
					}
				}
			});


			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Kontakt>(kontaktProvider, new KontaktCell(), selectionModel, null);

		}
		return null;
	}

	// Check if the specified value represents a leaf node. Leaf nodes
	// cannot be opened.
	@Override
	public boolean isLeaf(Object value) {
		return (value instanceof Kontakt); 
	}


	private class KontaktlistenCell extends AbstractCell<Kontaktliste> {

		@Override
		public void render(Context context, Kontaktliste value, SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendHtmlConstant("<table><td>");
				sb.appendEscaped(value.getBezeichnung());
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


	private class KontaktCell extends AbstractCell<Kontakt> {
		@Override
		public void render(Context context, Kontakt value, SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendHtmlConstant("<table><td>");
				sb.appendEscaped(value.getName());
				sb.appendHtmlConstant("</td><td>");
				if (value.getStatus() == 0) {
					sb.appendHtmlConstant("<img width=\"16\" src=\"images/singleperson.svg\">");

				} else if (value.getStatus() == 1) {

					sb.appendHtmlConstant("<img width=\"16\" src=\"images/group.svg\">");
				}
				sb.appendHtmlConstant("</td></table>");
			} else if(value == null){
				sb.appendHtmlConstant("<table><td>");
				sb.appendHtmlConstant("Leere Liste");
				sb.appendHtmlConstant("</td><td>");
			}
		}
	};


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
	}
}
