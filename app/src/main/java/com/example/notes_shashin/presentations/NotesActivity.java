package com.example.notes_shashin.presentations;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notes_shashin.R;
import com.example.notes_shashin.datas.RepoNotes;
import com.example.notes_shashin.domains.models.Note;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NotesActivity extends AppCompatActivity {
    GridLayout ItemsParent;
    View bthAddNotes;
    EditText etSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        bthAddNotes = findViewById(R.id.bth_add_notes);
        ItemsParent = findViewById(R.id.gl_notes);
        etSearch = findViewById(R.id.et_search);

        bthAddNotes.setOnClickListener(v -> {
            Intent intentActivityNote = new Intent(this, NoteActivity.class);
            startActivity(intentActivityNote);
        });

        etSearch.setOnKeyListener(SearchListener);
        LoadNotes(RepoNotes.Notes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadNotes(RepoNotes.Notes);
    }

    public void LoadNotes(ArrayList<Note> notes) {
        ItemsParent.removeAllViews();

        for(int i = 0; i < notes.size(); i++) {
            View item_notes = LayoutInflater.from(this).inflate(R.layout.item_note, ItemsParent, false);

            TextView tvTitle = item_notes.findViewById(R.id.tv_title);
            TextView tvText = item_notes.findViewById(R.id.tv_text);
            TextView tvDate = item_notes.findViewById(R.id.tv_date);

            tvTitle.setText(notes.get(i).title);
            tvDate.setText(notes.get(i).date);
            tvText.setText(notes.get(i).text);

            int Position = i;

            item_notes.setOnClickListener(v -> {
                Intent intentActivityNote = new Intent(this, NoteActivity.class);
                intentActivityNote.putExtra("position", Position);
                startActivity(intentActivityNote);
            });
            ItemsParent.addView(item_notes);
        }
    }

    View.OnKeyListener SearchListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            String Search = etSearch.getText().toString();

            ArrayList<Note> FindNotes = RepoNotes.Notes.stream().filter(
                    item -> item.text.contains(Search)
            ).collect(Collectors.toCollection(ArrayList::new));

            LoadNotes(FindNotes);

            return false;
        }
    };
}