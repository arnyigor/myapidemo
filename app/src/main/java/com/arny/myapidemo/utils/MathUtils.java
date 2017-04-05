package com.arny.celestiatools.utils;

import java.math.BigDecimal;

/**
 * Created by i.sedoy on 22.03.17.
 */
public class MathUtils {

    /**
     * дробная часть числа
     *
     * @param x
     * @return
     */
    public static double fracal(double x) {
        return x - (int) x;
    }

    /**
     * целая часть числа
     *
     * @param x
     * @return
     */
    public static int intact(double x) {
        return (int) x;
    }

    /**
     * Отстаток от деления
     *
     * @param x
     * @param y
     * @return
     */
    public static double modulo(double x, double y) {
        return y * (fracal(x / y));
    }

    public static double round(double val, int scale) {
        return new BigDecimal(val).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double Cos(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public static double Acos(double rad) {
        return Math.toDegrees(Math.acos(rad));
    }

    public static double Sin(double angle) {
        return Math.sin(Math.toRadians(angle));
    }

    public static double Asin(double rad) {
        return Math.toDegrees(Math.asin(rad));
    }

    public static double Tan(double angle) {
        return Math.tan(Math.toRadians(angle));
    }

    public static double Atan(double rad) {
        return Math.toDegrees(Math.atan(rad));
    }

    public static double Sqrt(double num) {
        return Math.sqrt(num);
    }

    public static double Exp(double num, double exp) {
        return Math.pow(num, exp);
    }

    public static double Abs(double num) {
        return Math.abs(num);
    }

    public static double getDecGrad(int D, double M, double S) {
        double sign = 1.0;
        if (D < 0 || M < 0 || S < 0) {
            sign = -1.0;
        }
        D = Math.abs(D);
        M = Math.abs(M);
        S = Math.abs(S);
        return sign * (D + (M / 60) + (S / 3600));
    }

    enum AngleFormat {
        Dd,
        DMM,
        DMMm,
        DMMSS,
        DMMSSs
    }

    public static String getGradMinSec(double grad, AngleFormat format) {
        //TODO
        double sign = 1.0;
        if (grad < 0) {
            sign = -1.0;
        }
        double x = Math.abs(grad);
        int D = (int) x;
        double y = (x - D) * 60;
        int M = (int) y;
        double z = (y - M) * 60;
        double S = round(z, 2);

        System.out.println(D + " " + M + " " + S);
        System.out.println(sign * D);
        switch (format) {
            case Dd:
                return "" + sign * (int) grad;
            case DMM:
                return "" + (int) grad + " " + (int) grad * 60;
            default:
                return "" + (int) grad;
        }
    }

}
