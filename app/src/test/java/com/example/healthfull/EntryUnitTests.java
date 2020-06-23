package com.example.healthfull;

import com.example.healthfull.entries.NewFoodEntryAdder;
import com.example.healthfull.entries.NewWaterEntryAdder;
import com.example.healthfull.util.MockFirebaseTask;
import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({
        FirebaseAuth.class,
        FirebaseFirestore.class
})

public class EntryUnitTests {

    CollectionReference collectionReference;
    DocumentReference documentReference;
    Task taskReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Before
    public void before() {
        collectionReference = PowerMockito.mock(CollectionReference.class);
        documentReference = PowerMockito.mock(DocumentReference.class);
        taskReference = PowerMockito.mock(Task.class);
        firebaseUser = PowerMockito.mock(FirebaseUser.class);
        firebaseAuth = PowerMockito.mock(FirebaseAuth.class);
        firebaseFirestore = PowerMockito.mock(FirebaseFirestore.class);

        PowerMockito.mockStatic(FirebaseAuth.class);
        when(FirebaseAuth.getInstance()).thenReturn(firebaseAuth);

        when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        when(firebaseAuth.getCurrentUser().getUid()).thenReturn("vCRMif3c2IhkirRb4mHVxAcnyeA3");

        PowerMockito.mockStatic(FirebaseFirestore.class);
        when(FirebaseFirestore.getInstance()).thenReturn(firebaseFirestore);

        when(firebaseFirestore.collection(any())).thenReturn(collectionReference);
        when(firebaseFirestore.collection(any()).document(any())).thenReturn(documentReference);

        when(documentReference.collection(any())).thenReturn(collectionReference);
        when(collectionReference.document(any())).thenReturn(documentReference);
        when(collectionReference.document()).thenReturn(documentReference);
        when(documentReference.set(any())).thenReturn(new MockFirebaseTask(true, true));
    }

    @Test
    public void addNewFoodEntry() {

        // Adds a new entry of type Egg
        CountDownLatch latch = new CountDownLatch(1);

        NewFoodEntryAdder adder = new NewFoodEntryAdder("UP3F8tKGp6JZJpnDWbHY");

        final boolean[] eggAdded = {false};

        adder.setOnDoneListener(new OnDoneListener() {
            @Override
            public void onSuccess(Object object) {
                eggAdded[0] = true;
                latch.countDown();
            }

            @Override
            public void onFailure(String message) {
                eggAdded[0] = false;
                latch.countDown();
            }
        });

        adder.save();

        try {
            latch.await(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // continue
        }

        assertTrue("Egg wasn't added", eggAdded[0]);
    }

    @Test
    public void addNewWaterEntry() {

        // Adds a new entry of type Water
        CountDownLatch latch = new CountDownLatch(1);

        NewWaterEntryAdder adder = new NewWaterEntryAdder();

        final boolean[] waterAdded = {false};

        adder.setOnDoneListener(new OnDoneListener() {
            @Override
            public void onSuccess(Object object) {
                waterAdded[0] = true;
                latch.countDown();
            }

            @Override
            public void onFailure(String message) {
                waterAdded[0] = false;
                latch.countDown();
            }
        });

        adder.save();

        try {
            latch.await(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // continue
        }

        assertTrue("Water wasn't added", waterAdded[0]);
    }

}
