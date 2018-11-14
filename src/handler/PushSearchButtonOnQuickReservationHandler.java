/**
 *
 */
package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.TimeDto;

/**
 *
 * サーブレット番号：13
 * @author p000527259
 *
 */
public class PushSearchButtonOnQuickReservationHandler implements Handler {

	/* (非 Javadoc)
	 * @see handler.Handler#handleService(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String handleService(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String usageEndHourForResourceSelect = request.getParameter("usageEndHour");
		String usageEndMinuteForResourceSelect = request.getParameter("usageEndMinute");
		String capacityForResourceSelect = request.getParameter("capacityForResourceSelect");

		TimeDto usageStartTimeForResourceSelect  = (TimeDto)session.getAttribute("usageStartTimeForResourceSelect");

		CommonValidator commonValidator = new CommonValidator();
		commonValidator.

		return null;
	}

}
