package com.example.pr29; // Проверь свой пакет!

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView timePick;
    Button btnDate, btnTime, btnDialog;

    // Объект календаря для хранения текущей даты/времени
    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим элементы
        timePick = findViewById(R.id.time_pick);
        btnDate = findViewById(R.id.button_date);
        btnTime = findViewById(R.id.button_time);
        btnDialog = findViewById(R.id.button_dialog);

        // Назначаем обработчики кликов
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnDialog.setOnClickListener(this);

        // Сразу выводим текущее время при запуске
        setInitialDateTime();
    }

    // Метод форматирования и вывода даты в TextView
    private void setInitialDateTime() {
        timePick.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
    }

    @Override
    public void onClick(View view) {
        // Смотрим, какая кнопка нажата
        if (view.getId() == R.id.button_date) {
            // Открываем диалог выбора даты
            new DatePickerDialog(MainActivity.this, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        }
        else if (view.getId() == R.id.button_time) {
            // Открываем диалог выбора времени
            new TimePickerDialog(MainActivity.this, t,
                    dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE), true)
                    .show();
        }
        else if (view.getId() == R.id.button_dialog) {
            // Вызываем наш кастомный диалог
            showCustomDialog();
        }
    }

    // Обработчик выбора даты в диалоге
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime(); // Обновляем текст на экране
        }
    };

    // Обработчик выбора времени в диалоге
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime(); // Обновляем текст на экране
        }
    };

    // Метод для показа кастомного диалога (Задание 3)
    private void showCustomDialog() {
        // Подключаем нашу разметку с полем ввода
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);

        // Создаем само окно
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                // Передаем данные В диалог (в заголовок)
                .setTitle("Текущая дата: " + timePick.getText().toString())
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Получаем данные ОБРАТНО из диалога
                        EditText input = dialogView.findViewById(R.id.editTextDialog);
                        String text = input.getText().toString();

                        // Выводим то, что ввел пользователь, обратно на экран
                        if (!text.isEmpty()) {
                            timePick.setText(text);
                        }
                    }
                })
                .setNegativeButton("Отмена", null);

        builder.create().show();
    }
}