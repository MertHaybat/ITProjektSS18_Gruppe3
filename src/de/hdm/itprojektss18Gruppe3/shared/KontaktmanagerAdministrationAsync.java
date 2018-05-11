package de.hdm.itprojektss18Gruppe3.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Das asynchrone Gegenstück des Interface {@link KontaktmanagerAdministration}.
 * Es wird semiautomatisch durch das Google Plugin erstellt und gepflegt.
 * 
 * @author Thomas, Mert
 */
public interface KontaktmanagerAdministrationAsync {

	void init(AsyncCallback<Void> callback);
	
}
