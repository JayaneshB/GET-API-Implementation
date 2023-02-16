package com.project.netprime.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [WishListDetails::class], version = 1, exportSchema = false)
abstract class NetPrimeDataBase : RoomDatabase() {

    companion object {

        private var INSTANCE : NetPrimeDataBase ?= null

        fun getInstance(context: Context) : NetPrimeDataBase? {

            if(INSTANCE==null) {
                synchronized(NetPrimeDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        NetPrimeDataBase::class.java,
                        "NetPrimeDataBase"
                    ).build()
                    return INSTANCE
                }
            }
            return INSTANCE
        }
    }

    abstract fun wishListDao(): WishListDetailsDAO
}