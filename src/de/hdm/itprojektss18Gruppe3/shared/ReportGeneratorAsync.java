package de.hdm.itprojektss18Gruppe3.shared;

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

	void createAlleKontakteByTeilhaberschaftReport(Nutzer a, Nutzer b, AsyncCallback<AlleKontakteByTeilhaberschaftReport> callback);

	void createKontakteMitBestimmtenEigenschaftenUndAuspraegungenReport(
			Eigenschaft eig, Eigenschaftsauspraegung ea,
			AsyncCallback<KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport> callback);

}