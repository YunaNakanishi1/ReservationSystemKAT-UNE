package service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.RegistReservationHandler;

/**
 * Servlet implementation class RegistReservationServlet
 */
@WebServlet("/registreserve")
public class RegistReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler handler = new RegistReservationHandler();

		String view = handler.handleService(request);
	    RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
