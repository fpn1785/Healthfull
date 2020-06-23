package com.example.healthfull.util;

/**
 * OnDoneListener is a callback interface that provides only two end states success or failure
 * @param <T> type that should be passed through on success
 */
public interface OnDoneListener<T> {
    /**
     * onSuccess callback is called once a task has finished successfully
     * @param object object of type T that is passed through only on success
     */
    void onSuccess(T object);

    /**
     * onFailure callback is called when an error or failure has occurred this does not guarantee
     * that the task has completed successfully
     * @param message error message that was reported by the task
     */
    void onFailure(String message);
}
