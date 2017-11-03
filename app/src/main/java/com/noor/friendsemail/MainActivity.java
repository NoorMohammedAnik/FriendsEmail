package com.noor.friendsemail;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSave,btnShow,btnUpdate,btnDelete;
    EditText txtID,txtName,txtEmail;

    MySqlite mySqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID=(EditText)findViewById(R.id.txtID);
        txtName=(EditText)findViewById(R.id.txtName);
        txtEmail=(EditText)findViewById(R.id.txtEmail);

        btnSave=(Button)findViewById(R.id.btnSave);
        btnShow=(Button) findViewById(R.id.btnShow);
        btnUpdate=(Button) findViewById(R.id.btnUpdate);

        btnDelete=(Button) findViewById(R.id.btnDelete);






        mySqlite=new MySqlite(this); //initilize

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               String ID= txtID.getText().toString();
                String Name=txtName.getText().toString();


                if (Name.isEmpty()) {

                    txtName.setError("Name can't be empty !");
                    requestFocus(txtName);

                }

                //Checking password field/validation
                if (ID.isEmpty()) {

                    txtID.setError("ID can't be empty !");
                    requestFocus(txtID);

                }



                boolean chk=mySqlite.addToTable(ID,txtName.getText().toString(),txtEmail.getText().toString());

                if (chk==true)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();

            }
        });
    }





    //added onclick method in xml
    public void viewData(View v)
    {


        Cursor result=mySqlite.display();

        if (result.getCount()==0)
        {
             Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

           // showMsg("Error","No Data Found ! ");
            //return;
        }


        StringBuffer buffer=new StringBuffer();

        result.moveToFirst();

        do {
            buffer.append("ID : "+result.getString(0)+"\n");
            buffer.append("NAME : "+result.getString(1)+"\n");
            buffer.append("EMAIL : "+result.getString(2)+"\n\n");

        }while (result.moveToNext());


        // Display(buffer.toString());
        showMsg("My Friends Info",buffer.toString());

    }


    public void showMsg(String title,String msg)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.show();
    }




    public void update(View v) {

        String id = txtID.getText().toString();
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();

        if (name.isEmpty()) {

            txtName.setError("Name can't be empty !");
            requestFocus(txtName);

        }

        //Checking password field/validation
        else if (id.isEmpty()) {

            txtID.setError("ID can't be empty !");
            requestFocus(txtID);

        } else if (email.isEmpty()) {

            txtEmail.setError("Email can't be empty !");
            requestFocus(txtEmail);

        } else if (!email.contains("@") ) {

            txtEmail.setError("Email is not correct !");
            requestFocus(txtEmail);



        }

        else if (!email.contains(".") ) {

            txtEmail.setError("Email is not correct !");
            requestFocus(txtEmail);
        }

        else {

            boolean chk = mySqlite.UpdateData(id, name, email);
            if (chk == true)
                Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Data Not Updated", Toast.LENGTH_SHORT).show();
        }
    }



    public void delete(View v)
    {

        int delChk= mySqlite.DeleteData(txtID.getText().toString());
        if (delChk>0)
            Toast.makeText(this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Error ! Data Not Deleted", Toast.LENGTH_SHORT).show();
    }



    //for request focus
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
