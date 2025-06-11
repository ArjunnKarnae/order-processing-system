package com.orderprocessing.payments;

import com.orderprocessing.payments.events.InventoryReservedEvent;

public class PaymentsTest {

    public static void main(String[] args) {
        for(int i=0; i< 5; i++){
            System.out.println(processPayment());
        }
    }

    private static boolean processPayment(){

        if(Math.floor(Math.random() * 10) < 5){
            return true;
        }
        return false;
    }
}
