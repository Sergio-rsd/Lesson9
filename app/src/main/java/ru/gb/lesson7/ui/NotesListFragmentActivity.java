package ru.gb.lesson7.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.gb.lesson7.R;
import ru.gb.lesson7.fragment.RecyclerFragmentNote;

public class NotesListFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list_fragment);

        RecyclerFragmentNote listNotes = new RecyclerFragmentNote();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_notes,listNotes)
                .commit();
    }
}