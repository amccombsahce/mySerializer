package com.allanmccombs.myserializer;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Calendar;


@Root
public class Car implements java.io.Serializable
{

    @Element
    private int carID;
    @Element(required=false)
    private Calendar dateManufature;


    public Car()
    {
        java.util.Date date =  new java.util.Date();
        setDateManufature(date);
    }

    public int getCarID()
    {
        return carID;
    }
    public void setCarID(int value)
    {
        carID = value;
    }

    public void setDateManufature(java.util.Date date)
    {
        dateManufature = Calendar.getInstance();

        dateManufature.setTime(date);
    }
   
}
