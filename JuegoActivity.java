package com.goper.slift;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

public class JuegoActivity extends AppCompatActivity {

    private int count=0;
    private CountDownTimer cdTimer;
    private TextView score;
    private Animation aumentaTamanio;
    private Random rnd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FullScreencall();

        aumentaTamanio = AnimationUtils.loadAnimation(this, R.anim.scale);

        final RelativeLayout screen= (RelativeLayout) findViewById(R.id.RelativeLayout);
        final TextView imageTap = (TextView) findViewById(R.id.imageTap);
        final ProgressBar timer = (ProgressBar) findViewById(R.id.timer);
        score = (TextView)findViewById(R.id.score);

        asignarListenerTap(imageTap, screen, 1);

        cdTimer = new CountDownTimer(1500, 10) {
            public void onTick(long millisUntilFinished) {
                timer.setProgress((int)(millisUntilFinished/10));
            }

            public void onFinish() {
                timer.setProgress(0);
                score.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);
                score.setText("Game Over");
            }
        };
    }

    public void crearObjetoTap(RelativeLayout screen)
    {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View principal = inflater.inflate(R.layout.activity_juego, null);
        TextView nuevoIV = (TextView)principal.findViewById(R.id.imageTap);
        RelativeLayout padre = (RelativeLayout)nuevoIV.getParent();
        padre.removeView(nuevoIV);
        screen.addView(nuevoIV);
        cambiarColorCirculo(nuevoIV);
        posicionarFigura(nuevoIV);
        int nTaps=rnd.nextInt(4)+1;

        if(nTaps<=3) asignarListenerTap(nuevoIV, screen, nTaps);
        else asignarListenerTouch(nuevoIV, screen);

        cdTimer.start();
        nuevoIV.setText(String.valueOf(nTaps));
    }

    public void asignarListenerTap(final TextView figura, final RelativeLayout screen, final int nTaps)
    {
        figura.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View v) {
                i++;
                if (i == nTaps) {
                    aumentarCuenta();
                    crearObjetoTap(screen);
                    screen.removeView(figura);
                }
            }
        });
    }

    public void asignarListenerTouch(final TextView figura, final RelativeLayout screen)
    {
        figura.setOnTouchListener(new View.OnTouchListener() {
            int i=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                i++;
                System.out.println(i+"");
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    i=0;
                    return true;
                }
                if(i==2){
                    aumentarCuenta();
                    crearObjetoTap(screen);
                    screen.removeView(figura);
                    return true;
                }
                return true;
            }
        });/*
        figura.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                aumentarCuenta();
                crearObjetoTap(screen);
                screen.removeView(figura);
                return false;
            }
        });*/
    }

    public void posicionarFigura(TextView figura){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        figura.setX((float) rnd.nextInt((int)(dm.widthPixels-dm.density*80)));
        figura.setY((float) rnd.nextInt((int)(dm.heightPixels-dm.density*80-7))+7);
    }

    public void aumentarCuenta(){
        count++;
        score.setText(String.valueOf(count));
        aumentaTamanio.reset();
        score.startAnimation(aumentaTamanio);
    }

    public void cambiarColorCirculo(TextView figura){
        int color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.circulo);
        drawable.setColor(color);
        figura.setBackground(drawable);
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
}