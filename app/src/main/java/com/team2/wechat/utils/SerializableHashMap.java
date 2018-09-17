package com.team2.wechat.utils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by wjsay on 2017/12/7.
 */

public class SerializableHashMap implements Serializable {
    private HashMap<String,Object> map;

    public SerializableHashMap () {

    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }
}
