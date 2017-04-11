package com.usana.autotom.impl;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.usana.autotom.FirebaseService;

/**
 * Created by kerrysouthworth on 4/10/17.
 */
@Service
public class FirebaseServiceImpl // implements FirebaseService
{
/*    @Autowired
    @Qualifier("main")
    DatabaseReference mainDatabaseReference;

    @Value("${firebase.path}")
    private String chatPath;

    @Override
    public void startFirebaseListener() {
        mainDatabaseReference.child(chatPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                System.out.println("SUCCESS!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error: "+databaseError.getMessage());
            }
        });
    } */
}
