package com.allanmccombs.myserializer;

import android.content.Context;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Calendar;


@Root(name = "myserialzer")
public class Cars implements java.io.Serializable
{

    private Context _context;

    @ElementList(required=false, name="cars")
    private ArrayList<Car> mycars = new ArrayList<>();

    public Cars(Context context)
    {
        _context = context;
    }
    public ArrayList<Car> getCars() { return mycars; }

    public void addCar(Car car)
    {
        car.setCarID(mycars.size() + 1);
        mycars.add(car);
    }

    public int getSize()
    {
        return mycars.size();
    }
   
}
