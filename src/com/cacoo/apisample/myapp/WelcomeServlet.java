package com.cacoo.apisample.myapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Your application.
 */
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = -8852146135863671439L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req
		.getRequestDispatcher("/WEB-INF/jsp/myapp/welcome.jsp")
		.forward(req, resp);
	}

}
