/**
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Random;

/**
 * This class implements the simple XOR-based (n,n) secret sharing.
 * <p>
 * Secrets and shares are both represented as byte[] arrays.
 * <p>
 * Randomness is taken from a {@link java.security.SecureRandom} object.
 *
 * @author elmar
 * @see SecureRandom
 */
public class XorSecretSharing {

    /**
     * Creates a XOR secret sharing object for n shares
     *
     * @param n number of shares to use. Needs to fulfill n >= 2.
     */
    public XorSecretSharing(int n) {
        assert (n >= 2);
        this.n = n;
        this.rng = new SecureRandom();
    }

    /**
     * Shares the secret into n parts.
     *
     * @param secret The secret to share.
     * @return An array of the n shares.
     */
    public byte[][] share(final byte[] secret) {

        // TODO: implement this
        byte[][] shares = new byte[n][secret.length];

        //Generate random numbers
        for (byte[] bytes : shares) {
            rng.nextBytes(bytes);
        }

        for (int i = 0; i < secret.length; i++) {
            shares[n - 1][i] = secret[i];
        }

        for (int i = 0; i < secret.length; i++) {
            for (int j = 0; j < n - 1; j++) {
                shares[n - 1][i] ^= shares[j][i];
            }
        }

        return shares;
    }


    /**
     * Recombines the given shares into the secret.
     *
     * @param shares The complete set of n shares for this secret.
     * @return The reconstructed secret.
     */
    public byte[] combine(final byte[][] shares) {

        // TODO: implement this
        byte[] secret = new byte[shares[0].length];
        for (int i = 0; i < shares[0].length; i++) {
            for (int j = 0; j < n; j++) {
                secret[i] ^= shares[j][i];
            }
        }

        return secret;
    }

    public File[] shareFile(File file) {

        File[] shareFiles = new File[n];

        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            byte[][] shareMatrix = share(fileContent);

            for (int i = 0; i < n; i++) {
                File newFile = new File("test" + i + ".txt");
                FileOutputStream fout = new FileOutputStream(newFile);
                fout.write(shareMatrix[i]);

                shareFiles[i] = newFile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shareFiles;
    }

    public File combineFile(File[] shareFiles) {
        File newFile = new File("newFile.txt");
        byte[][] shareMatrix = new byte[n][];

        try {
            for (int i = 0; i < shareMatrix.length; i++) {
                shareMatrix[i] = Files.readAllBytes(shareFiles[i].toPath());
            }


            byte[] result = combine(shareMatrix);
            FileOutputStream fout = new FileOutputStream(newFile);
            fout.write(result);


        }
        catch(Exception e){
            e.printStackTrace();
        }
        return newFile;
    }


    private int n;

    public int getN() {
        return n;
    }

    private Random rng;

    public static void main(String[] args) {
        XorSecretSharing secret = new XorSecretSharing(2);
        //a)
        byte[] test = {100, 111, 112, 113};

        byte[][] matrix = secret.share(test);
        byte[] ergebnis = secret.combine(matrix);

        File aFile = new File("C:\\Users\\User\\IdeaProjects\\Tag 6\\test123.txt");
        //b)
        secret.combineFile(secret.shareFile(aFile));

        for (byte b : ergebnis) {
            System.out.print(b + ", ");
        }
    }
}
