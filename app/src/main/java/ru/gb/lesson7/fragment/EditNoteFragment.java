package ru.gb.lesson7.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.gb.lesson7.R;
import ru.gb.lesson7.data.InMemoryRepoImpl;
import ru.gb.lesson7.data.Note;
import ru.gb.lesson7.data.Repo;

public class EditNoteFragment extends Fragment implements View.OnClickListener {

    private EditText title;
    private EditText description;
    private Button saveNote;
    private int id = -1;
    private Repo repository = InMemoryRepoImpl.getInstance();
    private Note note;
    public static final String RESULT = "RESULT";
    public static final String TAG = "happy";

    public static EditNoteFragment getInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment();

        Bundle args = new Bundle();
        args.putSerializable(RESULT, note);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        return inflater.inflate(R.layout.activity_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        Bundle args = getArguments();
//        Log.d(TAG, "getArguments() ONE = [" + view + "], args = [" + args + "]");
/*
        if (savedInstanceState != null && savedInstanceState.containsKey(RESULT)) {
            Note note = (Note) savedInstanceState.getSerializable(RESULT);

        }
        */
        title = view.findViewById(R.id.edit_note_title);
        description = view.findViewById(R.id.edit_note_description);
        saveNote = view.findViewById(R.id.edit_note_update);
//        Toast.makeText(requireContext(), "EDIT begin", Toast.LENGTH_SHORT).show();

        if (args != null && args.containsKey(RESULT)) {
//            Log.d(TAG, "getArguments() TWO = [" + view + "], args = [" + args + "]");
            Note note = (Note) args.getSerializable(RESULT);
            id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());

        }
        saveNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick() called with: v = [" + v + "]");
        Note note = new Note(id, title.getText().toString(), description.getText().toString());
        if (id == -1) {
            repository.create(note);
//            Log.d(TAG, "Create note " + note.getId());

        } else {
            repository.update(note);
//            Log.d(TAG, "Update note " + note.getId());
        }

        requireActivity()
                .getSupportFragmentManager()
                .popBackStack();

//                .popBackStackImmediate();

//                .popBackStack();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable(RESULT, note);
        outState = getArguments();
        Log.d(TAG, "onSaveInstanceState() called with: outState = [" + outState + "]");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() called");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getParentFragmentManager().popBackStack();
//        requireActivity().getSupportFragmentManager().popBackStack();
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }
 /*    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(RESULT)) {
            Note note = (Note) savedInstanceState.getSerializable(RESULT);

    *//*        id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            *//*
        }
//        setRetainInstance(true);
    }*/
}
