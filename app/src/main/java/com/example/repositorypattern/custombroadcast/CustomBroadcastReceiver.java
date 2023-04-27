package com.example.repositorypattern.custombroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.jetbrains.annotations.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom Broadcast Receiver
 */
public class CustomBroadcastReceiver extends BroadcastReceiver {

    /**
     * set of NetworkStateReceiverListener listeners
     */
    protected Set<CustomBroadcastReceiver.CustomBroadcastReceiverListener> listeners; //set of listeners

    /**
     * constructor
     */
    public CustomBroadcastReceiver() {
        listeners = new HashSet<>();
    }

    /**
     * function callback
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if(listeners == null && intent == null) {
            return;
        }
        listeners.iterator().next().onCustomBroadcast();
    }

    /**
     * function
     */
    public void removeListener(CustomBroadcastReceiver.CustomBroadcastReceiverListener l) {
        listeners.remove(l);
    }

    /**
     * function
     */
    public void addListener(@NotNull BroadcastReceiverActivity broadcastReceiverActivity) {
        listeners.add(broadcastReceiverActivity);
    }

    /**
     * NetworkStateReceiverListener interface
     */
    public interface CustomBroadcastReceiverListener {
        /**
         * callback
         */
        void onCustomBroadcast();
    }
}

