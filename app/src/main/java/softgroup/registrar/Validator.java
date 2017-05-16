package softgroup.registrar;

class Validator {

    /**
     * validate user email
     * @param email user email
     * @return true if email is valid
     */
    static boolean isValidEmail(String email)
    {
        return (email != null && email.length() >= 6 && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    /**
     * validate user password
     * @param password user password
     * @return true if password is valid
     */
    static boolean isValidPassword(String password)
    {
        return (password != null && password.length() >= 4);
    }
}
