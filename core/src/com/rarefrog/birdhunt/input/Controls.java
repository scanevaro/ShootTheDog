package com.rarefrog.birdhunt.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rarefrog.birdhunt.Game;
import com.rarefrog.birdhunt.interfaces.CalibrationData;

/**
 * Created by Elmar on 1/20/2015.
 */
public class Controls implements CalibrationData {
    private FIR fir;
    private float rawAzimuth = 0;
    private float azimuthValue = 0;
    private boolean calibrated = false;
    private float azimuthCalibration;
    private float notZero = 0;
    private float previousVal;
    public static boolean touchMovement = false;
    private float previousTouch = 0;
    private float touchX = 0;

    public Controls() {
        double coefficients[] = new double[10];
        for (int i = 0; i < 10; i++) {
            coefficients[i] = 1f / 10f;
        }

        fir = new FIR(coefficients);
    }

    public void update(float value) {
        if (touchMovement) {
            if (Gdx.input.isTouched()) {
                if (previousTouch != -1) {
                    touchX += Gdx.input.getX() - previousTouch;
                    if (touchX >= Game.VIRTUAL_WIDTH) {
                        touchX = Game.VIRTUAL_WIDTH;
                    }
                    if (touchX <= -Game.VIRTUAL_WIDTH) {
                        touchX = -Game.VIRTUAL_WIDTH;
                    }
                }
                previousTouch = Gdx.input.getX();
            } else {
                previousTouch = -1;
            }
        } else {
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    if (rawAzimuth < Game.VIRTUAL_WIDTH - 5)
                        rawAzimuth += 5f;
                    else
                        rawAzimuth = Game.VIRTUAL_WIDTH;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    if (rawAzimuth > -Game.VIRTUAL_WIDTH + 5)
                        rawAzimuth -= 5f;
                    else
                        rawAzimuth = -Game.VIRTUAL_WIDTH;
                }
                //System.out.println(rawAzimuth);
            } else {
                value *= 16;
                rawAzimuth = value;//Gdx.input.getAzimuth();
                //System.out.println(rawAzimuth + " raw raw " + value / 4);
                //clamp at -480 and 480, recalibrate?
            }

            azimuthValue = (float) fir.getOutputSample(rawAzimuth);
            if (!calibrated) {
                previousVal = azimuthCalibration = value;
                calibrated = true;
                notZero = 0;
            }
        }
    }

    public void outOfRightBounds(float value) {
        azimuthCalibration = azimuthCalibration - (-getCalibratedValue() - value);
    }

    public void outOfLeftBounds(float value) {
        azimuthCalibration = azimuthCalibration + (-getCalibratedValue() - value);
    }

    public float getCalibratedValue() {
        if (touchMovement)
            return touchX;
        //==========================================================//
        float returnValue = azimuthValue - azimuthCalibration;      //
        //
        //==========================================================//
        return returnValue;
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

    @Override
    public float getCalibration() {
        return azimuthCalibration;
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
