package si.ak93.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.inputField);
                String text = input.getText().toString();

                Log.i("MainActivity","Button pressed! "+text);

                TextView textView = findViewById(R.id.titleText);
                textView.setText(text);

                input.setText("");
            }
        });
    }
}














