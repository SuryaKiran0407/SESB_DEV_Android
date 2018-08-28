package com.enstrapp.fieldtekpro.networkconnection;

import android.content.Context;
import android.net.ConnectivityManager;

public class ConnectionDetector
{
	private Context _context;
	public ConnectionDetector(Context context) {
		this._context = context;
	}

	/*public boolean isConnectingToInternet()
	{
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}*/

	public final boolean isConnectingToInternet()
	{

		// get Connectivity Manager object to check connection
		//ConnectivityManager connec =  (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
		ConnectivityManager connec = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

		// Check for network connections
		if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED || connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED )
		{

			// if connected with internet
			return true;
		}
		else if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
		{

			return false;
		}
		return false;
	}

}
