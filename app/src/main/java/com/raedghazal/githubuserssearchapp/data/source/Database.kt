package com.raedghazal.githubuserssearchapp.data.source

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.raedghazal.githubuserssearchapp.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class Database : RoomDatabase() {
}