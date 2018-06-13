package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.core.client.GWT;

/*
 * Durch das Interface wird es ermöglicht, die standardmäßige GWT style theme
 * durch CSS Anweisungen abzuändern. Dadurch kann das Aussehen des CellTables verändert 
 * werden. In der CellTableResources.css sind alle für den CellTable definierten
 * CSS Befehle hinterlegt, die dann abgeändert werden können.
 * 
 * @author Kevin
 * 
 */
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTable.Style;

public interface CellTableResources extends CellTable.Resources {

	    public static CellTableResources INSTANCE = GWT.create(CellTableResources.class);

	    @Override
	    @Source("CellTableStyle.css")
	    public Style cellTableStyle();
	}
