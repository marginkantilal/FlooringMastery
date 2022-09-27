package com.fm.view;

import java.util.Scanner;


public class UserIOConsoleImpl implements UserIO {

    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        return Double.parseDouble(sc.nextLine());
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double num;
        do {
            print(prompt);
            num = Double.parseDouble(sc.nextLine());
        } while (num < min || num > max);

        return num;
    }



    @Override
    public int readInt(String prompt) {
    	 int input;
         while(true){
             System.out.println(prompt);
             try{
            	 input = sc.nextInt();
                 sc.nextLine();
                 break;
             }catch(Exception e){
                 System.out.println("***You must type a number***");
                 sc.next();
             }
         }

         return input;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int response;
        do{
            while(true){
                System.out.println(prompt);
                try{
                    response = sc.nextInt();
                    sc.nextLine();
                    break;
                }catch(Exception e){
                    System.out.println("***You must type a number***");
                    sc.next();
                }
            }
        }while(response < min || response > max);
        return response;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }

}
