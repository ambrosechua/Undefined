package io.makerforce.undefined.model;

import org.json.JSONObject;

import java.net.URL;

@Deprecated
public class JSONLibrary extends Library {

    public JSONLibrary(JSONObject o, URL endPoint) {
        super(o, endPoint);
    }

}
