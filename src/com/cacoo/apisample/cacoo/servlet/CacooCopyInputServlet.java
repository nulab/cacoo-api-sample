package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;

/*
 * Copy diagram.
 * https://cacoo.com/api_copy_diagram
 */
public class CacooCopyInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String diagramId = req.getParameter("diagramId");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		try{
			Map<String, String> params = new HashMap<String, String>();
			params.put("diagramId", diagramId);
			params.put("title", title);
			params.put("description", description);
			HttpResponse res = CacooUtils.cacooApi(req, resp, "api/v1/diagrams/"+diagramId+"/copy.json", params);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			if(res.getStatusLine().getStatusCode()!=200){
				req.setAttribute("message", "Error : "+res.getStatusLine().getReasonPhrase()+" ("+res.getStatusLine().getStatusCode()+")");
			}else{
				req.setAttribute("message", "Diagrams has copied.");				
			}
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/copy_done.jsp")
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
