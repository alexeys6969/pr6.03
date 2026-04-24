package com.example.notes_shashin.presentations;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes_shashin.R;
import com.example.notes_shashin.datas.DbContext;
import com.example.notes_shashin.datas.NotesContext;
import com.example.notes_shashin.domains.models.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    Note note;
    EditText etTitle, etText;
    TextView tvDate;
    View bthSelectColor, bthBack, bthTrash;
    DbContext dbContext;

    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        dbContext = new DbContext(this);

        bthSelectColor = findViewById(R.id.bth_select_color);
        bthBack = findViewById(R.id.bth_back);
        bthTrash = findViewById(R.id.bth_trash);
        etTitle = findViewById(R.id.et_title);
        etText = findViewById(R.id.et_text);
        tvDate = findViewById(R.id.tv_date);

        int noteId = getIntent().getIntExtra("id", -1);

        if (noteId != -1) {
            ArrayList<Note> notes = NotesContext.AllNotes();

            for (Note item : notes) {
                if (item.id == noteId) {
                    note = item;
                    break;
                }
            }

            if (note != null) {
                etTitle.setText(note.title);
                etText.setText(note.text);
                tvDate.setText("Отредактировано: " + note.date);
            }
        } else {
            bthTrash.setVisibility(View.GONE);
            tvDate.setText("Новая заметка");
        }

        bthSelectColor.setOnClickListener(v -> {
            Toast.makeText(this, "Выбор цвета недоступен", Toast.LENGTH_SHORT).show();
        });

        bthBack.setOnClickListener(v -> saveAndClose());

        bthTrash.setOnClickListener(v -> {
            if (note != null) {
                NotesContext.Delete(note);
                Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }

    private void saveAndClose() {
        String title = etTitle.getText().toString();
        String text = etText.getText().toString();

        if (text.replace(" ", "")
                .replace("\r", "")
                .replace("\n", "")
                .isEmpty()) {
            Toast.makeText(this, "Нечего сохранять", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdate = true;

        if (note == null) {
            note = new Note();
            note.color = "";
            isUpdate = false;
        }

        note.title = title;
        note.text = text;
        note.date = format.format(new Date());

        NotesContext.Save(note, isUpdate);

        finish();
    }
}