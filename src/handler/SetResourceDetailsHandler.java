package handler;

import static handler.MessageHolder.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;

public class SetResourceDetailsHandler implements Handler{
	private HttpServletRequest _request;
	private Resource _resource;
	private String _type;
	private Logger _log= LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	private boolean precheck(){
		_type=_request.getParameter("type");
		_request.setAttribute("type", _type);
		String resourceId=_request.getParameter("resourceId");
		_request.setAttribute("resourceId", resourceId);
		String resourceName=_request.getParameter("resourceName");
		_request.setAttribute("resourceName", resourceName);
		String category=_request.getParameter("category");
		_request.setAttribute("category", category);
		String capacity=_request.getParameter("capacity");
		_request.setAttribute("capacity", capacity);
		String officeName=_request.getParameter("officeName");
		_request.setAttribute("officeName", officeName);;
		String stopStartDay=_request.getParameter("stopStartDay");
		_request.setAttribute("stopStartDay", stopStartDay);
		String stopStartHour=_request.getParameter("stopStartHour");
		_request.setAttribute("stopStartHour", stopStartHour);
		String stopStartMinute=_request.getParameter("stopStartMinute");
		_request.setAttribute("stopStartMinute", stopStartMinute);
		String stopEndDay=_request.getParameter("stopEndDay");
		_request.setAttribute("stopEndDay", stopEndDay);
		String stopEndHour=_request.getParameter("stopEndtHour");
		_request.setAttribute("stopEndHour", stopEndHour);
		String stopEndMinute=_request.getParameter("stopEndMinute");
		_request.setAttribute("stopEndMinute", stopEndMinute);
		String supplement=_request.getParameter("supplement");
		_request.setAttribute("supplement", supplement);
		List<String> facility = new ArrayList<String>(Arrays.asList(_request.getParameterValues("facility")));
		_request.setAttribute("facility", facility);
		CommonValidator commonValidator = new CommonValidator();
		if(commonValidator.notSetOn(resourceName)){
			_request.setAttribute("EMessage", EM27);
			return false;
		}
		if(commonValidator.notSetOn(category)){
			_request.setAttribute("EMessage", EM37);
			return false;
		}
		if(commonValidator.notSetOn(capacity)){
			_request.setAttribute("EMessage", EM30);
			return false;
		}
		if(commonValidator.notNumericOn(capacity)){
			_request.setAttribute("EMessage", EM31);
			return false;
		}
		int capacityNumber=commonValidator.getNumber();
		if(commonValidator.notSetOn(officeName)){
			_request.setAttribute("EMessage", EM33);
			return false;
		}
		Timestamp stopStartDate=null;
		if(!commonValidator.notSetOn(stopStartDay)){
			if(commonValidator.notDateOn(stopStartDay, stopStartHour, stopStartMinute)){
				_request.setAttribute("EMessage", EM38);
				return false;
			}
			stopStartDate=commonValidator.getDate();
		}
		Timestamp stopEndDate=null;
		if(!commonValidator.notSetOn(stopEndDay)){
			if(commonValidator.notDateOn(stopEndDay, stopEndHour, stopEndMinute)){
				_request.setAttribute("EMessage", EM38);
				return false;
			}
			stopEndDate=commonValidator.getDate();
		}
		_resource=new Resource(resourceId,resourceName,officeName,category,capacityNumber,supplement,0,facility,stopStartDate,stopEndDate);
		return true;
	}

	private String regist(){

	}

	private String change(){

	}

}
