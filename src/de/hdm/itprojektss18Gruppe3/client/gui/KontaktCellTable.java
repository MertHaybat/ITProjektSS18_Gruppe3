package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ImageBundle.Resource;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.EigenschaftsAuspraegungHybrid;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

public class KontaktCellTable extends CellTable<EigenschaftsAuspraegungHybrid> {

	private static KontaktmanagerAdministrationAsync kverwaltung = ClientsideSettings.getKontaktVerwaltung();

	public KontaktCellTable(final Kontakt k) {
		
		this.setStylePrimaryName("auspraegungCellTable");
		
		
		Column<EigenschaftsAuspraegungHybrid, String> wertEigenschaft = new Column<EigenschaftsAuspraegungHybrid, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(EigenschaftsAuspraegungHybrid object) {
				return object.getEigenschaft();
			}
		};
		
		Column<EigenschaftsAuspraegungHybrid, String> wertAuspraegung = new Column<EigenschaftsAuspraegungHybrid, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(EigenschaftsAuspraegungHybrid object) {
				return object.getAuspraegung();
			}
		};
		
		Column<EigenschaftsAuspraegungHybrid, String> wertStatus = new Column<EigenschaftsAuspraegungHybrid, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(EigenschaftsAuspraegungHybrid object) {
				return String.valueOf(object.getStatusAuspraegung());
			}
		};

		kverwaltung.findEigenschaftHybrid(k, new AllAuspraegungenCallback());
		this.addColumn(wertEigenschaft, "");
		this.addColumn(wertAuspraegung, "");
		this.addColumn(wertStatus, "");

	}

	class AllAuspraegungenCallback implements AsyncCallback<Vector<EigenschaftsAuspraegungHybrid>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<EigenschaftsAuspraegungHybrid> result) {
			// TODO
			setRowData(0, result);
			setRowCount(result.size(), true);
			

		}
	}
}
	


