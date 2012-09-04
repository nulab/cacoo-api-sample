package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/*
 * Create a new diagram and redirect to the edit page.
 * https://cacoo.com/api_create_diagram
 */
public class CacooCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			HttpResponse res = CacooUtils.cacooApi(req, resp, "api/v1/diagrams/create.json", null);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			JsonElement json = CacooUtils.httpResponse2json(res);
			JsonObject root = json.getAsJsonObject();
			String diagramId = root.get("diagramId").getAsString();
			// Redirect to editing page.
			resp.sendRedirect(CacooUtils.editorLink(diagramId, req));
			
		}catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", e.getMessage());
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp")
			.forward(req, resp);
		}
	}
}
