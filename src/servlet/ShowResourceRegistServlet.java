package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.Handler;
import handler.ShowResourceRegistHandler;

/**
 * リソース入力ページやエラーページに遷移する.
 *
 * Servlet implementation class ShowResourceRegistServlet
 */
@WebServlet("/resourceregist")
public class ShowResourceRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * リソース入力ページやエラーページに遷移する.
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Handler showResourceRegistHandler = new ShowResourceRegistHandler();

        //遷移先URL
        String view = showResourceRegistHandler.handleService(request);

        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
	}



}
