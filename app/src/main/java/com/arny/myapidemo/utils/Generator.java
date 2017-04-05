package com.arny.myapidemo.utils;


import java.util.Random;

public class Generator {
    /*api*/
//    private static final String PLACEIMG_URL = "http://placeimg.com/250/250/arch";
    private static final String PLACEIMG_URL_TYPE_ANIMAL = "animals";
    private static final String PLACEIMG_URL_TYPE_ARCH = "arch";
    private static final String PLACEIMG_URL_TYPE_NATURE = "nature";
    private static final String PLACEIMG_URL_TYPE_PEOPLE = "people";
    private static final String PLACEIMG_URL_TYPE_TECH = "tech";
    private static final String[] PLACEIMG_TYPES = {PLACEIMG_URL_TYPE_ANIMAL,PLACEIMG_URL_TYPE_ARCH,PLACEIMG_URL_TYPE_NATURE,PLACEIMG_URL_TYPE_PEOPLE,PLACEIMG_URL_TYPE_TECH};
    private static final String PLACEIMG_URL = "http://placeimg.com/";
    private static final String LOREMPIXEL_URL = "http://lorempixel.com/";
    private static final int GENERATOR_TYPE_IMG = 1;
    private int type;

    private int getType() {
        return type;
    }

    private void setType(int type) {
        this.type = type;
    }

    public static String getImageUrl(int width, int height) {
        if (width > 1200) width = 1200;
        if (height > 1200) height = 1200;
        Random rnd = new Random();
        int range = PLACEIMG_TYPES.length;
        int rand = rnd.nextInt(range);
        return PLACEIMG_URL + width + "/" + height + "/" + PLACEIMG_TYPES[rand];
    }

    public static String getString(int max) {
        return RandomStringUtils.randomAlphabetic(max);
    }
}
