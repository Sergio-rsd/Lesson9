package ru.gb.lesson7.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
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

    private static final String TAG = "happy";
    private Repo repository = InMemoryRepoImpl.getInstance();
    Note note;
    private boolean mIsDynamic;

//    public static final String TAG = "happy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list_fragment);

        RecyclerFragmentNote listNotes = new RecyclerFragmentNote();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_list_notes, listNotes)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

/*

        EditNoteFragment editNoteFragment = (EditNoteFragment) getSupportFragmentManager().findFragmentById(R.id.main_list_notes);
        mIsDynamic = editNoteFragment == null || !editNoteFragment.isInLayout();
        Toast.makeText(this, "Проверка существования " + mIsDynamic, Toast.LENGTH_SHORT).show();

*/

    }

    @Override
    public void beginEditNote(Note note) {

        if (isLandscape()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.edit_note_holder, EditNoteFragment.getInstance(note))
//                    .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note))
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note))
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

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

                if (isLandscape()) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.edit_note_holder, EditNoteFragment.getInstance(note))
                            .addToBackStack(null)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note))
                            .addToBackStack(null)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}