package com.thread;

public class Counter {

    private int sum;

    public  void count(){

        synchronized(this){
            sum++;
            System.out.println(Thread.currentThread().getName()+": "+sum);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {


            }
        }


    }
}
class ThreadTest{
    public static void main(String[] args) {
        final Counter counter0 = new Counter();
        final Counter counter1 = new Counter();

       new  Thread(new Runnable(){


           @Override
           public void run() {
               for (int i = 0; i <1000 ; i++) {

                   counter0.count();
               }

           }
       }).start();

       new  Thread(new Runnable(){
           @Override
           public void run() {
               for (int i = 0; i <1000 ; i++) {

                   counter1.count();
               }
           }
       }).start();

    }
}
