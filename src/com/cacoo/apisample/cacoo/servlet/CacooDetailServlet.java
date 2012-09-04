package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/*
 * Show detailed information of the diagram.
 * https://cacoo.com/api_diagram
 */
public class CacooDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String diagramId = req.getParameter("diagramId");
		try{
			HttpResponse res = CacooUtils.cacooApi(req, resp, "api/v1/diagrams/"+diagramId+".json", null);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			JsonElement json = CacooUtils.httpResponse2json(res);
			JsonObject root = json.getAsJsonObject();
			req.setAttribute("diagramId", diagramId);
			req.setAttribute("title", root.get("title").getAsString());
			req.setAttribute("imageUrl", root.get("imageUrl").getAsString());
			if(!root.get("description").isJsonNull()){
				req.setAttribute("description", root.get("description").getAsString());
			}
			List<Map<String, String>> comments = new ArrayList<Map<String,String>>();
			req.setAttribute("comments", comments);
			JsonArray jsonComments = root.get("comments").getAsJsonArray();
			for(int i=0; i<jsonComments.size(); i++){
				JsonObject c = jsonComments.get(i).getAsJsonObject();
				JsonObject user = c.get("user").getAsJsonObject();
				Map<String,String> map = new HashMap<String, String>();
				comments.add(map);
				map.put("imageUrl", user.get("imageUrl").getAsString());
				map.put("userName", user.get("nickname").getAsString());
				map.put("comment", c.get("content").getAsString());
			}
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/detail.jsp")
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
