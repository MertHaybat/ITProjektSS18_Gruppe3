package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.MainFrame;

/**
 * 
 * HTML Seite von Impressum wird angezeigt
 *
 */
public class Impressum extends MainFrame {

	private VerticalPanel vpanel = new VerticalPanel();
	private FlexTable ft1 = new FlexTable();
	private Anchor an1 = new Anchor("Text");
	private HTML html1 = new HTML(

			"<div class='Impressum'>" + "Hochschule der Medien" + "</br>"
					+ "<b>Wirtschaftsinformatik und Digitale Medien</b></br>" + "Nobelstrasse 10</br>"
					+ "70569 Stuttgart</br></br>" + "Kontakt</br>Telefon: +49 711 8923 10</br>"
					+ "<br><br>Der Studiengang Wirtschaftsinformatik und digitale "
					+ "Medien<br>wird rechtlich vertreten durch die Hochschule der Med"
					+ "ien Stuttgart. <br> <br><A HREF=\"https://www.hdm-stuttgart.de/"
					+ "impressum\"TARGET=\"_blank\">Impressum der Hochschule</A>" + "</div>");
	

	@Override
	protected void run() {
		ft1.setWidget(0, 0, an1);
		ft1.setWidget(0, 0, html1);
		
		vpanel.add(ft1);
		this.add(vpanel);	
	}
	
	public FlexTable getImpressum(){
		return this.ft1;
	}
}
