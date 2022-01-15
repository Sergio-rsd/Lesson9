package ru.gb.lesson7.fragment;

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

import ru.gb.lesson7.R;
import ru.gb.lesson7.data.InMemoryRepoImpl;
import ru.gb.lesson7.data.Note;
import ru.gb.lesson7.data.Repo;
import ru.gb.lesson7.recycler.NotesAdapter;


public class RecyclerFragmentNote extends Fragment implements NotesAdapter.OnNoteClickListener {

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
        adapter.setOnNoteClickListener(this);
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

    public interface Controller {
        void beginEditNote(Note note);
    }

    private Controller controller;

    @Override
    public void onNoteClick(Note note) {

        controller.beginEditNote(note);

        /*
//        adapter.setNotes(repository.getAll());
////        adapter.setOnNoteClickListener(this);
////        listAdapter = view.findViewById(R.id.list_notes);
//        listAdapter.setLayoutManager(new LinearLayoutManager(requireContext()));
//        listAdapter.setAdapter(adapter);
//        Bundle args = getArguments();
        EditNoteFragment args = EditNoteFragment.getInstance(note);
        Toast.makeText(requireContext(), "После клика " + args, Toast.LENGTH_SHORT).show();
*/
/*
        requireActivity().getSupportFragmentManager().popBackStack();

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.main_list_notes, EditNoteFragment.getInstance(note))
                .addToBackStack(null)
                .commit();
 */
    }

}
