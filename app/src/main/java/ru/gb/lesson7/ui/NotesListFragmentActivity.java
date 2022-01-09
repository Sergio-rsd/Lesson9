package ru.gb.lesson7.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.gb.lesson7.R;
import ru.gb.lesson7.data.InMemoryRepoImpl;
import ru.gb.lesson7.data.Note;
import ru.gb.lesson7.data.Repo;
import ru.gb.lesson7.fragment.EditNoteFragment;
import ru.gb.lesson7.fragment.RecyclerFragmentNote;

public class NotesListFragmentActivity extends AppCompatActivity implements RecyclerFragmentNote.Controller {
    private Repo repository = InMemoryRepoImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list_fragment);

        RecyclerFragmentNote listNotes = new RecyclerFragmentNote();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_notes, listNotes)
                .commit();
    }

    @Override
    public void beginEditNote(Note note) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note))
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
                        .addToBackStack(null)
                        .commit();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
//        flagRepo.setFlagInitRepo(true);
//        Log.d(TAG, "Flag Repo UPDATE " + flagRepo.isFlagInitRepo());
        adapter.setNotes(repository.getAll());
        list.setAdapter(adapter);
    }
    */

}