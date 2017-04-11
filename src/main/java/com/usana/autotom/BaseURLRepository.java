package com.usana.autotom;

/**
 * Created by kerrysouthworth on 1/3/17.
 */
public interface BaseURLRepository extends TomElementRepository
{
    BaseURL findByUrl(String url);
}
