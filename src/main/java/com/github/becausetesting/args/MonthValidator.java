package com.github.becausetesting.args;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class MonthValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		// TODO Auto-generated method stub
		int month = Integer.parseInt(value);
		if(month<1||month>12){
			throw new ParameterException("Parameter " + name + " should be between 1 and 12");
		}
	}

}
