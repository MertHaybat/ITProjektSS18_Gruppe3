package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktlistView.AnsehenClickHandler;
import de.hdm.itprojektss18Gruppe3.client.gui.KontaktlistView.EigenschaftAuspraegungCallback;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class KontaktCellList extends MainFrame{
	private HorizontalPanel kontaktInfoViewContainer = new HorizontalPanel();
	private ScrollPanel kontaktlistViewPanel = new ScrollPanel();
	private VerticalPanel allKontaktViewPanel = new VerticalPanel();
	private HorizontalPanel contentViewContainer = new HorizontalPanel();
	
	private ArrayList<Kontakt> selectedKontakteInCellTable = new ArrayList<Kontakt>();
	
	private CellList<Kontakt> kontaktCell = new CellList<Kontakt>(new KontaktCell(), CellListResources.INSTANCE);
	
	 private TextBox vornameBox = new TextBox();
	private TextBox nachnameBox = new TextBox();
	private TextBox geburtsdatum = new TextBox();
	private TextBox telefonnummer = new TextBox();
	private TextBox mail = new TextBox();
	
	private Button kontaktBearbeitenButton = new Button("Ansehen");
	private FlexTable kontaktInfoLayout = new FlexTable();
	private DecoratorPanel kontaktInfo = new DecoratorPanel();
	
	
	
	private Kontakt selectedKontakt = null;

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings
			.getKontaktVerwaltung();
	
	private SingleSelectionModel<Kontakt> selectionModel = new SingleSelectionModel<Kontakt>();

	private Kontaktliste kontaktliste = new Kontaktliste();
	
	public KontaktCellList() {
		run();
	}

	public KontaktCellList(Kontaktliste selection) {
		this.kontaktliste = selection;
		run();
	}
	
	
	public CellList<Kontakt> getKontaktCell() {
		return kontaktCell;
	}

	public void setKontaktCell(CellList<Kontakt> kontaktCell) {
		this.kontaktCell = kontaktCell;
	}

	@Override
	protected void run() {
		kontaktCell.setEmptyListWidget(new HTML("<b>Du hast keine Kontaktlisten</b>"));
		kontaktCell.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		vornameBox.setEnabled(false);
		nachnameBox.setEnabled(false);
		geburtsdatum.setEnabled(false);
		telefonnummer.setEnabled(false);
		mail.setEnabled(false);

		kontaktInfoLayout.setHTML(1, 0, "Vorname: ");
		kontaktInfoLayout.setWidget(1, 1, vornameBox);
		kontaktInfoLayout.setHTML(2, 0, "Nachname: ");
		kontaktInfoLayout.setWidget(2, 1, nachnameBox);
		kontaktInfoLayout.setHTML(3, 0, "Geburtsdatum: ");
		kontaktInfoLayout.setWidget(3, 1, geburtsdatum);
		kontaktInfoLayout.setHTML(4, 0, "Telefonnummer: ");
		kontaktInfoLayout.setWidget(4, 1, telefonnummer);
		kontaktInfoLayout.setHTML(5, 0, "E-Mail: ");
		kontaktInfoLayout.setWidget(5, 1, mail);
		kontaktInfoLayout.setWidget(6, 0, kontaktBearbeitenButton);

		kontaktInfo.setWidget(kontaktInfoLayout);
		kontaktCell.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedKontakt = selectionModel.getSelectedObject();

				kontaktInfoViewContainer.clear();
				kontaktInfoViewContainer.add(kontaktInfo);

				kontaktmanagerVerwaltung.findEigenschaftHybrid(selectedKontakt, new EigenschaftAuspraegungCallback());
			}
		});

		kontaktInfoLayout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = kontaktInfoLayout.getFlexCellFormatter();

		kontaktInfoLayout.setHTML(0, 0, "Kontakt Info");
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		kontaktlistViewPanel.add(kontaktCell);

		kontaktInfo.setStylePrimaryName("kontaktDecoratorPanel");
		allKontaktViewPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		kontaktlistViewPanel.setStylePrimaryName("kontaktlistenViewPanel");
		contentViewContainer.add(kontaktlistViewPanel);
		contentViewContainer.add(kontaktInfoViewContainer);
		kontaktBearbeitenButton.addClickHandler(new AnsehenClickHandler());
		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentViewContainer);
		
	}
	class AnsehenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontaktForm kontaktForm = new KontaktForm(selectedKontakt);
		}

	}
	static class KontaktCell extends AbstractCell<Kontakt> {

		@Override
		public void render(Context context, Kontakt value, SafeHtmlBuilder sb) {

			if (value == null) {
				return;
			}
			sb.appendHtmlConstant("<table>");
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
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
	

}
