package de.hdm.itprojektss18Gruppe3.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojektss18Gruppe3.client.ClientsideSettings;
import de.hdm.itprojektss18Gruppe3.server.db.KontaktKontaktlisteMapper;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministrationAsync;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.KontaktKontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Nutzer;

public class CellTableKontakt extends CellTable<Kontakt>{
	
	private List<Kontakt> allSelectedKontakte = new ArrayList<>();
	private Kontaktliste kontaktliste = null;
	private MultiSelectionModel<Kontakt> ssmAuspraegung = new MultiSelectionModel<Kontakt>();
	private final Handler<Kontakt> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();
	private CellTable cellTable = new CellTable<Kontakt>(15, (com.google.gwt.user.cellview.client.CellTable.Resources) GWT.create(CellTableResources.class));
	private Nutzer nutzer = new Nutzer();

	
	public CellTableKontakt(){
		this.setSelectionModel(ssmAuspraegung, selectionEventManager);
		run();
	}
	
	public CellTableKontakt(Kontaktliste kl){
		this.setSelectionModel(ssmAuspraegung, selectionEventManager);
		this.kontaktliste = kl;
		run();
	}
	
	public CellTableKontakt(SingleSelectionModel<Kontakt> ssm){
		this.setSelectionModel(ssm);
		run();
	}
	
	public MultiSelectionModel<Kontakt> getSsmAuspraegung() {
		return ssmAuspraegung;
	}

	public void run(){
		this.setEmptyTableWidget(new Label("Du hast bisher keine Kontakte angelegt"));
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
	
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
			if(nutzer.getId() != object.getNutzerID()) {
				sb.appendHtmlConstant("<img width=\"18\" src=\"images/received_share.png\" style=\"vertical-align: middle; opacity: 0.7\">");
			}
			else if (object.getStatus() == 0){
				sb.appendHtmlConstant("<img width=\"15\" src=\"images/singleperson.svg\" style=\"vertical-align: middle; opacity: 0.7\">");
			} else if (object.getStatus() == 1){

				sb.appendHtmlConstant("<img width=\"18\" src=\"images/group.svg\" style=\"vertical-align: middle; opacity: 0.7\">");
			}
			super.render(context, object, sb);
		}		

	}
}


