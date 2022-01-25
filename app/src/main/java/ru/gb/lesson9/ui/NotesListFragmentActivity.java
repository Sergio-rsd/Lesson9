package ru.gb.lesson9.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.gb.lesson9.R;
import ru.gb.lesson9.data.InMemoryRepoImpl;
import ru.gb.lesson9.data.Note;
import ru.gb.lesson9.data.Repo;
import ru.gb.lesson9.fragment.EditNoteFragment;
import ru.gb.lesson9.fragment.RecyclerFragmentNote;

public class NotesListFragmentActivity extends AppCompatActivity implements RecyclerFragmentNote.Controller {
    private static final String RESULT = "RESULT";

//    private static final String TAG = "happy";

    private Repo repository = InMemoryRepoImpl.getInstance();
    Note note;

    private FragmentManager fragmentManager;
    private boolean mIsDynamic = false;
    public static final String EDIT_NOTE_TAG = "EDIT_NOTE_TAG";
    private EditNoteFragment editNoteFragment;
    private RecyclerFragmentNote recyclerFragmentNote;


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

        fragmentManager = getSupportFragmentManager();

        mIsDynamic = (findViewById(R.id.edit_note_holder) != null);

        editNoteFragment = (EditNoteFragment) fragmentManager.findFragmentByTag(EDIT_NOTE_TAG);

        if (editNoteFragment != null) {
            Bundle args = editNoteFragment.getArguments();
            Note note = (Note) args.getSerializable(RESULT);

//            Note note = editNoteFragment.getArguments();

            fragmentManager.popBackStack(EDIT_NOTE_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fragmentManager.executePendingTransactions();

            if (mIsDynamic) {
                fragmentManager
                        .beginTransaction()
//                        .replace(R.id.edit_note_holder, EditNoteFragment.getInstance(note), EDIT_NOTE_TAG)
                        .add(R.id.edit_note_holder, EditNoteFragment.getInstance(note), EDIT_NOTE_TAG)
                        .addToBackStack(EDIT_NOTE_TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager
                        .beginTransaction()
//                        .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note), EDIT_NOTE_TAG)
                        .add(R.id.main_list_notes, EditNoteFragment.getInstance(note), EDIT_NOTE_TAG)
                        .addToBackStack(EDIT_NOTE_TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }

        }

    }

    @Override
    public void beginEditNote(Note note) {

        if (isLandscape()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.edit_note_holder, EditNoteFragment.getInstance(note), EDIT_NOTE_TAG)
//                    .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note))
                    .addToBackStack(EDIT_NOTE_TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note), EDIT_NOTE_TAG)
                    .addToBackStack(EDIT_NOTE_TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
//        recyclerFragmentNote.updateNotes(note,note.getId());
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
                Note note = new Note(-1, "New title", "New description","","");

                if (isLandscape()) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.edit_note_holder, EditNoteFragment.getInstance(note), EDIT_NOTE_TAG)
                            .addToBackStack(EDIT_NOTE_TAG)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note), EDIT_NOTE_TAG)
                            .addToBackStack(EDIT_NOTE_TAG)
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