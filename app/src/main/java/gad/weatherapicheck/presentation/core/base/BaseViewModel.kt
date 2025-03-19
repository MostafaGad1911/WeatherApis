package gad.weatherapicheck.presentation.core.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

open class BaseViewModel(
    protected val application: Application,
) : ViewModel() {


    private var navController: NavController? = null



    fun onBackPressed() {
       navController?.navigateUp()
    }

    fun navigate(route: String) {
        navController?.navigate(route)
    }


    fun getNavyController(): NavController? = navController

    open fun updateNavController(navController: NavController) {
        this.navController = navController
    }

}