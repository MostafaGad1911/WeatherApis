package gad.weatherapicheck

import android.app.Application
import gad.weatherapicheck.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LocationApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LocationApp)
            modules(appModule)
        }
    }
}
