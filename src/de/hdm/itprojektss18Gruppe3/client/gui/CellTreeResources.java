package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.CellTree.Style;

/*
 * Durch das Interface wird es ermöglicht, die standardmäßige GWT style theme
 * durch CSS Anweisungen abzuändern. Dadurch kann das Aussehen des Baumes verändert 
 * werden. In der CellTreeResources.css sind alle für den CellTree definierten
 * CSS Befehle hinterlegt, die dann abgeändert werden können.
 * 
 * @author Kevin
 * 
 */
public interface CellTreeResources extends CellTree.Resources {

	public static CellTreeResources INSTANCE = GWT.create(CellTreeResources.class);
	
    @Override
    @Source("CellTreeStyle.css")
    public Style cellTreeStyle();
}
