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
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktlisteWrapper;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.BusinessObject;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;

/*
 * Die Implementierung des TreeViewModel ermöglicht die Anzeige von Kontaktlisten und
 * den zugehörigen Kontakten durch eine Baumstruktur in dem linken seitlichen Panel.
 * 
 * @author Kevin, Mert, Rathke
 * 
 */
public class CustomTreeModel extends VerticalPanel implements TreeViewModel {

	private VerticalPanel treeContainer = new VerticalPanel();
	private Nutzer nutzerKontaktliste = new Nutzer();
	private Nutzer nutzer = new Nutzer();
	private CellTree navigationCellTree;
	private Label navigationHeadline = new Label("Kontaktlisten");
	private ScrollPanel navigationTreePanel = new ScrollPanel();
	private SingleSelectionModel<BusinessObject> selectionModel = null;
	private Kontaktliste selectedKontaktliste = null;
	private Kontakt selectedKontakt = null;
	private ListDataProvider<Kontaktliste> kontaktlistenDataProvider = null;
	private CustomTreeModel customTreeModel;
	private Teilhaberschaft teilhaberschaft = new Teilhaberschaft();
	private Vector<Teilhaberschaft> teilhaberschaftVector = new Vector<Teilhaberschaft>();
	/*
	 * Durch die Map werden die ListDataProvider mit den Kontakten, die sich in
	 * einer einer Kontaktliste befinden, gespeichert, wodurch die Anzeige als
	 * Baumknoten realisiert werden kann..
	 * 
	 */
	private Map<Kontaktliste, ListDataProvider<Kontakt>> kontaktDataProvider = null;

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();

	/*
	 * Im Konstruktor werden sowohl das SingleSelectionModel, das die Selektion
	 * des Nutzers registiert als auch die Map initialisiert. Auch wird aus dem
	 * Cookie die ID des Nutzers ausgelesen, was dann dazu verwendet werden
	 * kann, die Kontaktlisten des Nutzers laden zu können.
	 */
	public CustomTreeModel() {
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		selectionModel = new SingleSelectionModel<BusinessObject>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		kontaktDataProvider = new HashMap<Kontaktliste, ListDataProvider<Kontakt>>();
		nutzerKontaktliste.setId(Integer.parseInt(Cookies.getCookie("id")));
	}

	/*
	 * Setter und Getter um die vom Nutzer ausgewählte Kontaktliste bzw. Kontakt
	 * zu speichern
	 */
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
	 * SelectionChangeEventHandler um die Selektion des Nutzers auf eine
	 * Kontaktliste erfassen zu können und die einzelnen Kontakte innerhalb der
	 * angeklickten Kontaktliste anzeigen zu können. Dies geschieht in der
	 * Klasse KontaktlistView, an die die Selektion übergeben wird. Als Folge
	 * einer Baumknotenauswahl wird je nach Typ des Business-Objekts der
	 * "selectedKontaktliste" bzw. das "selectedKontakt" gesetzt.
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Kontaktliste) {
				setSelectedKontaktliste((Kontaktliste) selection);
				if(getSelectedKontaktliste().getBezeichnung().equals("Eigene Kontakte")){
					AllKontaktView allkontaktView = new AllKontaktView();
				} else {
					AllKontaktView allkontaktView = new AllKontaktView(getSelectedKontaktliste());

				}
			} else if (selection instanceof Kontakt) {
				setSelectedKontakt((Kontakt) selection);
				KontaktForm kontaktForm = new KontaktForm(getSelectedKontakt());
			}
		}
	}

	/*
	 * AsyncCallback zum Abfragen aller Kontaktlisten des eingeloggten Nutzers.
	 * Das Ergebnis wird dem DataListProvider übergeben, wodurch die Anzeige der
	 * einzelnen Kontaktlisten im CellTree ermöglicht wird
	 */
	public class FindAllKontaktlisteAsyncCallback implements AsyncCallback<Vector<Kontaktliste>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());

		}

		@Override
		public void onSuccess(Vector<Kontaktliste> result) {
			Kontaktliste eigeneKontakte = new Kontaktliste();
			eigeneKontakte.setBezeichnung("Eigene Kontakte");
			kontaktlistenDataProvider.getList().add(eigeneKontakte);
			Kontaktliste kontaktliste = new Kontaktliste();
			kontaktliste.setBezeichnung("Empfangene Kontakte");
			result.add(kontaktliste);
			for (Kontaktliste kL : result) {
				kontaktlistenDataProvider.getList().add(kL);
			}
			Nutzer nutzer = new Nutzer();
			nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
			kontaktmanagerVerwaltung.findNutzerTeilhaberschaftKontaktlisteWrapper(nutzer.getId(),
					new FindAllKontaktlisteByTeilhaberschaftCallback());

		}

		public class FindAllKontaktlisteByTeilhaberschaftCallback
		implements AsyncCallback<Vector<NutzerTeilhaberschaftKontaktlisteWrapper>> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Laden: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Vector<NutzerTeilhaberschaftKontaktlisteWrapper> result) {
				Vector<Kontaktliste> kontaktlisteVector = new Vector<Kontaktliste>();
				for (NutzerTeilhaberschaftKontaktlisteWrapper wrapper : result) {
					kontaktlisteVector.add(wrapper.getKontaktliste());
				}
				for (Kontaktliste kL : kontaktlisteVector) {
					kontaktlistenDataProvider.getList().add(kL);
				}
			}
		}
	}

	// Get the NodeInfo that provides the children of the specified value.
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if (value.equals("Root")) {

			kontaktlistenDataProvider = new ListDataProvider<Kontaktliste>();
			kontaktmanagerVerwaltung.findAllKontaktlisteByNutzerID(nutzerKontaktliste.getId(),
					new FindAllKontaktlisteAsyncCallback());

			// Return a node info that pairs the data with a cell.
			return new DefaultNodeInfo<Kontaktliste>(kontaktlistenDataProvider, new KontaktlistenCell(), selectionModel,
					null);

		} else if (value instanceof Kontaktliste) {
			final ListDataProvider<Kontakt> kontaktProvider = new ListDataProvider<Kontakt>();
			if (((Kontaktliste) value).getBezeichnung().equals("Empfangene Kontakte")) {
				kontaktmanagerVerwaltung.findEigenschaftsauspraegungAndKontaktByTeilhaberschaft(nutzer.getId(), new AsyncCallback<Vector<NutzerTeilhaberschaftKontaktWrapper>>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Vector<NutzerTeilhaberschaftKontaktWrapper> result) {
						Vector<Kontakt> kontakt = new Vector<Kontakt>();
						for (NutzerTeilhaberschaftKontaktWrapper wrapper : result) {

							kontakt.add(wrapper.getKontakt());
							teilhaberschaftVector.add(wrapper.getTeilhaberschaft());

						}
						for (Kontakt k : kontakt) {
							kontaktProvider.getList().add(k);
						}
					}
				});
			}
			if (((Kontaktliste) value).getBezeichnung().equals("Eigene Kontakte")) {
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				kontaktmanagerVerwaltung.findAllKontaktByNutzerID(nutzer.getId(), new AsyncCallback<Vector<Kontakt>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Fehler beim Laden" + caught.getMessage());
					}

					@Override
					public void onSuccess(Vector<Kontakt> result) {
						for (Kontakt k : result) {
							kontaktProvider.getList().add(k);
						}
					}
				});
			}

			kontaktDataProvider.put((Kontaktliste) value, kontaktProvider);

			kontaktmanagerVerwaltung.findAllKontakteByKontaktlisteID((Kontaktliste) value,
					new AsyncCallback<Vector<Kontakt>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Fehler beim Laden der Daten: " + caught.getMessage());
				}

				@Override
				public void onSuccess(Vector<Kontakt> result) {
					for (Kontakt k : result) {
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

	/*
	 * Klasse zur Darstellung von Kontaktlisten Objekten in einer Zelle. Hier
	 * wird zu der jeweiligen Kontaktliste noch überprüft, ob diese geteilt
	 * wurde (Status 0 bzw. 1). Dies wird mit einem Icon hinter der
	 * Kontaktlistenbezeichnung visualisiert.
	 */
	private class KontaktlistenCell extends AbstractCell<Kontaktliste> {

		@Override
		public void render(Context context, Kontaktliste value, SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendHtmlConstant("<table width='100%'><td>");
				sb.appendEscaped(value.getBezeichnung());
				sb.appendHtmlConstant("</td><td style='float: right'>");
				if (value.getBezeichnung().equals("Eigene Kontakte")){

				} else if(value.getBezeichnung().equals("Empfangene Kontakte")) {
					sb.appendHtmlConstant("<img width=\"22\" src=\"images/received_share.png\" style=\"vertical-align: middle; opacity: 0.7\">");
				}
				else if(value.getNutzerID() != nutzerKontaktliste.getId()) {
					sb.appendHtmlConstant("<img width=\"22\" src=\"images/received_share.png\" style=\"vertical-align: middle; opacity: 0.7\">");
				} else {
					if (value.getStatus() == 0) {
						sb.appendHtmlConstant("<img width=\"18\" src=\"images/singleperson.svg\" style=\"vertical-align: middle; margin-right: 2px; opacity: 0.7\">");

					} else if (value.getStatus() == 1) {
						sb.appendHtmlConstant("<img width=\"21\" src=\"images/group.svg\" style=\"vertical-align: middle; opacity: 0.7\">");
					}
				}
				sb.appendHtmlConstant("</td></table>");
			}
		}
	};

	/*
	 * Klasse zur Darstellung von Kontakt Objekten in einer Zelle. Hier wird zu
	 * dem jeweiligen Kontakt noch überprüft, ob dieser geteilt wurde (Status 0
	 * bzw. 1). Dies wird mit einem Icon hinter dem Kontaktnamen visualisiert.
	 */
	private class KontaktCell extends AbstractCell<Kontakt> {
		@Override
		public void render(Context context, Kontakt value, SafeHtmlBuilder sb) {
			if (value != null) {
				sb.appendHtmlConstant("<table width='85%'><td><td>");
				sb.appendEscaped(value.getName());
				sb.appendHtmlConstant("</td></table>");
			} else if (value == null) {
				sb.appendHtmlConstant("<table><td>");
				sb.appendHtmlConstant("Leere Liste");
				sb.appendHtmlConstant("</td><td>");
			}
		}
	};

	/*
	 * onLoad Methode, in der letzlich das CustomTreeModel und der eigentliche
	 * CellTree initialisiert werden. Dem Baum werden dann noch die
	 * CellTreeResource, die auf den für den CellTree definierten Stylesheet
	 * verweist, sowie das Interface CellTreeMessages , womit die emptyTree
	 * Message ("Empty") abgeändert werden kann, als Parameter übergeben.
	 */
	public void onLoad() {

		customTreeModel = new CustomTreeModel();
		navigationCellTree = new CellTree(customTreeModel, "Root", CellTreeResources.INSTANCE,
				new CellTree.CellTreeMessages() {

			@Override
			public String showMore() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String emptyTree() {
				// TODO Auto-generated method stub
				return "Leere Kontaktliste";
			}
		});

		navigationCellTree.setAnimationEnabled(true);
		navigationTreePanel.add(navigationCellTree);

		treeContainer.add(navigationHeadline);
		navigationTreePanel.setStylePrimaryName("treeContainerPanel");
		navigationHeadline.setStylePrimaryName("navigationPanelHeadline");
		treeContainer.add(navigationTreePanel);
		navigationCellTree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		RootPanel.get("leftmenutree").clear();
		RootPanel.get("leftmenutree").add(treeContainer);
	}
}
