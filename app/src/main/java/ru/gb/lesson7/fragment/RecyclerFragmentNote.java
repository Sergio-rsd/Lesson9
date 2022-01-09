package ru.gb.lesson7.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.lesson7.R;
import ru.gb.lesson7.data.InMemoryRepoImpl;
import ru.gb.lesson7.data.Note;
import ru.gb.lesson7.data.Repo;
import ru.gb.lesson7.recycler.NotesAdapter;

public class RecyclerFragmentNote extends Fragment implements NotesAdapter.OnNoteClickListener{

    private Repo repository = InMemoryRepoImpl.getInstance();
    private NotesAdapter adapter = new NotesAdapter();
    RecyclerView listAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycle_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        adapter.setNotes(repository.getAll());
        adapter.setOnNoteClickListener(this);
        listAdapter = view.findViewById(R.id.list_notes);
        listAdapter.setLayoutManager(new LinearLayoutManager(requireContext()));
        listAdapter.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(Note note) {
        Toast.makeText(requireContext(), "Клик", Toast.LENGTH_SHORT).show();
    }
}
