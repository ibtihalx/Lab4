package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {
Button btn_add;
Button btn_view;
EditText et_name, et_age;
ListView lv_StudentList;
ArrayAdapter studentArrayAdapter;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add=(Button)findViewById(R.id.btn_add);
        btn_view=(Button)findViewById(R.id.btn_view);
        et_name=(EditText) findViewById(R.id.et_name);
        et_age=(EditText) findViewById(R.id.et_age);
        lv_StudentList=(ListView) findViewById(R.id.lv_StudentList);
        databaseHelper=new DatabaseHelper(MainActivity.this);
        ShowStudentsOnListView(databaseHelper);

        //listeners

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper=new DatabaseHelper(MainActivity.this);
                ShowStudentsOnListView(databaseHelper);

            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentMod studentMod;
                try{
                    studentMod=new StudentMod(-1,et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()));
Toast.makeText(MainActivity.this,studentMod.toString(),Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(MainActivity.this,"Enter Valid input",Toast.LENGTH_SHORT).show();
                    studentMod=new StudentMod(-1,"ERROR",0);
                }

                DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
                boolean b = databaseHelper.addOne(studentMod);
                Toast.makeText(MainActivity.this,"SUCCESS= "+b,Toast.LENGTH_SHORT).show();
                ShowStudentsOnListView(databaseHelper);


            }
        });




lv_StudentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        StudentMod ClickedStudent=(StudentMod) adapterView.getItemAtPosition(i);
        databaseHelper.DeleteOne(ClickedStudent);
        ShowStudentsOnListView(databaseHelper);
        Toast.makeText(MainActivity.this,"Deleted"+ClickedStudent.toString(),Toast.LENGTH_SHORT).show();
    }
});


    }
    private void ShowStudentsOnListView(DatabaseHelper databaseHelper){
        studentArrayAdapter=new ArrayAdapter<StudentMod>(MainActivity.this, android.R.layout.simple_list_item_1,databaseHelper.getEveryone());
        lv_StudentList.setAdapter(studentArrayAdapter);
    }

}