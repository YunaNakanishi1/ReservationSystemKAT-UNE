package service;

import static handler.MessageHolder.*;

import dto.Resource;

public class ServiceValidator {


	private String _validateMessage;


	public String getValidateMessage() {
		return _validateMessage;
	}


	public boolean setResourseDetailValidate(Resource resource){


		String resourceName=resource.getResourceName();
		if(resourceName.length()>30){
			_validateMessage=EM28;
			return false;

		}

		int capacity=resource.getCapacity();
		if(capacity<0||999<capacity){
			_validateMessage=EM32;
			return false;

		}




	}



}
