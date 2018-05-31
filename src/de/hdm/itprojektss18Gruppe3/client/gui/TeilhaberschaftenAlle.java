package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;

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

	private VerticalPanel vPanel = new VerticalPanel();
	private Label menuBarHeadlineLabel = new Label("Teilhaberschaften");
	private FlowPanel menuBarContainerFlowPanel = new FlowPanel();
	private VerticalPanel menuBarContainerPanel = new VerticalPanel();
//	final Button teilhaberschaftKontakte = new Button("Kontakte aller Teilhaberschaften");
//	final Button teilhaberschaftKontaktlisten = new Button("Kontaktlisten aller Teilhaberschaften");
//	final HorizontalPanel hPanel = new HorizontalPanel();
	private Kontakt kontakt = new Kontakt();
	private Kontaktliste kliste = new Kontaktliste();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private Button teilhaberschaftKontakte = new Button("Kontakte aller Teilhaberschaften");
	private Button teilhaberschaftKontaktlisten = new Button("Kontaktlisten aller Teilhaberschaften");
	
	public TeilhaberschaftenAlle() {
		super.onLoad();
	}
	@Override
	protected void run() {
//		final HorizontalPanel hPanel = new HorizontalPanel();
//		final Button teilhaberschaftKontakte = new Button("Kontakte aller Teilhaberschaften");
//		final Button teilhaberschaftKontaktlisten = new Button("Kontaktlisten aller Teilhaberschaften");
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
		vPanel.add(hPanel);
		vPanel.setStylePrimaryName("cellListWidgetContainerPanel");
		
		
		teilhaberschaftKontakte.setWidth("100%");
		teilhaberschaftKontaktlisten.setWidth("100%");
//		teilhaberschaftKontakte.setHeight("200%");
//		teilhaberschaftKontaktlisten.setHeight("200%");
		teilhaberschaftKontakte.addClickHandler(new ShowKontakteClickHandler());
		teilhaberschaftKontaktlisten.addClickHandler(new ShowKontaktlistenClickHandler());
		
		
//		teilhaberschaftKontakte.addClickHandler(handler)
		
		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(hPanel);
		RootPanel.get("content").add(vPanel);
//		RootPanel.get("content").add(hPanel);
		
		
//		final class MyClickHandler implements ClickHandler{
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				
//			}
//		
//			
//		}
	}
	
	
	public class ShowKontakteClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			TeilhaberschaftKontakte kontakt = new TeilhaberschaftKontakte();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kontakt);
		}
		
	}
	
	public class ShowKontaktlistenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			TeilhaberschaftKontaktliste kontaktliste = new TeilhaberschaftKontaktliste();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kontaktliste);
		}
		
	}

}
