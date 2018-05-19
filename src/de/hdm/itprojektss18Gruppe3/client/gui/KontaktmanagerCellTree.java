package de.hdm.itprojektss18Gruppe3.client.gui;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import de.hdm.itprojektss18Gruppe3.client.LeftSideFrame;

public class KontaktmanagerCellTree extends LeftSideFrame {
	
	
	/*
	 * KA OB DAS WEG KANN WEIL DIREKT IN DER ENTRYPOINT KLASSE EINGEBAUT
	 */
    TreeItem root = new TreeItem();
    Label test = new Label("Test");
    HorizontalPanel testing = new HorizontalPanel();
    
	public void run () {

    root.setText("root");
    root.addTextItem("item0");
    root.addTextItem("item1");
    root.addTextItem("item2");

    // Add a CheckBox to the tree
    TreeItem item = new TreeItem(new CheckBox("item3"));
    root.addItem(item);

    Tree t = new Tree();
    t.addItem(root);
    
    testing.add(test);
    testing.add(t);
    testing.setWidth("100px");
    testing.setHeight("200px");

    // Add it to the root panel.
    RootPanel.get("leftmenutree").add(testing);
  }
}


