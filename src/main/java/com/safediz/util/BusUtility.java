/**
 * 
 */
package com.safediz.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BusUtility {

	public static String getHost() {
		InetAddress ip;
		String hostname;
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();

			return "Host name : " + hostname + " IP address :" + ip;
		} catch (UnknownHostException e) {
			return "unknown";
		}
	}

	public static String getComponentName(final Object obj) {
		return obj.getClass().asSubclass(obj.getClass()).getSimpleName();
	}
}
