package service;

import java.sql.SQLException;
import java.util.List;

import dao.ResourceDao;
import dto.Resource;
import exception.MyException;

/**
 * 引数で渡された条件から合致するリソースのリストを返す
 *
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 */
public class GetResourceListOnSearchService implements Service{


    private List<Resource> _resourceList;
    private String _resourceName;
    private int _capacity;
    private String _categoryId;
    private String _officeId;
    private List<String> _facilityIdList;


    public GetResourceListOnSearchService(String _resourceName, int _capacity, String _categoryId, String _officeId,
            List<String> _facilityIdList) {
        if(_resourceName == null || _categoryId == null || _officeId == null || _facilityIdList == null){
            throw new MyException();
        }
        this._resourceName = _resourceName;
        this._capacity = _capacity;
        this._categoryId = _categoryId;
        this._officeId = _officeId;
        this._facilityIdList = _facilityIdList;
    }

    @Override
    public boolean validate() {
        // 常にtrueを返す
        return true;
    }

    @Override
    public void execute() throws SQLException {

        ResourceDao rDao = new ResourceDao();

    }
    public List<Resource> getResourceList(){
        return _resourceList;
    }

}
