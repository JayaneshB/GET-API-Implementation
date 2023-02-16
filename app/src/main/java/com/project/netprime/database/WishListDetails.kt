package com.project.netprime.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WishLists")
class WishListDetails(
    @ColumnInfo(name = "Id")
    @PrimaryKey(autoGenerate = true)
    var id :Int,
    @ColumnInfo(name = "Title")
    var title : String,
    @ColumnInfo(name = "Date")
    var date : String,
    @ColumnInfo(name = "Status")
    var status : String

) {
}