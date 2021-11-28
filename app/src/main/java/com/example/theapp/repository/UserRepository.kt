package com.example.theapp.repository

import com.example.theapp.localdatasource.UserLocalDataSource
import com.example.theapp.model.User
import java.util.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
){

    fun getUser(id: String): User {
        return userLocalDataSource.getUser(id)
    }

    suspend fun insertUser(
        firstName: String,
        lastName: String,
        phoneNumber: String
    ) {
        userLocalDataSource.insertUser(
            User(
                id = UUID.randomUUID().toString(),
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
        )
    }

}