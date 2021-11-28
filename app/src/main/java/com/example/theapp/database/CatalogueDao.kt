package com.example.theapp.database

import com.example.theapp.PhoneDatabase
import com.example.theapp.model.CatalogueNew
import com.example.theapp.model.ProductNew
import com.example.theapp.model.ProductVariant
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatalogueDao @Inject constructor(
    private val database: PhoneDatabase
) {

    fun getCatalogues(): Flow<List<CatalogueNew>> {
//        val rows = database.phoneDatabaseQueries.getCatalogues().executeAsList()
//        val catalogueGrouping = rows.groupBy { it.id }
//        val catalogueObjects = catalogueGrouping.map{
//                    (catalogueId, catalogueEntries) ->
//                    CatalogueNew(
//                    id = catalogueId,
//                    name = catalogueEntries[0].name,
//                    description = catalogueEntries[0].description,
//                    category = catalogueEntries[0].category,
//                    products = catalogueEntries.groupBy{ it.product_id }
//                                    .map{ (productId, productVariants) ->
//                                        ProductNew(
//                                            productId,
//                                            productVariants[0].product_name,
//                                            productVariants[0].product_price,
//                                            productVariants.map{ ProductVariant(it.product_variant_id, it.product_variant_name, it.abbreviation) }
//                                        ) }
//                    )
//                }

        val rows = database.phoneDatabaseQueries.getCatalogues().asFlow().mapToList()
        val catalogueGrouping = rows.map { row ->
            row.groupBy { it.id }
        }
        val catalogueObjects = catalogueGrouping.map {
            map -> map.map { (catalogueId, catalogueEntries) ->
                CatalogueNew(
                    id = catalogueId,
                    name = catalogueEntries[0].name,
                    description = catalogueEntries[0].description,
                    category = catalogueEntries[0].category,
                    products = catalogueEntries.groupBy{ it.product_id }
                        .map{ (productId, productVariants) ->
                            ProductNew(
                                productId,
                                productVariants[0].product_name,
                                productVariants[0].product_price,
                                productVariants.map{ ProductVariant(it.product_variant_id, it.product_variant_name, it.abbreviation) }
                            ) }
                )
            }
        }


        //val d = database.phoneDatabaseQueries.getCatalogues().asFlow().mapToList()
        //d.collect {}
        //d.map {}

        return catalogueObjects
    }

}