package com.martinil.cheese;

import android.widget.ImageView;

public class Queso {
    int id;
    double aX;
    double aY;
    double vX;
    double vY;
    double X;
    double Y;
    double FX;
    double FY;
    double W;
    double rot;
    double radi;
    double massa;
    double densitat = 7.64;
    ImageView sprite;

    public Queso (int id, double aX, double aY, double vX, double vY, double X, double Y, double FX,
                  double FY, double W, double rot, double radi, ImageView sprite) {
        this.id = id;
        this.aX = aX;
        this.aY = aY;
        this.vX = vX;
        this.vY = vY;
        this.X = X;
        this.Y = Y;
        this.FX = FX;
        this.FY = FY;
        this.W = W;
        this.rot = rot;
        this.radi = radi;
        this.massa = densitat * radi * radi * radi * 4 * 3.1415 / 3.;
        this.sprite = sprite;
    }


}
