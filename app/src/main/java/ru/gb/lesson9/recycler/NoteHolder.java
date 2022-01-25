package ru.gb.lesson9.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.lesson9.R;
import ru.gb.lesson9.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private TextView interest;
    private TextView dataPerformance;
    private Note note;

    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        interest = itemView.findViewById(R.id.interest_currency);
        dataPerformance = itemView.findViewById(R.id.data_stamp);

        itemView.setOnClickListener(v -> listener.onNoteClick(note));
    }

    void bind(Note note)
    {
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        interest.setText(note.getInterest());
        dataPerformance.setText(note.getDataPerformance());
    }
}
