package handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Resource;
import service.ShowResourceListService;
public class ShowResourceListHandler implements Handler{

    private static Logger _log = LogManager.getLogger();
    private static int SUPPLEMENT_MAX = 30;

    @Override
    public String handleService(HttpServletRequest request) {
        //セッションから権限を取得
        int authority = 0; //0 権限あり 1 なし

        HttpSession httpSession = request.getSession(false);
        //フィルタを通しているのでセッションは必ず存在する
        authority = (int) httpSession.getAttribute("authority");


        ShowResourceListService service = new ShowResourceListService();
        List<Resource> resourceList;

        if(service.validate()){
            try {
                //実行
                service.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                _log.error("SQLException");
                return ViewHolder.ERROR_PAGE;
            }
            resourceList = service.getResourceList();

        }else{
            _log.error("validateError");
            return ViewHolder.ERROR_PAGE;
        }

        if(authority != 0){
            //権限のないユーザの処理
            List<Resource> forNotAuthorityResourceList = new ArrayList<>();
            //削除済みでないものをリストにセット
            for (Resource resource : resourceList) {
                if(resource.getDeleted() == 0){
                    forNotAuthorityResourceList.add(resource);
                }
            }
            //権限のない人用のリストをセットするためのリストに代入
            resourceList = forNotAuthorityResourceList;//追記

            //                    if(forNotAuthorityResourceList.size() == 0){
            //                        request.setAttribute("Emessage", "検索結果は0件です。");
            //                    }
            //                    request.setAttribute("resourceList", forNotAuthorityResourceList);
            //                    request.setAttribute("resourceListSize", forNotAuthorityResourceList.size());
        }
        //else{
        //権限のあるユーザとないユーザの同時処理
        if(resourceList.size() == 0){
            request.setAttribute("Emessage", "検索結果は0件です。");
        }

        //補足が30文字を超えた場合、「・・」で省略を行う
        for (int i=0; i<resourceList.size();i++) {
            Resource resource = resourceList.get(i);
            if(resource.getSupplement().length() > SUPPLEMENT_MAX){
                //先頭の文字を取得して「・・」と連結
                String shortSupplement = resource.getSupplement().substring(0, SUPPLEMENT_MAX)+"・・";

                //新規にインスタンスを生成してリストと置き換え
                resource = new Resource(resource.getResourceId(),
                        resource.getResourceName(),
                        resource.getOfficeName(),
                        resource.getCategory(),
                        resource.getCapacity(),
                        shortSupplement,
                        resource.getDeleted(),
                        resource.getFacility(),
                        resource.getUsageStopStartDate(),
                        resource.getUsageStopEndDate());

                resourceList.set(i, resource);
            }
        }
        request.setAttribute("resourceList", resourceList);
        request.setAttribute("resourceListSize", resourceList.size());

        //ステータスの設定
        List<String> statusList = new ArrayList<>();
        //現在時刻取得
        Date now = Calendar.getInstance().getTime();
        System.out.println("now"+now);


        for (Resource resource : resourceList) {

            if(resource.getDeleted() == 1){
                statusList.add("削除済み");
            }
            else if(resource.getUsageStopStartDate() == null || resource.getUsageStopEndDate() == null){
                statusList.add("利用可能");
            }
            //beforeとafterのメソッドを逆にした
            //旧
            //else if(resource.getUsageStopStartDate().after(now) && resource.getUsageStopEndDate().before(now)){
            else if(resource.getUsageStopStartDate().before(now) && resource.getUsageStopEndDate().after(now)){
                statusList.add("利用停止");
            }else{
                statusList.add("利用可能");
            }
        }
        request.setAttribute("statusList", statusList);

        //}
        return ViewHolder.RESOURCE_LIST;



    }

}
