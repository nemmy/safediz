package com.safediz.ui;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Portallayout;
import org.zkoss.zul.Div;
import org.zkoss.zul.Panel;

public class PortalLayoutController extends SelectorComposer<Component> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	@Wire
	private Portallayout portalLayout;

	@Listen("onPortalMove = #portalLayout")
	public void saveStatus() {
		int i = 0;
		for (Component portalChild : portalLayout.getChildren()) {
			List<String> portletIds = new ArrayList<>();
			for (Component portlet : portalChild.getChildren())
				portletIds.add(portlet.getId());
			Executions.getCurrent().getSession().setAttribute("PortalChildren" + i++, portletIds);
		}
	}

	@SuppressWarnings("unchecked")
	@Listen("onCreate = #portalLayout")
	public void initStatus() {

		List<? extends Component> panelchildren = portalLayout.getChildren();
		for (int i = 0; i < panelchildren.size(); i++) {
			List<String> panelIds = (List<String>) Executions.getCurrent().getSession()
					.getAttribute("PortalChildren" + i);
			if (panelIds != null && !panelIds.isEmpty()) {
				for (String panelId : panelIds) {
					if (!panelId.isEmpty()) {
						Panel newPanel = (Panel) portalLayout.getFellow(panelId);
						if (!panelchildren.isEmpty()) {
							panelchildren.get(i).insertBefore(newPanel, panelchildren.get(0));
						} else {
							newPanel.setParent(panelchildren.get(i));
						}
					}

				}
			}
		}
	}
	
	public void onTimer$timer(Event e){
		Div div = (Div) Path.getComponent("/contents");
		div.getChildren().clear();
		Executions.getCurrent().createComponents("dashboard", null, null);
	}
}
