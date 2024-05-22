package com.example.trratoria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Rezerv extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneEditText;
    private Spinner timeSpinner, guestsSpinner, tableSpinner;
    private Button submitButton;
    private DatePicker datePicker;

    private static String ip = "146.19.207.159";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "cafe";
    private static String username = "sa";
    private static String password = "DbReFoRpm228@";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezerv);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        phoneEditText = findViewById(R.id.phoneNumberEditText);
        timeSpinner = findViewById(R.id.timeSpinner);
        guestsSpinner = findViewById(R.id.guestsSpinner);
        tableSpinner = findViewById(R.id.tableSpinner);
        datePicker = findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();

        calendar.set(2024, Calendar.MAY, 18);
        long minDate = calendar.getTimeInMillis();

        calendar.set(2025, Calendar.MAY, 18);
        long maxDate = calendar.getTimeInMillis();

        datePicker.setMinDate(minDate);
        datePicker.setMaxDate(maxDate);

        int[] tableNumbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[] tableGuestCounts = {10, 4, 4, 6, 6, 2, 2, 2, 8, 8, 8};

        Spinner spinnerGuestsCount = findViewById(R.id.guestsSpinner);
        Spinner spinnerTableNumber = findViewById(R.id.tableSpinner);

        int selectedGuestCount = Integer.parseInt(spinnerGuestsCount.getSelectedItem().toString());
        spinnerGuestsCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int selectedGuestCount = Integer.parseInt(spinnerGuestsCount.getSelectedItem().toString());

                List<String> availableTables = new ArrayList<>();
                for (int i = 0; i < tableNumbers.length; i++) {
                    if (tableGuestCounts[i] >= selectedGuestCount) {
                        availableTables.add(String.valueOf(tableNumbers[i]));
                    }
                }

                ArrayAdapter<String> tableAdapter = new ArrayAdapter<>(Rezerv.this, android.R.layout.simple_spinner_item, availableTables);
                spinnerTableNumber.setAdapter(tableAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        List<String> availableTables = new ArrayList<>();
        for (int i = 0; i < tableNumbers.length; i++) {
            if (tableGuestCounts[i] >= selectedGuestCount) {
                availableTables.add(String.valueOf(tableNumbers[i]));
            }
        }

        ArrayAdapter<String> tableAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableTables);
        spinnerTableNumber.setAdapter(tableAdapter);
    }

    public void submitButton(View view) {
        Connection connection = null;
        ResultSet resultSet = null;
        int newBronID = 0;

        try {
            connection = DataBaseHelper.getConnection();
            String selectQuery = "SELECT TOP 1 BronID FROM Bron ORDER BY BronID DESC";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int lastBronID = resultSet.getInt("BronID");
                newBronID = lastBronID + 1;
            } else {
                newBronID = 1;
            }

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            Date bookingDate = new Date(year - 1900, month, day);

            if (firstNameEditText.getText().toString().isEmpty() || lastNameEditText.getText().toString().isEmpty() || phoneEditText.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            if (firstNameEditText.getText().toString().length() > 45 || lastNameEditText.getText().toString().length() > 45) {
                Toast toast = Toast.makeText(this, "Имя и фамилия не должны превышать 45 символов", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            if (phoneEditText.getText().toString().length() != 11) {
                Toast toast = Toast.makeText(this, "Номер телефона должен содержать ровно 11 цифр", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            String checkQuery = "SELECT COUNT(*) FROM Bron WHERE BookingDate = ? AND BookingTime = ? AND StolID = ? ";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(2, timeSpinner.getSelectedItem().toString());
            checkStatement.setInt(3, Integer.parseInt(tableSpinner.getSelectedItem().toString()));
            checkStatement.setDate(1, bookingDate);

            resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                Toast toast = Toast.makeText(this, "Запись с такими данными уже существует", Toast.LENGTH_LONG);
                toast.show();
                return;
            }



            String insertQuery = "INSERT INTO Bron (BronID, FirstName, LastName, PhoneNumber, BookingDate, BookingTime, GuestsCount, StolID, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'expectation')";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, newBronID);
            insertStatement.setString(2, firstNameEditText.getText().toString());
            insertStatement.setString(3, lastNameEditText.getText().toString());
            insertStatement.setString(4, phoneEditText.getText().toString());
            insertStatement.setDate(5, bookingDate);
            insertStatement.setString(6, timeSpinner.getSelectedItem().toString());
            insertStatement.setInt(7, Integer.parseInt(guestsSpinner.getSelectedItem().toString()));
            insertStatement.setInt(8, Integer.parseInt(tableSpinner.getSelectedItem().toString()));

            int count = insertStatement.executeUpdate();

            if (count > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Бронирование успешно")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Очистка всех полей
                                firstNameEditText.setText("");
                                lastNameEditText.setText("");
                                phoneEditText.setText("");
                                timeSpinner.setSelection(0);
                                guestsSpinner.setSelection(0);
                                tableSpinner.setSelection(0);
                                datePicker.updateDate(2024, Calendar.MAY, 18);

                                ScrollView scrollView = findViewById(R.id.ScrollView1);
                                scrollView.smoothScrollTo(0, 0);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast toast = Toast.makeText(this, "Ошибка бронирования", Toast.LENGTH_LONG);
                toast.show();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}