package util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {


    /*
    den her metode skal bruges hver gang en bruger bliver oprettet, hvadend
    brugeren angiver som password skal køres igennem den her metode og så gemt i databasen sådan
     */
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /*
    Den her metode kan så bruges til at checke om det brugeren skriver i loginfeltet
    passer med den encrypted version som vi har
     */
    public boolean checkPassword(String plainPassword, String encryptedPasswordFromDataBase) {
        return BCrypt.checkpw(plainPassword, encryptedPasswordFromDataBase);
    }
}
