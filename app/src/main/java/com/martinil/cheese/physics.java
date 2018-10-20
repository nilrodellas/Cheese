package com.martinil.cheese;

import java.util.ArrayList;

public class physics {
    double dt = 40./1000.;
    double c_reb = 0.35;
    double accAngular = 0.;
    double velAngular = 0.;
    double angle = 0;
    double FGrav = 9.81;
    double factForca = 9.81;
    double factSuavitat = 4;
    double FSalt = 0.8;
    double factAcc = 0.1;
    ArrayList<Queso> cossos;

    public physics(ArrayList<Queso> cossos){
        this.cossos = cossos;
    }

    public void actualitzar_Terra () {
        velAngular = velAngular + accAngular*dt;
        angle = angle + velAngular*dt;
    }

    public void actualitzar_accAngular(){
        accAngular = (Math.random()-.5)*factAcc;
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
        cos.rot=  cos.W * dt + cos.rot;
    }

    public void colisions (Queso cos) {
        for (int i = cos.id + 1; i < cossos.size(); i++) {
            Queso cos2 = cossos.get(i);
            double d = distancia(cos, cos2);
            if ( d <= cos.radi + cos2.radi) {
                double patata = (cos.vX - cos2.vX) * (cos.X - cos2.X) + (cos.vY - cos2.vY) * (cos.Y - cos2.Y);
                patata = 2* patata / (d*d*(cos.massa + cos2.massa));
                cos.vX = cos.vX - patata * cos2.massa * (cos.X - cos2.X);
                cos.vY = cos.vY - patata * cos2.massa * (cos.Y - cos2.Y);
                cos2.vX = cos2.vX - patata * cos.massa * (cos2.X - cos.X);
                cos2.vY = cos2.vY - patata * cos.massa * (cos2.Y - cos.Y);
            }
        }

    }

    public void colTerra (Queso cos) {
        if (cos.Y <= 0) {
            cos.vY = -cos.vY * c_reb;
        }
    }

    public double FextX(double posX){
        return factForca*(0.5 - 1 / (1 + Math.exp((-(posX)) / factSuavitat)));
    }

    public void aplicarF (Queso cos) {
        cos.FX = Math.sin(angle)*FGrav + FextX(cos.X);
        cos.FY = -Math.cos(angle)*FGrav;
    }

    public void saltar(Queso cos) {
        if (cos.Y < 0.1) {
            cos.vY = FSalt * cos.massa;
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
