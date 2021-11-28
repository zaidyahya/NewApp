package com.example.theapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.theapp.databinding.ActivityMainBinding
import com.example.theapp.ui.checkout.shoppingcart.viewmodel.ShoppingCartViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    val cartViewModel: ShoppingCartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //How many activities?

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController

        // Top-level destinations, meaning back button should not be shown on them
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.marketFragment, R.id.storeFragment, R.id.accountFragment, R.id.orderPlacedFragment)
        )

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        //bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.marketFragment -> binding.bottomNavMenu.visibility = View.VISIBLE
                R.id.storeFragment -> binding.bottomNavMenu.visibility = View.VISIBLE
                R.id.accountFragment -> binding.bottomNavMenu.visibility = View.VISIBLE
                else -> binding.bottomNavMenu.visibility = View.GONE
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    @ExperimentalBadgeUtils // https://github.com/material-components/material-components-android/issues/1405
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)

        val itemsInCart = cartViewModel.order.value?.items?.size!!
        //val itemsInCart = cartViewModel.shoppingCart.value?.items?.size!!

        if (itemsInCart != 0){
            val badgeDrawable = BadgeDrawable.create(this)
            badgeDrawable.number = itemsInCart
            BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.toolbar, R.id.shopping_cart)
        }

        return true
    }

    /**
     * TODO :- ShoppingCart button multiple clicks causes it to nest down
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.shopping_cart -> {
//                if(cartViewModel.order.value?.items?.size == 0) {
//                    val action = NavGraphDirections.actionGlobalShoppingCartEmptyFragment()
//                    navController.navigate(action)
//                } else {
//                    val action = NavGraphDirections.actionGlobalShoppingCartFragment()
//                    navController.navigate(action)
//                }
//                if(cartViewModel.shoppingCart.value?.items?.size == 0) {
//                    val action = NavGraphDirections.actionGlobalShoppingCartEmptyFragment()
//                    navController.navigate(action)
//                } else {
//                    val action = NavGraphDirections.actionGlobalShoppingCartFragment()
//                    navController.navigate(action)
//                }

                val action = NavGraphDirections.actionGlobalShoppingCartFragment()
                navController.navigate(action)
                true
            }
            else -> item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp() : Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun addOrderItem() {
        Log.d("MainActivity", "AddProduct")
        //cartViewModel.addOrderItem()
    }
}