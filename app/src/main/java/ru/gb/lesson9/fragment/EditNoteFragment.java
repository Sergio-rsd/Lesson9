package ru.gb.lesson9.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

import ru.gb.lesson9.R;
import ru.gb.lesson9.data.InMemoryRepoImpl;
import ru.gb.lesson9.data.Note;
import ru.gb.lesson9.data.Repo;
import ru.gb.lesson9.recycler.NotesAdapter;

public class EditNoteFragment extends Fragment implements View.OnClickListener {

    private EditText title;
    private EditText description;
    private String interest;
//    private String updateData;
    private TextView currentDateTime;
    private Button saveNote;
    private Button changeData;
    private int id = -1;
    private Repo repository = InMemoryRepoImpl.getInstance();
    private Note note;
    private NotesAdapter adapter;
    private String selectedInterest;
    private String selectSaved;
    private String[] arrayInterest;
    public static final String RESULT = "RESULT";
    public static final String EDIT_NOTE = "EDIT_NOTE";
    public static final String REQUEST_KEY = "REQUEST_KEY";
    public static final String EDIT_NOTE_TAG = "EDIT_NOTE_TAG";
    public static final String TAG = "happy";
    Calendar dataChoice = Calendar.getInstance();

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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");

        return inflater.inflate(R.layout.activity_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //  Spinner
        Spinner spinner = view.findViewById(R.id.interest_place);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(requireContext(), R.array.interest_name,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер spinner
        spinner.setAdapter(adapter);
// Spinner end
        // DataPicker

        Bundle args = getArguments();

        title = view.findViewById(R.id.edit_note_title);
        description = view.findViewById(R.id.edit_note_description);
        saveNote = view.findViewById(R.id.edit_note_update);
//        Log.d(TAG,"Args :" + args);
        arrayInterest = getResources().getStringArray(R.array.interest_name);

        currentDateTime = view.findViewById(R.id.current_date);
//        changeData = view.findViewById(R.id.change_data);

        if (args != null && args.containsKey(RESULT)) {
            Note note = (Note) args.getSerializable(RESULT);
//            Log.d(TAG,"Note :" + note);

            id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());

            if (note.getDataPerformance() == null) {
                note.setDataPerformance("");
            }
            currentDateTime.setText(note.getDataPerformance());
            setInitialDate();

//            Log.d(TAG,"Важность до :" + note.getInterest());
            selectSaved = note.getInterest();
            for (int i = 0; i < arrayInterest.length; i++) {
                if (selectSaved.equals(arrayInterest[i])) {
                    spinner.setSelection(i);
                    break;
                }
            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedInterest = arrayInterest[position];
                    note.setInterest(selectedInterest);
                    interest = note.getInterest();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
//        changeData.setOnClickListener(this::setDate);
        currentDateTime.setOnClickListener(this::setDate);
        saveNote.setOnClickListener(this);
    }

    // установка начальной даты
    private void setInitialDate() {
        currentDateTime.setText(DateUtils.formatDateTime(requireContext(),
                dataChoice.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(requireContext(), d,
                dataChoice.get(Calendar.YEAR),
                dataChoice.get(Calendar.MONTH),
                dataChoice.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dataChoice.set(Calendar.YEAR, year);
            dataChoice.set(Calendar.MONTH, monthOfYear);
            dataChoice.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

    @Override
    public void onClick(View v) {
//        Log.d(TAG, "onClick() called with: v = [" + v + "]");
        Note note = new Note(id, title.getText().toString(), description.getText().toString(), interest, currentDateTime.getText().toString());

        Bundle resultEditNote = new Bundle();
        resultEditNote.putSerializable(EDIT_NOTE, note);
        getParentFragmentManager().setFragmentResult(REQUEST_KEY, resultEditNote);
        requireActivity()
                .getSupportFragmentManager()
                .popBackStack(EDIT_NOTE_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

}
