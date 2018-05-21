package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import de.hdm.itprojektss18Gruppe3.client.gui.report.HTMLResultPanel;
import de.hdm.itprojektss18Gruppe3.client.gui.report.ReportSelectMenu;
public class KontaktmanagerReport extends HTMLResultPanel implements EntryPoint {

	
	@Override
	public void onModuleLoad() {
		
		RootPanel.get("leftmenutree").add(new ReportSelectMenu());
		

	}

}
