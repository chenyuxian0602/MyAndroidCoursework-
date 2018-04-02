package com.example.experience3_basic;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 陈昱宪 on 2016/10/16.
 */

public class my_people{
    private String name;
    private String phone;
    private String type;
    private String from;
    private int bgc;

    public my_people(String _name, String _phone, String _type, String _from, int _bgc) {
        this.bgc = _bgc;
        this.from = _from;
        this.phone = _phone;
        this.name = _name;
        this.type = _type;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public int getBgc() {
        return bgc;
    }
}
