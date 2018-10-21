package com.martinil.cheese;

import java.util.ArrayList;
import java.util.Random;

public class physics {
    double dt = 40./1000.; //Ok
    double c_reb = 0.35;  //Ok
    double c_xoc = 0.5;
   // double accAngular = 0.;
    //double velAngular = 0;
    double angle = 0; // Inicial, okei
    double angle_in;
    double angle_fi = 0; //No cal tocar
    double temps_canvi;
    double b;
    double t0 = 0;
    double limit_baixada = 1.1; //Angle límit baixada
    double limit_pujada = -0.7; //Angle límit pujada
    double FGrav = 9.81; //Okei
    double factForca = 15; //Okei
    double factSuavitat = 4; //Okei
    double FSalt = 0.8; //Okei
   // double factAcc = 0.;
   // double factVel = 0.05;
    double factGrav = 0.5;
    double offsetRot = 0.25;
    double attenSaltX = 0.3;

    ArrayList<Queso> cossos;

    public physics(ArrayList<Queso> cossos){
        this.cossos = cossos;
    }

    public void actualitzar_Terra () {
        //velAngular = 1 / (1 + Math.exp(-3.5*(b*t0 - 1)));
        //angle = angle + velAngular*dt;
        angle = (angle_fi - angle_in) / (1 + Math.exp(-7*(b*t0 - 1))) + angle_in;
        t0 = t0 + dt;
        if (angle > limit_baixada) { angle = limit_baixada;}
        if (angle < limit_pujada) {angle = limit_pujada;}
    }

    public void actualitzar_AngTem(){
        angle_in = angle;
        angle_fi = (limit_pujada - limit_baixada) * Math.random() + limit_baixada;
        temps_canvi = (10 - 2) *Math.random() + 2;
        b = (2.99573227355/7 + 1)/temps_canvi;
        t0 = 0;
    }


    public void actualitzar_queso (Queso cos) {
        cos.aX = cos.FX / cos.massa;
        cos.aY = cos.FY / cos.massa;
        cos.vX = cos.aX * dt + cos.vX;
        cos.vY = cos.aY * dt + cos.vY;
        cos.X  = cos.vX * dt + cos.X;
        cos.Y  = cos.vY * dt + cos.Y;
        if (cos.Y < 0){
            cos.Y = 0;
        }
        cos.W  = cos.vX / cos.radi;
        cos.rot=  cos.W * dt + cos.rot + offsetRot;
        //cos.rot = velRot + cos.rot;
    }

    public void colisions (Queso cos) {
        for (int i = cos.id + 1; i < cossos.size(); i++) {
            Queso cos2 = cossos.get(i);
            double d = distancia(cos, cos2);
            if ( d <= cos.radi + cos2.radi) {
                cos.X  = -1.5 * cos.vX * dt + cos.X;
                cos.Y  = - 1.5 *cos.vY * dt + cos.Y;
                cos2.X  = -1.5*cos2.vX * dt + cos2.X;
                cos2.Y  = -1.5*cos2.vY * dt + cos2.Y;
                double patata = (cos.vX - cos2.vX) * (cos.X - cos2.X) + (cos.vY - cos2.vY) * (cos.Y - cos2.Y);
                patata = 2* patata / (d*d*(cos.massa + cos2.massa));
                cos.vX = (cos.vX - patata * cos2.massa * (cos.X - cos2.X)) *c_xoc;
                cos.vY = (cos.vY - patata * cos2.massa * (cos.Y - cos2.Y)) ;
                cos2.vX = (cos2.vX - patata * cos.massa * (cos2.X - cos.X)) *c_xoc;
                cos2.vY = (cos2.vY - patata * cos.massa * (cos2.Y - cos.Y));

            }
        }

    }

    public void colTerra (Queso cos) {
        if (cos.Y <= 0) {
            cos.vY = -cos.vY * c_reb;
        }
        if (cos.X > 5.5) {
            cos.vX = -cos.vX*c_xoc;
        }
        if (cos.X < -5.5) {
            cos.vX = -cos.vX*c_xoc;
        }
        if (cos.Y > 5.5) {
            cos.vY = -cos.vY*c_xoc;
        }
    }

    public double FextX(double posX){
        return factForca*(0.5 - 1 / (1 + Math.exp((-(posX)) / factSuavitat)));
    }

    public void aplicarF (Queso cos) {
        cos.FX = Math.sin(angle)*FGrav* factGrav + FextX(cos.X);
        cos.FY = -Math.cos(angle)*FGrav;
    }

    public void saltar(Queso cos) {
        if (cos.Y < 0.1) {
            cos.vY = FSalt * cos.massa;
            cos.vX = FSalt * cos.massa * attenSaltX + cos.vX;
        }
    }

    public double distancia (Queso i, Queso j) {
        return Math.sqrt(Math.pow(i.X - j.X, 2) + Math.pow(i.Y - j.Y, 2));
    }

    public float CoordsX (double x, double y) {
        return (float) (Math.cos(angle) * x + Math.sin(angle) * y);
    }

    public float CoordsY (double x, double y) {
        return (float) (Math.sin(angle) * x - Math.cos(angle) * y);
    }
}
