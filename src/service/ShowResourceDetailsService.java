/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;

import dao.ResourceDao;
import dto.Resource;


/**
*
* リソース詳細画面に表示するリソースDTOを取得するサービス.
* @author リコーITソリューションズ株式会社 KAT-UNE
*
*/
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
