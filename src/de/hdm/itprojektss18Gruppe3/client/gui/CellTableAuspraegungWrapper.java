package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.EigenschaftsAuspraegungWrapper;

public class CellTableAuspraegungWrapper extends CellTable<EigenschaftsAuspraegungWrapper> {
	
	private final Handler<EigenschaftsAuspraegungWrapper> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();
	private MultiSelectionModel<EigenschaftsAuspraegungWrapper> ssmAuspraegung = null;
	
	public CellTableAuspraegungWrapper(NoSelectionModel<EigenschaftsAuspraegungWrapper> ssm) {
		this.setSelectionModel(ssm);
		run();
		}
	public CellTableAuspraegungWrapper(MultiSelectionModel<EigenschaftsAuspraegungWrapper> ssm) {
		this.setSelectionModel(ssm, selectionEventManager);
		this.addCellPreviewHandler(new PreviewClickHander());

		this.ssmAuspraegung = ssm;
		run();
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
			object.setIDEigenschaftsauspraegungValue(object.getIDEigenschaftsauspraegungValue());
			return object.getAuspraegung().getWert();
		}
	}
	public class CheckBoxBolumn extends Column<EigenschaftsAuspraegungWrapper, Boolean>{

		public CheckBoxBolumn(Cell<Boolean> cell) {
			super(cell);
		}

		@Override
		public Boolean getValue(EigenschaftsAuspraegungWrapper object) {
			return ssmAuspraegung.isSelected(object);
		}
		
	}
	public class IconColumn extends TextColumn<EigenschaftsAuspraegungWrapper>{

		@Override
		public String getValue(EigenschaftsAuspraegungWrapper object) {
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
	public class PreviewClickHander implements Handler<EigenschaftsAuspraegungWrapper> {
		@Override
		public void onCellPreview(CellPreviewEvent<EigenschaftsAuspraegungWrapper> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final EigenschaftsAuspraegungWrapper value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}
}
	


