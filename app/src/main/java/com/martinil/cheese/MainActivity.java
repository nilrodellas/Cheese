package com.martinil.cheese;

import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private Button buttonDret;
    private Button buttonEsq;
    private ImageView terra;
    private ImageView formatge1;
    private int counter = 0;
    private physics my_physics = new physics();
    private Queso my_ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formatge1 = findViewById(R.id.f1);
        terra = findViewById(R.id.terra);
        buttonDret = findViewById(R.id.buttonDret);
        buttonEsq = findViewById(R.id.buttonEsq);

        my_ques = new Queso(0,0,0,0,0,0,0,0,0,0,0,50,1,1);

        //formatge1.getLayoutParams().width = 100;
        //formatge1.getLayoutParams().height = 100;
        formatge1.setPivotX(100);
        formatge1.setPivotY(100);
        terra.setPivotX(1500);
        terra.setPivotY(1500);


        update();
    }

    private void moureboto(ImageView boto1) {
        boto1.setX(boto1.getX() + 1);
        boto1.setRotation(1 + boto1.getRotation());
    }

    public void update(){
        handler.postDelayed(new Runnable(){
            public void run() {
                physics.
                handler.postDelayed(this, 10);

            }
        }, 10);
    }
}
