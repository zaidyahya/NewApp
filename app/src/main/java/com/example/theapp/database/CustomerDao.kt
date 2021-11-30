package com.example.theapp.database

import com.example.theapp.CustomerEntity
import com.example.theapp.PhoneDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerDao @Inject constructor(
    private val database: PhoneDatabase
) {

    fun getCustomers(): Flow<List<CustomerEntity>> {
        return database.phoneDatabaseQueries.selectAllCustomers().asFlow().mapToList()
    }

    suspend fun insertCustomer(customer: CustomerEntity) {
        database.phoneDatabaseQueries.insertCustomer(
            id = customer.id,
            name = customer.name,
            phone_number = customer.phone_number,
            address_line_1 = customer.address_line_1,
            address_line_2 = customer.address_line_2,
            city = customer.city,
            zip_code = customer.zip_code
        )
    }

    suspend fun updateCustomer(customer: CustomerEntity) {
        database.phoneDatabaseQueries.updateCustomer(
            id = customer.id,
            name = customer.name,
            phone_number = customer.phone_number,
            address_line_1 = customer.address_line_1,
            address_line_2 = customer.address_line_2,
            city = customer.city,
            zip_code = customer.zip_code
        )
    }

    suspend fun updateCustomerSelected(id: String, selected: Boolean) {
        database.phoneDatabaseQueries.updateCustomerSelected(
            id = id,
            is_selected = selected
        )
    }
}