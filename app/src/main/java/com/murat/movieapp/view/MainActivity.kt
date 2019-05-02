package com.murat.movieapp.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.View
import android.view.WindowManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.murat.movieapp.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    override fun supportFragmentInjector(): AndroidInjector<android.support.v4.app.Fragment> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        supportActionBar?.hide()

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        mFirebaseRemoteConfig.fetch().addOnCompleteListener(this, OnCompleteListener {
            if (it.isSuccessful) {
                mFirebaseRemoteConfig.activateFetched()
            }
            displayWelcomeMessage()
        })

    }

    private fun displayWelcomeMessage() {
        message.text = mFirebaseRemoteConfig.getString("appName")
        Handler().postDelayed({
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.content, SearchFragment())
            fragmentTransaction.commit()
            message.visibility = View.GONE
        }, 3000)
    }
}
