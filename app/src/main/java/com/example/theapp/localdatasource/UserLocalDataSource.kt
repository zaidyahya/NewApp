package com.example.theapp.localdatasource

import com.example.theapp.database.UserDao
import com.example.theapp.database.mapper.toDomain
import com.example.theapp.database.mapper.toEntity
import com.example.theapp.model.User
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao
) {

    fun getUser(id: String): User {
        return userDao.getUser(id).toDomain()
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

}