package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Forward to input page of diagram copy.
 */
public class CacooCopyInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("diagramId", req.getParameter("diagramId"));
		req
		.getRequestDispatcher("/WEB-INF/jsp/cacoo/copy_input.jsp")
		.forward(req, resp);
	}
}
