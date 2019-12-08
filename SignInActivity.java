package com.example.recyclearquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recyclearquiz.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText) findViewById(R.id.edtphone);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if user does not exist in database
                        if (edtPhone.getText().toString().isEmpty() ||edtPassword.getText().toString().isEmpty()){
                            mDialog.dismiss();
                            Toast.makeText(SignInActivity.this,"You must fill in all the particular to sign in",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                mDialog.dismiss();
                                //Get user's info
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Toast.makeText(SignInActivity.this, "Sign In Successfully!", Toast.LENGTH_SHORT).show();
                                    openStartQuizActivity();
                                } else {
                                    Toast.makeText(SignInActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignInActivity.this, "User does not exist!", Toast.LENGTH_SHORT).show();
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

    public void openStartQuizActivity() {
        Intent intent = new Intent(this, StartQuizActivity.class);
        startActivity(intent);
    }
}

