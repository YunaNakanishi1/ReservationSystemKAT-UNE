package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.ShowResourceSelectHandler;

/**
 * Servlet implementation class ShowResourceSelectServlet
 */
@WebServlet("/reservesystem/ShowResourceSelect")
public class ShowResourceSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * サーブレット番号：36
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler showResourceSelectHandler = new ShowResourceSelectHandler();

        //遷移先URL
        String view = showResourceSelectHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}

}
