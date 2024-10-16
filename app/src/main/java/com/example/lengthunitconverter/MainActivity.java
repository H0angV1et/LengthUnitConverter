package com.example.lengthunitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    Toolbar myToolBar;
    SeekBar decimal;

    EditText editInput;
    TextView textOutput;

    Spinner spinnerUnitFrom, spinnerUnitTo;

    Button swapBtn, clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolBar);

        getInputs();
    }

    public void getInputs() {
        editInput = findViewById(R.id.edit_input);
        spinnerUnitFrom  = findViewById(R.id.spinner_unit_from);
        spinnerUnitTo = findViewById(R.id.spinner_unit_to);

        spinnerUnitFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convert(editInput.getText().toString(), spinnerUnitFrom.getSelectedItem().toString(),
                        spinnerUnitTo.getSelectedItem().toString(), decimal.getProgress());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerUnitTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convert(editInput.getText().toString(), spinnerUnitFrom.getSelectedItem().toString(),
                        spinnerUnitTo.getSelectedItem().toString(), decimal.getProgress());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        editInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                convert(editInput.getText().toString(), spinnerUnitFrom.getSelectedItem().toString(),
                        spinnerUnitTo.getSelectedItem().toString(), decimal.getProgress());
            }
        });

        clear();
        swap();
        changeDecimal();
    }

    public void convert(String inputNumber, String getUnitFrom, String getUnitTo, int progress) {
        textOutput = findViewById(R.id.text_output);

        if (inputNumber.isEmpty()) {
            textOutput.setText("");
            return;
        }

        if (!getUnitFrom.isEmpty() && !getUnitTo.isEmpty()) {
            double convertedNumber = Double.parseDouble(inputNumber);
            switch(getUnitFrom) {
                case "Meters (m)":
                    setKm(convertedNumber / 1000, getUnitTo, progress);
                    break;
                case "Millimeters (mm)":
                    setKm(convertedNumber / 1000000, getUnitTo, progress);
                    break;
                case "Foot (ft)":
                    setKm(convertedNumber / 3280.839895, getUnitTo, progress);
                    break;
                case "Miles (ml)":
                    setKm(convertedNumber / 0.6213711922, getUnitTo, progress);
                    break;
            }
        }
    }

    public void setKm(double convertedNumber, String getUnitTo, int progress) {
        BigDecimal outputNumber;

        switch(getUnitTo) {
            case "Meters (m)":
                outputNumber = new BigDecimal(convertedNumber * 1000).setScale(
                        progress, RoundingMode.HALF_UP);
                break;
            case "Millimeters (mm)":
                outputNumber = new BigDecimal(convertedNumber * 1000000).setScale(
                        progress, RoundingMode.HALF_UP);
                break;
            case "Foot (ft)":
                outputNumber = new BigDecimal(convertedNumber * 3280.839895).setScale(
                        progress, RoundingMode.HALF_UP);
                break;
            case "Miles (ml)":
                outputNumber = new BigDecimal(convertedNumber * 0.6213711922).setScale(
                        progress, RoundingMode.HALF_UP);
                break;
            default:
                outputNumber = new BigDecimal(0);
                break;
        }
        textOutput.setText(String.valueOf(outputNumber));
    }


    public void clear() {
        clearBtn = findViewById(R.id.button_clear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInput.setText("");
            }
        });
    }

    public void swap() {
        swapBtn = findViewById(R.id.button_swap);
        swapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fromPosition = spinnerUnitFrom.getSelectedItemPosition();
                int toPosition = spinnerUnitTo.getSelectedItemPosition();

                spinnerUnitFrom.setSelection(toPosition);
                spinnerUnitTo.setSelection(fromPosition);

                convert(editInput.getText().toString(), spinnerUnitFrom.getSelectedItem().toString()
                        , spinnerUnitTo.getSelectedItem().toString(), decimal.getProgress());
            }
        });
    }

    public void changeDecimal() {
        decimal = findViewById(R.id.seekBar_decimal_change);
        decimal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                convert(editInput.getText().toString(), spinnerUnitFrom.getSelectedItem().toString()
                        , spinnerUnitTo.getSelectedItem().toString(), i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}