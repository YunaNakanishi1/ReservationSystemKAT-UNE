package service;

import java.sql.SQLException;

import dao.ResourceDao;

public class DeleteResourceService implements Service{

	private String _resourceId;   //リソースID
	private int  _result;         //削除結果

	public DeleteResourceService(String resourceId){
		_resourceId = resourceId;
	}

	@Override
	public boolean validate(){
		return true;
	}

	public void execute() throws SQLException {
		ResourceDao resourceDao = new ResourceDao();
		//削除フラグ立て処理
		_result = resourceDao.delete(_resourceId);
	}

	public int getResult(){
		return _result;
	}
}
