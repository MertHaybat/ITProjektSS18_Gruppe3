package de.hdm.itprojektss18Gruppe3.client.gui.report;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HTMLResultPanel extends VerticalPanel{

	public void append(String text) {
		HTML content = new HTML(text);
		content.setStylePrimaryName("kontaktverwaltung-simpletext");
		this.add(content);
	}
	
}
