package com.steinigkejulian.lonlyforest.utile;

import static java.lang.Math.*;

public class Vector2 {

    public float getNumber1() {
        return number1;
    }

    public float getNumber2() {
        return number2;
    }

    public void setNumber1(float number1) {
        this.number1 = number1;
    }

    public void setNumber2(float number2) {
        this.number2 = number2;
    }

    private float number1;
    private float number2;

    public Vector2(float number1, float number2){

        this.number1 = number1;
        this.number2 = number2;

    }

    public Vector2(Vector2 vector){

        number1 = vector.number1;
        number2 = vector.number2;

    }
    public Vector2(){


        number1 = 0;
        number2 = 0;

    }

    public void setVector(float number1, float number2){

        this.number1 = number1;
        this.number2 = number2;

    }

    public void setVector(Vector2 vector){

        this.number1 = vector.getNumber1();
        this.number2 = vector.getNumber2();

    }

    public void addVector(Vector2 vector){

        number1 += vector.getNumber1();
        number2 += vector.getNumber2();

    }

    public void subVector(Vector2 vector){

        number1 -= vector.getNumber1();
        number2 -= vector.getNumber2();

    }

    public void divVector(Vector2 vector){

        if(vector.getNumber1() != 0) number1 /= vector.getNumber1();
        if(vector.getNumber2() != 0) number2 /= vector.getNumber2();

    }

    public void divVector(float divisor){

        number1 /= divisor;
        number2 /= divisor;

    }

    public void mulVector(float divisor){

        number1 *= divisor;
        number2 *= divisor;

    }

    public float absValue(){

        return (float) sqrt(pow(number1,2)+ pow(number2,2));

    }

    public boolean equalVector(Vector2 vector){
        if(number1 == vector.getNumber1() && number2 == vector.getNumber2()){
            return true;
        }
        return false;

    }

}
