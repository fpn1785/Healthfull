package com.example.healthfull.util;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

public class MockFirebaseTask extends Task<Void> {

    private boolean complete;
    private boolean success;

    public MockFirebaseTask(boolean complete, boolean success) {
        this.complete = complete;
        this.success = success;
    }

    @NonNull
    @Override
    public Task<Void> addOnCompleteListener(@NonNull OnCompleteListener<Void> onCompleteListener) {
        onCompleteListener.onComplete(this);
        return this;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Nullable
    @Override
    public Void getResult() {
        return null;
    }

    @Nullable
    @Override
    public <X extends Throwable> Void getResult(@NonNull Class<X> aClass) throws X {
        return null;
    }

    @Nullable
    @Override
    public Exception getException() {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(@NonNull OnSuccessListener<? super Void> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super Void> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super Void> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        return null;
    }
}
