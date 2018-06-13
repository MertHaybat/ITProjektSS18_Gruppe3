package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.core.client.GWT;

/*
 * Durch das Interface wird es ermöglicht, die standardmäßige GWT style theme
 * durch CSS Anweisungen abzuändern. Dadurch kann das Aussehen der Liste verändert 
 * werden. In der CellListResources.css sind alle für den CellList definierten
 * CSS Befehle hinterlegt, die dann abgeändert werden können.
 * 
 * @author Kevin
 * 
 */
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;

public interface CellListResources extends CellList.Resources {

    public static CellListResources INSTANCE = GWT.create(CellListResources.class);

    @Override
    @Source("CellListStyle.css")
    public Style cellListStyle();
}