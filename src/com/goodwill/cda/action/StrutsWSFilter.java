package com.goodwill.cda.action;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class StrutsWSFilter extends StrutsPrepareAndExecuteFilter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		String url = ((HttpServletRequest) req).getRequestURI();
		if (url.indexOf("ws") < 0) { //另外一种过滤cxf方式  
			super.doFilter(req, res, chain);
		} else {
			chain.doFilter(req, res);
		}
	}
}
