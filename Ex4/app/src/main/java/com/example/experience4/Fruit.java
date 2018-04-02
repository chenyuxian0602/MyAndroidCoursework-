package com.example.experience4;

import java.io.Serializable;

/**
 * Created by 陈昱宪 on 2016/10/24.
 */

public class Fruit implements Serializable {
    private int img;
    private String name;

    public Fruit(int _img, String _name) {
        img = _img;
        name = _name;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }
}
