package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.bo.*;
import de.hdm.itprojektss18Gruppe3.shared.*;

/**
 * Die Klasse "Kontaktformular" beinhaltet einige festgelegten Textboxen, die mit Eigenschaften bezeichnet sind und durch deren Eintrag
 * erhalten die Eigenschaften ihre jeweiligen Eigenschaftsausprägungen. Des Weiteren lässt die TextArea einen Hinweis-Text zu für einen neuen Kontakt.
 * Damit ermöglicht die Klasse "Kontaktformular" das erstellen von einem Objekt "Kontakt" mit den dazugehörigen Eigenschaften und Eigenschaftsausprägungen.
 * 
 * @version 1.0 18 May 2018
 * @author wahidvanaki
 *
 */
public class Kontaktformular extends VerticalPanel {
	
	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();
	
	/**
	 * Erstellen der Platzhalter für das Kontaktformular
	 * Variable vorname, nachname, geburtsdatum, nummer und email,
	 * weitere eigenschaften können bei bedarf hinzugefügt werden durch den Button "hinzufügen +"
	 */
	private TextBox tvorname = new TextBox();
	private TextBox tnachname = new TextBox();
	private DateBox dgeburtsdatum = new DateBox();
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
	private Label lb4 = new Label("Telefonnummer: ");
	private Label lb5 = new Label("E-Mail: ");
	private Label lb6 = new Label("Status: ");
	private Label lb7 = new Label("Hinweis hinzufügen: ");
	
	/**
	 * Die Buttons ermöglichen das speichern, abbrechen und löschen
	 */
	private Button bspeichern = new Button("Speichern");
	private Button babbrechen = new Button("Abbrechen");
	private Button bloeschen = new Button("Löschen");
	private Button badd = new Button("Hinzufügen +");
	
	private Kontakt kontakt = new Kontakt();
	
	public Kontaktformular(){
	
		/**
		 * Die Anordnung der Textboxen festlegen und dazu deren Bezeichnungen.
		 */
		Grid kontaktWidget = new Grid(11,7);
		
		kontaktWidget.setWidget(2, 0, lb1);
		kontaktWidget.setWidget(2, 1, tvorname);
		kontaktWidget.setWidget(3, 0, lb2);
		kontaktWidget.setWidget(3, 1, tnachname);
		kontaktWidget.setWidget(4, 0, lb3);
		kontaktWidget.setWidget(4, 1, dgeburtsdatum);
		kontaktWidget.setWidget(5, 0, lb4);
		kontaktWidget.setWidget(5, 1, tnummer);
		kontaktWidget.setWidget(6, 0, lb5);
		kontaktWidget.setWidget(6, 1, temail);
		kontaktWidget.setWidget(7, 0, lb6);
		kontaktWidget.setWidget(7, 1, tstatus);
		kontaktWidget.setWidget(9, 0, badd);
		kontaktWidget.setWidget(9, 2, bspeichern);
		kontaktWidget.setWidget(9, 3, bloeschen);
		kontaktWidget.setWidget(9, 4, babbrechen);
		kontaktWidget.setWidget(0, 6, lb7);
		kontaktWidget.setWidget(1, 6, thinweistext);
		
		thinweistext.setWidth("600px");
		thinweistext.setHeight("100px");
		
		this.add(kontaktWidget);
		
		dgeburtsdatum.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd-MM-yyyy")));
		dgeburtsdatum.getDatePicker().setYearArrowsVisible(true);
		dgeburtsdatum.getDatePicker().setYearAndMonthDropdownVisible(true);
		dgeburtsdatum.getDatePicker().setVisibleYearCount(100);
	}
}