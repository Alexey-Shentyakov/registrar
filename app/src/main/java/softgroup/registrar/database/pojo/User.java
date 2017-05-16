package softgroup.registrar.database.pojo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String email;
    private String passwordHash;

    public User(String email, String passwordHash)
    {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    /**
     * hash user password
     * @param password user password
     * @return MD5 hash
     */
    public static String hashPassword(String password)
    {
        String hash = null;

        if (password != null) {

            try {
                // MD5 hash
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(password.getBytes());
                byte messageDigest[] = digest.digest();

                // hex string
                StringBuilder hexString = new StringBuilder();

                for (int i = 0; i < messageDigest.length; i++) {
                    hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                }

                hash = hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return hash;
    }
}
