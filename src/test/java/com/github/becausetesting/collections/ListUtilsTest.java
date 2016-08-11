package com.github.becausetesting.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.becausetesting.apache.commons.ArrayUtils;

public class ListUtilsTest {

	public int[] arrayData={3,108,4,231,100,54,21,2,72};
	@Test
	public void test() {
		
		List<Integer> asList = Arrays.asList(ArrayUtils.toObject(arrayData));
		List<Integer> list=new ArrayList<Integer>();
		list.addAll(asList);
		ListUtils.sortList(list);
		System.out.println(list.toString());
	}

}
