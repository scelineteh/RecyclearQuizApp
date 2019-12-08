package com.example.recyclearquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recyclearquiz.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {
    MaterialEditText edtPhone , edtName , edtPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (MaterialEditText)findViewById(R.id.edtname);
        edtPhone = (MaterialEditText)findViewById(R.id.edtphone);
        edtPassword = (MaterialEditText)findViewById(R.id.edtpassword);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Check if it is already the user's phone
                        if (edtPhone.getText().toString().isEmpty() ||edtName.getText().toString().isEmpty()||edtPassword.getText().toString().isEmpty()){
                            mDialog.dismiss();
                            Toast.makeText(SignUpActivity.this,"You must fill in all the particular to sign up",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists())
                            {
                                mDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "This phone number already exists!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                mDialog.dismiss();
                                User user = new User(edtName.getText().toString(),edtPassword.getText().toString());
                                table_user.child(edtPhone.getText().toString()).setValue(user);
                                Toast.makeText(SignUpActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
