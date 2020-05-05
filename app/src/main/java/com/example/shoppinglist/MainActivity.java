package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.DB.FeedReaderContract;
import com.example.shoppinglist.dummy.DummyContent;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements ItemFragment.OnListFragmentInteractionListener {

    int i = 1;

    private DummyContent.DummyItem dummyItem;

    FeedReaderContract.FeedReaderDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new FeedReaderContract.FeedReaderDbHelper(this);
        this.getDataFromDatabase();

        Button b = findViewById(R.id.buttonDeleteItem);
        b.setVisibility(View.INVISIBLE);

        if (findViewById(R.id.fragment_container_main_activity) != null) {
            if (savedInstanceState != null) {
                return;
            }
                this.changeFragment(new ItemFragment());

        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        //If you interact with the same item again
        //deselect it and set button to invisible again
        if (dummyItem == item) {
            dummyItem = null;
            Button b = findViewById(R.id.buttonDeleteItem);
            b.setVisibility(View.INVISIBLE);
        } else {
            dummyItem = item;
            Button b = findViewById(R.id.buttonDeleteItem);
            b.setVisibility(View.VISIBLE);
        }
        ItemFragment itemFragment = (ItemFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container_main_activity);
        MyItemRecyclerViewAdapter adapter = itemFragment.getAdapter();
        adapter.setHighlight(item);
    }

    public void deleteItem(View view) {
        DummyContent.deleteItem(dummyItem);

        ItemFragment itemFragment = (ItemFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container_main_activity);
        MyItemRecyclerViewAdapter adapter = itemFragment.getAdapter();
        adapter.notifyDataSetChanged();

        Button b = findViewById(R.id.buttonDeleteItem);
        b.setVisibility(View.INVISIBLE);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = FeedReaderContract.FeedEntry.COLUMN_CONTENT + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { dummyItem.content };
        // Issue SQL statement.
        int deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
        dummyItem = null;
    }



    public void onButtonChangeToAddNewItem(View view) {
        this.changeFragment(new AddNewItem());
        Button b = findViewById(R.id.addItemFragmentButton);
        b.setVisibility(View.INVISIBLE);
    }

    private void saveDataToDatabase(String content, String amount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Save the values
        values.put(FeedReaderContract.FeedEntry.COLUMN_CONTENT, content);
        values.put(FeedReaderContract.FeedEntry.COLUMN_AMOUNT, amount);

        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        db.close();
    }

    private void getDataFromDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_CONTENT,
                FeedReaderContract.FeedEntry.COLUMN_AMOUNT
        };

        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_CONTENT + " DESC";

        Cursor cursor = db.rawQuery("SELECT * FROM items", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                DummyContent.addItem(new DummyContent.DummyItem(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_CONTENT)), cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_AMOUNT))));
                cursor.moveToNext();
            }
        }

        db.close();
    }


    public void addToData() {
        TextView itemNameTV = findViewById(R.id.addNewItemName);
        TextView itemAmountTV = findViewById(R.id.addNewItemAmount);
        if (itemNameTV != null && itemAmountTV != null && !alreadyInDatabase(itemNameTV.getText().toString())) {
            String content = itemNameTV.getText().toString();
            String amount = itemAmountTV.getText().toString();
            if (!content.equals("") && !amount.equals("")) {
                DummyContent.addItem(new DummyContent.DummyItem(content, amount));
                this.saveDataToDatabase(content, amount);
            }

        }
        itemNameTV.setText("");
        itemAmountTV.setText("");
    }

    //Check wether there is already an entry in the db with the same name
    //Which therefore shouldn't be added
    private boolean alreadyInDatabase(String content) {

        boolean alreadyInDB = true;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_CONTENT,
        };

        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_CONTENT + " DESC";

        String selection = FeedReaderContract.FeedEntry.COLUMN_CONTENT + " = ?";
        String[] selectionArgs = { content };

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
                );


        if (!cursor.moveToFirst()) {
            alreadyInDB = false;
        }

        db.close();

        return alreadyInDB;
    }

    public void changeFragment(Fragment fragment) {
        if (fragment  instanceof ItemFragment) {
            Button b = findViewById(R.id.addItemFragmentButton);
            b.setVisibility(View.VISIBLE);
        }
        fragment.setArguments(getIntent().getExtras());
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fragment);
        ft.commit();

    }

}
