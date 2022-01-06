package ru.gb.lesson6.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.gb.lesson6.R;
import ru.gb.lesson6.data.Constants;
import ru.gb.lesson6.data.InMemoryRepoImpl;
import ru.gb.lesson6.data.Note;
import ru.gb.lesson6.data.Repo;

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
//            if (intent.hasExtra(Constants.NOTE_ID) && intent.getIntExtra(Constants.NOTE_ID, id_new) == id) {
//            if (intent.hasExtra(Constants.NOTE_ID)) {
//                int id_new = intent.getIntExtra(Constants.NOTE_ID,id_create);
//                int id_new = intent.getIntExtra(Constants.NOTE_ID,id_create);
//                if (intent.getIntExtra(Constants.NOTE_ID, id_create) == id_create) {

//                Note note = new Note(intent.getIntExtra(Constants.NOTE_ID, id_new), "New title", "New description");
//                Note note = new Note( "New title", "New description");
////                note.setTitle(String.valueOf(title));
////                note.setDescription(String.valueOf(description));
////                id = note.getId();
//                title.setText(note.getTitle());
//                description.setText(note.getDescription());

//                }

//            } else {
//            if (intent.getIntExtra(Constants.NOTE, id_create) == id_create) {
//                Note note = (Note) intent.getSerializableExtra(Constants.NOTE);
//                note.setTitle(title.getText().toString());
//                note.setDescription(description.getText().toString());
//            } else {
            Note note = (Note) intent.getSerializableExtra(Constants.NOTE);
            id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());
//            }
        }
        saveNote.setOnClickListener(this);
//        }
    }

    @Override
    public void onClick(View v) {
//        note.setTitle(String.valueOf(title));
//        note.setDescription(String.valueOf(description));

//        note.setTitle(title.getText().toString());
//        note.setDescription(description.getText().toString());
        Note note = new Note(id, title.getText().toString(), description.getText().toString());

//        if (note.getId() == id) {
        if (id == -1) {
            repository.create(note);
//            Toast.makeText(this,
//                    "Create note " + note.getId(),
//                    Toast.LENGTH_LONG
//                    ).show();
//            id = repository.create(note);
            Log.d(TAG, "Create note " + note.getId());
//            Log.d(TAG, "Create note " + id);
//            id = note.getId();
//            title.setText(note.getTitle());
//            description.setText(note.getDescription());
        } else {
            repository.update(note);
            Log.d(TAG, "Update note " + note.getId());
        }
//        onBackPressed();

        Intent intent = new Intent(this,NotesListActivity.class);
        intent.putExtra(Constants.NOTE,note);
        startActivity(intent);
        finish();

    }
}
