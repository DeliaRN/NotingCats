package com.dels.notisimas.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.LottieAnimationView


import com.dels.notisimas.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Se llama a la animación cuando la app se abre.
         */
        val confettiView = findViewById<LottieAnimationView>(R.id.confettiView)
        confettiView.visibility = View.VISIBLE
        confettiView.playAnimation()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        val adderButton = findViewById<ImageButton>(R.id.adder_fab)
        adderButton.setOnClickListener{
            Toast.makeText(this, "¡Añadiendo...!", Toast.LENGTH_SHORT).show()
        }

        adderButton.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToNoteEditorFragment(noteId = -1)
            navController.navigate(action)
        }

        val helperButton = findViewById<ImageButton>(R.id.helper_fab)
        helperButton.setOnClickListener{
            Toast.makeText(this, "¡Ayudando...!", Toast.LENGTH_SHORT).show()
        }

        val setterButton = findViewById<ImageButton>(R.id.setter_fab)
        setterButton.setOnClickListener{
            Toast.makeText(this, "¡Ajustando...!", Toast.LENGTH_SHORT).show()
        }

    }

}