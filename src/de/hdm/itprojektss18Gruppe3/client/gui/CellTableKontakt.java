package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;

public class CellTableKontakt extends CellTable<Kontakt>{
	
	private List<Kontakt> allSelectedKontakte = new ArrayList<>();
	private MultiSelectionModel<Kontakt> selectionModelCellTable = new MultiSelectionModel<Kontakt>();

	
	public CellTableKontakt(){
		run();
	}
	
	public void run(){
		this.setEmptyTableWidget(new Label("Du hast bisher keine Kontakte angelegt"));
	}
	
	public class CheckColumn extends Column<Kontakt, Boolean>{

		public CheckColumn(Cell<Boolean> cell) {
			super(cell);
		}
		@Override
		public Boolean getValue(Kontakt object) {
			allSelectedKontakte.add(object);
			return selectionModelCellTable.isSelected(object);
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
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getValue(Kontakt object) {
			// TODO Auto-generated method stub
			return "";
		}
		@Override
		public void render(Context context, Kontakt object, SafeHtmlBuilder sb) {
			// TODO Auto-generated method stub
			sb.appendHtmlConstant("<button type=\"button\" class=\"viewProfileIcon\" tabindex=\"-1\">");

			super.render(context, object, sb);
		}
		
	}
		
		
			
	}
