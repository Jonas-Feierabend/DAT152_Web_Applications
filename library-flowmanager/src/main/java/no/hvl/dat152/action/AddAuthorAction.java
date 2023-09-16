/**
 * 
 */
package no.hvl.dat152.action;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.hvl.dat152.dao.AuthorDAO;
import no.hvl.dat152.model.Author;

/**
 * 
 */
public class AddAuthorAction implements ControllerAction {

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String forename = request.getParameter("forename");
		String lastname = request.getParameter("lastname");


		
		Author author = new Author(forename,lastname);
		
		// save in DB
		AuthorDAO dao = new AuthorDAO();
		dao.addAuthor(author);
		
		request.setAttribute("authors", dao.getAllAuthors());
		
//		response.sendRedirect("viewbooks");
		return ControllerAction.SUCCESS;

	}

}
