package com.github.becauseQA.lang;



import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

	public int getRandomNumber(int min, int max) {
		// rand.nextInt((max - min) + 1) + min;
		// Min + (int)(Math.random() * ((Max - Min) + 1))
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	
	public String getGUID() {
		return UUID.randomUUID().toString();
	}

	public int getMaxNumber(int[] arrays) {

		// a_n=a_1+(n-1)d
		int size = arrays.length;
		int max = 0;
		for (int k = 0; k < size; k++) {
			for (int j = 1; j < size - k; j++) {
				if (arrays[j - 1] > arrays[j]) {
					max = arrays[j - 1];
					arrays[j - 1] = arrays[j];
					arrays[j] = max;

				}
			}
		}
		//System.out.println(arrays);
		return arrays[size-1];
	}
	
	
	public int getMinNumber(int[] arrays){
		int size=arrays.length;
		int min=0;
		for(int k=0;k<size;k++){
			for(int j=1;j<size-k;j++){
				if(arrays[j-1]<arrays[j]){
					min=arrays[j-1];
					arrays[j-1]=arrays[j];
					arrays[j]=min;
				}
			}
		}
		return arrays[size-1];
	}
	public int getMinNumber2(int[] arrays){
		int size=arrays.length;
		int min=0;
		for(int k=0;k<size;k++){
			min=Math.max(arrays[k], min);
		}
		return min;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getMinNumber3(int[] arrays){
		List asList = Arrays.asList(arrays);
		Integer maxnumber = (Integer)Collections.min(asList);
		return maxnumber.intValue();
		
	}
}
