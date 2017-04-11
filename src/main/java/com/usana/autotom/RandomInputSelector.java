package com.usana.autotom;

import java.util.Random;

/**
 * Created by kerrysouthworth on 3/23/17.
 */
public class RandomInputSelector
{

    public static int getRandomIndex(int collectionLen)
    {
        Random rand = new Random();
        return rand.nextInt(collectionLen);
    }
}
