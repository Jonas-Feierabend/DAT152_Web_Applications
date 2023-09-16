/**
 * 
 */
package no.hvl.dat152.action;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.hvl.dat152.dao.BookDAO;

/**
 * 
 */
public class DeleteBookAction implements ControllerAction {

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String isbn = request.getParameter("isbn");
		System.out.println("got isbn: "+ isbn); 
		
		// save in DB
		BookDAO dao = new BookDAO();
		dao.deleteBook(isbn);
		
		new ViewBookAction().execute(request, response);
//		if(success)
//			response.sendRedirect("viewbook?isbn="+isbn+"&authorid="+authorid);
//		else
//			response.sendError(401, "Update Not Successful...");
		
		return ControllerAction.SUCCESS;


	}

}
