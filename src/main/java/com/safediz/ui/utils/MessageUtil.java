
package com.safediz.ui.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;

public abstract class MessageUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);

	/** The position. */
	private static String position = "top_right";

	/** The container. */
	private static String container = "/container";
	
	/** The Constant duration. */
	private static int duration = 5000;

	/**
	 * Busy.
	 */
	public static void busy() {
		Clients.showBusy(getTopWindow(), "Processing ...");
	}

	/**
	 * Clear busy.
	 */
	public static void clearBusy() {
		Clients.clearBusy(getTopWindow());
	}

	/**
	 * Display.
	 *
	 * @param str
	 *            the str
	 */
	public static void display(final String str) {
		display(Clients.NOTIFICATION_TYPE_INFO, str);
	}

	/**
	 * Display warning.
	 *
	 * @param str
	 *            the str
	 */
	public static void displayWarning(final String str) {
		display(Clients.NOTIFICATION_TYPE_WARNING, str);
	}

	/**
	 * Display.
	 *
	 * @param ex
	 *            the ex
	 */
	public static void display(final Exception ex) {
		String message = ex.getMessage();
		if (message == null || ex instanceof NullPointerException) {
			LOGGER.error(ExceptionUtils.getStackTrace(ex));
			message = "Severe system error has occurred.";
		}
		display(Clients.NOTIFICATION_TYPE_ERROR, message);
	}

	/**
	 * Display User defined Error messages.
	 *
	 * @param str
	 *            the str
	 */
	public static void displayUserDefinedErrorMessage(final String str) {
		display(Clients.NOTIFICATION_TYPE_ERROR, str);
	}

	/**
	 * Display.
	 *
	 * @param type
	 *            the type
	 * @param message
	 *            the message
	 */
	private static void display(final String type, final String message) {

		if (type.equals(Clients.NOTIFICATION_TYPE_INFO)) {
			Clients.showNotification(message, Clients.NOTIFICATION_TYPE_INFO, getTopWindow(), position, duration);
		} else {
			duration = 7000;
			if (type.equals(Clients.NOTIFICATION_TYPE_ERROR)) {
				Clients.showNotification(message, Clients.NOTIFICATION_TYPE_ERROR, getTopWindow(), position, duration, true);
			} else {
				Clients.showNotification(message, Clients.NOTIFICATION_TYPE_WARNING, getTopWindow(), position,
						duration);
			}

		}
	}

	/**
	 * Gets the top window.
	 *
	 * @return the top window
	 */
	private static Component getTopWindow() {
			Div div = (Div) Path.getComponent(container);
			return div.getFirstChild();	
	}
}
