package com.project.netprime.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface WishListDetailsDAO {

    @Insert
    fun insert(wishList  : WishListDetails)
    
}