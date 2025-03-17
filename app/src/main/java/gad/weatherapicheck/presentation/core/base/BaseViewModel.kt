package gad.weatherapicheck.presentation.core.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

open class BaseViewModel(
    protected val application: Application,
) : ViewModel() {


    private var navController: NavController? = null



    fun onBackPressed() {
        val currentDestination = navController?.currentBackStackEntry?.destination
        if (currentDestination?.id != navController?.graph?.startDestinationId && currentDestination?.id != null) {
            navController?.popBackStack(currentDestination.id, inclusive = true, saveState = false)
        }

    }

    fun navigate(route: String) {
        navController?.navigate(route) {
            popUpTo(navController?.graph?.startDestinationId!!) {
                inclusive = true
                saveState = false

            }
        }
    }


    fun getNavyController(): NavController? = navController

    open fun updateNavController(navController: NavController) {
        this.navController = navController
    }

}