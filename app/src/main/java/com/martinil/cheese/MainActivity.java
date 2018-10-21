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
    private ImageView formatge2;
    private int counter = 100;
    private physics my_physics;
    private Queso my_ques;
    private Queso my_ques2;
    private ArrayList<Queso> Quesos = new ArrayList<>();
    private int TempsAcc = 400;
    int width = 1920;
    int height = 1080;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formatge1 = findViewById(R.id.f1);
        formatge2 = findViewById(R.id.f2);
        terra = findViewById(R.id.terra);

        my_ques = new Queso(0,0,0,0,0,4,0,0,0,0,0,0.5, formatge1);
        my_ques2 = new Queso(1,0,0,0,0,-3,0,0,0,0,0,0.5, formatge2);
        Quesos.add(my_ques);
        Quesos.add(my_ques2);
        my_physics = new physics(Quesos);
        //formatge1.getLayoutParams().width = 100;
        //formatge1.getLayoutParams().height = 100;
        formatge1.setPivotX(100);
        formatge1.setPivotY(100);
        formatge2.setPivotX(100);
        formatge2.setPivotY(100);
        terra.setPivotX(1500);
        terra.setPivotY(1500);

        //int width = this.getResources().getDisplayMetrics().widthPixels;
        //int height = this.getResources().getDisplayMetrics().heightPixels;

        updater();
    }

    public void saltetDret (View view) { my_physics.saltar(my_ques);}
    public void saltetEsq  (View view) { my_physics.saltar(my_ques2);}

    public void updater(){
        handler.postDelayed(new Runnable(){
            public void run() {
                if(Math.abs(my_physics.angle_fi - my_physics.angle) < 0.01){
                    update(true);
                }
                else{
                    update(false);
                }
                handler.postDelayed(this, 15);

            }
        }, 15);
    }

    public void update(Boolean updateAcc){
        if(updateAcc){
            my_physics.actualitzar_AngTem();
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
        terra.setRotation((float) (my_physics.angle * 180 / 3.141592));
    }

    private void moveQues(Queso cos) {
        //Log.i("POSX", String.valueOf(cos.X));
        //Log.i("POSY", String.valueOf(cos.Y));
        cos.sprite.setX(my_physics.CoordsX(cos.X, cos.Y)*200 + width / 2 - (float) cos.radi * 200);
        cos.sprite.setY(my_physics.CoordsY(cos.X, cos.Y)*200 + height/ 2 - (float) cos.radi * 200);
        cos.sprite.setRotation((float)(cos.rot * 180 / 3.141592));
        //Log.i("cX", String.valueOf(cos.sprite.getX()));
        //Log.i("cY", String.valueOf(cos.sprite.getY()));
    }
}
