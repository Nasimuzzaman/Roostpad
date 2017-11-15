package com.example.nasimuzzaman.roostpad.libraryPackage;

import android.util.Log;

import com.example.nasimuzzaman.roostpad.gmail.GMailSender;

import java.util.Random;

/**
 * Created by nasimuzzaman on 11/15/17.
 */

public class CustomLibrary {

    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    //using javax.mail
    public void sendPassword(final String recipientsEmail, final String newPassword) {
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("roostpaddb@gmail.com", "Kaz123Mig");
                    sender.sendMail("Notification from ROOSTPAD",
                            "Your Roostpad account has been created. Your password is '"+ newPassword + '"',
                            "roostpaddb@gmail.com",
                            recipientsEmail);
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }
}
