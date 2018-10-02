package handler;

import static handler.MessageHolder.*;
import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;
import service.ChangeResourceService;
import service.RegistResourceService;

public class SetResourceDetailsHandler implements Handler {
	private HttpServletRequest _request;
	private Resource _resource;
	private String _type;
	private Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {
		_request = request;

		// セッションから権限を取得
		HttpSession session = request.getSession(false);

		int authority = (int) session.getAttribute("authority");

		if(authority==0){
			if(precheck()){
				if("regist".equals(_type)){
					return regist();
				}else{
					return change();
				}
			}else{
				return RESOURCE_REGIST_SERVLET;
			}

		}else{
			_log.error("no authority");
			return ERROR_PAGE;
		}

	}

	private boolean precheck() {
		_type = _request.getParameter("type");
		_request.setAttribute("type", _type);
		String resourceId = _request.getParameter("resourceId");
		_request.setAttribute("resourceId", resourceId);
		String resourceName = _request.getParameter("resourceName");
		_request.setAttribute("resourceName", resourceName);
		String category = _request.getParameter("category");
		_request.setAttribute("category", category);
		String capacity = _request.getParameter("capacity");
		_request.setAttribute("capacity", capacity);
		String officeName = _request.getParameter("officeName");
		_request.setAttribute("officeName", officeName);
		;
		String stopStartDay = _request.getParameter("stopStartDay");
		_request.setAttribute("stopStartDay", stopStartDay);
		String stopStartHour = _request.getParameter("stopStartHour");
		_request.setAttribute("stopStartHour", stopStartHour);
		String stopStartMinute = _request.getParameter("stopStartMinute");
		_request.setAttribute("stopStartMinute", stopStartMinute);
		String stopEndDay = _request.getParameter("stopEndDay");
		_request.setAttribute("stopEndDay", stopEndDay);
		String stopEndHour = _request.getParameter("stopEndtHour");
		_request.setAttribute("stopEndHour", stopEndHour);
		String stopEndMinute = _request.getParameter("stopEndMinute");
		_request.setAttribute("stopEndMinute", stopEndMinute);
		String supplement = _request.getParameter("supplement");
		_request.setAttribute("supplement", supplement);
		List<String> facility = new ArrayList<String>(Arrays.asList(_request.getParameterValues("facility")));
		_request.setAttribute("facility", facility);
		CommonValidator commonValidator = new CommonValidator();
		if (commonValidator.notSetOn(resourceName)) {
			_request.setAttribute("EMessage", EM27);
			return false;
		}
		if (commonValidator.notSetOn(category)) {
			_request.setAttribute("EMessage", EM37);
			return false;
		}
		if (commonValidator.notSetOn(capacity)) {
			_request.setAttribute("EMessage", EM30);
			return false;
		}
		if (commonValidator.notNumericOn(capacity)) {
			_request.setAttribute("EMessage", EM31);
			return false;
		}
		int capacityNumber = commonValidator.getNumber();
		if (commonValidator.notSetOn(officeName)) {
			_request.setAttribute("EMessage", EM33);
			return false;
		}
		Timestamp stopStartDate = null;
		if (!commonValidator.notSetOn(stopStartDay)) {
			if (commonValidator.notDateOn(stopStartDay, stopStartHour, stopStartMinute)) {
				_request.setAttribute("EMessage", EM38);
				return false;
			}
			stopStartDate = commonValidator.getDate();
		}
		Timestamp stopEndDate = null;
		if (!commonValidator.notSetOn(stopEndDay)) {
			if (commonValidator.notDateOn(stopEndDay, stopEndHour, stopEndMinute)) {
				_request.setAttribute("EMessage", EM38);
				return false;
			}
			stopEndDate = commonValidator.getDate();
		}
		_resource = new Resource(resourceId, resourceName, officeName, category, capacityNumber, supplement, 0,
				facility, stopStartDate, stopEndDate);
		return true;
	}

	private String regist() {
		RegistResourceService registResourceService = new RegistResourceService(_resource);
		if (registResourceService.validate()) {
			try {
				registResourceService.execute();
				int result = registResourceService.getResult();
				String resourceId = registResourceService.getResourceId();
				if (result == 1 && resourceId != null) {
					_request.setAttribute("resourceId", resourceId);
					return RESOURCE_DETAILS_SERVLET;
				} else {
					_log.error("regist failed");
					return ERROR_PAGE;
				}
			} catch (SQLException e) {
				_log.error("SQLException");
				return ERROR_PAGE;
			}
		} else {
			String validationMessage = registResourceService.getValidationMessage();
			_request.setAttribute("EMessage", validationMessage);
			return RESOURCE_REGIST_SERVLET;
		}

	}

	private String change() {
		ChangeResourceService changeResourceService = new ChangeResourceService(_resource);
		if (changeResourceService.validate()) {
			try {
				changeResourceService.execute();
				int result = changeResourceService.getResult();
				if (result == 1) {
					return RESOURCE_DETAILS_SERVLET;
				} else {
					_log.error("change failed");
					return ERROR_PAGE;
				}
			} catch (SQLException e) {
				_log.error("SQLException");
				return ERROR_PAGE;
			}
		} else {
			String validationMessage = changeResourceService.getValidationMessage();
			_request.setAttribute("EMessage", validationMessage);
			return RESOURCE_REGIST_SERVLET;
		}

	}

}
