package com.example.repositorypattern.custombroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom Broadcast Receiver
 */
public class NetworkStateChangeReceiver extends BroadcastReceiver {

    /**
     * set of NetworkStateReceiverListener listeners
     */
    protected Set<NetworkStateReceiverListener> listeners; //set of listeners

    /**
     * boolean flag
     */
    protected Boolean connected;
    /**
     * constructor
     */
    public NetworkStateChangeReceiver() {
        listeners = new HashSet<>();
        connected = null;
    }

    /**
     * function callback
     */
    public void onReceive(Context context, Intent intent) {
        if(intent == null && listeners == null) {
            return;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            connected = false;
        }
        notifyStateToAll();
    }

    private void notifyStateToAll() {
        for (NetworkStateReceiverListener listener : listeners) {
            notifyState(listener);
        }
    }

    private void notifyState(NetworkStateReceiverListener listener) {
        if (connected != null && listener != null) {
            if (connected) {
                listener.networkAvailable();
            } else {
                listener.networkUnavailable();
            }
        }
    }

    /**
     * function
     */
    public void addListener(NetworkStateReceiverListener l) {
        listeners.add(l);
        notifyState(l);
    }

    /**
     * function
     */
    public void removeListener(NetworkStateReceiverListener l) {
        listeners.remove(l);
    }

    /**
     * NetworkStateReceiverListener interface
     */
    public interface NetworkStateReceiverListener {
        /**
         * callback
         */
        void networkAvailable();

        /**
         * callback
         */
        void networkUnavailable();
    }
}

