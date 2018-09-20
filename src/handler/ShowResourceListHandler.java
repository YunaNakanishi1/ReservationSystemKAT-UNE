package handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;
import service.ShowResourceListService;
public class ShowResourceListHandler implements Handler{

    private static Logger _log = LogManager.getLogger(); //これはクラス図にはないんですが

    @Override
    public String handleService(HttpServletRequest request) {
        //セッションから権限を取得
        int authority = 0; //0 権限あり 1 なし

        ShowResourceListService service = new ShowResourceListService();
        if(service.validate()){
            try {
                //実行
                service.execute();
                List<Resource> resourceList = service.getResourceList();

                if(authority != 0){
                    //権限のないユーザの処理
                    List<Resource> forNotAuthorityResourceList = new ArrayList<>();
                    //削除済みでないものをリストにセット
                    for (Resource resource : resourceList) {
                        if(resource.getDeleted() == 0){
                            forNotAuthorityResourceList.add(resource);
                        }
                    }
                    if(forNotAuthorityResourceList.size() == 0){
                        request.setAttribute("Emessage", "検索結果は0件です。");
                    }
                    request.setAttribute("resourceList", forNotAuthorityResourceList);
                    request.setAttribute("resourceListSize", forNotAuthorityResourceList.size());
                }else{
                    //権限のあるユーザの処理
                    if(resourceList.size() == 0){
                        request.setAttribute("Emessage", "検索結果は0件です。");
                    }
                    request.setAttribute("resourceList", resourceList);
                    request.setAttribute("resourceListSize", 0);

                }
                return ViewHolder.RESOURCE_LIST;

            } catch (SQLException e) {
            	e.printStackTrace();
                _log.error("SQLException");
                return ViewHolder.ERROR_PAGE;
            }
        }else{
            _log.error("validateError");
            return ViewHolder.ERROR_PAGE;
        }

    }

}
