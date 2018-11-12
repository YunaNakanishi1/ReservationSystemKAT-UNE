package handler;

import javax.servlet.http.HttpSession;

public class HandlerHelper {

	/**
	 * sessionに（属性名、初期値）の形でセット
	 * @param session
	 * @author リコーITソリューションズ株式会社 KAT-UNE
	 */
	public static void initializeAttributeForReservationRegist(HttpSession session){
		session.setAttribute("usageStartTimeForReservationChange", null);
		session.setAttribute("usageEndTimeForReservationChange", null);
		session.setAttribute("reservationNameForReservationChange",null );
		session.setAttribute("numberOfParticipantsForReservationChange", 0);
		session.setAttribute("coReservedPersonIdForReservationChange", null);
		session.setAttribute("attendanceTypeIdForReservationChange", null);
		session.setAttribute("reserveSupplementForReservationChange", null);
		session.setAttribute("reservationIdForReservationDetails",null);
		session.setAttribute("usageDateForReservationRegist",null);
		session.setAttribute("usageStartTimeForResourceSelect",null);
		session.setAttribute("usageEndTimeForResourceSelect",null);
		session.setAttribute("usageTimeForReservationSelect",null);
		session.setAttribute("resourceNameForResourceSelect",null);
		session.setAttribute("categoryIdForResourceSelect",null);
		session.setAttribute("officeIdForResourceSelect",null);
		session.setAttribute("facilityIdListForResourceSelect",null);
		session.setAttribute("selectedFacilityListForResourceSelect",null);
		session.setAttribute("capacityForResourceSelect",0);
		session.setAttribute("displayCapacityForResourceSelect",null);
		session.setAttribute("reservationNameForReservationRegist",null);
		session.setAttribute("numberOfParticipantsForReservationRegist",0);
		session.setAttribute("displayNumberOfParticipantsForReservationRegist",null);
		session.setAttribute("coReservedPersonIdForReservationRegist",null);
		session.setAttribute("attendanceTypeIdForReservationRegist",null);
		session.setAttribute("reserveSupplementForReservationRegist",null);
		session.setAttribute("returnPageForResourceSelect",null);
		session.setAttribute("reservationListForResourceDeleteConfirm",null);
		session.setAttribute("resourceNameForReservationRegist",null);
		session.setAttribute("resourceIdForReservationRegist",null);
		session.setAttribute("reservationDTOForReservationChange",null);
		session.setAttribute("userListForReservationRegist",null);
		session.setAttribute("attendanceTypeListForReservationRegist",null);
		session.setAttribute("reservationListForSuspensionUseConfirm",null);
		session.setAttribute("categoryIdForReservationList",null);
		session.setAttribute("officeIdForReservationList",null);
		session.setAttribute("usageDateForReservationList",null);
		session.setAttribute("usageStartHourForReservationList",null);
		session.setAttribute("usageEndHourForResourceSelect",null);
		session.setAttribute("displayOnlyMyReservation",null);
		session.setAttribute("displayPastReservation",null);
		session.setAttribute("displayDeletedReservatioj",null);
		session.setAttribute("usageStartHourForReservationRegist",null);
		session.setAttribute("usageEndHourForReservationRegist",null);
		session.setAttribute("reservableListForResourceSelect",null);
		session.setAttribute("reservationListForReservationList",null);
		session.setAttribute("categoryListForResourceSelect",null);
		session.setAttribute("officeListForResourceSelect",null);
		session.setAttribute("usableStartTimeForReservationRegist",null);
		session.setAttribute("usableEndTimeForReservationRegist",null);
		session.setAttribute("usableStartTimeForReservationChange",null);
		session.setAttribute("usableEndTimeForReservationChange",null);
		session.setAttribute("userListForReservationChange",null);
		session.setAttribute("attendanceTypeListForReservationChange",null);
		session.setAttribute("linkToResourceDetails",null);
		session.setAttribute("reservationDTOForReservationDetails",null);
		session.setAttribute("categoryListForReservationList",null);
		session.setAttribute("officeListForReservationList",null);
		session.setAttribute("messageForReservationListLower",null);
		session.setAttribute("messageForReservationListUpper",null);
		session.setAttribute("messageForResourceSelectLower",null);
		session.setAttribute("messageForResourceSelectUpper",null);
		session.setAttribute("messageForQuickReservation",null);
		session.setAttribute("messageForReservationChange",null);
		session.setAttribute("messageForResourceDeleteConfirm",null);
		session.setAttribute("messageForSuspensionUseConfirm",null);
		session.setAttribute("messageForReservationRegist",null);
		session.setAttribute("resourceDTOForResourceDetailsTab",null);
		session.setAttribute("facilityListForResourceSelect",null);

	}

}
