package com.goper.slift;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

public class JuegoActivity extends AppCompatActivity {

    private int countScore=0;
    //private int countCombo=0;
    private RelativeLayout screen;
    private TextView imageTap;
    private CountDownTimer cdTimer;
    private CountDownTimer timerTap;
    private TextView txtScore;
    private Random rnd = new Random();
    private ProgressBar timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FullScreencall();

        screen= (RelativeLayout) findViewById(R.id.RelativeLayout);
        imageTap = (TextView) findViewById(R.id.imageTap);
        txtScore = (TextView)findViewById(R.id.score);
        //txtCombo = (TextView)findViewById(R.id.combo);

        asignarListener(1);
        startTimer(timer = (ProgressBar) findViewById(R.id.timer));
    }

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT < 19){
            //19 or above api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for lower api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void startTimer(final ProgressBar timer){
        cdTimer = new CountDownTimer(1500, 10) {
            public void onTick(long millisUntilFinished) {
                timer.setProgress((int)(millisUntilFinished/10));
            }
            public void onFinish() {
                timer.setProgress(0);
                txtScore.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);
                txtScore.setText("Game Over");
                //posicionarVIew(txtScore, 0);
                //countCombo=0;
            }
        };
    }

    public void startGame(){
        int nTaps=rnd.nextInt(4)+1;
        if(nTaps<=3) {
            asignarListener(nTaps);
            imageTap.setText(String.valueOf(nTaps));
        }
        else {
            imageTap.setText("H");
            asignarListenerTouch();
        }
        cdTimer.start();
    }

    public void asignarListener(final int nTaps)    {
        screen.setOnTouchListener(null);
        screen.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View v) {
                i++;
                if (i == nTaps) {
                    aumentarCuenta();
                    startGame();
                }
            }
        });
    }

    public void asignarListenerTouch()    {
        screen.setOnClickListener(null);
        screen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    timerTap = new CountDownTimer(600, 200) {
                        @Override
                        public void onTick(long arg0) { }
                        @Override
                        public void onFinish() {
                            aumentarCuenta();
                            startGame();
                        }
                    }.start();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    timerTap.cancel();
                }
                return true;
            }
        });
    }

    public void aumentarCuenta(){
        countScore++;
        txtScore.setText(String.valueOf(countScore));
        //aumentarCombo();
    }

    /* public void aumentarCombo(){
        countCombo++;
        txtCombo.setText(String.valueOf(countCombo));
        ScaleAnimation animation = new ScaleAnimation(1, (300+countCombo)/300, 1, (300+countCombo)/300, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);
    }*/
}