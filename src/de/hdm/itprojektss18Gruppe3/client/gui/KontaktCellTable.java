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
import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

public class KontaktCellTable extends CellTable<EigenschaftsAuspraegungWrapper> {

	private static KontaktmanagerAdministrationAsync kontaktmanagerVerwaltung = ClientsideSettings.getKontaktVerwaltung();

	public KontaktCellTable(final Kontakt k) {
		
		this.setStylePrimaryName("auspraegungCellTable");
		
		
		Column<EigenschaftsAuspraegungWrapper, String> wertEigenschaft = new Column<EigenschaftsAuspraegungWrapper, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(EigenschaftsAuspraegungWrapper object) {
				return object.getBezeichnungEigenschaftValue();
			}
		};
		
		Column<EigenschaftsAuspraegungWrapper, String> wertAuspraegung = new Column<EigenschaftsAuspraegungWrapper, String>(
				new ClickableTextCell()) {

			@Override
			public String getValue(EigenschaftsAuspraegungWrapper object) {
				return object.getWertEigenschaftsauspraegungValue();
			}
		};
		
		

//		kverwaltung.findEigenschaftHybrid(k, new AllAuspraegungenCallback());
		this.addColumn(wertEigenschaft, "");
		this.addColumn(wertAuspraegung, "");

	}


}
	


