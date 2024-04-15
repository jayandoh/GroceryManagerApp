package com.example.fridgeassistant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.fridgeassistant.Food.FoodDbHelper;
import com.example.fridgeassistant.Food.FoodItem;

import java.util.Calendar;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;

public class EditorDialogFragment extends DialogFragment {
    Button dateButton, addButton;
    EditText nameEditTxt;
    DatePickerDialog.OnDateSetListener setListener;
    Calendar calendar = Calendar.getInstance();
    FoodDbHelper dbHelper;
    FoodItem editingItem;
    ThemedToggleButtonGroup tagsToggleGroup;
//    ThemedButton carbToggle, proteinToggle, fatToggle;
    int tagId;

    @Override
    public void onStart()
    {
        super.onStart();

        if (getDialog() == null)
            return;

        int dialogHeight = 1400;
        int dialogWidth = 900;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_editor_dialog, null);

        // SQL Database
        dbHelper = new FoodDbHelper(requireContext());

        // Layout variables
        nameEditTxt = dialogView.findViewById(R.id.etxt_foodName);
        dateButton = dialogView.findViewById(R.id.btn_date);
        addButton = dialogView.findViewById(R.id.btn_save);
        tagsToggleGroup = dialogView.findViewById(R.id.togGroup_tags);
//        carbToggle = dialogView.findViewById(R.id.tog_carb);
//        proteinToggle = dialogView.findViewById(R.id.tog_protein);
//        fatToggle = dialogView.findViewById(R.id.tog_fat);

        // If dialog is initiated with the EDIT button
        if (getArguments() != null && getArguments().containsKey("foodItem")) {
            addButton.setText("Save Changes");

            editingItem = getArguments().getParcelable("foodItem");
            nameEditTxt.setText(editingItem.getName());
            tagsToggleGroup.selectButton(editingItem.getTagId());
            calendar = editingItem.getExp_date();
        }

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setDate(year, month, day);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(), android.R.style.Theme_Holo_Dialog, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                setDate(year, month, dayOfMonth);
            }
        };

        tagsToggleGroup.setOnSelectListener((ThemedButton btn) -> {
            tagId = btn.getId();
            return kotlin.Unit.INSTANCE;
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editingItem == null) {
                    FoodItem foodItem = new FoodItem(
                            nameEditTxt.getText().toString(),
                            tagId,
                            calendar);

                    dbHelper.addFoodItemToDatabase(requireContext(), foodItem);

                } else {
                    editingItem.setName(nameEditTxt.getText().toString());
                    editingItem.setTagId(tagId);
                    editingItem.setExp_date(calendar);
                    dbHelper.updateFoodItemInDatabase(requireContext(), editingItem);
                }

                Intent intent = new Intent(requireContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dismiss();
            }
        });

        builder.setView(dialogView);
        return builder.create();
    }

    public void setDate(int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = month + "/" + dayOfMonth + "/" + year;
        dateButton.setText(date);
    }
}