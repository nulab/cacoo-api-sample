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
 * Post a comment for the diagram.
 * https://cacoo.com/api_post_comment
 */
public class CacooCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String diagramId = req.getParameter("diagramId");
		String comment = req.getParameter("comment");
		try{
			Map<String,String> params = new HashMap<String, String>();
			params.put("content", comment);
			HttpResponse res = CacooUtils.cacooApi(req, resp, "api/v1/diagrams/"+diagramId+"/comments/post.json", params);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			// Prevent double posting by reload.
			resp.sendRedirect("detail?diagramId="+diagramId);
			
		}catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", e.getMessage());
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp")
			.forward(req, resp);
		}
	}
}
