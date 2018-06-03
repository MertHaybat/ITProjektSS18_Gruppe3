package de.hdm.itprojektss18Gruppe3.server.report;

import java.text.SimpleDateFormat;
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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));
		
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
	public AlleKontakteByTeilhaberschaftReport createAlleKontakteByTeilhaberschaftReport(String a, String b)
			throws IllegalArgumentException {

		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		Nutzer nutzerA = findNutzerByMail(a);
		Nutzer nutzerB = findNutzerByMail(b);
		String alle = "Alle";
		Vector<Kontakt> alleKontakteByTeilhaberschaft = new Vector<Kontakt>();
		AlleKontakteByTeilhaberschaftReport result = new AlleKontakteByTeilhaberschaftReport();

		result.setTitle("Alle Kontakte der Teilhaberschaften zwischen zwei Nutzern");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

		Row headline = new Row();
		headline.addColumn(new Column("Kontaktname"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifkationsdatum"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Ersteller"));

		result.addRow(headline);
		
		if(b.equals(alle)){
			alleKontakteByTeilhaberschaft= this.getKontaktVerwaltung().findAllKontakteByEigentuemerID(nutzerA.getId());
		} else {
			alleKontakteByTeilhaberschaft = this.getKontaktVerwaltung()
					.findAllKontaktByTeilhaberschaften(nutzerA.getId(), nutzerB.getId());
		}

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
			String eig, String auspraegung) throws IllegalArgumentException {

		if (this.getKontaktVerwaltung() == null) {
			return null;
		}
		
		Eigenschaft eigenschaft = findEigenschaftByBezeichnung(eig);
		Eigenschaftsauspraegung ea = new Eigenschaftsauspraegung();
		ea.setWert(auspraegung);
		

		KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport result = new KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport();

		result.setTitle("Kontakte mit angegebenen Eigenschaften und Ausprägungen");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		result.setCreated(simpleDateFormat.format(new Date()));

		Row headline = new Row();
		headline.addColumn(new Column("Kontaktname"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifkationsdatum"));
		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Ersteller"));

		result.addRow(headline);
		Vector<Kontakt> kontakteMitBestimmtenEigenschaftenUndAuspraegungen = this.getKontaktVerwaltung()
				.findAllKontakteByEigenschaftUndEigenschaftsauspraegungen(eigenschaft, ea);

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


	@Override
	public Vector<Nutzer> findNutzer() throws IllegalArgumentException {

		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		return this.getKontaktVerwaltung().findAllNutzer();
	}

	@Override
	public Nutzer findNutzerByMail(String email) throws IllegalArgumentException {
		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		return this.getKontaktVerwaltung().checkEmail(email);
	}

	@Override
	public Vector<Eigenschaft> findAllEigenschaften() throws IllegalArgumentException {
		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		return this.getKontaktVerwaltung().findAllEigenschaften();
	}

	@Override
	public Eigenschaft findEigenschaftByBezeichnung(String bezeichnung) throws IllegalArgumentException {
		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		Eigenschaft e = new Eigenschaft();
		e.setBezeichnung(bezeichnung);

		return this.getKontaktVerwaltung().findEigenschaftByBezeichnung(bezeichnung);
	}
}
