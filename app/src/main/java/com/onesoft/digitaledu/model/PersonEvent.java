package com.onesoft.digitaledu.model;

/**
 * Created by Jayden on 2016/11/17.
 */

public class PersonEvent {

    public int type;
    public PersonInfo data;

    public PersonEvent(int type, PersonInfo data) {
        this.type = type;
        this.data = data;
    }
}
