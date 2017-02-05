package com.example.charlie.mouse;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener,SensorEventListener,View.OnClickListener {


    TextView textView ;
    Button button;
    Sensor acc;
    SensorManager sm;
    client c;
    private  boolean StartButton=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        acc=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        textView = (TextView)findViewById(R.id.textView);
        button=(Button)findViewById(R.id.stop);
        button.setOnClickListener(this);
        try {
            c=new client("192.168.0.103",8080);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("asdfasdfa","asdfasdf");
        textView.setText("Touch coordinates : " +
                String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
        return true;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String msg="X:"+ (int)event.values[0]+"," + "Y:"+(int)event.values[1] +", "+"Z:"+(int)event.values[2]+"";
        c.list.add(msg);
        textView.setText(msg);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    @Override
    public void onClick(View v) {
        if(StartButton){
            button.setText("stop");
            StartButton=false;
            sm.registerListener(this,acc,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            button.setText("start");
            StartButton=true;
            sm.unregisterListener(this);
        }
    }
}