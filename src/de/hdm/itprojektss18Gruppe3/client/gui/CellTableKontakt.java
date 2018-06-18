package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

public class CellTableKontakt extends CellTable<Kontakt>{
	
	private List<Kontakt> allSelectedKontakte = new ArrayList<>();
	private MultiSelectionModel<Kontakt> ssmAuspraegung = new MultiSelectionModel<Kontakt>();
	private final Handler<Kontakt> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();
	
	public CellTableKontakt(){
		run();
	}
	
	public MultiSelectionModel<Kontakt> getSsmAuspraegung() {
		return ssmAuspraegung;
	}

	public void setSsmAuspraegung(MultiSelectionModel<Kontakt> ssmAuspraegung) {
		this.ssmAuspraegung = ssmAuspraegung;
	}

	public void run(){
		this.setEmptyTableWidget(new Label("Du hast bisher keine Kontakte angelegt"));
		this.setSelectionModel(ssmAuspraegung, selectionEventManager);

		this.addCellPreviewHandler(new PreviewClickHander());
	}
	
	public class CheckColumn extends Column<Kontakt, Boolean>{

		public CheckColumn(Cell<Boolean> cell) {
			super(cell);
		}
		@Override
		public Boolean getValue(Kontakt object) {
			allSelectedKontakte.add(object);
			return ssmAuspraegung.isSelected(object);
		}
		
	}
	
	public class KontaktnameColumn extends Column<Kontakt, String>{

		public KontaktnameColumn(Cell<String> cell) {
			super(cell);
		}
		
		@Override
		public String getValue(Kontakt object) {
			return object.getName();
		}
		
	}
	
	public class IconColumn extends Column<Kontakt, String>{
		String value = "";
		public IconColumn(Cell<String> cell) {
			super(cell);
		
		}
		@Override
		public String getValue(Kontakt object) {
			// TODO Auto-generated method stub
			return "";
		}
		@Override
		public void render(Context context, Kontakt object, SafeHtmlBuilder sb) {
			// TODO Auto-generated method stub
			if (object.getStatus() == 0){
				sb.appendHtmlConstant("<img width=\"20\" src=\"images/singleperson.svg\">");
			} else if (object.getStatus() == 1){

				sb.appendHtmlConstant("<img width=\"20\" src=\"images/group.svg\">");
			}
			super.render(context, object, sb);
		}		
	}	
	
	public class VisitProfileButtonColumn extends Column<Kontakt, String>{

		public VisitProfileButtonColumn(Cell<String> cell) {
			super(cell);
		}

		@Override
		public String getValue(Kontakt object) {
			return "";
		}
	
		@Override
		public void render(Context context, Kontakt object, SafeHtmlBuilder sb) {
			sb.appendHtmlConstant("<button type=\"button\" class=\"viewProfileIcon\" tabindex=\"-1\">");

			super.render(context, object, sb);
		}
		
	}
		
	public class PreviewClickHander implements Handler<Kontakt> {
		@Override
		public void onCellPreview(CellPreviewEvent<Kontakt> event) {
			if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

				final Kontakt value = event.getValue();
				final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
				event.getDisplay().getSelectionModel().setSelected(value, state);
				event.setCanceled(true);
			}
		}
	}
			
	}

