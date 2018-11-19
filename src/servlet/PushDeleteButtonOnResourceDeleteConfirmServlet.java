package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.PushDeleteButtonOnResourceDeleteConfirmHandler;

/**
 * Servlet implementation class PushDeleteButtonOnResourceDeleteConfirmServlet
 */
@WebServlet("/reservesystem/pushDeleteButton")
public class PushDeleteButtonOnResourceDeleteConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PushDeleteButtonOnResourceDeleteConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler handler = new PushDeleteButtonOnResourceDeleteConfirmHandler();

	    String view = handler.handleService(request);


		 RequestDispatcher rd = request.getRequestDispatcher(view);
	        rd.forward(request, response);
	}

}