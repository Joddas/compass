package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.hardware.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView textView;
    //private ImageView imageView;
    private SensorManager sensorManager;
    private Sensor acceletometer, magnetometer;

    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnetometer = new float[3];
    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];

    boolean isLastAccelerometerArrayCopied = false;
    boolean isLastMagnetometerArrayCopied = false;

    long lastUpdatedTime = 0;
    float currentDegree = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.timertext);
        //imageView = findViewById(R.id.imageView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acceletometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == acceletometer){
            System.arraycopy(sensorEvent.values,0,lastAccelerometer,0,sensorEvent.values.length);
            isLastAccelerometerArrayCopied = true;
        }else if(sensorEvent.sensor == magnetometer){
            System.arraycopy(sensorEvent.values,0,lastMagnetometer,0,sensorEvent.values.length);
            isLastMagnetometerArrayCopied = true;
        }

        if(isLastAccelerometerArrayCopied && isLastMagnetometerArrayCopied && System.currentTimeMillis() - lastUpdatedTime > 250){
            SensorManager.getRotationMatrix(rotationMatrix,null,lastAccelerometer,lastMagnetometer);
            SensorManager.getOrientation(rotationMatrix,orientation);

            float azimuthInRadians = orientation[0];
            float azimuthInDegree = (float) Math.toDegrees(azimuthInRadians);

            //RotateAnimation rotateAnimation = new RotateAnimation(currentDegree, -azimuthInDegree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            //rotateAnimation.setDuration(250);
            //rotateAnimation.setFillAfter(true);
            //imageView.startAnimation(rotateAnimation);
            currentDegree = -azimuthInDegree;
            lastUpdatedTime = System.currentTimeMillis();
            int x = (int) azimuthInDegree;
            textView.setText("from North: " + x);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,acceletometer,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,magnetometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, acceletometer);
        sensorManager.unregisterListener(this, magnetometer);
    }
}


//public class MainActivity extends AppCompatActivity {
//
//    DrawingSpace myDrawing;
//    Random alea;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        alea = new Random(System.currentTimeMillis());
//        myDrawing = new DrawingSpace(this,alea);
//        setContentView(R.layout.activity_main);
//        LinearLayout ll = findViewById(R.id.ll);
//        ll.addView(myDrawing);
//    }
//
//    /**
//     * when the button is clicked, both circle centers will be
//     * changed
//     * @param v
//     */
//    public void moveCircles(View v) {
//        myDrawing.changeCirclesCenters(50+alea.nextInt(600),
//                50+alea.nextInt(600),
//                50+alea.nextInt(600),
//                50+alea.nextInt(600));
//    }
//
//    public void startTimer(View v){
//        myDrawing.counter=0;
//        TextView timertext = (TextView) findViewById(R.id.timertext);
//        new CountDownTimer(15000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                timertext.setText("seconds remaining: " + millisUntilFinished / 1000 + "  ");
//            }
//
//            public void onFinish() {
//                timertext.setText("Time's up! Your score: " + myDrawing.counter + "  ");
//            }
//
//        }.start();
//    }
//}
//
//class DrawingSpace extends View implements View.OnTouchListener,
//        View.OnClickListener {
//
//    Random alea;
//    Paint style1,style2;
//    float x1 = 400.0f, x2 = 400.0f;
//    float y1 = 300.0f, y2 = 500.0f;
//    float xrect1 = 100.0f, yrect1 = 100.0f;
//    int counter=0;
//
//    public DrawingSpace(Context context, Random alea) {
//        super(context);
//        this.alea = alea;
//        init();
//    }
//
//    /**
//     * onDraw is a callback method and thus the canvas
//     * parameter is given by the system to me (programmer)
//     * for creating new geometric objects to be displayed
//     * @param canvas
//     */
//    public void onDraw(Canvas canvas) {
//        canvas.drawColor(Color.WHITE);
//        //canvas.drawRect(xrect1,yrect1,xrect1+100,yrect1+100,style1);
//        //canvas.drawRect(100,310,200,500,style2);
//        //canvas.drawCircle(x1,y1,50,style2);
//        canvas.drawCircle(x2,y2,70,style1);
//        canvas.drawCircle(x2,y2,40,style1);
//        canvas.drawCircle(x2,y2,10,style1);
//    }
//
//    /**
//     * initialization of the styles and
//     * register this object as listener of touch and click events
//     * on itself
//     */
//    public void init() {
//        style1 = new Paint();
//        style1.setColor(Color.BLACK);
//        style1.setAntiAlias(true);
//        style1.setStyle(Paint.Style.STROKE);
//        style1.setStrokeWidth(5.0f);
//        style2 = new Paint();
//        style2.setColor(Color.BLUE);
//        style2.setAntiAlias(false);
//        style2.setStyle(Paint.Style.FILL);
//        this.setOnClickListener(this);
//        this.setOnTouchListener(this);
//    }
//
//    public void changeCirclesCenters(float x1, float y1, float x2, float y2) {
//        this.x1 = x1;
//        this.x2 = x2;
//        this.y1 = y1;
//        this.y2 = y2;
//        // force android to redraw the scene
//        // invalidate() triggers a call to onDrawn()
//        this.invalidate();
//    }
//
//    public void hideCircle(Canvas canvas){
//
//    }
//
//    /**
//     * when the finger is removed from the screen this
//     * triggers a call to this onClick method
//     * @param view
//     */
//    @Override
//    public void onClick(View view) {
////        style2.setColor(Color.rgb(50+alea.nextInt(200),
////                50+alea.nextInt(200),
////                50+alea.nextInt(200)));
//        if(xrect1<this.x2+70 && xrect1>this.x2-70 && yrect1<this.y2+70 && yrect1>this.y2-70){
//            counter++;
//            changeCirclesCenters(70+alea.nextInt(600),
//                    70+alea.nextInt(600),
//                    70+alea.nextInt(600),
//                    70+alea.nextInt(600));
//        }
//        this.invalidate();
//    }
//
//    /**
//     * this method is called by Android as soon as one Action on the screen is
//     * performed ACTION_DOWN (finger arrives on the screen)
//     * ACTION_MOVE (finger slides on the screen)
//     * ACITON_UP (finger quits the screen)
//     * @param view
//     * @param motionEvent
//     * @return
//     */
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        xrect1 = motionEvent.getX();
//        yrect1 = motionEvent.getY();
//        this.invalidate();
//        // when false is returned, the motionEvent is transmitted
//        // to the onClick() method otherwise the motionEvent is
//        // consumed by the onTouch() method.
//        return false;
//    }
//}


//public class MainActivity extends AppCompatActivity implements SensorEventListener {
//    private CanvasView canvas;
//
//    Random random = new Random();
//
//    private int circleRadius = 30;
//    private float circleX;
//    private float circleY;
//
//    private int circleRadius2 = 60;
//    private float circleX2;
//    private float circleY2;
//
//    private Timer timer;
//    private Handler handler;
//
//    private SensorManager sensorManager;
//    private Sensor accelerometer, magnetometer;
//
//    private float sensorX;
//    private float sensorY;
//    private float sensorZ;
//    private long lastSensorUpdateTime = 0;
//
//    private TextView textView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
//
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//
//        int screenWidth = size.x;
//        int screenHeight = size.y;
//
//        //circleX = screenWidth / 2 - circleRadius;
//        //circleY = screenHeight / 2 - circleRadius;
//
//        circleX = (float) (Math.random() * ((screenWidth-circleRadius)-circleRadius) + circleRadius);
//        circleY = (float) (Math.random() * ((screenHeight-circleRadius)-circleRadius) + circleRadius);
//
//        circleX2 = (float) (Math.random() * ((screenWidth-circleRadius2)-circleRadius2) + circleRadius2);
//        circleY2 = (float) (Math.random() * ((screenHeight-circleRadius2)-circleRadius2) + circleRadius2);
//
//        canvas = new CanvasView(MainActivity.this);
//        setContentView(canvas);
//
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message message){
//                canvas.invalidate();
//            }
//        };
//
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Vibrator vibor = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//
//                if(sensorX < 0){
//                    if(circleX<=screenWidth) {
//                        circleX += 5;
//                        if(circleX==screenWidth){vibor.vibrate(500);}
//                    }
//                } else{
//                    if(circleX>=0) {
//                        circleX -= 5;
//                        if(circleX==0){vibor.vibrate(500);}
//                    }
//                }
//
//                if(sensorY < 0){
//                    if(circleY<=screenHeight) {
//                        circleY += 5;
//                        if(circleY==screenHeight){vibor.vibrate(500);}
//                    }
//                } else{
//                    if(circleY>=0) {
//                        circleY -= 5;
//                        if(circleY==0){vibor.vibrate(500);}
//                    }
//                }
//
//                if(circleX+circleRadius>=circleX2-circleRadius2 && circleX-circleRadius<=circleX2+circleRadius2 &&
//                        circleY+circleRadius>=circleY2-circleRadius2 && circleY-circleRadius<=circleY2+circleRadius2){
//                    finish();
//                    System.exit(0);
//                }
//
//
//                //circleY--;
//                handler.sendEmptyMessage(0);
//            }
//        },0,100);
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent) {
//        Sensor mySensor = sensorEvent.sensor;
//
//        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
//            sensorX = sensorEvent.values[0];
//            sensorY = sensorEvent.values[1];
//            sensorZ = sensorEvent.values[2];
//
//            long currentTime = System.currentTimeMillis();
//
//            if((currentTime - lastSensorUpdateTime) > 100){
//                lastSensorUpdateTime=currentTime;
//
//                sensorX = sensorEvent.values[0];
//                sensorY = sensorEvent.values[1];
//                sensorZ = sensorEvent.values[2];
//            }
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//
//    }
//
//    private class CanvasView extends View{
//
//        private Paint pen;
//        private Paint pen2;
//
//        public CanvasView(Context context){
//            super(context);
//            setFocusable(true);
//
//            pen = new Paint();
//            pen2 = new Paint();
//        }
//
//        public void onDraw(Canvas screen){
//            pen.setStyle(Paint.Style.FILL);
//            pen.setAntiAlias(true);
//            pen.setTextSize(30f);
//
//            pen2.setStyle(Paint.Style.FILL);
//            pen2.setColor(Color.BLUE);
//            pen2.setAntiAlias(true);
//            pen2.setTextSize(30f);
//
//            screen.drawCircle(circleX, circleY, circleRadius, pen);
//            screen.drawCircle(circleX2, circleY2, circleRadius2, pen2);
//        }
//    }
//
//
//}

