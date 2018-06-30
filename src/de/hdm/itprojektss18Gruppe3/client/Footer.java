package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.gui.Impressum;

public class Footer extends HorizontalPanel {
	
	private Label footerLabel = new HTML("IT Projekt SS18 | Hochschule der Medien Stuttgart |");
	private Button impressum = new Button("Impressum");
	private HorizontalPanel footerPanel = new HorizontalPanel();
	
	
	public Footer() {
		impressum.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Impressum ip = new Impressum();
				
			}
			
		});
		impressum.setStylePrimaryName("impressumButton");
		footerPanel.add(footerLabel);
		footerPanel.add(impressum);
		footerPanel.setStylePrimaryName("footerPanel");
		RootPanel.get("footer").clear();
		RootPanel.get("footer").add(footerPanel);
	}
	
	public void run() {

	}
}
