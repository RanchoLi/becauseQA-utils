package com.github.becauseQA.args;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * ClassName: MonthValidator  
 * Function: TODO ADD FUNCTION.  
 * Reason: TODO ADD REASON 
 * date: Apr 23, 2016 6:06:25 PM  
 * @author alterhu2020@gmail.com
 * @version 1.0.0
 * @since JDK 1.8
 */
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
