package com.jason.core;

import java.net.URI;
import java.net.URISyntaxException;

public class URITest {
    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("/home/jason");
        System.out.println(uri.getRawPath());
        System.out.println(uri.getPath());
        System.out.println(uri.getScheme());

        System.out.println("==========http==========");
        uri = new URI("http://www.baidu.com/home/jason");
        System.out.println(uri.getRawPath());
        System.out.println(uri.getPath());
        System.out.println(uri.getScheme());

        System.out.println("==========file==========");
        uri = new URI("file:///home/jason");
        System.out.println(uri.getRawPath());
        System.out.println(uri.getPath());
        System.out.println(uri.getScheme());
    }
}
