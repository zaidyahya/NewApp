package com.example.theapp.database.mapper

import com.example.theapp.CustomerEntity
import com.example.theapp.model.CustomerNew

fun CustomerNew.toEntity() = CustomerEntity(
    id,
    isSelected,
    name,
    phoneNumber,
    addressLine1,
    addressLine2,
    city,
    zipCode
)

fun Iterable<CustomerEntity>.toDomain() = this.map {
    it.toDomain()
}

fun CustomerEntity.toDomain() = CustomerNew(
    id,
    is_selected,
    name,
    phone_number,
    address_line_1,
    address_line_2,
    city,
    zip_code
)