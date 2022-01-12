package ru.gb.lesson7.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ru.gb.lesson7.R;
import ru.gb.lesson7.data.InMemoryRepoImpl;
import ru.gb.lesson7.data.Note;
import ru.gb.lesson7.data.Repo;
import ru.gb.lesson7.fragment.EditNoteFragment;
import ru.gb.lesson7.fragment.RecyclerFragmentNote;

public class NotesListFragmentActivity extends AppCompatActivity implements RecyclerFragmentNote.Controller {

    private Repo repository = InMemoryRepoImpl.getInstance();
    Note note;
    public static final String TAG = "happy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list_fragment);

        RecyclerFragmentNote listNotes = new RecyclerFragmentNote();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_notes, listNotes)
//                .addToBackStack(null) // TODO надо выходить совсем
                .commit();
/*

        if (note != null) {
            Toast.makeText(this, "Редактирование", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
            beginEditNote(note);
*/
/*
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note))
//                .add(R.id.main_list_notes, EditNoteFragment.getInstance(note))
                    .addToBackStack(null)
                    .commit();
            *//*

        }
*/

//        EditNoteFragment editNoteFragment = EditNoteFragment.getInstance();

    }

    @Override
    public void beginEditNote(Note note) {

        getSupportFragmentManager()
//        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note))
//                .add(R.id.main_list_notes, EditNoteFragment.getInstance(note))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.main_create:
                Note note = new Note(-1, "New title", "New description");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note))
//                        .add(R.id.main_list_notes, EditNoteFragment.getInstance(note))
                        .addToBackStack(null)
                        .commit();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*    @Override
    protected void onResume() {
        super.onResume();
        EditNoteFragment editNote = (EditNoteFragment) getSupportFragmentManager().findFragmentById(R.id.edit_note_title);
        if(editNote != null){

//            beginEditNote(Note note); // TODO как?
        }
    }*/
}