package de.hdm.itprojektss18Gruppe3.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Verwaltung von Kontakten.
 * 
 * @author Thomas, Mert
 */
@RemoteServiceRelativePath("kontaktmanager")
public interface KontaktmanagerAdministration extends RemoteService {
	
	/**
	 * Initialisierung des Objekts. Diese ist hauptsächlich
	 * zum No Argument Constructor der implementierenden Klasse notwendig.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;
	
}
