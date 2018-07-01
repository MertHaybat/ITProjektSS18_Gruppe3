package de.hdm.itprojektss18Gruppe3.server.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
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
import de.hdm.itprojektss18Gruppe3.shared.report.CompositeParagraph;
import de.hdm.itprojektss18Gruppe3.shared.report.KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport;
import de.hdm.itprojektss18Gruppe3.shared.report.Report;
import de.hdm.itprojektss18Gruppe3.shared.report.Row;
import de.hdm.itprojektss18Gruppe3.shared.report.SimpleParagraph;

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
	private SimpleDateFormat dtf = new SimpleDateFormat("dd.MM.yyyy 'um' HH:mm");
	
	/**
	 * Ein No-Argument-Konstruktor für die Client-seitige Erzeugung von
	 * GWT.create
	 * 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	public ReportGeneratorImpl() throws IllegalArgumentException{
	}
	

	/**
	 * Initialisierungsmethode. Diese Methode muss für jede Instanz von
	 * ReportGeneratorImpl aufgerufen werden.
	 * 
	 * @see ReportGeneratorImpl()
	 */
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
	  
	  protected void addImprint(Report r) {
		  
		    /*
		     * Das Imressum soll mehrzeilig sein.
		     */
		    CompositeParagraph imprint = new CompositeParagraph();

		    imprint.addSubParagraph(new SimpleParagraph("Kontaktmanager der HdM"));
		    imprint.addSubParagraph(new SimpleParagraph("Nobelstraße 10"));
		    imprint.addSubParagraph(new SimpleParagraph("70569 Stuttgart"));

		    // Das eigentliche Hinzufügen des Impressums zum Report.
		    r.setImprint(imprint);

		  }
	
	
	/**
	 * Erstellen von <code>AlleKontakteReport</code>-Objekten
	 * 
	 * @param lbEmail;
	 * 				die Email des Kontakts, zu dem alle Kontakte ausgegeben werden 
	 * 				sollen.
	 * @return AlleKontakteReport-Objekt, der Report, der alle Kontakte
	 * 				ausgibt.
	 */
	@Override
	public AlleKontakteReport createAlleKontakteReport(Nutzer nutzer) throws IllegalArgumentException {
		if(this.getKontaktVerwaltung() == null){
			return null;
		}
		else{
			
		
		Vector<NutzerTeilhaberschaftKontaktWrapper> allContacts = new Vector<NutzerTeilhaberschaftKontaktWrapper>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		
		AlleKontakteReport result = new AlleKontakteReport();
		result.setTitle("Alle Kontakte im Kontaktmanager");
		this.addImprint(result);
		
		result.setCreated(simpleDateFormat.format(new Date()));
		
		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Nutzer: " + nutzer.getMail()));
		
		result.setHeaderData(header);
		
		Row headline = new Row();
		headline.addColumn(new Column("Kontaktname"));
//		headline.addColumn(new Column("Status"));
//		headline.addColumn(new Column("Nutzer"));
		headline.addColumn(new Column("Empfangen von"));
		headline.addColumn(new Column("Geteilt mit"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifkationsdatum"));
		
		result.addRow(headline);
//		if (lbEmail == "Alle") {
//		allContacts = this.getKontaktVerwaltung().findAllKontakte();
//		}
//		else {
//			
//			Nutzer nutzer = this.getKontaktVerwaltung().checkEmail(lbEmail);
//			allContacts =	this.getKontaktVerwaltung().findAllKontaktByNutzerID(nutzer.getId());
//		}
		allContacts = this.getKontaktVerwaltung().findAllKontakteAndTeilhaberschaftenByNutzer(nutzer);
		
		for (NutzerTeilhaberschaftKontaktWrapper kontakt : allContacts) {
			Row kontakte = new Row();
			
			
			if(kontakt.getNutzer()== null && kontakt.getTeilhaberschaft() == null && kontakt.getKontakt().getStatus() == 0){
				kontakte.addColumn(new Column(kontakt.getKontakt().getName()));
//				kontakte.addColumn(new Column("Nicht Geteilt"));
				kontakte.addColumn(new Column(""));
				kontakte.addColumn(new Column(""));
				kontakte.addColumn(new Column(dtf.format(kontakt.getKontakt().getErzeugungsdatum()).toString()));
				kontakte.addColumn(new Column(dtf.format(kontakt.getKontakt().getModifikationsdatum()).toString()));
				
				result.addRow(kontakte);
				
			} else if(kontakt.getNutzer().getId() == kontakt.getTeilhaberschaft().getEigentuemerID() && kontakt.getTeilhaberschaft() != null && kontakt.getKontakt() != null ){
				kontakte.addColumn(new Column(kontakt.getKontakt().getName()));
//				kontakte.addColumn(new Column("Empfangen"));
				kontakte.addColumn(new Column(kontakt.getNutzer().getMail()));
				kontakte.addColumn(new Column(""));
				kontakte.addColumn(new Column(dtf.format(kontakt.getKontakt().getErzeugungsdatum()).toString()));
				kontakte.addColumn(new Column(dtf.format(kontakt.getKontakt().getModifikationsdatum()).toString()));
				
				result.addRow(kontakte);
				
			} else if(kontakt.getTeilhaberschaft().getEigentuemerID() == nutzer.getId() && kontakt.getTeilhaberschaft() != null && kontakt.getKontakt() != null ){				
			
				kontakte.addColumn(new Column(kontakt.getKontakt().getName()));
//				kontakte.addColumn(new Column("Geteilt"));
				kontakte.addColumn(new Column(""));
				kontakte.addColumn(new Column(kontakt.getNutzer().getMail()));
				kontakte.addColumn(new Column(dtf.format(kontakt.getKontakt().getErzeugungsdatum()).toString()));
				kontakte.addColumn(new Column(dtf.format(kontakt.getKontakt().getModifikationsdatum()).toString()));
				
				result.addRow(kontakte);
			}

			
			
//			if(kontakt.getStatus() == 1){
//				kontakte.addColumn(new Column("Geteilt"));
//			} else if (kontakt.getStatus() == 0){	
//			}
//			kontakte.addColumn(new Column(this.getKontaktVerwaltung().findNutzerByID(kontakt.getNutzerID()).getMail()));
			
		}
		
		return result;
	}}

	/**
	 * Erstellen von <code>AlleKontakteByTeilhaberschaftReport</code>-Objekten
	 * 
	 * @param a, b; 
	 * 			die Emailadressen der Nutzer, zu denen alle miteinander geteilten 
	 * 			Kontakte ausgegeben	werden sollen.
	 * @return AlleKontakteByTeilhaberschaftReport-Objekt, der Report, der alle,
	 * 			zwischen den beiden geteilten, Kontakte ausgibt.
	 */
	@Override
	public AlleKontakteByTeilhaberschaftReport createAlleKontakteByTeilhaberschaftReport(String eigentuemer, String teilnehmer)
			throws IllegalArgumentException {

		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		Nutzer nutzerEigentuemer = findNutzerByMail(eigentuemer);
		Nutzer nutzerTeilnehmer = findNutzerByMail(teilnehmer);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
		
		Vector<Kontakt> alleKontakteByTeilhaberschaft = new Vector<Kontakt>();
		AlleKontakteByTeilhaberschaftReport result = new AlleKontakteByTeilhaberschaftReport();
		result.setTitle("Alle Kontakte der Teilhaberschaften zwischen zwei Nutzern");
		this.addImprint(result);
		
		result.setCreated(simpleDateFormat.format(new Date()));
		
		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Nutzer: " + eigentuemer));

		result.setHeaderData(header);	
		
		Row headline = new Row();
		headline.addColumn(new Column("Kontaktname"));
//		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Ersteller"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifkationsdatum"));

		result.addRow(headline);
		
		if(teilnehmer.equals("")){
			alleKontakteByTeilhaberschaft= this.getKontaktVerwaltung().findAllKontakteByEigentuemerID(nutzerEigentuemer.getId());
		} else {
			alleKontakteByTeilhaberschaft = this.getKontaktVerwaltung()
					.findKontaktTeilhaberschaftByEigentuemerAndTeilhaber(nutzerEigentuemer.getId(), nutzerTeilnehmer.getId());
					
		}

		for (Kontakt kontakt : alleKontakteByTeilhaberschaft) {
			Row kontakte = new Row();
			kontakte.addColumn(new Column(kontakt.getName()));
//			if (kontakt.getStatus() == 1) {
//				kontakte.addColumn(new Column("Geteilt"));
//			} else if (kontakt.getStatus() == 0) {
//				kontakte.addColumn(new Column("Nicht Geteilt"));
//			}
			kontakte.addColumn(new Column(this.getKontaktVerwaltung().findNutzerByID(kontakt.getNutzerID()).getMail()));
			kontakte.addColumn(new Column(dtf.format(kontakt.getErzeugungsdatum()).toString()));
			kontakte.addColumn(new Column(dtf.format(kontakt.getModifikationsdatum()).toString()));
			result.addRow(kontakte);
		}
		return result;
	}

	/**
	 * Erstellen von <code>KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport</code>-Objekten
	 * 
	 * @param eig;
	 * 			die Eigenschaft über die gesucht werden soll 
	 * 		  auspraegung;
	 * 			die Eigenschaftsausprägung nach der gesucht werden soll
	 * 
	 * @return KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport, der Report,
	 * 			der alle Kontakte mit den Eigenschaften und Eigenschaftsausprägungen ausgibt,
	 * 			die in der Suche angegeben wurden.
	 */
	@Override
	public KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(
			String nutzer, String eig, String auspraegung) throws IllegalArgumentException {

		if (this.getKontaktVerwaltung() == null) {
			return null;
		}
		
		Nutzer loggedinNutzer = findNutzerByMail(nutzer);
		Eigenschaft eigenschaft = findEigenschaftByBezeichnung(eig);
		Eigenschaftsauspraegung ea = new Eigenschaftsauspraegung();
		ea.setWert(auspraegung);
		
		KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport result = new KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");

		result.setTitle("Kontakte mit angegebenen Eigenschaften und Ausprägungen");
		this.addImprint(result);
		
		result.setCreated(simpleDateFormat.format(new Date()));
		
		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph("Nutzer: " + nutzer));
		result.setHeaderData(header);
		

		Row headline = new Row();
		headline.addColumn(new Column("Kontaktname"));
//		headline.addColumn(new Column("Status"));
		headline.addColumn(new Column("Ersteller"));
		headline.addColumn(new Column("Eigenschaften"));
		headline.addColumn(new Column("Erzeugungsdatum"));
		headline.addColumn(new Column("Modifkationsdatum"));
//		for (Eigenschaft eigenschaft2 : eigenschaften) {
//			headline.addColumn(new Column(eigenschaft2.getBezeichnung()));
//		}
		
		result.addRow(headline);
		
		Vector<Kontakt> kontakteMitBestimmtenEigenschaftenUndAuspraegungen = this.getKontaktVerwaltung()
					.findAllKontakteEigenschaftAuspraegung(loggedinNutzer, eigenschaft, ea);	
		Vector<EigenschaftsAuspraegungWrapper> auspraegungen = new Vector<EigenschaftsAuspraegungWrapper>();
		
		for (Kontakt kontakt : kontakteMitBestimmtenEigenschaftenUndAuspraegungen) {
			Row kontakte = new Row();
			kontakte.addColumn(new Column(kontakt.getName()));
			
			auspraegungen = this.getKontaktVerwaltung().findEigenschaftHybrid(kontakt);
			
			
//			if (kontakt.getStatus() == 1) {
//				kontakte.addColumn(new Column("Geteilt"));
//			} else if (kontakt.getStatus() == 0) {
//				kontakte.addColumn(new Column("Nicht Geteilt"));
//			}
			kontakte.addColumn(new Column(this.getKontaktVerwaltung().findNutzerByID(kontakt.getNutzerID()).getMail()));
			
			kontakte.addColumn(new Column(auspraegungen.toString().replace("[", "").replace("]", "").replace(",", "")));
			kontakte.addColumn(new Column(dtf.format(kontakt.getErzeugungsdatum()).toString()));
			kontakte.addColumn(new Column(dtf.format(kontakt.getModifikationsdatum()).toString()));
			
			
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
	
	@Override
	public Vector<EigenschaftsAuspraegungWrapper> findEigenschaftWrapper(Kontakt p)throws IllegalArgumentException {
		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		return this.getKontaktVerwaltung().findEigenschaftHybrid(p);
	}
	@Override
	public Vector<NutzerTeilhaberschaftKontaktWrapper> findAllKontakteAndTeilhaberschaftenByNutzer(Nutzer nutzer)throws IllegalArgumentException {
		if (this.getKontaktVerwaltung() == null) {
			return null;
		}

		return this.getKontaktVerwaltung().findAllKontakteAndTeilhaberschaftenByNutzer(nutzer);
	}
	
	@Override
	public Nutzer nutzerTeilhaberschaft(int teilhaberid) throws IllegalArgumentException {
		if (this.getKontaktVerwaltung() == null) {
			return null;
		}
		
		
		return this.getKontaktVerwaltung().findNutzerByID(teilhaberid);
		
	}
	
}
