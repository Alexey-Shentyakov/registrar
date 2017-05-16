package softgroup.registrar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import softgroup.registrar.database.DatabaseHelper;
import softgroup.registrar.database.pojo.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // check user session
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String userEmail = sharedPreferences.getString("user-email", null);

        if (userEmail != null)
        {
            // login from session
            Intent successIntent = new Intent(this, SuccessActivity.class);
            successIntent.putExtra("user-email", userEmail);
            startActivity(successIntent);
        }
        else
        {
            // login manually
            EditText editTextEmail = (EditText) findViewById(R.id.editText_email);
            EditText editTextPassword = (EditText) findViewById(R.id.editText_password);

            editTextEmail.setText("");
            editTextPassword.setText("");
        }
    }

    public void onLoginButtonClick(View view)
    {
        EditText editTextEmail = (EditText) findViewById(R.id.editText_email);
        EditText editTextPassword = (EditText) findViewById(R.id.editText_password);

        // get user input
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // find user by email
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        User userFromDB = databaseHelper.UsersTable_getUserByEmail(email);
        databaseHelper.close();

        if (userFromDB != null && User.hashPassword(password).equals(userFromDB.getPasswordHash())) {

            // user was found and password is ok
            // start success activity
            Intent successIntent = new Intent(this, SuccessActivity.class);
            successIntent.putExtra("user-email", userFromDB.getEmail());
            startActivity(successIntent);
        }
        else {
            // user not found or invalid password
            Toast.makeText(this, getString(R.string.invalid_credentials_message), Toast.LENGTH_SHORT).show();
        }
    }

    public void onSignupButtonClick(View view)
    {
        // start sign-up activity
        startActivity(new Intent(this, SignupActivity.class));
    }
}
