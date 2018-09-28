package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.DeleteResourceHandler;
import handler.Handler;

/**
 * Servlet implementation class DeleteResourceServlet
 */
@WebServlet("/reservesystem/deleteresource")
public class DeleteResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Handler deleteResourceHandler = new DeleteResourceHandler();

        //遷移先URL
        String view = deleteResourceHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
