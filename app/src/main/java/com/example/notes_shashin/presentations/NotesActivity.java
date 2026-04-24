package com.example.notes_shashin.presentations;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes_shashin.R;
import com.example.notes_shashin.datas.DbContext;
import com.example.notes_shashin.datas.NotesContext;
import com.example.notes_shashin.domains.models.Note;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NotesActivity extends AppCompatActivity {

    GridLayout ItemsParent;
    View bthAddNotes;
    EditText etSearch;
    DbContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        dbContext = new DbContext(this);

        bthAddNotes = findViewById(R.id.bth_add_notes);
        ItemsParent = findViewById(R.id.gl_notes);
        etSearch = findViewById(R.id.et_search);

        bthAddNotes.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteActivity.class);
            startActivity(intent);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString().toLowerCase();

                ArrayList<Note> findNotes = NotesContext.AllNotes()
                        .stream()
                        .filter(item ->
                                item.title.toLowerCase().contains(search)
                                        || item.text.toLowerCase().contains(search)
                        )
                        .collect(Collectors.toCollection(ArrayList::new));

                LoadNotes(findNotes);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        LoadNotes(NotesContext.AllNotes());
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadNotes(NotesContext.AllNotes());
    }

    public void LoadNotes(ArrayList<Note> notes) {
        ItemsParent.removeAllViews();

        for (int i = 0; i < notes.size(); i++) {
            View item_notes = LayoutInflater.from(this).inflate(R.layout.item_note, ItemsParent, false);

            TextView tvTitle = item_notes.findViewById(R.id.tv_title);
            TextView tvText = item_notes.findViewById(R.id.tv_text);
            TextView tvDate = item_notes.findViewById(R.id.tv_date);

            Note currentNote = notes.get(i);

            tvTitle.setText(currentNote.title);
            tvText.setText(currentNote.text);
            tvDate.setText(currentNote.date);

            item_notes.setOnClickListener(v -> {
                Intent intent = new Intent(this, NoteActivity.class);
                intent.putExtra("id", currentNote.id);
                startActivity(intent);
            });

            ItemsParent.addView(item_notes);
        }
    }
}