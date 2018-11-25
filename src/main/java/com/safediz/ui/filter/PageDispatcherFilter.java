package com.safediz.ui.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PageDispatcherFilter implements Filter {

	private static final String ZUL_VIEW_SUFFIX = ".zul";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().substring(req.getContextPath().length());
		
		if (path.contains(";")) {
			int index = path.indexOf(';');
			path = path.substring(0, index);
		}
		
		if (!(path.equals("/") || path.startsWith("/img") || path.startsWith("/css") || path.startsWith("/js")
				|| path.startsWith("/vendors") || path.startsWith("/zkau") || path.startsWith("/zkwm")
				|| path.contains(".zul") || path.contains("html"))) {
			request.getRequestDispatcher(path.concat(ZUL_VIEW_SUFFIX)).forward(request, response);
		} else {
			chain.doFilter(request, response); // Goes to default servlet.
		}
	}

	@Override
	public void destroy() {
	}
}