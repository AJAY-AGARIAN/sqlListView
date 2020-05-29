package com.indianrailway.sqllistview;

public class classListItems
{

    public String img; //Image URL
    public String name; //Name

    public classListItems(String name, String img)
    {
        this.img = img;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }
}

