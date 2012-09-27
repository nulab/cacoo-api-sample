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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/*
 * Edit diagram (Automatically add image from URL)
 * https://cacoo.com/api_editor_token
 * https://cacoo.com/api_editor_automation
 */
public class CacooEditorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String diagramId = req.getParameter("diagramId");
		try{
			HttpResponse res;
			JsonElement json;
			JsonObject root;
			
			// step-1 : get authentication token to open editing page
			res = CacooUtils.cacooApi(req, resp, "api/v1/diagrams/"+diagramId+"/editor/token.json", null);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			json = CacooUtils.httpResponse2json(res);
			root = json.getAsJsonObject();
			String editorToken = root.get("token").getAsString();
			
			// step-2 : register automation command and get token
			Map<String,String> params = new HashMap<String, String>();
			
			// FIXME Replace URL that you want to insert image.
			String imageUrl = "http://www.nulab.co.jp/";
			
			params.put("command", "{\"operations\":[{\"type\":\"AddImageUrl\",\"parameter\":{\"url\":\""+imageUrl+"\",\"x\":10,\"y\":10}}]}");
			res = CacooUtils.cacooApi(req, resp, "api/v1/diagrams/"+diagramId+"/editor/automation.json", params);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			json = CacooUtils.httpResponse2json(res);
			root = json.getAsJsonObject();
			String automationToken = root.get("token").getAsString();
			
			// step-3 : redirect to editing page.
			resp.sendRedirect(CacooUtils.editorLink(diagramId, editorToken, automationToken, req));
		}catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", e.getMessage());
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp")
			.forward(req, resp);
		}
	}
}
