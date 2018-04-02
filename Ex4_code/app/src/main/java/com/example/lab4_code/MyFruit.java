package com.example.lab4_code;

import java.io.Serializable;

/**
 * Created by 陈昱宪 on 2016/10/23.
 */

public class MyFruit implements Serializable {
    private String name;
    private int id;

    public MyFruit(String _n, int _id) {
        name = _n;
        id = _id;
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
}
