package ru.gb.lesson9.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.lesson9.R;
import ru.gb.lesson9.data.InMemoryRepoImpl;
import ru.gb.lesson9.data.Note;
import ru.gb.lesson9.data.PopupMenuClick;
import ru.gb.lesson9.data.Repo;
import ru.gb.lesson9.data.YesNoDialogController;
import ru.gb.lesson9.dialog.NoteDialog;
import ru.gb.lesson9.dialog.YesNoDialog;
import ru.gb.lesson9.recycler.NoteHolder;
import ru.gb.lesson9.recycler.NotesAdapter;

public class MainActivity extends AppCompatActivity
        implements PopupMenuClick,
        NoteDialog.NoteDialogController,
        YesNoDialogController {

    private Repo repository = InMemoryRepoImpl.getInstance();
    Note note;
    RecyclerView listAdapter;
    private NotesAdapter adapter = new NotesAdapter();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycle_note);

        adapter.setOnPopupMenuClick(this);

        listAdapter = findViewById(R.id.list_notes);
        listAdapter.setAdapter(adapter);
        listAdapter.setLayoutManager(new LinearLayoutManager(this));
        adapter.setNotes(repository.getAll());

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(0, swipeFlag);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                NoteHolder holder = (NoteHolder) viewHolder;
                Note note = holder.getNote();
                repository.delete(note.getId());
                adapter.delete(repository.getAll(), position);

            }
        });
        helper.attachToRecyclerView(listAdapter);

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
                NoteDialog.getInstance(null).show(
                        getSupportFragmentManager(),
                        NoteDialog.NOTE
                );
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void click(int command, Note note, int position) {
        switch (command) {
            case R.id.context_delete:
                repository.delete(note.getId());
                adapter.delete(repository.getAll(), position);
                return;
            case R.id.context_modify:
                NoteDialog.getInstance(note).show(
                        getSupportFragmentManager(),
                        NoteDialog.NOTE
                );

                return;
        }
    }

    @Override
    public void update(Note note) {
        repository.update(note);
        adapter.setNotes(repository.getAll());
    }

    @Override
    public void create(String title, String description, String interest, String dataPerformance) {
        Note note = new Note(title, description, interest, dataPerformance);
        repository.create(note);
        adapter.setNotes(repository.getAll());

    }

    @Override
    public void onBackPressed() {
        showYesNoDialogFragment();

    }

    private void showYesNoDialogFragment() {
        new YesNoDialog().show(getSupportFragmentManager(), null);
    }

    @Override
    public void createAnswer() {
        finish();
    }
}
