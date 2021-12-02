package com.example.theapp.database

import com.example.theapp.PhoneDatabase
import com.example.theapp.UserEntity
import javax.inject.Inject

class UserDao @Inject constructor(
    private val database: PhoneDatabase
) {

    fun getUser(id: String): UserEntity {
        return database.phoneDatabaseQueries.selectUser(id).executeAsOne()
    }

    suspend fun setup() {
        insertUser()
        insertCategories()
        insertCatalogues()
        insertProducts()
        insertProductVariants()
        insertShoppingCart()
        insertShoppingCartItems()
        insertCustomers()
        insertOrder()
        insertOrderItems()
    }

    suspend fun insertUser(user: UserEntity) {
//        database.phoneDatabaseQueries.insertUser(
//            id = user.id,
//            first_name = user.first_name,
//            last_name = user.last_name,
//            phone_number = user.phone_number
//        )
        setup()
    }

    private fun insertCategories() {
        database.phoneDatabaseQueries.insertCategory(
            id = "AA",
            name = "Kurti"
        )

        database.phoneDatabaseQueries.insertCategory(
            id = "AB",
            name = "Dupatta"
        )
    }

    private fun insertCatalogues() {
        database.phoneDatabaseQueries.insertCatalogue(
            id = "1A",
            name = "Ravishing Shaadi Kurtis",
            description = "Dynamic shaadi kurtis",
            category_id = "AA"
        )

        database.phoneDatabaseQueries.insertCatalogue(
            id = "1B",
            name = "Flamboyant Kurtis",
            description = "Daily wear kurtis",
            category_id = "AA"
        )

        database.phoneDatabaseQueries.insertCatalogue(
            id = "1C",
            name = "Shaadi Dupattas",
            description = "Beautiful dupattas",
            category_id = "AB"
        )
    }

    private fun insertProducts() {
        // Catalogue 1
        database.phoneDatabaseQueries.insertProduct(
            id = "P1",
            name = "Red Shaadi Kurti",
            price = 550,
            catalogue_id = "1A"
        )

        database.phoneDatabaseQueries.insertProduct(
            id = "P2",
            name = "Green Shaadi Kurti",
            price = 600,
            catalogue_id = "1A"
        )

        // Catalogue 2
        database.phoneDatabaseQueries.insertProduct(
            id = "P3",
            name = "Daily Wear Grey Kurti",
            price = 300,
            catalogue_id = "1B"
        )

        database.phoneDatabaseQueries.insertProduct(
            id = "P4",
            name = "Daily Wear Green Kurti",
            price = 360,
            catalogue_id = "1B"
        )

        // Catalogue 3
        database.phoneDatabaseQueries.insertProduct(
            id = "P5",
            name = "Shocking Red Dupatta",
            price = 1200,
            catalogue_id = "1C"
        )

        database.phoneDatabaseQueries.insertProduct(
            id = "P6",
            name = "Shocking Blue Dupatta",
            price = 1000,
            catalogue_id = "1C"
        )
    }

    private fun insertProductVariants() {
        // Product 1
        database.phoneDatabaseQueries.insertProductVariant(
            id = "PV1",
            name = "Small",
            abbreviation = "S",
            product_id = "P1"
        )

        database.phoneDatabaseQueries.insertProductVariant(
            id = "PV2",
            name = "Medium",
            abbreviation = "L",
            product_id = "P1"
        )

        // Product 2
        database.phoneDatabaseQueries.insertProductVariant(
            id = "PV3",
            name = "Small",
            abbreviation = "S",
            product_id = "P2"
        )
        database.phoneDatabaseQueries.insertProductVariant(
            id = "PV4",
            name = "Large",
            abbreviation = "L",
            product_id = "P2"
        )

        // Product 3
        database.phoneDatabaseQueries.insertProductVariant(
            id = "PV5",
            name = "Large",
            abbreviation = "L",
            product_id = "P3"
        )

        // Product 4
        database.phoneDatabaseQueries.insertProductVariant(
            id = "PV6",
            name = "Extra Large",
            abbreviation = "XL",
            product_id = "P4"
        )

        // Product 5
        database.phoneDatabaseQueries.insertProductVariant(
            id = "PV7",
            name = "Free Size",
            abbreviation = "Free Size",
            product_id = "P5"
        )

        // Product 6
        database.phoneDatabaseQueries.insertProductVariant(
            id = "PV8",
            name = "Free Size",
            abbreviation = "Free Size",
            product_id = "P6"
        )
    }

    private fun insertShoppingCart() {
        database.phoneDatabaseQueries.insertShoppingCart(
            id = "S1",
            margin = null,
            cash_to_collect = null,
        )
    }

    private fun insertShoppingCartItems() {
        database.phoneDatabaseQueries.insertOrReplaceShoppingCartItem(
            id = "1SIT",
            shopping_cart_id = "S1",
            product_variant_id = "PV4",
            quantity = 2
        )


        database.phoneDatabaseQueries.insertOrReplaceShoppingCartItem(
            id = "2SIT",
            shopping_cart_id = "S1",
            product_variant_id = "PV7",
            quantity = 1
        )
    }

    private fun insertCustomers() {
        database.phoneDatabaseQueries.insertCustomer(
            id = "1",
            name = "Ralf Rangnick",
            phone_number = "92321820093",
            address_line_1 = "Apt 34-A, 13th Street",
            address_line_2 = "Trafford Rd, Trafford",
            city = "Manchester",
            zip_code = "75500"
        )

        database.phoneDatabaseQueries.insertCustomer(
            id = "2",
            name = "Thomas Tuchel",
            phone_number = "92321833893",
            address_line_1 = "64B/2, 29th Street",
            address_line_2 = "Stamford Bridge",
            city = "London",
            zip_code = "M4P1Z2"
        )
    }

    private fun insertOrder() {
        database.phoneDatabaseQueries.insertOrder(
            id = "O1",
            margin = 300,
            cash_to_collect = 1000,
            product_charges = 700,
            delivery_charges = 0,
            order_total = 700,
            customer_id = "2",
            status = "In Progress",
            date_placed = "21-08-2022"
        )

        database.phoneDatabaseQueries.insertOrder(
            id = "O2",
            margin = 500,
            cash_to_collect = 1500,
            product_charges = 1000,
            delivery_charges = 0,
            order_total = 1000,
            customer_id = "1",
            status = "In Progress",
            date_placed = "22-04-2022"
        )

        database.phoneDatabaseQueries.insertOrder(
            id = "O3",
            margin = 400,
            cash_to_collect = 1500,
            product_charges = 1100,
            delivery_charges = 0,
            order_total = 1100,
            customer_id = "1",
            status = "Completed",
            date_placed = "01-01-2022"
        )

        database.phoneDatabaseQueries.insertOrder(
            id = "O4",
            margin = 150,
            cash_to_collect = 550,
            product_charges = 400,
            delivery_charges = 0,
            order_total = 400,
            customer_id = "1",
            status = "Completed",
            date_placed = "10-01-2022"
        )

        database.phoneDatabaseQueries.insertOrder(
            id = "O5",
            margin = 600,
            cash_to_collect = 2000,
            product_charges = 1200,
            delivery_charges = 0,
            order_total = 1200,
            customer_id = "2",
            status = "Completed",
            date_placed = "15-04-2022"
        )

        database.phoneDatabaseQueries.insertOrder(
            id = "O6",
            margin = 1500,
            cash_to_collect = 3000,
            product_charges = 1500,
            delivery_charges = 0,
            order_total = 1500,
            customer_id = "2",
            status = "Cancelled",
            date_placed = "15-04-2022"
        )
    }

    private fun insertOrderItems() {
        //O1
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI1",
            order_id = "O1",
            product_variant_id = "PV4",
            quantity = 3
        )

        //O2
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI2",
            order_id = "O2",
            product_variant_id = "PV2",
            quantity = 2
        )
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI3",
            order_id = "O2",
            product_variant_id = "PV6",
            quantity = 1
        )

        //O3
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI4",
            order_id = "O3",
            product_variant_id = "PV1",
            quantity = 1
        )
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI5",
            order_id = "O3",
            product_variant_id = "PV3",
            quantity = 1
        )
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI6",
            order_id = "O3",
            product_variant_id = "PV8",
            quantity = 1
        )

        //O4
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI7",
            order_id = "O4",
            product_variant_id = "PV1",
            quantity = 1
        )
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI8",
            order_id = "O4",
            product_variant_id = "PV3",
            quantity = 1
        )

        //O5
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI9",
            order_id = "O5",
            product_variant_id = "PV1",
            quantity = 1
        )
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI10",
            order_id = "O5",
            product_variant_id = "PV3",
            quantity = 1
        )

        //O6
        database.phoneDatabaseQueries.insertOrderItem(
            id = "OI11",
            order_id = "O6",
            product_variant_id = "PV3",
            quantity = 1
        )
    }

    private fun insertUser() {
        database.phoneDatabaseQueries.insertUser(
            id = "1",
            first_name = "Nuno",
            last_name = "Espritos Santos",
            phone_number = "111 222 333"
        )
    }

}