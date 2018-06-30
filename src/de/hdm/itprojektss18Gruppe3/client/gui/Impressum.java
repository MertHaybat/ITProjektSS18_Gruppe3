package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojektss18Gruppe3.client.MainFrame;

/**
 * 
 * HTML Seite von Impressum wird angezeigt
 *
 */
public class Impressum extends PopupPanel {

	private PopupPanel pp = new PopupPanel();
	private Button hide = new Button("Ausblenden");
	private VerticalPanel vpanel = new VerticalPanel();
	private FlexTable ft1 = new FlexTable();
	private Anchor an1 = new Anchor("Text");
	private HTML html1 = new HTML(

			"<div class='Impressum'>" + "Hochschule der Medien" + "</br>"
					+ "<b>Wirtschaftsinformatik und Digitale Medien</b></br>" + "Nobelstrasse 10</br>"
					+ "70569 Stuttgart</br></br>" + "Kontakt</br>Telefon: +49 711 8923 10</br>"
					+ "<br><br>Dieses Projekt wurde realisiert durch:<br><ul><li>Thomas Burkhardt</li>"
					+ "<li>Ersin Barut</li><li>Giuseppe Galati</li><li>Mert Kenan Haybat</li><li>Kevin Hofmann</li><li>Wahid Vanaki</li></ul>"
					+ "<br>Der Studiengang Wirtschaftsinformatik und digitale "
					+ "Medien<br>wird rechtlich vertreten durch die Hochschule der Med"
					+ "ien Stuttgart. <br> <br><A HREF=\"https://www.hdm-stuttgart.de/"
					+ "impressum\"TARGET=\"_blank\">Impressum der Hochschule</A>" + "</div>");
	

	public Impressum() {
		super(true);
		pp.setGlassEnabled(true);
		run();
	}
	
	protected void run() {
		hide.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pp.hide();
			}
		});
		
		ft1.setWidget(0, 0, an1);
		ft1.setWidget(0, 0, html1);
		
		vpanel.add(ft1);
		vpanel.add(new HTML("<br>"));
		vpanel.add(hide);
		pp.add(vpanel);	
		pp.center();
	}
	
	public FlexTable getImpressum(){
		return this.ft1;
	}
}
