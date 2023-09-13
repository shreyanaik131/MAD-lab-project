package com.example.classroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class classroompage extends AppCompatActivity {
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9;
    private Button nextbtn,backbtn;
    private boolean isAdmin;
    EditText nameEditText, emailEditText, messageEditText,roomno,section;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroompage);
        mAuth = FirebaseAuth.getInstance();
        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.checkbox3);
        checkBox4 = findViewById(R.id.checkbox4);
        checkBox5 = findViewById(R.id.checkbox5);
        checkBox6 = findViewById(R.id.checkbox6);
        checkBox7 = findViewById(R.id.checkbox7);
        checkBox8 = findViewById(R.id.checkbox8);
        checkBox9 = findViewById(R.id.checkbox9);
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        messageEditText = findViewById(R.id.msg);
        roomno = findViewById(R.id.roomno);
        section = findViewById(R.id.section);
        nextbtn = findViewById(R.id.button);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateMailTemplate();
            }
        });
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the signup button click event
                Intent intent = new Intent(classroompage.this,mainpage2.class);
                startActivity(intent);
            }
        });
    }

    private void generateMailTemplate() {

        StringBuilder mailTemplate = new StringBuilder();
        String role;
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Check if user has the "faculty" custom claim
            String userRole = user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()
                    ? "admin"
                    : "student";
            if (userRole.equals("admin")) {
                // User is an admin (faculty)
                isAdmin = true;
                // Perform admin/faculty-specific operations
            } else {
                // User is not an admin (student)
                isAdmin = false;
                // Perform student-specific operations
            }
        }
        if(isAdmin==true){
            role="student";
        }
        else{
            role="faculty";
        }
        mailTemplate.append("Dear Sir/Madam,\n");
        mailTemplate.append("I hope this email finds you well. As a "+role+" at Sahyadri college of engineering and management, I am writing to bring your attention to an issue that requires maintenance assistance.");
        mailTemplate.append("These are the issues we are facing\n");
        List<String> selectedIssues = new ArrayList<>();
        if (checkBox1.isChecked()) {
            selectedIssues.add("The projector does not turn on or display properly.");
            mailTemplate.append("The projector does not turn on or display properly.\n");
        }

        if (checkBox2.isChecked()) {
            selectedIssues.add("The chalk is depleted or unavailable.");
            mailTemplate.append("The chalk is depleted or unavailable.\n");
        }

        if (checkBox3.isChecked()) {
            selectedIssues.add("Difficulty in opening or closing the door.");
            mailTemplate.append("Difficulty in opening or closing the door.\n");
        }

        if (checkBox4.isChecked()) {
            selectedIssues.add("The fan is malfunctioning, causing inadequate air circulation or noise.");
            mailTemplate.append(" The fan is malfunctioning, causing inadequate air circulation or noise.\n");
        }

        if (checkBox5.isChecked()) {
            selectedIssues.add("The tubelight flickers or does not illuminate.");
            mailTemplate.append("The tubelight flickers or does not illuminate\n");
        }

        if (checkBox6.isChecked()) {
            selectedIssues.add("The window is broken or unable to open.");
            mailTemplate.append("The window is broken or unable to open.\n");
        }

        if (checkBox7.isChecked()) {
            selectedIssues.add("Floor is not clean.");
            mailTemplate.append("Floor is not clean\n");
        }

        if (checkBox8.isChecked()) {
            selectedIssues.add("The desks are broken or in poor condition.");
            mailTemplate.append("The desks are broken or in poor condition.\n");
        }

        if (checkBox9.isChecked()) {
            selectedIssues.add("The curtains are torn.");
            mailTemplate.append("The curtains are torn\n");
        }
        String message = messageEditText.getText().toString();
        mailTemplate.append("Other issues: ").append(message).append("\n");
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String sec = section.getText().toString();
        String room = roomno.getText().toString();

        mailTemplate.append("This issue is causing inconvenience and may potentially affect the safety and functionality of the campus. Therefore, I kindly request your prompt attention and resolution of the problem.\n" +
                "\n" +
                "I understand that the maintenance department is responsible for handling various requests, but I urge you to consider the importance and urgency of this matter. Timely action will contribute to the well-being and smooth functioning of our campus.\n" +
                "\n" +
                "Please keep me updated on the progress of the repairs and provide an estimated timeline for resolution. If there are any additional details or information required from me, please let me know, and I will be glad to assist.\n" +
                "\n" +
                "Thank you for your attention to this matter.\n\n"+
        "Sincerely,\n\n");
        mailTemplate.append("\n");
        mailTemplate.append("Name: ").append(name).append("\n");
        mailTemplate.append("Email: ").append(email).append("\n");
        mailTemplate.append("Section: ").append(sec).append("\n");
        mailTemplate.append("Classroom number: ").append(room).append("\n");

        String template = mailTemplate.toString();
        Intent intent = new Intent(classroompage.this, classsendrequest.class);
        intent.putExtra("template", template);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("section", sec);
        intent.putExtra("classroom number", room);
        intent.putStringArrayListExtra("selectedIssues", new ArrayList<>(selectedIssues));


        startActivity(intent);

    }
}
