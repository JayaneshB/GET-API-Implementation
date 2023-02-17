package com.project.netprime.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.View
import com.google.android.material.snackbar.Snackbar

class NetworkChangeReceiver(private val view: View): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if(networkInfo !=null && networkInfo.isConnected) {
            Snackbar.make(view,"Back Online",Snackbar.LENGTH_SHORT).show()
        }
        else {
            Snackbar.make(view,"You are Offline",Snackbar.LENGTH_SHORT).show()
        }
    }

}