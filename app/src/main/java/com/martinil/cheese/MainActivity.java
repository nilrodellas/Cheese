package com.martinil.cheese;

import android.graphics.Point;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private ImageView terra;
    private ImageView formatge1;
    private int counter = 0;
    private physics my_physics;
    private Queso my_ques;
    private ArrayList<Queso> Quesos = new ArrayList<>();
    private int TempsAcc = 5000;
    int width = 1920;
    int height = 1080;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formatge1 = findViewById(R.id.f1);
        terra = findViewById(R.id.terra);

        my_ques = new Queso(0,0,0,0,0,0,0,0,0,0,0,0.5, formatge1);
        Quesos.add(my_ques);
        my_physics = new physics(Quesos);
        //formatge1.getLayoutParams().width = 100;
        //formatge1.getLayoutParams().height = 100;
        formatge1.setPivotX(100);
        formatge1.setPivotY(100);
        terra.setPivotX(1500);
        terra.setPivotY(1500);

        //int width = this.getResources().getDisplayMetrics().widthPixels;
        //int height = this.getResources().getDisplayMetrics().heightPixels;

        updater();
    }

    public void saltetDret (View view) {
        my_physics.saltar(my_ques);
    }

    public void updater(){
        handler.postDelayed(new Runnable(){
            public void run() {
                counter ++;
                if(counter > TempsAcc*Math.random()){
                    update(true);
                    counter = 0;
                }
                else{
                    update(false);
                }
                handler.postDelayed(this, 20);

            }
        }, 20);
    }

    public void update(Boolean updateAcc){
        if(updateAcc){
            my_physics.actualitzar_accAngular();
        }
        my_physics.actualitzar_Terra();
        rotarPla();
        for(int i = 0; i<Quesos.size(); i++){
            Queso ques = Quesos.get(i);
            my_physics.aplicarF(ques);
            my_physics.actualitzar_queso(ques);
            my_physics.colisions(ques);
            my_physics.colTerra(ques);
            moveQues(ques);
        }

    }

    private void rotarPla () {
        terra.setRotation((float) my_physics.angle * 66);
    }

    private void moveQues(Queso cos) {
        Log.i("POSX", String.valueOf(cos.X));
        Log.i("POSY", String.valueOf(cos.Y));
        cos.sprite.setX(my_physics.CoordsX(cos.X, cos.Y)*200 + width / 2 - (float) cos.radi * 200);
        cos.sprite.setY(my_physics.CoordsY(cos.X, cos.Y)*200 + height/ 2 - (float) cos.radi * 2*200);
        cos.sprite.setRotation((float)cos.rot * 66);
        Log.i("cX", String.valueOf(cos.sprite.getX()));
        Log.i("cY", String.valueOf(cos.sprite.getY()));
    }
}
