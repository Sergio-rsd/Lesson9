package ru.gb.lesson7.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.gb.lesson7.R;
import ru.gb.lesson7.data.Constants;
import ru.gb.lesson7.data.InMemoryRepoImpl;
import ru.gb.lesson7.data.InitRepo;
import ru.gb.lesson7.data.Note;
import ru.gb.lesson7.data.Repo;
import ru.gb.lesson7.recycler.NotesAdapter;

public class NotesListActivity extends AppCompatActivity implements NotesAdapter.OnNoteClickListener {

    // private Repo repository = new InMemoryRepoImpl();
    private Repo repository = InMemoryRepoImpl.getInstance();
    private RecyclerView list;
    private NotesAdapter adapter;

//    private InitRepo flagRepo = new InitRepo();
//    public static final String RESULT = "RESULT";
//    public static final String TAG = "happy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

//        Log.d(TAG, "Flag Repo After " + flagRepo.isFlagInitRepo());

        adapter = new NotesAdapter();
        adapter.setNotes(repository.getAll());

        adapter.setOnNoteClickListener(this);


        list = findViewById(R.id.list);
        // list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // horizontal
        list.setLayoutManager(new LinearLayoutManager(this)); // Vertical
        list.setAdapter(adapter);

    }


    @Override
    public void onNoteClick(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(Constants.NOTE, note);
//        flagRepo.setFlagInitRepo(true);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_create:
                // создать Intent
                // TODO запустить EditNoteActivity
                Intent intent = new Intent(this, EditNoteActivity.class);
//                flagRepo.setFlagInitRepo(true);
                Note note = new Note(-1, "New title", "New description");
                intent.putExtra(Constants.NOTE, note);
//                Log.d(TAG, "Flag Repo begin CREATE note " + flagRepo.isFlagInitRepo());
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            flagRepo.setFlagInitRepo(true);
            Log.d(TAG, "Flag Repo CREATE note " + flagRepo.isFlagInitRepo());
            adapter.setNotes(repository.getAll());
            list.setAdapter(adapter);
        }
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
//        flagRepo.setFlagInitRepo(true);
//        Log.d(TAG, "Flag Repo UPDATE " + flagRepo.isFlagInitRepo());
        adapter.setNotes(repository.getAll());
        list.setAdapter(adapter);
    }

}