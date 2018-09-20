package handler;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dto.Resource;
import service.ShowResourceListService;
public class ShowResourceListHandler implements Handler{

    @Override
    public String handleService(HttpServletRequest request) {
        ShowResourceListService service = new ShowResourceListService();
        if(service.validate()){
            try {
               service.execute();
               List<Resource> resourceList = service.getResourceList();
               request.setAttribute("resourceList", resourceList);
               return null;
            } catch (SQLException e) {
                return ViewHolder.ERROR_PAGE;
            }
        }else{
                return ViewHolder.ERROR_PAGE;
        }
    }

}
