package com.dels.notisimas.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView


import com.dels.notisimas.R
import com.dels.notisimas.data.NoteDAO
import com.dels.notisimas.data.NoteEntity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var noteDao: NoteDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Log.d("ICON_ID", "note_icon1 ID = ${R.drawable.note_icon1}")

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

        /**
         * Adder button for new notes
         */
        val adderButton = findViewById<ImageButton>(R.id.adder_fab)
        adderButton?.isEnabled = true
        adderButton.setOnClickListener{
            Toast.makeText(this, "¡Añadiendo...!", Toast.LENGTH_SHORT).show()
        }

        adderButton.setOnClickListener{
            /*val action = MainFragmentDirections.actionMainFragmentToNoteCreatorFragment(noteId = -1)
            navController.navigate(action)*/
            openNoteCreatorFragment()
        }

        /**
         * Botón de ayuda para futuras funcionalidades - Sólo toaster de momento -
         */
        val helperButton = findViewById<ImageButton>(R.id.helper_fab)
        helperButton.setOnClickListener{
            Toast.makeText(this, "¡Ayudando...!", Toast.LENGTH_SHORT).show()
            ayudaInutil()
        }

        /**
         * Botón de settings para futuras funcionalidades - Sólo toaster de momento -
         */
        val setterButton = findViewById<ImageButton>(R.id.setter_fab)
        setterButton.setOnClickListener{

            Toast.makeText(this, "¡Ajustando...!", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * Funciones auxiliares para la navegación
     */

    fun setAdderAsDeleter(enabled: Boolean, onClick: (()-> Unit)? = null) {
        val adderButton = findViewById<ImageButton>(R.id.adder_fab)

        if(enabled) {
            adderButton.setBackgroundResource(R.drawable.eraser_cat)
            adderButton.setOnClickListener{
                onClick?.invoke()
            }
        } else {
            adderButton.setBackgroundResource(R.drawable.adder_cat)
            adderButton.setOnClickListener{
                openNoteCreatorFragment() //OPEN NOTE CREATOR
            }
        }
    }


    private fun openNoteCreatorFragment() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        val action = MainFragmentDirections.actionMainFragmentToNoteCreatorFragment(noteId = -1)
        navController.navigate(action)
    }

    private fun ayudaInutil() {
        AlertDialog.Builder(this)
            .setTitle("Soy el Gato Ayudante")
            .setMessage("De momento, no ayudo mucho. \nSólo te informo de que puedes:" +
                    "\n- Editar las notas tocándolas" +
                    "\n- Crear notas nuevas tocando a mi colega, el gato Añadidor" +
                    "\n Todos los elementos de las notas son editables, título, contenido ¡e icono!" +
                    "\n \n~¡Por cierto! Que no te engañe el Gato Ajustador, es un mentiroso...")
            .setIcon(R.drawable.helper_cat)
            .setNeutralButton("Ok..?", null)
            .show()
    }
}