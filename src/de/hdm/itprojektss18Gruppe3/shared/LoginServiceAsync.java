package de.hdm.itprojektss18Gruppe3.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojektss18Gruppe3.client.LoginInfo;

public interface LoginServiceAsync {

	void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
