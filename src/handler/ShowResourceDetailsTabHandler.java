package handler;

import static handler.ViewHolder.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;
import exception.MyException;
import service.GetResouceFromIdService;

public class ShowResourceDetailsTabHandler implements Handler {

	private static Logger _log = LogManager.getLogger();

	@Override
	public String handleService(HttpServletRequest request) {
		String resourceId = request.getParameter("resourceId");
		try {
			GetResouceFromIdService getResouceFromIdService = new GetResouceFromIdService(resourceId);
			if(getResouceFromIdService.validate()){
				getResouceFromIdService.execute();
				Resource resource = getResouceFromIdService.getResource();
				if(resource==null){
					_log.error("resource not found");
					return ERROR_PAGE;
				}
				if( (resource.getUsageStopStartDate() != null) && (resource.getUsageStopEndDate() != null) ){
	                //利用停止期間をフォーマットに即して変換してセット
	            	//旧
	            	//String format = "yyyy/MM/dd　H時m分";
	                String format = "yyyy/MM/dd　HH:mm";
	                String stopStartDate = new SimpleDateFormat(format).format(resource.getUsageStopStartDate());
	                String stopEndDate = new SimpleDateFormat(format).format(resource.getUsageStopEndDate());
	                request.setAttribute("stopStartDate", stopStartDate);
	                request.setAttribute("stopEndDate", stopEndDate);
	            }
				request.setAttribute("resourceDTOForResourceDetailsTab", resource);
			}
		} catch (MyException e) {
			_log.error("GetResouceFromIdService input error");
			return ERROR_PAGE;
		} catch (SQLException e) {
			_log.error("GetResouceFromIdService input error");
			e.printStackTrace();
			return ERROR_PAGE;
		}


		return RESOURCE_DETAILS_TAB;
	}

}
