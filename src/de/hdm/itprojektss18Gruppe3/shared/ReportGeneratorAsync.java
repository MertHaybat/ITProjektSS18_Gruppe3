package de.hdm.itprojektss18Gruppe3.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.client.NutzerTeilhaberschaftKontaktWrapper;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteByTeilhaberschaftReport;
import de.hdm.itprojektss18Gruppe3.shared.report.AlleKontakteReport;
import de.hdm.itprojektss18Gruppe3.shared.report.KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport;

public interface ReportGeneratorAsync {

	void init(AsyncCallback<Void> callback);

	void createAlleKontakteReport(Nutzer nutzer, AsyncCallback<AlleKontakteReport> callback);

	void createAlleKontakteByTeilhaberschaftReport(String a, String b,
			AsyncCallback<AlleKontakteByTeilhaberschaftReport> callback);

	void createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(String nutzer, String eig, String auspraegung,
			AsyncCallback<KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport> callback);

	void findNutzer(AsyncCallback<Vector<Nutzer>> callback);

	void findNutzerByMail(String email, AsyncCallback<Nutzer> callback);

	void findAllEigenschaften(AsyncCallback<Vector<Eigenschaft>> callback);

	void findEigenschaftByBezeichnung(String bezeichnung, AsyncCallback<Eigenschaft> callback);

	void findEigenschaftWrapper(Kontakt kontakt, AsyncCallback<Vector<EigenschaftsAuspraegungWrapper>> callback);

	void findAllKontakteAndTeilhaberschaftenByNutzer(Nutzer nutzer, AsyncCallback<Vector<NutzerTeilhaberschaftKontaktWrapper>> callback);

	void nutzerTeilhaberschaft(int teilhaberid, AsyncCallback<Nutzer> callback);


}