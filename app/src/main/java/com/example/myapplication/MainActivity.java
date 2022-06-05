package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

//public class MainActivity extends AppCompatActivity {
//
//    TextView tv;
//    EditText et;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // this method seetContentView instantiates
//        // all the elements that are defined in the resources
//        // files --> objects that are created: LinearLayout
//        // TextView, EditText, Button and also a String
//        setContentView(R.layout.activity_main);
//        // after a call to this method, every View described in the
//        // layout xml file has an instance of an object
//        tv = findViewById(R.id.mytextview);
//        et = findViewById(R.id.myedittext);
//    }
//
//    /**
//     * this is the method associated to the Button
//     * the definition of the method is always the same
//     * public void nameOfTheMethod(View v) {}
//     * ---- here, when the Button is clicked the content
//     * of the EditText is read and copy into the TextView
//     * @param v
//     */
//    public void buttonMethod(View v) {
//        //System.out.println("======== BUTTON WAS CLICKED!! =======");
//        String text = et.getText().toString();
//        tv.setText(text);
//    }
//}



//public class MainActivity extends AppCompatActivity {
//
//    DrawingSpace myDrawing;
//    Random alea;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        myDrawing=new DrawingSpace(this);
//        setContentView(R.layout.activity_main);
//        LinearLayout ll = findViewById(R.id.ll);
//        ll.addView(myDrawing);
//        alea=new Random(System.currentTimeMillis());
//    }
//
//    public void moveCircles(View v){
//        myDrawing.changeCirclesCenters(50+alea.nextInt(600),
//                50+alea.nextInt(600), 50+alea.nextInt(600), 50+alea.nextInt(600));
//    }
//
//
//    class DrawingSpace extends View{
//
//        Paint style1,style2;
//        float x1=400.0f, x2=400.0f;
//        float y1=300.0f, y2=500.0f;
//
//        public DrawingSpace(Context context){
//            super(context);
//            initStyles();
//        }
//
//        public void onDraw(Canvas canvas){
//            canvas.drawColor(Color.WHITE);
//            canvas.drawRect(100,100,200,300,style1);
//            canvas.drawRect(100,310,200,500,style2);
//            canvas.drawCircle(x1,y1,50,style2);
//            canvas.drawCircle(x2,y2,50,style1);
//        }
//
//        public void initStyles(){
//            style1=new Paint();
//            style1.setColor(Color.RED);
//            style1.setAntiAlias(true);
//            style1.setStyle(Paint.Style.STROKE);
//            style1.setStrokeWidth(5.0f);
//            style2 = new Paint();
//            style2.setColor(Color.BLUE);
//            style2.setAntiAlias(false);
//            style2.setStyle(Paint.Style.FILL);
//        }
//
//        public void changeCirclesCenters(float x1, float y1, float x2, float y2){
//            this.x1=x1;
//            this.x2=x2;
//            this.y1=y1;
//            this.y2=y2;
//            this.invalidate();
//        }
//    }
//}


public class MainActivity extends AppCompatActivity {

    DrawingSpace myDrawing;
    Random alea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alea = new Random(System.currentTimeMillis());
        myDrawing = new DrawingSpace(this,alea);
        setContentView(R.layout.activity_main);
        LinearLayout ll = findViewById(R.id.ll);
        ll.addView(myDrawing);
    }

    /**
     * when the button is clicked, both circle centers will be
     * changed
     * @param v
     */
    public void moveCircles(View v) {
        myDrawing.changeCirclesCenters(50+alea.nextInt(600),
                50+alea.nextInt(600),
                50+alea.nextInt(600),
                50+alea.nextInt(600));
    }

    public void startTimer(View v){
        myDrawing.counter=0;
        TextView timertext = (TextView) findViewById(R.id.timertext);
        new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                timertext.setText("seconds remaining: " + millisUntilFinished / 1000 + "  ");
            }

            public void onFinish() {
                timertext.setText("Time's up! Your score: " + myDrawing.counter + "  ");
            }

        }.start();
    }
}

class DrawingSpace extends View implements View.OnTouchListener,
        View.OnClickListener {

    Random alea;
    Paint style1,style2;
    float x1 = 400.0f, x2 = 400.0f;
    float y1 = 300.0f, y2 = 500.0f;
    float xrect1 = 100.0f, yrect1 = 100.0f;
    int counter=0;

    public DrawingSpace(Context context, Random alea) {
        super(context);
        this.alea = alea;
        init();
    }

    /**
     * onDraw is a callback method and thus the canvas
     * parameter is given by the system to me (programmer)
     * for creating new geometric objects to be displayed
     * @param canvas
     */
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        //canvas.drawRect(xrect1,yrect1,xrect1+100,yrect1+100,style1);
        //canvas.drawRect(100,310,200,500,style2);
        //canvas.drawCircle(x1,y1,50,style2);
        canvas.drawCircle(x2,y2,70,style1);
        canvas.drawCircle(x2,y2,40,style1);
        canvas.drawCircle(x2,y2,10,style1);
    }

    /**
     * initialization of the styles and
     * register this object as listener of touch and click events
     * on itself
     */
    public void init() {
        style1 = new Paint();
        style1.setColor(Color.BLACK);
        style1.setAntiAlias(true);
        style1.setStyle(Paint.Style.STROKE);
        style1.setStrokeWidth(5.0f);
        style2 = new Paint();
        style2.setColor(Color.BLUE);
        style2.setAntiAlias(false);
        style2.setStyle(Paint.Style.FILL);
        this.setOnClickListener(this);
        this.setOnTouchListener(this);
    }

    public void changeCirclesCenters(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        // force android to redraw the scene
        // invalidate() triggers a call to onDrawn()
        this.invalidate();
    }

    public void hideCircle(Canvas canvas){

    }

    /**
     * when the finger is removed from the screen this
     * triggers a call to this onClick method
     * @param view
     */
    @Override
    public void onClick(View view) {
//        style2.setColor(Color.rgb(50+alea.nextInt(200),
//                50+alea.nextInt(200),
//                50+alea.nextInt(200)));
        if(xrect1<this.x2+70 && xrect1>this.x2-70 && yrect1<this.y2+70 && yrect1>this.y2-70){
            counter++;
            changeCirclesCenters(70+alea.nextInt(600),
                    70+alea.nextInt(600),
                    70+alea.nextInt(600),
                    70+alea.nextInt(600));
        }
        this.invalidate();
    }

    /**
     * this method is called by Android as soon as one Action on the screen is
     * performed ACTION_DOWN (finger arrives on the screen)
     * ACTION_MOVE (finger slides on the screen)
     * ACITON_UP (finger quits the screen)
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        xrect1 = motionEvent.getX();
        yrect1 = motionEvent.getY();
        this.invalidate();
        // when false is returned, the motionEvent is transmitted
        // to the onClick() method otherwise the motionEvent is
        // consumed by the onTouch() method.
        return false;
    }
}