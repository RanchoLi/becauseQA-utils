/**
 * Project Name:commons
 * File Name:MapUtils.java
 * Package Name:com.github.becausetesting.collections
 * Date:Apr 24, 20164:44:57 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * ClassName:MapUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 24, 2016 4:44:57 PM 
 * @author   alterhu2020@gmail.com
 * @version  1.0.0
 * @since    JDK 1.8 
 */
public class MapUtils {

	
	
	public void iteratorMap(){
		Map<String,String> mapobj=new HashMap<>();
		Set<Entry<String,String>> entrySet = mapobj.entrySet();
		
		Iterator<Entry<String, String>> iterator = entrySet.iterator();
		
		Set<String> keySet = mapobj.keySet();
		
	}
	
}

