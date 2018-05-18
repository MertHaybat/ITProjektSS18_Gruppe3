package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTable.Style;

public interface CellTableResources extends CellTable.Resources {

	    public static CellTableResources INSTANCE = GWT.create(CellTableResources.class);

	    @Override
	    @Source("CellTableStyle.css")
	    public Style cellTableStyle();
	}
