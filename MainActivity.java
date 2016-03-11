package com.goper.slift;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    ViewGroup.MarginLayoutParams marginParams;
    RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FullScreencall();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView circulo = (ImageView) findViewById(R.id.circuloplay);
        ImageView play = (ImageView) findViewById(R.id.play);
        ImageView letras = (ImageView) findViewById(R.id.letrasslift);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotar);
        circulo.startAnimation(rotation);

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.playslift, dimensions);
        int height = dimensions.outHeight;
        posicionarVIew(letras,(int)(height*-2.75));
        posicionarVIew(circulo,0);
        posicionarVIew(play,(int)(height*0.35));
        System.out.println(height*0.75+"");
    }

    private void posicionarVIew(ImageView view,int extraMargin) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        marginParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        marginParams.setMargins(0, (dm.heightPixels / 2) + extraMargin, 0, 0);
        layoutParams = new RelativeLayout.LayoutParams(marginParams);
        view.setLayoutParams(layoutParams);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        view.setLayoutParams(layoutParams);
    }

    //Ocultar barra de navegación (barra inferior)
    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void lanzarActividadJuego(View view){
        Intent intent = new Intent(this, JuegoActivity.class);
        startActivity(intent);
    }
}
