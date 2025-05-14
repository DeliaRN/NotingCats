package com.dels.notisimas.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dels.notisimas.Note.Note

import com.dels.notisimas.R
import com.dels.notisimas.data.NoteDatabase
import com.dels.notisimas.data.NoteEditorViewModelFactory
import com.dels.notisimas.data.NoteEntity
import com.dels.notisimas.data.NoteRepository
import kotlinx.coroutines.launch

class NoteEditorFragment : Fragment() {
    /**
     *  Lógica de notas
     * */

    private lateinit var viewModel: NoteEditorViewModel
    private val args: NoteEditorFragmentArgs by navArgs()
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = NoteDatabase.getInstance(requireContext()).noteDao()
        val repo = NoteRepository(dao)
        val factory = NoteEditorViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory)[NoteEditorViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
         * Si se está editando una nota, se carga desde la base de datos
         */

        if (args.noteId != 0) {
            viewModel.getNoteById(args.noteId).observe(viewLifecycleOwner) { note ->
                note.let {
                    titleEditText.setText(it?.title)
                    contentEditText.setText(it?.content)
                }

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun guardarNota() {
        val title = titleEditText.text.toString()
        val content = contentEditText.text.toString()
        //val icon = view?.findViewById<Image?>(R.id.noteIcon)?.text.toString() ??

        if (title.isBlank() && content.isBlank()) return //Da igual si puso icono, color, o no

        val note = NoteEntity(
            id = if (args.noteId == 0) 0 else args.noteId,
            title = title,
            content = content,
            colorHex = "#ffffff"
        )

        lifecycleScope.launch {
            if (args.noteId == 0) {
                viewModel.insertNote(note)
            } else {
                viewModel.updateNote(note)
            }
        }

    }
}