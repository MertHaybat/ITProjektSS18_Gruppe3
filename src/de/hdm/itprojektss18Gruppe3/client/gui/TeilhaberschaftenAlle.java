package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;

/**
 * 
 * @author ersinbarut
 *
 */
public class TeilhaberschaftenAlle extends MainFrame{

	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();


//	private Button showKontakte = new Button("Kontakte aller Teilhaberschaften ");
//	private Button showKontaktliste = new Button("Kontaktlisten aller Teilhaberschaften ");
//	private Label lb1 = new Label("Kontakte aller Teilhaberschaften");
//	private Label lb2 = new Label("Kontaktlisten aller Teilhaberschaften");

	VerticalPanel vPanel = new VerticalPanel();
	Label menuBarHeadlineLabel = new Label("Teilhaberschaften");
	FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	VerticalPanel menuBarContainerPanel = new VerticalPanel();
//	final Button teilhaberschaftKontakte = new Button("Kontakte aller Teilhaberschaften");
//	final Button teilhaberschaftKontaktlisten = new Button("Kontaktlisten aller Teilhaberschaften");
//	final HorizontalPanel hPanel = new HorizontalPanel();
	

	@Override
	protected void run() {
		
		final HorizontalPanel hPanel = new HorizontalPanel();
		final Button teilhaberschaftKontakte = new Button("Kontakte aller Teilhaberschaften");
		final Button teilhaberschaftKontaktlisten = new Button("Kontaktlisten aller Teilhaberschaften");
		hPanel.setWidth("600px");
		hPanel.setBorderWidth(4);
		
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		/*
		 * Menüleiste mit den Buttons für die Anlage von einer neuen Kontaktliste und dem Löschen einer Kontaktliste erzeugen
		 * und dem Panel zuweisen
		 */
		menuBarHeadlineLabel.setStylePrimaryName("menuBarLabel");
		menuBarContainerFlowPanel.add(menuBarHeadlineLabel);
		
		teilhaberschaftKontakte.setStylePrimaryName("mainButton");
		teilhaberschaftKontaktlisten.setStylePrimaryName("mainButton");
		menuBarContainerFlowPanel.add(teilhaberschaftKontakte);
		menuBarContainerFlowPanel.add(teilhaberschaftKontaktlisten);
		menuBarContainerFlowPanel.setWidth("200%");
		menuBarContainerFlowPanel.setHeight("200%");
		
		
		Label contentHeadline = new Label("Deine Teilhaberschaften");
		contentHeadline.setStylePrimaryName("h2");
		
		
		vPanel.add(contentHeadline);
		hPanel.add(teilhaberschaftKontakte);
		hPanel.add(teilhaberschaftKontaktlisten);
		vPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		
		teilhaberschaftKontakte.setWidth("100%");
		teilhaberschaftKontaktlisten.setWidth("100%");
//		teilhaberschaftKontakte.setHeight("200%");
//		teilhaberschaftKontaktlisten.setHeight("200%");

//		teilhaberschaftKontakte.addClickHandler(handler)
		
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(menuBarContainerPanel);
		RootPanel.get("content").add(vPanel);
		RootPanel.get("content").add(hPanel);

		
		final class MyClickHandler implements ClickHandler{

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		
			
		}
	}
	


}
