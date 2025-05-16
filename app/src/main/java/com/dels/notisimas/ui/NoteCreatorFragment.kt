package com.dels.notisimas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.navigateUp
import com.dels.notisimas.R
import com.dels.notisimas.data.NoteDatabase
import com.dels.notisimas.data.NoteCreatorViewModelFactory
import com.dels.notisimas.data.NoteEntity
import com.dels.notisimas.data.NoteRepository
import kotlinx.coroutines.launch

class NoteCreatorFragment : Fragment() {

    private lateinit var viewModel: NoteCreatorViewModel
    private val args: NoteCreatorFragmentArgs by navArgs()
    private lateinit var titleEditText: EditText
    private lateinit var  contentEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = NoteDatabase.getInstance(requireContext()).noteDao()
        val repo = NoteRepository(dao)
        val factory = NoteCreatorViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory)[NoteCreatorViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as? MainActivity)?.setAdderAsDeleter(true) {
            confirmarBorrado()
        }

        val view = inflater.inflate(R.layout.fragment_note_editor, container, false)

        titleEditText = view.findViewById(R.id.noteTitle)
        contentEditText = view.findViewById(R.id.noteContent)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)

        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)

        /**
         * Doble opción de navegación: Tenemos el botón físimo de atrás y el botón de la toolbar
         */

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            guardarNota()
            findNavController().navigateUp()
        }

        toolbar.setNavigationOnClickListener {
            guardarNota()
            findNavController().navigateUp()
        }

        /**
         * Funcionalidad de selector de iconos para cada nota
         */

        val noteIcon: ImageButton = view.findViewById(R.id.noteIcon)
        noteIcon.setOnClickListener{
            val dialog = IconChooserDialogFragment {
                    selectedIconId -> noteIcon.setImageResource(selectedIconId)

                viewModel.setSelectedIcon(selectedIconId)
            }

            dialog.show(parentFragmentManager, "IconChooserDialog")
            viewModel.selectedIcon.observe(viewLifecycleOwner) { resId ->
                noteIcon.setImageResource(resId)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as? MainActivity)?.setAdderAsDeleter(false)
    }

    /**
     *  Funciones auxiliares de navegación y funcionalidad del NoteEditor:
     *
     *  guardarNota(): añade o actualiza la nota. Al terminar la función,
     *  devuelve el botón de añadir nota a su estado original.
     *  Puesto aquí para evitar duplicar código y garantizar la acción modular
     *  del código. Si se guarda, sí o sí tiene que volver a su estado.
     *
     *  confirmarBorrado(): Spawnea un diálogo de confirmación.
     *  En caso afirmativo, borra la nota y vuelve a la página principal.
     *  En caso negativo, no hace nada.
     *
     *  NOTA: En un futuro, probablemente debería extraerse a una clase abstracta padre
     *  para centralizar estas dos funciones en un único modelo BaseNoteFragment.
     */

    private fun guardarNota() {
        val title = titleEditText.text.toString()
        val content = contentEditText.text.toString()
        val iconId = viewModel.selectedIcon.value ?: R.drawable.note_icon1

        if (title.isBlank() && content.isBlank()) return //Da igual si puso icono, color, o no

        val note = NoteEntity(
            id = if (args.noteId == -1) 0 else args.noteId,
            title = title,
            content = content,
            iconId = iconId,
            colorHex = "#ffffff"
        )

        lifecycleScope.launch {
                viewModel.insertNote(note)
        }
        /**
         * Garantizar que el botón de añadir nota se activa al salir de la vista
         */
        (requireActivity() as? MainActivity)?.setAdderAsDeleter(false)
    }

    private fun confirmarBorrado() {
        AlertDialog.Builder(requireContext())
            .setTitle("¿Estás segurrrrro?")
            .setMessage("La borrarás para siempre")
            .setIcon(R.drawable.helper_cat)
            .setPositiveButton("Eliminar") {_, _ ->
                lifecycleScope.launch {
                    viewModel.deleteNote(args.noteId)
                    (requireActivity() as? MainActivity)?.setAdderAsDeleter(false)
                    findNavController().navigateUp()
                }
            }
            .setNegativeButton("Mejor no", null)
            .show()
    }

}