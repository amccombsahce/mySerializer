package com.allanmccombs.myserializer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{

    private Context _context;
    private Locale _locale;
    private String _filename;
    private String _foldername;

    public Cars _cars;

    private TextView layout_textview_carcount;

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(updateBaseContextLocale(base));
    }
    public Context updateBaseContextLocale(Context context)
    {
        Locale locale001 = new Locale("es", "MX");
        //Locale locale001 = new Locale("en", "US");

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale001);
        Locale.setDefault(locale001);
        Context context002 = context.createConfigurationContext(configuration);
        _context = context002;
        _locale = locale001;
        return context002;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        layout_textview_carcount = findViewById(R.id.layout_textview_carcount);
        setTitle(getString(R.string.app_name));

        _foldername = String.format("%s/%s", Environment.getExternalStorageDirectory().getAbsolutePath(),
                _context.getString(R.string.app_name_folder));

        File folders = new File(_foldername);
        boolean b = folders.mkdirs();

        _filename = String.format("%s/%s",  _foldername, "cars.xml");


        _cars = new Cars(_context);

        readFile();

        layout_textview_carcount.setText(String.format(_locale, "%d", _cars.getSize()));

    }

    protected void buttonClick(View v)
    {
        Car car = new Car();
        _cars.addCar(car);

        writeFile();

        layout_textview_carcount.setText(String.format(_locale, "%d", _cars.getSize()));
    }
    public boolean readFile()
    {
        boolean bReturn = false;
        try
        {
            File file = new File(_filename);
            if (file.exists())
            {
                org.simpleframework.xml.Serializer serializerREAD = new Persister();
                serializerREAD.read(_cars, file);
                bReturn = true;
            }
        }
        catch (Exception ex)
        {
            String error = ex.getLocalizedMessage();

            int breakpoint = 1;

            // unparsable date
            if (ex.getMessage().contains("Unparseable date"))
            {

                File file = new File(_filename);
                file.delete();
            }

        }
        return bReturn;
    }
    public boolean writeFile()
    {
        boolean bReturn = false;
        try
        {
            File file = new File(_filename);
            org.simpleframework.xml.Serializer serializer = new Persister();
            serializer.write(_cars, file);
            bReturn = true;
        }
        catch (Exception ex)
        {
            String error = ex.getLocalizedMessage();

            int breakpoint = 1;
        }
        return bReturn;
    }
}
