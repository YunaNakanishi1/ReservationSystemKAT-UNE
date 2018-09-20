package service;

import java.sql.SQLException;

import dao.ResourceDao;
import dto.Resource;

public class ShowResourceDetailsService implements Service {

    private String _resourceId;
    private  Resource _resource;

    public ShowResourceDetailsService(String resourceId) {
        _resourceId = resourceId;
    }
    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() throws SQLException {
        ResourceDao resourceDao = new ResourceDao();
        _resource = resourceDao.displayDetails(_resourceId);

    }
    public Resource getResult() {
        return _resource;
    }

}
