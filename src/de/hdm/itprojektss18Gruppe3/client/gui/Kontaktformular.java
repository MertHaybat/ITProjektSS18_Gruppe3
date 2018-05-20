package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.bo.*;
import de.hdm.itprojektss18Gruppe3.shared.*;

/**
 * Die Klasse "Kontaktform" beinhaltet einige festgelegten Textboxen, die mit Eigenschaften bezeichnet sind und durch deren Eintrag
 * erhalten die Eigenschaften ihre jeweiligen Eigenschaftsausprägungen. Des Weiteren lässt die TextArea einen Hinweis-Text zu für einen neuen Kontakt.
 * Damit ermöglicht die Klasse "Kontaktform" das erstellen von einem Objekt "Kontakt" mit den dazugehörigen Eigenschaften und Eigenschaftsausprägungen.
 * 
 * @version 1.10 18 May 2018
 * @author wahidvanaki
 *
 */
public class Kontaktformular extends VerticalPanel {
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	/**
	 * Erstellen der Platzhalter für das Kontaktformular
	 * Variable vorname, nachname, geburtsdatum, nummer und email,
	 * weitere eigenschaften können später hinzugefügt werden
	 */
	private TextBox tvorname = new TextBox();
	private TextBox tnachname = new TextBox();
	private DateBox dgeburtsdatum = new DateBox();
	private DateBox derzeugungsdatum = new DateBox();
	private DateBox dmodifikationsdatum = new DateBox();
	private TextBox tnummer = new TextBox();
	private TextBox temail = new TextBox();
	private TextBox tstatus = new TextBox();
	private TextArea thinweistext = new TextArea();
	
	/**
	 * Die Labels beschreiben die Textboxen, also beziehen sich auf die Eigenschaften.
	 */
	private Label lb1 = new Label("Vorname: ");
	private Label lb2 = new Label("Nachname: ");
	private Label lb3 = new Label("Geburtsdatum: ");
	private Label lb4 = new Label("Erzeugungsdatum: ");
	private Label lb5 = new Label("Modifikationsdatum: ");
	private Label lb6 = new Label("Telefonnummer: ");
	private Label lb7 = new Label("E-Mail: ");
	private Label lb8 = new Label("Status: ");
	
	/**
	 * Die Buttons ermöglichen das speichern, abbrechen und löschen
	 */
	private Button bspeichern = new Button("Speichern");
	private Button babbrechen = new Button("Abbrechen");
	private Button bloeschen = new Button("Löschen");
	
	private Kontakt kontakt = new Kontakt();
	
	public Kontaktformular(){
		
		/**
		 * Die Anordnung der Textboxen festlegen und dazu deren Bezeichnungen.
		 */
		Grid kontaktWidget = new Grid(11,10);
		Grid hinweisWidget = new Grid(4,4);
		
		kontaktWidget.setWidget(1, 0, lb1);
		kontaktWidget.setWidget(1, 1, tvorname);
		kontaktWidget.setWidget(2, 0, lb2);
		kontaktWidget.setWidget(2, 1, tnachname);
		kontaktWidget.setWidget(3, 0, lb3);
		kontaktWidget.setWidget(3, 1, dgeburtsdatum);
		kontaktWidget.setWidget(4, 0, lb4);
		kontaktWidget.setWidget(4, 1, derzeugungsdatum);
		kontaktWidget.setWidget(5, 0, lb5);
		kontaktWidget.setWidget(5, 1, dmodifikationsdatum);
		kontaktWidget.setWidget(6, 0, lb6);
		kontaktWidget.setWidget(6, 1, tnummer);
		kontaktWidget.setWidget(7, 0, lb7);
		kontaktWidget.setWidget(7, 1, temail);
		kontaktWidget.setWidget(8, 0, lb8);
		kontaktWidget.setWidget(8, 1, tstatus);
		kontaktWidget.setWidget(10, 0, bspeichern);
//		kontaktWidget.setWidget(10, 1, bloeschen);
//		kontaktWidget.setWidget(10, 2, babbrechen);
		
		hinweisWidget.setWidget(1, 0, thinweistext);

		this.add(kontaktWidget);
		
		dgeburtsdatum.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd-MM-yyyy")));
		dgeburtsdatum.getDatePicker().setYearArrowsVisible(true);
		dgeburtsdatum.getDatePicker().setYearAndMonthDropdownVisible(true);
		dgeburtsdatum.getDatePicker().setVisibleYearCount(100);
	}
}