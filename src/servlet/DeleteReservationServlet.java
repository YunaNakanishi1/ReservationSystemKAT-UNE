package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.DeleteReservationHandler;
import handler.Handler;

/**
 * サーブレット番号：2
 * 予約詳細画面で削除ボタンが押下され時に呼び出されるサーブレット
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
@WebServlet("/reservesystem/deleteReservation")
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
