package ru.gb.lesson9.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.lesson9.R;
import ru.gb.lesson9.data.InMemoryRepoImpl;
import ru.gb.lesson9.data.Note;
import ru.gb.lesson9.data.PopupMenuClick;
import ru.gb.lesson9.data.Repo;
import ru.gb.lesson9.recycler.NotesAdapter;

//public class RecyclerFragmentNote extends Fragment implements NotesAdapter.OnNoteClickListener {
public class RecyclerFragmentNote extends Fragment implements PopupMenuClick {

    private static final String TAG = "happy";
    private Repo repository = InMemoryRepoImpl.getInstance();
    private NotesAdapter adapter = new NotesAdapter();
    RecyclerView listAdapter;
    private Note note;
    public static final String EDIT_NOTE ="EDIT_NOTE";
    public static final String REQUEST_KEY = "REQUEST_KEY";


    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof Controller) {
            this.controller = (Controller) context;
        } else {
            throw new IllegalStateException("Activity must implement Controller");
        }
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getSerializable(EDIT_NOTE);
                updateNotes(note, note.getId());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycle_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        adapter.setNotes(repository.getAll());
        adapter.setOnPopupMenuClick(this);
        listAdapter = view.findViewById(R.id.list_notes);
        listAdapter.setLayoutManager(new LinearLayoutManager(requireContext()));
        listAdapter.setAdapter(adapter);

    }

    public void updateNotes(Note note, int position) {
        if (note.getId() == -1) {
            repository.create(note);
            adapter.notifyItemInserted(repository.getAll().size());
        } else {
            repository.update(note);
            adapter.notifyItemChanged(position);
        }
    }

    @Override
    public void click(int command, Note note, int position) {
        switch (command){
            case R.id.context_delete:
                //
                repository.delete(note.getId());
                adapter.delete(repository.getAll(), position);
                return;
        }
    }

    public interface Controller {
        void beginEditNote(Note note);
    }

    private Controller controller;

/*    @Override
    public void onNoteClick(Note note) {

        controller.beginEditNote(note);

    }*/

}
