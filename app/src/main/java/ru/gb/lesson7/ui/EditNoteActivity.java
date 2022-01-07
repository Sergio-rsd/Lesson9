package ru.gb.lesson7.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.gb.lesson7.R;
import ru.gb.lesson7.data.Constants;
import ru.gb.lesson7.data.InMemoryRepoImpl;
import ru.gb.lesson7.data.Note;
import ru.gb.lesson7.data.Repo;

public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText title;
    private EditText description;
    private Button saveNote;
    private int id = -1;
    private Repo repository = InMemoryRepoImpl.getInstance();
    public static final String TAG = "happy";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.edit_note_title);
        description = findViewById(R.id.edit_note_description);
        saveNote = findViewById(R.id.edit_note_update);

        Intent intent = getIntent();
        if (intent != null) {
            Note note = (Note) intent.getSerializableExtra(Constants.NOTE);
            id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());
        }
        saveNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Note note = new Note(id, title.getText().toString(), description.getText().toString());

        if (id == -1) {
            repository.create(note);
//            Log.d(TAG, "Create note " + note.getId());

        } else {
            repository.update(note);
//            Log.d(TAG, "Update note " + note.getId());
        }

/*
        Intent intent = new Intent(this,NotesListActivity.class);
        intent.putExtra(Constants.NOTE,note);
        startActivity(intent);
*/
        finish();

    }
}
