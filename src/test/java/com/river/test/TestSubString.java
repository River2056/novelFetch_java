package com.river.test;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestSubString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        System.out.println("please provide base url: ");
//        String baseUrl = sc.nextLine(); // "https://t.uukanshu.com/";
        String baseUrl = null;
        System.out.println("please provide index page url: ");
        String url = sc.nextLine(); // "https://t.uukanshu.com/book.aspx?id=128714#gsc.tab=0";
        Pattern urlPattern = Pattern.compile("https://.*?com/");
        Matcher m = urlPattern.matcher(url);
        if(m.find()) {
            baseUrl = m.group(0);
        }
        System.out.println(baseUrl);
    }
}
