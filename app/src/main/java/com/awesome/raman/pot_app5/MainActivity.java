package com.awesome.raman.pot_app5;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    public static final String DATA_FOLDER = "/storage/sdcard0/Downloads";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void start(View v){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements SensorEventListener,LocationListener{

        long startTime = 0;
        long stopTime = 0;
        ArrayList<DataObject> appData = new ArrayList<>();
        ArrayList<EventData> eventData = new ArrayList<>();
        int id=0;
        float px_Acc = 0;
        float py_Acc = 0;
        float pz_Acc = 0;
        float px_Gyro = 0;
        float py_Gyro = 0;
        float pz_Gyro = 0;
        SensorManager sensorManager;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Button startButton = (Button)rootView.findViewById(R.id.start);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = 0;
                    appData = new ArrayList<>();
                    eventData = new ArrayList<>();

                    sensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
                    sensorManager.registerListener(PlaceholderFragment.this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                    sensorManager.registerListener(PlaceholderFragment.this,sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_NORMAL);
                    startTime = System.currentTimeMillis();
                }
            });

            Button stopButton = (Button)rootView.findViewById(R.id.stop);
            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopTime = System.currentTimeMillis();
                    long currTime = System.currentTimeMillis();
                    StringBuilder metaData = new StringBuilder();
                    metaData.append("Metadata File \n");
                    metaData.append("Starting Time:- " + new Date(startTime) + "\n");
                    metaData.append("Stopping Time:- " + new Date(stopTime) + "\n");
                    metaData.append("Total Data Points Collected :- " + appData.size() + "\n");
                    metaData.append("Total Events Collected :- " + eventData.size() + "\n");
                    sensorManager.unregisterListener(PlaceholderFragment.this);
                    final String dataFileName = "data" + currTime + ".csv";
                    final String eventFileName = "event" + currTime + ".csv";
                    final String metaFileName = "meta" + currTime + ".csv";
                    File dir = new File(MainActivity.DATA_FOLDER);
                    if(dir.exists() == false){
                        dir.mkdirs();
                        Log.d("onStart","Directory created.");
                    }
                    File dataFile = new File(dir,dataFileName);
                    File eventFile = new File(dir,eventFileName);
                    File metaFile = new File(dir,metaFileName);
                    try{
                        FileWriter dataWriter = new FileWriter(dataFile);
                        dataWriter.write(TextUtils.join("\n",appData));
                        dataWriter.close();
                        Log.d("hello","dataFile saved");

                        dataWriter = new FileWriter(eventFile);
                        dataWriter.write(TextUtils.join("\n",eventData));
                        dataWriter.close();
                        Log.d("hello","eventFile saved");

                        dataWriter = new FileWriter(metaFile);
                        dataWriter.write(metaData.toString());
                        dataWriter.close();
                        Log.d("hello","metaFile saved");

                    }
                    catch (IOException ex){
                        Log.e("FILE_WRITING",ex.toString());
                    }
                }
            });

            Button potholeButton = (Button)rootView.findViewById(R.id.pothole);
            potholeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long timeStamp = System.currentTimeMillis();
                    EventData data = new EventData(timeStamp,roadEvent.POTHOLE.getValue());
                    eventData.add(data);
                }
            });


            Button bumpButton = (Button)rootView.findViewById(R.id.bump);
            bumpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long timeStamp = System.currentTimeMillis();
                    EventData data = new EventData(timeStamp,roadEvent.BUMP.getValue());
                    eventData.add(data);
                }
            });


            Button brakeButton = (Button)rootView.findViewById(R.id.brake);
            brakeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long timeStamp = System.currentTimeMillis();
                    EventData data = new EventData(timeStamp,roadEvent.BRAKE.getValue());
                    eventData.add(data);
                }
            });


            Button roughroadButton = (Button)rootView.findViewById(R.id.rough_road);
            roughroadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long timeStamp = System.currentTimeMillis();
                    EventData data = new EventData(timeStamp,roadEvent.ROUGH_ROAD.getValue());
                    eventData.add(data);
                }
            });

            return rootView;
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Sensor sensor = sensorEvent.sensor;
            if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                px_Acc = sensorEvent.values[0];
                py_Acc = sensorEvent.values[1];
                pz_Acc = sensorEvent.values[2];
                DataObject data = new DataObject(id++,System.currentTimeMillis(),px_Acc,py_Acc,pz_Acc,px_Gyro,py_Gyro,pz_Gyro);
                appData.add(data);
            }else if(sensor.getType() == Sensor.TYPE_GYROSCOPE){
                px_Gyro = sensorEvent.values[0];
                py_Gyro = sensorEvent.values[1];
                pz_Gyro = sensorEvent.values[2];
                DataObject data = new DataObject(id++,System.currentTimeMillis(),px_Acc,py_Acc,pz_Acc,px_Gyro,py_Gyro,pz_Gyro);
                appData.add(data);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
}
