package de.hdm.itprojektss18Gruppe3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS18Gruppe3 implements EntryPoint {

	private VerticalPanel vpanel = new VerticalPanel();
	private TextBox tb = new TextBox();
	private Button bt = new Button("Abschicken");
	
	@Override
	public void onModuleLoad() {
		RootPanel.get("nameFieldContainer").clear();
		vpanel.add(tb);
		vpanel.add(bt);
		RootPanel.get("nameFieldContainer").add(vpanel);
	}
	
}