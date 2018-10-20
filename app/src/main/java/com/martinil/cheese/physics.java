package com.martinil.cheese;

import java.util.ArrayList;

public class physics {
    double dt = 0.01;
    double c_abs = 1.;
    double accAngular = 0.;
    double velAngular = 0.;
    double angle = 0;
    double FGrav = 9.81;
    double factForca = 20;
    double factSuavitat = 4;
    double FSalt = 10;
    ArrayList<Queso> cossos = new ArrayList<>();

    public void actualitzar_Terra () {
        velAngular = velAngular + accAngular*dt;
        angle = angle + velAngular*dt;
    }

    public void actualitzar_accAngular(){
        accAngular = (Math.random()-.7)*10;
    }

    public void actualitzar_queso (Queso cos) {
        cos.aX = cos.FX / cos.massa;
        cos.aY = cos.FY / cos.massa;
        cos.vX = cos.aX * dt + cos.vX;
        cos.vY = cos.aY * dt + cos.vY;
        cos.X  = cos.vX * dt + cos.X;
        cos.Y  = cos.vY * dt + cos.Y;
        cos.W  = Math.sqrt(cos.vX * cos.vX + cos.vY * cos.vY) / cos.radi;
        cos.rot=  cos.W * dt + cos.rot;
    }

    public void colisions (Queso cos) {
        for (int i = cos.id + 1; i <= cossos.size(); i++) {
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
            cos.vY = -cos.vY * c_abs;
        }
    }

    public double FextX(double posX){
        return factForca*(0.5 - 1 / (1 + Math.exp((-(posX)) / factSuavitat));
    }

    public void aplicarF (Queso cos) {
        cos.FX = Math.sin(angle)*FGrav+FextX(cos.X);
        cos.FY = Math.cos(angle)*FGrav;
    }

    public void saltar(Queso cos) {
        if cos.Y < 5:
            cos.vY = FSalt * cos.massa;
    }

    public double distancia(Queso i, Queso j) {
        return Math.sqrt(Math.pow(i.X - j.X, 2) + Math.pow(i.Y - j.Y, 2));
    }
}
