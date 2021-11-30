package com.example.theapp.localdatasource

import com.example.theapp.database.CustomerDao
import com.example.theapp.database.mapper.toDomain
import com.example.theapp.database.mapper.toEntity
import com.example.theapp.model.CustomerNew
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerLocalDataSource@Inject constructor(
    private val customerDao: CustomerDao
) {

    suspend fun insertCustomer(customer: CustomerNew) {
        customerDao.insertCustomer(customer.toEntity())
    }

    fun getCustomers(): Flow<List<CustomerNew>> {
        return customerDao.getCustomers().map { list ->
            list.toDomain()
        }
    }

    suspend fun updateCustomer(customer: CustomerNew) {
        customerDao.updateCustomer(customer.toEntity())
    }

    suspend fun updateCustomerSelected(id: String, selected: Boolean) {
        customerDao.updateCustomerSelected(id, selected)
    }
}