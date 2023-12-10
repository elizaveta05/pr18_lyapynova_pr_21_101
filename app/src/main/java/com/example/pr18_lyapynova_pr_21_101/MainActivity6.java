package com.example.pr18_lyapynova_pr_21_101;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;


public class MainActivity6 extends AppCompatActivity {

    private GestureDetector gestureDetector;
    ExpandableListView elvMain;
    DB2 db;
    Button btn1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity6.this, MainActivity7.class);
            startActivity(intent);
        });
        db = new DB2(this);
        db.open();
        Cursor cursor = db.getCompanyData();
        startManagingCursor(cursor);
        String[] groupFrom = { DB2.COMPANY_COLUMN_NAME };
        int[] groupTo = { android.R.id.text1 };
        String[] childFrom = { DB2.PHONE_COLUMN_NAME };
        int[] childTo = { android.R.id.text1 };
        SimpleCursorTreeAdapter sctAdapter = new MyAdapter(this, cursor,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, android.R.layout.simple_list_item_1, childFrom,
                childTo);
        elvMain = findViewById(R.id.elvMain);
        elvMain.setAdapter(sctAdapter);

        //свайп
        gestureDetector = new GestureDetector(this, new SwipeGestureListener());
    }
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            float diffY = event2.getY() - event1.getY();
            float diffX = event2.getX() - event1.getX();

            if (Math.abs(diffX) > Math.abs(diffY) && Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX < 0) {

                    Intent intent = new Intent(MainActivity6.this, MainActivity7.class);
                    startActivity(intent);
                    return true;
                }else {
                    Intent intent = new Intent(MainActivity6.this, MainActivity5.class);
                    startActivity(intent);
                    return true;
                }
            }
            return false;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    class MyAdapter extends SimpleCursorTreeAdapter {

        public MyAdapter(Context context, Cursor cursor, int groupLayout,
                         String[] groupFrom, int[] groupTo, int childLayout,
                         String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childFrom, childTo);
        }

        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // получаем курсор по элементам для конкретной группы
            int idColumn = groupCursor.getColumnIndex(DB2.COMPANY_COLUMN_ID);
            return db.getPhoneData(groupCursor.getInt(idColumn));
        }
    }
}