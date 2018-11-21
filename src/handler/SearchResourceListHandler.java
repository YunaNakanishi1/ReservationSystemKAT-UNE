package handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.AvailableDto;
import dto.Resource;
import dto.TimeDto;
import exception.MyException;
import service.GetResourceListOnSearchService;
import service.MakeAvailableListService;

public class SearchResourceListHandler implements Handler {

    private HttpServletRequest _request;
    private HttpSession _session;
    private Logger _log = LogManager.getLogger();

    @Override
    public String handleService(HttpServletRequest request) {
        _request = request;
        _session = request.getSession(true);
        List<Resource> resourceList;
        List<AvailableDto> avList;

        try{
            //リソースの検索処理
            resourceList = searchResource();

            if(resourceList.size() == 0){
                //一件もヒットしなかった
                request.setAttribute("messageForResourceSelectLower", MessageHolder.EM08);
            }
        }catch (MyException e) {
            _log.error("Search Error");
            e.printStackTrace();
            return ViewHolder.ERROR_PAGE;

        }

        try{
            //リソースの予約可能時間の計算
            avList = getAvailableList(resourceList);

            if(avList.size() == 0){
                //一件もヒットしなかった
                request.setAttribute("messageForResourceSelectLower", MessageHolder.EM08);
            }

        }catch (MyException e) {
            _log.error("Available Error");
            e.printStackTrace();
            return ViewHolder.ERROR_PAGE;
        }

        _session.setAttribute("availableListForResourceSelect", avList);
        return ViewHolder.SHOW_RESOURCE_SELECT_SERVLET;
    }
    /**
     * 検索条件に合致するリソースの一覧を取得する
     * @return
     */
    private List<Resource> searchResource(){
        //セッションから定員、カテゴリID、事業所ID、リソース特性IDリスト、リソース名を取得
        int capacity = (int)_session.getAttribute("capacityForResourceSelect");
        String categoryId =(String)_session.getAttribute("categoryIdForResourceSelect");
        String officeId = (String)_session.getAttribute("officeIdForResourceSelect");
        List<String> facilittyIdList = (List<String>)_session.getAttribute("facilityIdListForResourceSelect");
        String resourceName = (String) _session.getAttribute("resourceNameForResourceSelect");
        List<Resource> resources;

        try {
            //サービスに値を渡して検索処理
            GetResourceListOnSearchService service = new GetResourceListOnSearchService(resourceName, capacity, categoryId, officeId, facilittyIdList);
            if(service.validate()){
                service.execute();
                resources = service.getResourceList();
            }else{
                _log.error("ValidateError");
                throw new MyException();
            }

        } catch (SQLException e) {
            _log.error("SQLException");
            e.printStackTrace();
            throw new MyException();
            }

        return resources;
    }
    /**
     * 予約可能状況を取得する
     * @param resourceList
     * @return
     */
    private List<AvailableDto> getAvailableList(List<Resource> resourceList){
        TimeDto startTime = (TimeDto) _session.getAttribute("usageStartTimeForResourceSelect");
        TimeDto endTime = (TimeDto) _session.getAttribute("usageEndTimeForResourceSelect");
        TimeDto usageTime = (TimeDto) _session.getAttribute("usageTimeForResourceSelect");
        String usageDate = (String) _session.getAttribute("usageDateForResourceSelect");
        _session.setAttribute("usageDateForReservationRegist", usageDate);

        //返す結果の初期化
        List<AvailableDto> availableList = new ArrayList<>();
        try{
            MakeAvailableListService mService = new MakeAvailableListService(resourceList, startTime, endTime, usageTime, usageDate);

            if(mService.validate()){
                mService.execute();
                availableList = mService.getAvailableList();

                if(availableList == null){
                    _log.error("availableList is Null");
                    throw new MyException();
                }

            }else{
                _log.error("ValidateError");
                throw new MyException();
            }
        }
        catch (MyException | SQLException e) {
            _log.error("AvailableError");
            e.printStackTrace();
            throw new MyException();
        }

        return availableList;
    }
}
