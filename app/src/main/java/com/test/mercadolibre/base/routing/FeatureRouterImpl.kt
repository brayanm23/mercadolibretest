package com.test.mercadolibre.base.routing

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.test.mercadolibre.view.MainActivity

class FeatureRouterImpl : FeatureRouter {
    override fun getFeatureIntent(context: Context?, feature: FeatureRouter.Feature, clearBackStack: Boolean, data: Uri?): Intent? {
        context?.let {
            val cls: Class<*> = when (feature) {
                //is FeatureRouter.Feature.Splash -> SplashActivity::class.java
                is FeatureRouter.Feature.Search -> MainActivity::class.java
                else ->  MainActivity::class.java
            }

            val intent = Intent(it, cls)
            intent.data = data
            feature.createBundleData()?.let { data ->
                intent.putExtras(data.createBundle())
            }
            if (clearBackStack) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            return intent
        }
        return null
    }

}
