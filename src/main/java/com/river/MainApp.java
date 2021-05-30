package com.river;

import com.river.application.QBNovelFetch;
import com.river.application.UUBookFetch;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose an application: ");
        System.out.println("1. UUBookFetch");
        System.out.println("2. QBNovelFetch");

        String choice = sc.nextLine();
        switch(choice) {
            case "1":
                UUBookFetch uuBookFetch = new UUBookFetch();
                uuBookFetch.startFetch();
                break;
            case "2":
                QBNovelFetch qbNovelFetch = new QBNovelFetch();
                qbNovelFetch.startFetch();
                break;
        }
        sc.close();
    }
}
