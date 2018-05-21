package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteByTeilhaberschaftReport;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18Gruppe3.shared.report.KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);

	void createAlleKontakteReport(AsyncCallback<AlleKontakteReport> callback);

	void createAlleKontakteByTeilhaberschaftReport(String a, String b,
			AsyncCallback<AlleKontakteByTeilhaberschaftReport> callback);

	void createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(String eig, String auspraegung,
			AsyncCallback<KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport> callback);

	void findNutzer(AsyncCallback<Vector<Nutzer>> callback);

	void findNutzerByMail(String email, AsyncCallback<Nutzer> callback);

	void findAllEigenschaften(AsyncCallback<Vector<Eigenschaft>> callback);

	void findEigenschaftByBezeichnung(String bezeichnung, AsyncCallback<Eigenschaft> callback);

}