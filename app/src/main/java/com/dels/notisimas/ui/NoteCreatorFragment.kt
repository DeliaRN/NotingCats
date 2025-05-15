package com.dels.notisimas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.dels.notisimas.R
import com.dels.notisimas.data.NoteDatabase
import com.dels.notisimas.data.NoteCreatorViewModelFactory
import com.dels.notisimas.data.NoteRepository



class NoteCreatorFragment : Fragment() {

    private lateinit var titleEditText: EditText
    private lateinit var  contentEditText: EditText
    private lateinit var viewModel: NoteCreatorViewModel

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
        (requireActivity() as? MainActivity)?.setAddButtonEnabled(false)
        (requireActivity() as? MainActivity)?.setAdderAsDeleter(true)

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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            guardarNota() // implement
            findNavController().navigateUp()
        }

        toolbar.setNavigationOnClickListener {
            guardarNota() // implement
            findNavController()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? MainActivity)?.setAddButtonEnabled(true)
        (requireActivity() as? MainActivity)?.setAdderAsDeleter(false)
    }

}