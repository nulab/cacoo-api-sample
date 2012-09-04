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
 * Show button to create new diagram and list of diagram.
 * https://cacoo.com/api_diagrams
 */
public class CacooDiagramsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			// User name and icon.
			HttpResponse res = CacooUtils.cacooApi(req, resp, "api/v1/account.json", null);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			JsonElement json = CacooUtils.httpResponse2json(res);
			JsonObject root = json.getAsJsonObject();
			req.setAttribute("userName", root.get("nickname").getAsString());
			req.setAttribute("imageUrl", root.get("imageUrl").getAsString());
			
			// diagrams
			res = CacooUtils.cacooApi(req, resp, "api/v1/diagrams.json", null);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			json = CacooUtils.httpResponse2json(res);
			root = json.getAsJsonObject();
			JsonArray result = root.get("result").getAsJsonArray();
			List<Map<String, String>> diagrams = new ArrayList<Map<String,String>>();
			req.setAttribute("diagrams", diagrams);
			for(int i=0; i<result.size(); i++){
				JsonObject diagram = result.get(i).getAsJsonObject();
				Map<String, String> map = new HashMap<String, String>();
				diagrams.add(map);
				map.put("diagramId", diagram.get("diagramId").getAsString());
				map.put("title", diagram.get("title").getAsString());
				map.put("imageUrl", diagram.get("imageUrl").getAsString());
			}
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/diagrams.jsp")
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
