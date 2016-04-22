/**
 * Project Name:commons
 * File Name:JSONUtils.java
 * Package Name:com.github.becausetesting.json
 * Date:Apr 17, 20162:30:48 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * ClassName:JSONUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 17, 2016 2:30:48 PM 
 * @author   Administrator
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class JSONUtils {

	
	private static Gson gson;
	static{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.setPrettyPrinting().serializeNulls().create();
	}
	
	/**
	 * toJson: 
	 * TODO
	 * TODO
	 * TODO 
	 * TODO serivziable
	 *
	 * @author Administrator
	 * @param jsonElement
	 * @return
	 * @since JDK 1.8
	 */
	public String fromObject(Object jsonElement){
		return gson.toJson(jsonElement);
	}
	
	public <T> T toJson(String json,Class<T> classOfT){
		return gson.fromJson(json, classOfT);
	}
	
	public JsonElement toJsonElementParse(String json){
		JsonParser parser=new JsonParser();
		JsonElement JsonElement= parser.parse(json);
		//JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
		//JsonArray jsonArray = jsonElement.getAsJsonArray();
		return JsonElement;
	}
	public JsonElement toJsonElement(String json){
		
		JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
		//JsonObject jsonObject = jsonElement.getAsJsonObject();
		return jsonElement;
	}
}
