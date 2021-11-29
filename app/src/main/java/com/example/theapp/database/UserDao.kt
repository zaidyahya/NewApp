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
        database.phoneDatabaseQueries.insertShoppingCartItem(
            id = "1SIT",
            shopping_cart_id = "S1",
            product_variant_id = "PV4",
            quantity = 2
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