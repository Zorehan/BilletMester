package UnitTest;

import org.junit.jupiter.api.Test;
import util.Encryption;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptionTest {

    @Test
    public void testEncryptPassword() {
        Encryption encryption = new Encryption();

        String password1 = "password1";
        String password2 = "password2";
        String encryptedPassword1 = encryption.encryptPassword(password1);
        String encryptedPassword2 = encryption.encryptPassword(password2);

        assertNotNull(encryptedPassword1, "Encrypted password should not be null for password1");
        assertNotNull(encryptedPassword2, "Encrypted password should not be null for password2");
        assertNotEquals(encryptedPassword1, encryptedPassword2, "Encrypted passwords should be different for different passwords");
    }

    @Test
    public void testCheckPassword() {
        Encryption encryption = new Encryption();

        String password = "password";
        String encryptedPassword = encryption.encryptPassword(password);

        assertTrue(encryption.checkPassword(password, encryptedPassword), "Password should match the encrypted password");

        assertFalse(encryption.checkPassword("wrongPassword", encryptedPassword), "Incorrect password should not match the encrypted password");

    }

}
