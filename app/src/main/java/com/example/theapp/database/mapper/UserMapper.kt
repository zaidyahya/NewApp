package com.example.theapp.database.mapper

import com.example.theapp.UserEntity
import com.example.theapp.model.User

fun UserEntity.toDomain() = User(
    id,
    first_name,
    last_name,
    phone_number
)

fun User.toEntity() = UserEntity(
    id,
    firstName,
    lastName,
    phoneNumber
)