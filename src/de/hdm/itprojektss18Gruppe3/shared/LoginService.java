package de.hdm.itprojektss18Gruppe3.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojektss18Gruppe3.client.LoginInfo;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{
	
	public LoginInfo login(String requestUri);

}
