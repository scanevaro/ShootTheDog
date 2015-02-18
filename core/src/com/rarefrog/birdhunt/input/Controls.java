package com.rarefrog.birdhunt.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Elmar on 1/20/2015.
 */
public class Controls {
    private FIR fir;
    private float rawAzimuth = 0;
    private float azimuthValue = 0;
    private boolean calibrated = false;
    private float azimuthPrevious[];
    private float azimuthCalibration;

    public Controls() {
        double coefficients[] = new double[10];
        for (int i = 0; i < 10; i++) {
            coefficients[i] = 1f/10f;
        }
        azimuthPrevious = new float[100];

        fir = new FIR(coefficients);
    }

    public void update(float value) {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            rawAzimuth+=5f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            rawAzimuth-=5f;
        }
        //System.out.println(rawAzimuth);
        //clamp at -480 and 480, recalibrate?

        value *= 4;
        //rawAzimuth = value;//Gdx.input.getAzimuth();
        azimuthValue = (float) fir.getOutputSample(rawAzimuth);
        if(!calibrated){
            azimuthCalibration = value;
            calibrated = true;
        }
    }

    public float getCalibratedValue() {
        return azimuthValue - azimuthCalibration;
    }

    public float getAzimuthValue() {
        return azimuthValue;
    }

    public boolean isCalibrated() {
        return calibrated;
    }

    public float getAzimuthCalibration() {
        return azimuthCalibration;
    }

    public void calibrate() {
        calibrated = false;
    }

    public void blockingCalibrate() {
        while (!calibrated) {
            calibrate();
        }
    }

    public float getRawValue() {
        return rawAzimuth;
    }

    class FIR {
        private int length;
        private double[] delayLine;
        private double[] impulseResponse;
        private int count = 0;

        FIR(double[] coefs) {
            length = coefs.length;
            impulseResponse = coefs;
            delayLine = new double[length];
        }

        double getOutputSample(double inputSample) {
            delayLine[count] = inputSample;
            double result = 0.0;
            int index = count;
            for (int i = 0; i < length; i++) {
                result += impulseResponse[i] * delayLine[index--];
                if (index < 0) index = length - 1;
            }
            if (++count >= length) count = 0;
            return result;
        }
    }
}
