package si.ak93.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Anže Kožar on 31.5.2018.
 * nzkozar@gmail.com
 */
public class ItemActivity extends AppCompatActivity {

    public final static int REQUEST_CODE = 0;
    public final static int RESULT_CODE_CLOSE = 0;
    public final static int RESULT_CODE_DELETE = 1;

    private TodoItem item;

    public static void startActivity(Activity activity,TodoItem item){
        Intent intent = new Intent(activity,ItemActivity.class);
        intent.putExtra("item",item);
        activity.startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        setResult(RESULT_CODE_CLOSE);

        item = (TodoItem)getIntent().getSerializableExtra("item");

        TextView nameText = findViewById(R.id.itemNameText);
        nameText.setText(item.name);

        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultData = new Intent();
                resultData.putExtra("item",item);
                setResult(RESULT_CODE_DELETE,resultData);
                finish();
            }
        });
    }


}
