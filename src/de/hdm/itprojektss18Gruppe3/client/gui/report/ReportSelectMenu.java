package de.hdm.itprojektss18Gruppe3.client.gui.report;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.AllKontakte;

public class ReportSelectMenu extends VerticalPanel{

	private Button bt1 = new Button("Report 1");
	private Button bt2 = new Button("Report 2");
	private Button bt3 = new Button("Report 3");

	public ReportSelectMenu() {
		bt1.setStylePrimaryName("reportmenubutton");
		bt2.setStylePrimaryName("reportmenubutton");
		bt3.setStylePrimaryName("reportmenubutton");

		bt1.addClickHandler(new reportEinsClickHandler());

		bt2.addClickHandler(new reportZweiClickHandler());

		bt3.addClickHandler(new reportDreiClickHandler());

		this.add(bt1);
		this.add(bt2);
		this.add(bt3);

	}

	class reportEinsClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontakteReportForm allKontakteReport = new KontakteReportForm();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(allKontakteReport);
		}

	}

	class reportZweiClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			TeilnahmenReportForm teilnahmenReportForm = new TeilnahmenReportForm();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(teilnahmenReportForm);
		}

	}

	class reportDreiClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			EigenschaftenReportForm eigenschaftenReportForm = new EigenschaftenReportForm();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(eigenschaftenReportForm);
		}

	}
}
