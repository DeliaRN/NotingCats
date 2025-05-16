package com.dels.notisimas.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import com.dels.notisimas.R

class IconChooserDialogFragment(
    private val onIconSelected: (Int) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = layoutInflater.inflate(R.layout.icon_choser, null)

        val iconMap = listOf(
            R.id.icon1 to R.drawable.note_icon1 ,
                    R.id.icon2 to R.drawable.note_icon2,
                    R.id.icon3 to R.drawable.note_icon3,
                    R.id.icon4 to R.drawable.note_icon4,
                    R.id.icon5 to R.drawable.note_icon5,
                    R.id.icon6 to R.drawable.note_icon6,
                    R.id.icon7 to R.drawable.note_icon7,
                    R.id.icon8 to R.drawable.note_icon8,
                    R.id.icon9 to R.drawable.note_icon9
        )

        iconMap.forEach { (viewId, drawableId) ->
            dialogView.findViewById<ImageButton>(viewId).setOnClickListener {
                onIconSelected(drawableId)
                dismiss()
            }
        }

        return AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
    }

}