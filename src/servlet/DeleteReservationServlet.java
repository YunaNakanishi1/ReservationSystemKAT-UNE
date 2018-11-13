package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;

/**
 * Servlet implementation class DeleteReservationServlet
 */
@WebServlet("/reservesysyem/deleteReservation")
public class DeleteReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReservationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Handler deleteReservationHandler = new DeleteReservationHandler();

	        //遷移先URL
	        String view = deleteReservationHandler.handleService(request);

	        RequestDispatcher rd = request.getRequestDispatcher(view);
	        rd.forward(request, response);
	}

}
