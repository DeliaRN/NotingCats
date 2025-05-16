package com.dels.notisimas.Note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dels.notisimas.R
import com.dels.notisimas.data.NoteEntity

class NoteAdapter(
    private var notes: List<NoteEntity>,
    private val onItemClick: (NoteEntity) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.noteIcon)
        private val title: TextView = itemView.findViewById(R.id.noteTitle)

        init{
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val note = notes[position]
                    onItemClick(note)
                }
            }
        }

        fun bind(note: NoteEntity) {
            icon.setImageResource(note.iconId)
            title.text = note.title

            itemView.setOnClickListener{
                onItemClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(newNotes: List<NoteEntity>) {
        this.notes = newNotes
        notifyDataSetChanged()
    }
}