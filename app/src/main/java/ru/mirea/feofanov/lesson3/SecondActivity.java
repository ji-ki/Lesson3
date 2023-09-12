package ru.mirea.feofanov.lesson3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private TextView tvOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tvOut = findViewById(R.id.textView2);
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("key");
        String text = (String) getIntent().getSerializableExtra("key");
        tvOut.setText("квадрат значения моего номера по списку в группе составляет 625, а текущее время "+text);

    }
}
