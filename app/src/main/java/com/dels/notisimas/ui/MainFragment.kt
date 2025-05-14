package com.dels.notisimas.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dels.notisimas.Note.Note
import com.dels.notisimas.Note.NoteAdapter
import com.dels.notisimas.data.NoteDatabase
import com.dels.notisimas.data.NoteEditorViewModelFactory
import com.dels.notisimas.data.NoteRepository
import com.dels.notisimas.databinding.FragmentMainBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // Example for testing
    /*val notes = listOf(
        Note(1, "aaa", "qwertuyyiop"/*, 1*/, "#bae2ff"),
        Note(2, "bbb", "asdfghjk"/*, 1*/ ,"#ffffff"),
        Note(3, "ccc", "zxcvbnm,"/*, 1*/, "#75b66a")
    )*/

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter
    private lateinit var viewModel: MainViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val dao = NoteDatabase.getInstance(requireContext()).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteEditorViewModelFactory(repository)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        recyclerView = _binding!!.notesRecyclerView


        adapter = NoteAdapter(emptyList()) { note ->
            /**Toast.makeText(this, "click en: ${note.title}",
            Toast.LENGTH_SHORT).show()*/
            val action = MainFragmentDirections
                .actionMainFragmentToNoteEditorFragment(note.id)
            findNavController().navigate(action)
        }
        _binding!!.notesRecyclerView.adapter = adapter
        _binding!!.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.notes.observe(viewLifecycleOwner) {notes ->
            adapter.updateNotes(notes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
