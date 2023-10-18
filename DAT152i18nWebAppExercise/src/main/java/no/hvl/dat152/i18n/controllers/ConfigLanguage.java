package no.hvl.dat152.i18n.controllers;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.jstl.core.Config;

/**
 * Servlet implementation class ConfigLanguage
 */
@WebServlet("/configlang")
public class ConfigLanguage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfigLanguage() {
        super();
        
    }
    
    private void saveLocaleCookie(String locale, HttpServletResponse response) {
    	
    	Cookie cookie = new Cookie("locale", locale);
		cookie.setMaxAge(365 * 24 * 60 * 60); // 1 year
		response.addCookie(cookie);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acceptLang = request.getHeader("Accept-Language");
		System.out.println("acceptLang = " + acceptLang);
		System.out.println("preferredLocales = " + Collections.list(request.getLocales()));
		
		String locale = request.getParameter("locale");
		if(locale != null) {
			Config.set(request.getSession(), Config.FMT_LOCALE, locale);
			
			// take this as preferred locale setting by user - save it in browser cookie
			saveLocaleCookie(locale, response);
			
			response.sendRedirect(request.getHeader("referer"));
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}