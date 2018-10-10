package service;

import java.sql.SQLException;
import java.util.List;

import dao.ResourceDao;
import dto.Resource;

/**
 * リソース一覧を表示する.
 * displayAllの結果をHandlerに渡す.
 * @author z00h230741
 *
 */
public class ShowResourceListService implements Service{

    private List<Resource> _resourcelist;

    @Override
    public boolean validate() {

        return true;
    }

    @Override
    public void execute() throws SQLException {
        ResourceDao resourceDao = new ResourceDao();
        _resourcelist = resourceDao.displayAll();
    }

    public List<Resource> getResourceList() {
        return _resourcelist;
    }

}
