package de.hdm.itprojektss18Gruppe3.server.report;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss18Gruppe3.server.KontaktmanagerAdministrationImpl;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministration;
import de.hdm.itprojektss18Gruppe3.shared.ReportGenerator;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteByTeilhaberschaftReport;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18Gruppe3.shared.report.Column;
import de.hdm.itprojektss18Gruppe3.shared.report.KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport;
import de.hdm.itprojektss18Gruppe3.shared.report.Row;

/**
 * 
 * @see ReportGenerator
 * @author Thomas - In Anlehnung an das BankProjekt
 */
@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator{

	/**
	   * Ein ReportGenerator muss auf die Klasse KontaktmanagerAdministration zugreifen können,
	   * da diese den Zugriff auf die BusinessObjects hat.
	   */
	private KontaktmanagerAdministration kontaktmanagerAdministration = null;
	
	
	public ReportGeneratorImpl() throws IllegalArgumentException{
	}
	

	@Override
	public void init() throws IllegalArgumentException {
		/*
	     * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
	     * KontaktmanagerAdministrationImpl-Instanz.
	     */
		KontaktmanagerAdministrationImpl k = new KontaktmanagerAdministrationImpl();
		k.init();
		this.kontaktmanagerAdministration = k;
	}
	

	  protected KontaktmanagerAdministration getKontaktVerwaltung() {
	    return this.kontaktmanagerAdministration;
	  }
	
	@Override
	public AlleKontakteReport createAlleKontakteReport() throws IllegalArgumentException {
		if(this.getKontaktVerwaltung() == null){
			return null;
		}
		
		AlleKontakteReport result = new AlleKontakteReport();
		
		result.setTitle("Alle Kontakte im Kontaktmanager");
		
		result.setCreated(new Date());
		
		Row headline = new Row();
		headline.addColumn(new Column("Kontaktname"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifkationsdatum"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Ersteller"));
		
		result.addRow(headline);
		Vector<Kontakt> allContacts = this.getKontaktVerwaltung().findAllKontakte();
		
		for (Kontakt kontakt : allContacts) {
			Row kontakte = new Row();
			kontakte.addColumn(new Column(kontakt.getName()));
			kontakte.addColumn(new Column(kontakt.getErzeugungsdatum().toString()));
			kontakte.addColumn(new Column(kontakt.getModifikationsdatum().toString()));
			if(kontakt.getStatus() == 1){
				kontakte.addColumn(new Column("Geteilt"));
			} else if (kontakt.getStatus() == 0){	
				kontakte.addColumn(new Column("Nicht Geteilt"));
			}
			kontakte.addColumn(new Column(this.getKontaktVerwaltung().findNutzerByID(kontakt.getNutzerID()).getMail()));
			result.addRow(kontakte);
			
		}
		
		return result;
	}

	@Override
	public AlleKontakteByTeilhaberschaftReport createAlleKontakteByTeilhaberschaftReport(Nutzer a, Nutzer b)
			throws IllegalArgumentException {

		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		AlleKontakteByTeilhaberschaftReport result = new AlleKontakteByTeilhaberschaftReport();

		result.setTitle("Alle Kontakte der Teilhaberschaften zwischen zwei Nutzern");

		result.setCreated(new Date());

		Row headline = new Row();
		headline.addColumn(new Column("Kontaktname"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifkationsdatum"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Ersteller"));

		result.addRow(headline);

		Vector<Kontakt> alleKontakteByTeilhaberschaft = this.getKontaktVerwaltung()
				.findAllKontaktByTeilhaberschaften(a.getId(), b.getId());

		for (Kontakt kontakt : alleKontakteByTeilhaberschaft) {
			Row kontakte = new Row();
			kontakte.addColumn(new Column(kontakt.getName()));
			kontakte.addColumn(new Column(kontakt.getErzeugungsdatum().toString()));
			kontakte.addColumn(new Column(kontakt.getModifikationsdatum().toString()));
			if (kontakt.getStatus() == 1) {
				kontakte.addColumn(new Column("Geteilt"));
			} else if (kontakt.getStatus() == 0) {
				kontakte.addColumn(new Column("Nicht Geteilt"));
			}
			kontakte.addColumn(new Column(this.getKontaktVerwaltung().findNutzerByID(kontakt.getNutzerID()).getMail()));
			result.addRow(kontakte);
		}
		return result;
	}

	@Override
	public KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(
			Eigenschaft eig, Eigenschaftsauspraegung ea) throws IllegalArgumentException {

		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport result = new KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport();

		result.setTitle("Kontakte mit angegebenen Eigenschaften und Ausprägungen");

		result.setCreated(new Date());

		Row headline = new Row();
		headline.addColumn(new Column("Kontaktname"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifkationsdatum"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Ersteller"));

		result.addRow(headline);
		Vector<Kontakt> kontakteMitBestimmtenEigenschaftenUndAuspraegungen = this.getKontaktVerwaltung()
				.findAllKontakteByEigenschaftUndEigenschaftsauspraegungen(eig, ea);

		for (Kontakt kontakt : kontakteMitBestimmtenEigenschaftenUndAuspraegungen) {
			Row kontakte = new Row();
			kontakte.addColumn(new Column(kontakt.getName()));
			kontakte.addColumn(new Column(kontakt.getErzeugungsdatum().toString()));
			kontakte.addColumn(new Column(kontakt.getModifikationsdatum().toString()));
			if (kontakt.getStatus() == 1) {
				kontakte.addColumn(new Column("Geteilt"));
			} else if (kontakt.getStatus() == 0) {
				kontakte.addColumn(new Column("Nicht Geteilt"));
			}
			kontakte.addColumn(new Column(this.getKontaktVerwaltung().findNutzerByID(kontakt.getNutzerID()).getMail()));
			result.addRow(kontakte);
		}
		return result;
	}
}
