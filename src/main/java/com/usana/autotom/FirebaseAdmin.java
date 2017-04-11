package com.usana.autotom;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

import java.io.FileInputStream;

/**
 * Created by kerrysouthworth on 4/10/17.
 */
public class FirebaseAdmin
{

    public void sendMessage(){
        try
        {
            FileInputStream serviceAccount = new FileInputStream("./tomaServiceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder().setCredential(FirebaseCredentials.fromCertificate(serviceAccount)).setDatabaseUrl("https://tomresults-9ddb8.firebaseio.com").build();

            FirebaseApp.initializeApp(options);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
