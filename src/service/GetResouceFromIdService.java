/*
 * Copyright© Ricoh IT Solutions Co.,Ltd.
 * All Rights Reserved.
 */
package service;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.ResourceDao;
import dto.Resource;
import exception.MyException;

/**
 * (S3).
 * @author リコーITソリューションズ株式会社 KAT-UNE
 */
public class GetResouceFromIdService implements Service{

	 private Resource _resource;
	 private String _resourceId;
	 private static Logger _log = LogManager.getLogger();



	 public GetResouceFromIdService(String resourceId) throws MyException {
		_resourceId = resourceId;
		 if(resourceId == null){
			throw new MyException();
		}
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

	public Resource getResource(){
		return _resource;

	}



}
