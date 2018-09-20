package handler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import service.ShowResourceListService;
public class ShowResourceListHandler implements Handler{

    @Override
    public String handleService(HttpServletRequest request) {
        ShowResourceListService service = new ShowResourceListService();
        if(service.validate()){
            try {
                service.execute();
            } catch (SQLException e) {
                return ;
            }
        }else{

        }
    }

}
