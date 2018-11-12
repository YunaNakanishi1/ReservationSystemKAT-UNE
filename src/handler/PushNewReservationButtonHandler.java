package handler;

import static handler.ViewHolder.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.TimeDto;

public class PushNewReservationButtonHandler implements Handler {

	private static int ZERO = 0;
	private static int FIFTEEN = 15;
	private static int TWENTY_FOUR = 24;
	private Logger _log = LogManager.getLogger();

	/* (非 Javadoc)
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String handleService(HttpServletRequest request) {
		 //セッションは存在する
		 //コメントプッシュ
		HttpSession session = request.getSession(true);

		//HandlerHelperのinitializeAttributeForReservationRegist()メソッドを呼び出す
		HandlerHelper.initializeAttributeForReservationRegist(session);


		//当日の日付を取得
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime usageDate = LocalDateTime.now();
		String usageDateForReservationRegist = usageDate.format(formatter);

		//リソース選択画面の検索フォームをデフォルト値でセットする
		session.setAttribute("usageDateForReservation", usageDateForReservationRegist);
		session.setAttribute("usageStartTimeForResourceSelect",new TimeDto(ZERO,ZERO));
		session.setAttribute("usageEndTimeForResourceSelect", new TimeDto(TWENTY_FOUR,ZERO));
		session.setAttribute("usageTimeForReservationSelect", new TimeDto(ZERO,FIFTEEN));
		session.setAttribute("facilityIdListForResourceSelect",new ArrayList<String>());

		//戻るボタンの行き先をセットする
		session.setAttribute("ShowfirstReservationListServlet",RESERVE_LIST);

		return SHOW_RESOURCE_SELECT_SERVLET;


	}

}
