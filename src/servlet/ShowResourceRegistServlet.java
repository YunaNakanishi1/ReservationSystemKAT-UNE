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
 * @author リコーITソリューションズ株式会社 KAT-UNE
 *
 * Servlet implementation class ShowResourceRegistServlet
 */
@WebServlet("/reservesystem/resourceregist")
public class ShowResourceRegistServlet extends HttpServlet {

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}


}
