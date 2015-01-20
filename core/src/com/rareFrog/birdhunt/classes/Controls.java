package com.rareFrog.birdhunt.classes;

import com.badlogic.gdx.Gdx;

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
    private float azimuthPreviousAverage;
    int azimuthCounter = 0;

    public Controls() {
        double coefficients[] = {0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
        azimuthPrevious = new float[100];

        fir = new FIR(coefficients);
    }

    public void update() {
        rawAzimuth = Gdx.input.getAzimuth();
        azimuthValue = (float) fir.getOutputSample(rawAzimuth);
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
        azimuthPrevious[azimuthCounter] = azimuthValue;
        System.out.println("cal: " + calibrated + " count: " + azimuthCounter);
        if (azimuthCounter == 99) {
            azimuthCounter = 0;
            float tempAverage = 0;
            for (int i = 0; i < 100; i++) {
                tempAverage += azimuthPrevious[i];
            }
            tempAverage /= 100;
            System.out.println("temp:" + tempAverage);
            if (Math.abs(azimuthPreviousAverage - tempAverage) < 10) {
                azimuthCalibration = (azimuthPreviousAverage + tempAverage) / 2;
                calibrated = true;
            }
            azimuthPreviousAverage = tempAverage;
            System.out.println("Average: " + tempAverage + " calibration: " + azimuthCalibration);
        }
        azimuthCounter++;
    }

    public void blockingCalibrate() {
        while (!calibrated) {
            update();
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
