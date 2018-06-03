package si.ak93.todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String LOG_TAG = "MainActivity";

    String todoList = "";
    List<TodoItem> itemList = new ArrayList<>();


    TextView itemListText;
    RecyclerView itemListView;
    EditText newItemInput;

    RecyclerViewAdapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemListText = findViewById(R.id.itemList);
        newItemInput = findViewById(R.id.inputNewItem);


        //itemListAdapter = new RecyclerViewAdapter(itemList);
        itemListAdapter = new RecyclerViewAdapter(itemList, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i(LOG_TAG,"Item clicked: "+itemList.get(position).name);
                ItemActivity.startActivity(MainActivity.this,itemList.get(position));
            }
        });

        itemListView = findViewById(R.id.itemList1);
        itemListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        itemListView.setAdapter(itemListAdapter);


        findViewById(R.id.buttonAddNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItemName = newItemInput.getText().toString();
                if(newItemName.length()>0) {
                    Log.i(LOG_TAG, "Button clicked: " + newItemName);

                    TodoItem newItem = new TodoItem(newItemName,itemList.size());
                    itemList.add(newItem);
                    itemListAdapter.notifyDataSetChanged();

                    /*
                    todoList += "\n" + newItemName;
                    itemListText.setText(todoList);
                     */

                    newItemInput.setText("");

                    saveListItems();
                }
            }
        });

        findViewById(R.id.buttonClearList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSavedItems();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(LOG_TAG,"onPause ItemList size: "+itemList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();

        itemList.clear();

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        todoList = sp.getString("todo_list","");
        Log.i(LOG_TAG,"Fetching todoList: "+todoList);
        //itemListText.setText(todoList);

        if(todoList.trim().length()>0){
            String[] items = todoList.split(";");
            int i = 0;
            for(String s:items){
                itemList.add(new TodoItem(s,i));
                i++;
            }

            itemListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(LOG_TAG,"onActivityResult("+requestCode+","+resultCode+",data)");
        switch (requestCode){
            case ItemActivity.REQUEST_CODE:
                if(resultCode==ItemActivity.RESULT_CODE_DELETE && data!=null){
                    TodoItem item = (TodoItem) data.getSerializableExtra("item");
                    //delete item
                    itemList.remove(item);
                    itemListAdapter.notifyDataSetChanged();
                    saveListItems();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void saveListItems(){
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        todoList = "";
        for(int i = 0;i<itemList.size();i++) {
            todoList+=itemList.get(i).name;
            if(i<itemList.size()-1)todoList+=";";
        }

        sp.edit().putString("todo_list",todoList).apply();

        Log.i(LOG_TAG,"Saving todoList: "+todoList);
    }

    void clearSavedItems(){
        new AlertDialog.Builder(this)
                .setMessage("Do you really want to delete all items on your TODO list?")
                .setCancelable(true)
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sp = getPreferences(MODE_PRIVATE);
                        todoList = "";
                        sp.edit().putString("todo_list",todoList).apply();
                        itemList.clear();
                        itemListAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"Items deleted!",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

}
