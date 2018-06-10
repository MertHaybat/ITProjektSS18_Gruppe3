package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.CellTree.Style;

public interface CellTreeResources extends CellTree.Resources {

	public static CellTreeResources INSTANCE = GWT.create(CellTreeResources.class);
	
    @Override
    @Source("CellTreeStyle.css")
    public Style cellTreeStyle();
}
