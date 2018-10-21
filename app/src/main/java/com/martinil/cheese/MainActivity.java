package com.martinil.cheese;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Button speak, stop;
    static EditText textView;
    static String word = "0";

    private SpeechRecognizer sr;
    private static final String TAG = "MyActivity";
    ProgressDialog dialog;
    int code;
    private Messenger mServiceMessenger;
    boolean isEndOfSpeech = false;
    boolean serviceconneted;

    static final Integer LOCATION = 0x1;



    private float deltaX1 = 1;
    private float deltaX2 = 3;
    private float deltaX3 = 7;
    //private float deltaA = 10;
   // private float deltap = (float) 3;
   // private float pX = 0;

    private Handler handler = new Handler();
    private ImageView fons0;
    private ImageView fons1a;
    private ImageView fons1b;
    private ImageView fons2a;
    private ImageView fons2b;
    private ImageView fons3a;
    private ImageView fons3b;
    //private ImageView arbres_a;
    //private ImageView arbres_b;
    //private ImageView pedretes;
    private ImageView terra;
    private ImageView formatge1;
    private ImageView formatge2;
    private int counter = 100;
    private physics my_physics;
    private Queso my_ques;
    private Queso my_ques2;
    private ArrayList<Queso> Quesos = new ArrayList<>();
    int width = 1920;
    int height = 1080;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formatge1 = findViewById(R.id.f1);
        formatge2 = findViewById(R.id.f2);
        fons0 = findViewById(R.id.fons0);
        fons1a = findViewById(R.id.fons1a);
        fons1b = findViewById(R.id.fons1b);
        fons2a = findViewById(R.id.fons2a);
        fons2b = findViewById(R.id.fons2b);
        fons3a = findViewById(R.id.fons3a);
        fons3b = findViewById(R.id.fons3b);
        //arbres_a = findViewById(R.id.arbres_a);
        //arbres_b = findViewById(R.id,arbres_b);
        //pedretes = findViewById(R.id.pedretes);
        terra = findViewById(R.id.terra);

        my_ques = new Queso(0,0,0,0,0,4,0,0,0,0,0,0.5, formatge1);
        my_ques2 = new Queso(1,0,0,0,0,-3,0,0,0,0,3.141592 / 4.,0.5, formatge2);
        Quesos.add(my_ques);
        Quesos.add(my_ques2);
        my_physics = new physics(Quesos);
        //formatge1.getLayoutParams().width = 100;
        //formatge1.getLayoutParams().height = 100;
        formatge1.setPivotX(100);
        formatge1.setPivotY(100);
        formatge2.setPivotX(100);
        formatge2.setPivotY(100);
        terra.setPivotX(1250);
        terra.setPivotY(1250);
        //pedretes.setPivotX(1000);
        //pedretes.setPivotY(1000);
        //pedretes.setX(0);
        //pedretes.setY(-500);
        fons0.setX(0);
        fons0.setY(0);
        fons1a.setX(0);
        fons1a.setY(0);
        fons1b.setX(1920);
        fons1b.setY(0);
        fons2a.setX(0);
        fons2a.setY(0);
        fons2b.setX(1920);
        fons2b.setY(0);
        fons3a.setX(0);
        fons3a.setY(0);
        fons3b.setX(1919);
        fons3b.setY(0);
//        arbres_a.setX(0);
//        arbres_a.setY(0);
//        arbres_b.setX(1919);
//        arbres_b.setY(0);
        //int width = this.getResources().getDisplayMetrics().widthPixels;
        //int height = this.getResources().getDisplayMetrics().heightPixels;


//        AnimationDrawable anim = new AnimationDrawable();
//        anim.addFrame(
//                getResources().getDrawable(R.drawable.pedretes_1),
//                25);
//        anim.addFrame(
//                getResources().getDrawable(R.drawable.pedretes_2),
//                25);
//        anim.addFrame(
//                getResources().getDrawable(R.drawable.pedretes_3),
//                25);
//        anim.addFrame(
//                getResources().getDrawable(R.drawable.pedretes_4),
//                25);
//        anim.addFrame(
//                getResources().getDrawable(R.drawable.pedretes_5),
//                25);
//        anim.addFrame(
//                getResources().getDrawable(R.drawable.pedretes_6),
//                25);

        //......So on, so forth until you have a satisfying animation sequence


        //set ImageView to AnimatedDrawable
        //pedretes.setImageDrawable(anim);

        //if you want the animation to loop, set false
       // anim.setOneShot(false);
        //anim.start();

        updater();

        speak = findViewById(R.id.speak);
        stop = findViewById(R.id.stop);
        textView = findViewById(R.id.write);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyService.class);
                stopService(i);
                Toast.makeText(MainActivity.this, "stop speaking", Toast.LENGTH_SHORT).show();
            }
        });
        sr = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
        sr.setRecognitionListener(new Listner());

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    askForPermission(Manifest.permission.RECORD_AUDIO, LOCATION);
                }
                Intent i = new Intent(MainActivity.this, MyService.class);
                bindService(i, connection, code);
                startService(i);
                Toast.makeText(MainActivity.this, "Start Speaking", Toast.LENGTH_SHORT).show();


            }
        });
        
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
        avancarCel();
        for(int i = 0; i<Quesos.size(); i++){
            Queso ques = Quesos.get(i);
            my_physics.aplicarF(ques);
            my_physics.actualitzar_queso(ques);
            my_physics.colisions(ques);
            my_physics.colTerra(ques);
            moveQues(ques);
        }
        comptar();
    }

    private void rotarPla () {
        terra.setRotation((float) (my_physics.angle * 180 / 3.141592));
        //pedretes.setRotation((float) (my_physics.angle * 180 / 3.141592));
    }

    private void avancarCel () {
        if (fons1a.getX() < -1919) {fons1a.setX(1920);}
        if (fons1b.getX() < -1919) {fons1b.setX(1920);}
        fons1a.setX(fons1a.getX() - deltaX1);
        fons1b.setX(fons1b.getX() - deltaX1);
        if (fons2a.getX() < -1919) {fons2a.setX(1920);}
        if (fons2b.getX() < -1919) {fons2b.setX(1920);}
        fons2a.setX(fons2a.getX() - deltaX2);
        fons2b.setX(fons2b.getX() - deltaX2);
        if (fons3a.getX() < -1919) {fons3a.setX(1920);}
        if (fons3b.getX() < -1919) {fons3b.setX(1920);}
        fons3a.setX(fons3a.getX() - deltaX3);
        fons3b.setX(fons3b.getX() - deltaX3);
//        if (arbres_a.getX() < -1919) {arbres_a.setX(1920);}
//        if (arbres_b.getX() < -1919) {arbres_b.setX(1920);}
//        arbres_a.setX(arbres_a.getX() - deltaA);
//        arbres_b.setX(arbres_b.getX() - deltaA);
        //pX = pX - deltap;
//        pedretes.setX(pedretes.getX() -deltap);
//        if (pedretes.getX() < -1910) {
//            pedretes.setX(1910);
//        }
        //pedretes.setPivotX(pedretes.getPivotX() - (float) Math.cos(my_physics.angle) * pX);
        //pedretes.setPivotY(pedretes.getPivotY() - (float) Math.sin (my_physics.angle) * pX);
        //pedretes.setX((float) Math.cos(my_physics.angle) * pX);
        //pedretes.setY((float) Math.sin(my_physics.angle) * pX);
        //pedretes.setPivotY(pedretes.getPivotY());
        //pedretes.setX(my_physics.CoordsX(pX, 0)*200 + width / 2 - 200);
        //pedretes.setY(my_physics.CoordsY(pX, 0)*200 + height / 2 - 200);
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


    class Listner implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d("Speech", "ReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d("Speech", "beginSpeech");

        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.d("Speech", "onrms");

        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d("Speech", "onbuffer");

        }

        @Override
        public void onEndOfSpeech() {
            isEndOfSpeech = true;

        }

        @Override
        public void onError(int error) {
            Log.d(TAG, "error " + error);
            if (!isEndOfSpeech) {
                return;
            }
            Toast.makeText(MainActivity.this, "Try agine", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String word = (String) data.get(data.size() - 1);
            textView.setText(word);
            dialog.dismiss();


        }

        @Override
        public void onPartialResults(Bundle partialResults) {

            ArrayList data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String word = (String) data.get(data.size() - 1);

            textView.setText(word);

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            }

        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }



    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.d("service", "connected");

            mServiceMessenger = new Messenger(service);
            Message msg = new Message();
            msg.what = MyService.MSG_RECOGNIZER_START_LISTENING;
            try {
                mServiceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            serviceconneted = false;
            Log.d("service", "disconnetd");
        }
    };

    public void comptar(){
        if(word.contains("1")){
            my_physics.saltar(my_ques);
            word = "0";
        }
        if(word.contains("2")){
            my_physics.saltar(my_ques2);
            word = "0";
        }
    }


}
