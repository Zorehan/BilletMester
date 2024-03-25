package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {


    /*
    den her metode skal bruges hver gang en bruger bliver oprettet, hvadend
    brugeren angiver som password skal køres igennem den her metode og så gemt i databasen sådan
     */
    public String encryptPassword(String password)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            String encryptedPassword = Base64.getEncoder().encodeToString(hash);
            return encryptedPassword;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /*
    Den her metode kan så bruges til at checke om det brugeren skriver i loginfeltet
    passer med den encrypted version som vi har
     */
    public boolean checkPassword(String plainPassword, String encryptedPasswordFromDataBase)
    {
        String encryptedPassword = encryptPassword(plainPassword);

        return encryptedPassword.equals(encryptedPasswordFromDataBase);
    }
}
