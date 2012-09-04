package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;

/*
 * Delete a diagram.
 * https://cacoo.com/api_delete_diagram
 */
public class CacooDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			HttpResponse res = CacooUtils.cacooApi(req, resp, "api/v1/diagrams/"+req.getParameter("diagramId")+"/delete.json", null);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			if(res.getStatusLine().getStatusCode()!=200){
				req.setAttribute("message", "Error : "+res.getStatusLine().getReasonPhrase()+" ("+res.getStatusLine().getStatusCode()+")");
			}else{
				req.setAttribute("message", "Diagram has been deleted.");
			}
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/delete.jsp")
			.forward(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", e.getMessage());
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp")
			.forward(req, resp);
		}
	}
}
