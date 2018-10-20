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
    double densitat = 1;
    String idImatge;

    public Queso (int id, double aX, double aY, double vX, double vY, double X, double Y, double FX,
                  double FY, double W, double rot, double radi, double massa, double idImatge) {
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
        this.massa = densitat * radi;
        this.idImatge = idImatge;
    }


}
