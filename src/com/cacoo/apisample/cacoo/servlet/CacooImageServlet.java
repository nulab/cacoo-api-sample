package com.cacoo.apisample.cacoo.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;

/*
 * Show an image of the diagram.
 * https://cacoo.com/api_image
 */
public class CacooImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String diagramId = req.getParameter("diagramId");
		try{
			HttpResponse res = CacooUtils.cacooApi(req, resp, "api/v1/diagrams/"+diagramId+".png", null);
			if(res==null){
				// OAuth authentication is required.
				return;
			}
			resp.setContentType(res.getEntity().getContentType().getValue());
			resp.setContentLength((int)res.getEntity().getContentLength());
			BufferedInputStream in = new BufferedInputStream(res.getEntity().getContent());
			BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream());
			try{
				byte[] buf = new byte[64*1024];
				while(true){
					int len = in.read(buf);
					if(len<0){
						break;
					}
					out.write(buf, 0, len);
				}
			}finally{
				try{
					in.close();
				}catch (Exception e) {}
				try{
					out.close();
				}catch (Exception e) {}
			}
		}catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", e.getMessage());
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp")
			.forward(req, resp);
		}
	}
}
