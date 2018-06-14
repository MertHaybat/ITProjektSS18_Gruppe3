package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class CellTableAuspraegungWrapper extends CellTable<EigenschaftsAuspraegungWrapper> {
	
	private final MultiSelectionModel<EigenschaftsAuspraegungWrapper> ssmAuspraegung = new MultiSelectionModel<EigenschaftsAuspraegungWrapper>();

	public CellTableAuspraegungWrapper() {
		
		run();
	
		}
	
	
	public MultiSelectionModel<EigenschaftsAuspraegungWrapper> getSsmAuspraegung() {
		return ssmAuspraegung;
	}


	public void run(){
		this.setStylePrimaryName("auspraegungCellTable");
	}
	
	public class WertEigenschaftColumn extends Column<EigenschaftsAuspraegungWrapper, String>{

		public WertEigenschaftColumn(Cell<String> cell) {
			super(cell);
		}

		@Override
		public String getValue(EigenschaftsAuspraegungWrapper object) {
			return object.getBezeichnungEigenschaftValue();
		}
	}

	public class WertAuspraegungColumn extends Column<EigenschaftsAuspraegungWrapper, String>{

		public WertAuspraegungColumn(Cell<String> cell) {
			super(cell);
		}

		@Override
		public String getValue(EigenschaftsAuspraegungWrapper object) {
			return object.getAuspraegung().getWert();
		}
	}
	public class CheckBoxBolumn extends Column<EigenschaftsAuspraegungWrapper, Boolean>{

		public CheckBoxBolumn(Cell<Boolean> cell) {
			super(cell);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Boolean getValue(EigenschaftsAuspraegungWrapper object) {
			return ssmAuspraegung.isSelected(object);
		}
		
	}
	public class IconColumn extends TextColumn<EigenschaftsAuspraegungWrapper>{

		@Override
		public String getValue(EigenschaftsAuspraegungWrapper object) {
			// TODO Auto-generated method stub
			return "";
		}
		  @Override
	        public void render(Context context, EigenschaftsAuspraegungWrapper object, SafeHtmlBuilder sb) {
	        	// TODO Auto-generated method stub
	        	if(object.getStatusValue() == 0){
	        		sb.appendHtmlConstant("<img width=\"20\" src=\"images/singleperson.svg\">"); 
	        		
	        	} else {
	        		sb.appendHtmlConstant("<img width=\"20\" src=\"images/group.svg\">"); 
	        	}

	        	super.render(context, object, sb);
	        }
		
	}
}
	


