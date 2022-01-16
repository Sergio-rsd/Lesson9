package ru.gb.lesson7.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.gb.lesson7.R;
import ru.gb.lesson7.data.InMemoryRepoImpl;
import ru.gb.lesson7.data.Note;
import ru.gb.lesson7.data.Repo;
import ru.gb.lesson7.recycler.NotesAdapter;

public class EditNoteFragment extends Fragment implements View.OnClickListener {

    private EditText title;
    private EditText description;
    private Button saveNote;
    private int id = -1;
    private Repo repository = InMemoryRepoImpl.getInstance();
    private Note note;
    private NotesAdapter adapter;
    public static final String RESULT = "RESULT";
    public static final String EDIT_NOTE = "EDIT_NOTE";
    public static final String REQUEST_KEY = "REQUEST_KEY";
//    public static final String TAG = "happy";

    public static EditNoteFragment getInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment();

        Bundle args = new Bundle();
        args.putSerializable(RESULT, note);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);

/*
        if (savedInstanceState != null) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        return inflater.inflate(R.layout.activity_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        Bundle args = getArguments();

        title = view.findViewById(R.id.edit_note_title);
        description = view.findViewById(R.id.edit_note_description);
        saveNote = view.findViewById(R.id.edit_note_update);

        if (args != null && args.containsKey(RESULT)) {
            Note note = (Note) args.getSerializable(RESULT);
            id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());

        }
        saveNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        Log.d(TAG, "onClick() called with: v = [" + v + "]");
        Note note = new Note(id, title.getText().toString(), description.getText().toString());


        Bundle resultEditNote = new Bundle();
        resultEditNote.putSerializable(EDIT_NOTE, note);
        getParentFragmentManager().setFragmentResult(REQUEST_KEY, resultEditNote);

        requireActivity()
                .getSupportFragmentManager()
                .popBackStack();

    }

}
