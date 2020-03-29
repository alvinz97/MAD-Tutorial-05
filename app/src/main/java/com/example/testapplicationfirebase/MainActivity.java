package com.example.testapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    EditText txtID, txtName, txtAddress, txtContact;
    Button btnSave, btnShow, btnUpdate, btnDelete;
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);
    DatabaseReference databaseReference, readDatabaseReference, updateDatabaseReference, deleteDatabaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Student student;

    public void clearControl() {
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = (EditText) findViewById(R.id.sid);
        txtName = (EditText) findViewById(R.id.sName);
        txtAddress = (EditText) findViewById(R.id.sAddress);
        txtContact = (EditText) findViewById(R.id.sPhone);

        btnSave = (Button) findViewById(R.id.saveBtn);
        btnShow = (Button) findViewById(R.id.showBtn);
        btnUpdate = (Button) findViewById(R.id.updateBtn);
        btnDelete = (Button) findViewById(R.id.deleteBtn);

        student = new Student();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Student");

                try {
                    if (TextUtils.isEmpty(txtID.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter the ID ", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(txtName.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter Student Name ", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(txtAddress.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter Student Address ", Toast.LENGTH_SHORT).show();
                    } else {
                        student.setsId(txtID.getText().toString().trim());
                        student.setName(txtName.getText().toString().trim());
                        student.setAddress(txtAddress.getText().toString().trim());
                        student.setContact(Integer.parseInt(txtContact.getText().toString().trim()));

//                        databaseReference.push().setValue(student);

//                        AUTO GENERATE ID
                        int studentID = ID_GENERATOR.getAndIncrement();

                        databaseReference.child(String.valueOf(studentID)).setValue(student);


                        Toast.makeText(getApplicationContext(), "Data Successfully Saved", Toast.LENGTH_SHORT).show();
                        clearControl();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                firebaseUser = firebaseAuth.getCurrentUser();
//                assert firebaseUser != null;
//                String userID = firebaseUser.getUid();

                readDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child("St1");

                readDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            txtID.setText(dataSnapshot.child("sId").getValue().toString());
                            txtName.setText(dataSnapshot.child("name").getValue().toString());
                            txtAddress.setText(dataSnapshot.child("address").getValue().toString());
                            txtContact.setText(dataSnapshot.child("contact").getValue().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Student");
                updateDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("St1")) {
                            try {
                                student.setsId(txtID.getText().toString().trim());
                                student.setName(txtName.getText().toString().trim());
                                student.setAddress(txtAddress.getText().toString().trim());
                                student.setContact(Integer.parseInt(txtContact.getText().toString().trim()));

                                updateDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child("St1");
                                updateDatabaseReference.setValue(student);
                                clearControl();

                                Toast.makeText(getApplicationContext(), "Data Successfully Updated ", Toast.LENGTH_SHORT).show();
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No Source to Update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Student");
                deleteDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("St1")) {
                            deleteDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child("St1");
                            deleteDatabaseReference.removeValue();
                            clearControl();

                            Toast.makeText(getApplicationContext(), "Data Successfully Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No Source to Delete ", Toast.LENGTH_SHORT).show();
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
