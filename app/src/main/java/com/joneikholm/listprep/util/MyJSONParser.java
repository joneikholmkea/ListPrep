package com.joneikholm.listprep.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MyJSONParser {
    JSONParser jsonParser = new JSONParser();
    public JSONObject parse(String s){
        System.out.println("parsing string: ");
        System.out.println(s);
        try {
            Object o = jsonParser.parse(s);
            JSONObject jsonObject = (JSONObject) o;
            return jsonObject;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
