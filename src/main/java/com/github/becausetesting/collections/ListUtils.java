/**
 * Project Name:commons
 * File Name:ListUtils.java
 * Package Name:com.github.becausetesting.collections
 * Date:Apr 17, 20168:49:15 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.collections;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * ClassName:ListUtils  
Collection
├List
│├LinkedList
│├ArrayList
│└Vector
│　└Stack
└Set
Map
├Hashtable
├HashMap
└WeakHashMap
 * @author   Administrator
 * @version  1.0.0
 * @since    JDK 1.8 
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ListUtils {

	
	public String list2String(List list){
		return list.toString();
	}
	
	
	public String list2String(List list,String delimiter){
		return String.join(delimiter, list);
	}
	
	public Object[] list2Array(List list){
		return list.toArray();
	}
	
	
	public List fromArray(String[] arrays){
		return Arrays.asList(arrays);
	}
	
	//sorted list,Collections.sort
	
	/**
	 * sortList: Sorts the specified list into ascending order, 
	 * according to the natural ordering of its elements. 
	 * All elements in the list must implement the Comparable interface. 
	 * Furthermore, all elements in the list must be mutually
	 *  comparable (that is, e1.compareTo(e2) must not throw a ClassCastException 
	 *  for any elements e1 and e2 in the list). 
	 *  default is asc
	 *  http://blog.csdn.net/zxy_snow/article/details/7232035
	 * @author alterhu2020@gmail.com
	 * @param list
	 * @since JDK 1.8
	 */
	public void sortList(List list){
		 Collections.sort(list);
		 Collections.sort(list,Collator.getInstance(java.util.Locale.CHINA)); //sort
		 //desc
		 Collections.reverse(list);//
	}
	
	public ArrayList toArrayList(LinkedList list){
		ArrayList arrayList = new ArrayList(list);
		return arrayList;
	}
	
}

