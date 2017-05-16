package softgroup.registrar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import softgroup.registrar.database.DatabaseHelper;
import softgroup.registrar.database.pojo.User;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onSignupNewUserButtonClick(View view)
    {
        EditText editTextEmail = (EditText) findViewById(R.id.editText_email);
        EditText editTextPassword = (EditText) findViewById(R.id.editText_password);
        EditText editTextPasswordConfirmation = (EditText) findViewById(R.id.editText_password_confirmation);

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String passwordConfirmation = editTextPasswordConfirmation.getText().toString();

        if (Validator.isValidEmail(email) && Validator.isValidPassword(password))
        {
            // check email if it is in use already
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            User userFromDB = databaseHelper.UsersTable_getUserByEmail(email);

            if (userFromDB == null)
            {
                // user with this email was not found in DB

                // check password confirmation
                if (password.equals(passwordConfirmation)) {

                    // password confirmation OK
                    // insert new user into DB
                    User newUser = new User(email, User.hashPassword(password));

                    if (databaseHelper.UsersTable_InsertNewUser(newUser)) {
                        // new user was inserted
                        Toast.makeText(this, getString(R.string.insert_new_user_success), Toast.LENGTH_SHORT).show();

                        // close activity
                        finish();
                    } else {
                        // new user was not inserted
                        Toast.makeText(this, getString(R.string.insert_new_user_fail), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    // password confirmation mismatch
                    Toast.makeText(this, getString(R.string.password_confirmation_mismatch), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                // this email is in use
                Toast.makeText(this, getString(R.string.email_in_use_message), Toast.LENGTH_SHORT).show();
            }

            databaseHelper.close();
        }
        else
        {
            // email or/and password is not valid
            Toast.makeText(this, getString(R.string.email_password_length_message), Toast.LENGTH_SHORT).show();
        }
    }
}
