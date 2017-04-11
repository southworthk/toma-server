package com.usana.autotom;

import java.util.Comparator;

/**
 * Created by kerrysouthworth on 3/23/17.
 */
public class TomElementIdSort implements Comparator<TomElement>
{
    public int compare(TomElement one, TomElement two)
    {
        return one.getId().compareTo(two.getId());
    }
}
