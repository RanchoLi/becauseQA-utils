package com.github.becausetesting.random;



import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

	public int getRandomNumber(int min, int max) {
		// rand.nextInt((max - min) + 1) + min;
		// Min + (int)(Math.random() * ((Max - Min) + 1))
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public String getRandomString(int len){
		String result="";
		String alphabet ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; //9
		int n = alphabet.length(); //10
		for(int k=0;k<len;k++){
			 result = result + alphabet.charAt(new SecureRandom().nextInt(n)); //13
		}
		return result;
		
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
	
}
