package handler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;
import service.ShowResourceDetailsService;

public class ShowResourceDetailsHandler implements Handler{
    private static Logger _log = LogManager.getLogger(); //これはクラス図にはないんですが

    @Override
    public String handleService(HttpServletRequest request) {

        String resourceId = request.getParameter("resourceId");

        ShowResourceDetailsService service = new ShowResourceDetailsService(resourceId);

        if(service.validate()){
            try {
                service.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                _log.error("SQLException");
                return ViewHolder.ERROR_PAGE;
            }

            Resource resource = service.getResult();
            if(resource == null){
                _log.error("This record has been deleted");
                return ViewHolder.ERROR_PAGE;
            }

            //利用停止期間をフォーマットに即して変換してセット
            String format = "yyyy/MM/dd　H時m分";

            String stopStartDate = new SimpleDateFormat(format).format(resource.getUsageStopStartDate());
            String stopEndDate = new SimpleDateFormat(format).format(resource.getUsageStopEndDate());
            if(!"0001/01/01　0時0分".equals(stopStartDate)){
                request.setAttribute("stopStartDate", stopStartDate);
            }
            if(!"0001/01/01　0時0分".equals(stopEndDate)){
                request.setAttribute("stopEndDate", stopEndDate);
            }

            //取得したリソースをセット
            request.setAttribute("resource", resource);
        }else{
            _log.error("validateError");
            return ViewHolder.ERROR_PAGE;
        }
        return ViewHolder.RESOURCE_DETAILS;
    }

}
