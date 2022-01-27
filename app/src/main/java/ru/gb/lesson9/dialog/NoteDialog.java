package ru.gb.lesson9.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.gb.lesson9.R;
import ru.gb.lesson9.data.Note;

public class NoteDialog extends DialogFragment {

    public static final String NOTE = "NOTE";
    private Note note;
    private String[] arrayInterest;
    private String selectSaved;
//    private String interest;
    public static final String TAG = "happy";
    private String interest = "";

    public interface NoteDialogController {
        void update(Note note);
        void create(String title, String description, String interest);

    }

    private NoteDialogController controller;

    @Override
    public void onAttach(@NonNull Context context) {
        controller = (NoteDialogController) context;
        super.onAttach(context);
    }

    public static NoteDialog getInstance(Note note) {
        NoteDialog noteDialog = new NoteDialog();
        Bundle args = new Bundle();
        args.putSerializable(NOTE, note);
        noteDialog.setArguments(args);
        return noteDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        note = (Note) args.getSerializable(NOTE);

/*
        String title = "";
        String description = "";

        if (note != null) {
            title = note.getTitle();
            description = note.getDescription();

        }
*/

        View dialog = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_note, null);
        String title = "";
        String description = "";

        //  Spinner
        Spinner spinner = dialog.findViewById(R.id.interest_place);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(requireContext(), R.array.interest_name,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер spinner
        spinner.setAdapter(adapter);
        arrayInterest = getResources().getStringArray(R.array.interest_name);
// Spinner end
        if (note != null) {
            title = note.getTitle();
            description = note.getDescription();
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
//                    selectedInterest = ;
                    note.setInterest(arrayInterest[position]);
                    interest = note.getInterest();
//                    Log.d(TAG, " position = [" + position + "], id = [" + id + "]");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        EditText dialogTitle = dialog.findViewById(R.id.edit_note_title);
        EditText dialogDescription = dialog.findViewById(R.id.edit_note_description);

        dialogTitle.setText(title);
        dialogDescription.setText(description);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        String buttonText = "";
        if (note == null) {
            buttonText = "Create";
            builder.setTitle("Create note");
        } else {
            buttonText = "Modify";
            builder.setTitle("Modify note");
        }

        builder.setView(dialog)
                .setCancelable(true)
                .setNegativeButton("Cancel", (dialogInterface, which) -> dialogInterface.cancel())
                .setPositiveButton(buttonText, (dialogInterface, which) -> {
                    if (note == null) {
                        interest = spinner.getSelectedItem().toString();
                        controller.create(
                                dialogTitle.getText().toString(),
                                dialogDescription.getText().toString(),
                                interest
                        );
                    } else {
                        note.setTitle(dialogTitle.getText().toString());
                        note.setDescription(dialogDescription.getText().toString());
                        note.setInterest(interest);
                        controller.update(note);
                    }
                    dialogInterface.dismiss();
                })
        ;


        return builder.create();
    }
}
