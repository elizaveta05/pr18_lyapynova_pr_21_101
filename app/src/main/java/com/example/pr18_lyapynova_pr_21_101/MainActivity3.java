package com.example.pr18_lyapynova_pr_21_101;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    // имена атрибутов для Map
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_PB = "pb";
    final String ATTRIBUTE_NAME_LL = "ll";

    ListView lvSimple;

    Button btn;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity3.this, MainActivity4.class);
                startActivity(intent);
            }
        });

        // массив данных
        int load[] = { 41, 48, 22, 35, 30, 67, 51, 88 };

        // упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                load.length);
        Map<String, Object> m;
        for (int i = 0; i < load.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, "Day " + (i+1) + ". Load: " + load[i] + "%");
            m.put(ATTRIBUTE_NAME_PB, load[i]);
            m.put(ATTRIBUTE_NAME_LL, load[i]);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_PB,
                ATTRIBUTE_NAME_LL };
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.tvLoad, R.id.pbLoad, R.id.llLoad };

        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item3,
                from, to);
        // Указываем адаптеру свой биндер
        sAdapter.setViewBinder(new MyViewBinder());

        // определяем список и присваиваем ему адаптер
        lvSimple = (ListView) findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);
    }

    class MyViewBinder implements SimpleAdapter.ViewBinder {

        int red = getResources().getColor(R.color.Red);
        int orange = getResources().getColor(R.color.Orange);
        int green = getResources().getColor(R.color.Green);

        @Override
        public boolean setViewValue(View view, Object data,
                                    String textRepresentation) {
            int i = 0;
            switch (view.getId()) {
                // LinearLayout
                case R.id.llLoad:
                    i = ((Integer) data).intValue();
                    if (i < 40) view.setBackgroundColor(green); else
                    if (i < 70) view.setBackgroundColor(orange); else
                        view.setBackgroundColor(red);
                    return true;
                // ProgressBar
                case R.id.pbLoad:
                    i = ((Integer) data).intValue();
                    ((ProgressBar)view).setProgress(i);
                    return true;
            }
            return false;
        }
    }
}