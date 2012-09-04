package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cacoo.apisample.cacoo.CacooUtils;
import com.cacoo.apisample.cacoo.CacooUtils.OAuthToken;

/*
 * You will be redirected to this Servlet after authentication.
 */
public class CacooCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//String authToken = req.getParameter("oauth_token");
		String oauthVerifier = req.getParameter("oauth_verifier");
		try{
			OAuthToken token = CacooUtils.retrieveAccessToken(req, oauthVerifier);
			HttpSession session = req.getSession();
			// FIXME Usually the tokens will be stored into Database.
			session.setAttribute(CacooUtils.SESSION_CACOO_TOKEN, token);
			String url = (String)session.getAttribute(CacooUtils.SESSION_NEXT_URL);
			// Redirect to the URL after authentication.
			session.removeAttribute(CacooUtils.SESSION_NEXT_URL);
			resp.sendRedirect(url);
		}catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", e.getMessage());
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp")
			.forward(req, resp);
		}
	}
	
}
