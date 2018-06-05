package de.hdm.itprojektss18Gruppe3.client.gui;


import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.itprojektss18Gruppe3.client.MainFrame;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

/**
 * 
 * @author ersinbarut
 *
 */

public class TeilhaberschaftKontakte extends MainFrame {


	private Kontakt kontakt = new Kontakt();

	protected void run() {


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
	public class ShowKontaktClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			KontaktForm kForm = new KontaktForm(kontakt);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(kForm);
		}

	}
	
}
