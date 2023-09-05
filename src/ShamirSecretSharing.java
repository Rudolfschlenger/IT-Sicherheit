import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * This class implements Shamir's (t,n) secret sharing.
 * 
 * Secrets are represented as BigInteger objects, shares as ShamirShare objects.
 * 
 * Randomness is taken from a {@link java.security.SecureRandom} object.
 * 
 * @see ShamirShare
 * @see BigInteger
 * @see SecureRandom
 * 
 * @author elmar
 * 
 */
public class ShamirSecretSharing {

	/**
	 * Creates a (t,n) Shamir secret sharing object for n shares with threshold
	 * t.
	 * 
	 * @param t
	 *            threshold: any subset of t <= i <= n shares can recover the
	 *            secret.
	 * @param n
	 *            number of shares to use. Needs to fulfill n >= 2.
	 */
	public ShamirSecretSharing(int t, int n) {
		assert (t >= 2);
		assert (n >= t);

		this.t = t;
		this.n = n;
		this.rng = new SecureRandom();

		// use p = 2^256 + 297
		this.p = BigInteger.ONE.shiftLeft(256).add(BigInteger.valueOf(297));
		assert (this.p.isProbablePrime(2));
	}

	/**
	 * Shares the secret into n parts.
	 * 
	 * @param secret
	 *            The secret to share.
	 * 
	 * @return An array of the n shares.
	 */
	public ShamirShare[] share(BigInteger secret) {

		// TODO: implement this
		BigInteger[] randoms = new BigInteger[t];
		ShamirShare[] result = new ShamirShare[t];
		Random rnd = new Random();

		for (int i = 0; i < randoms.length; i++) {
			randoms[i] = new BigInteger(p.bitLength(), rng);
		}

		for (int i = 0; i < result.length; i++) {
			result[i] = new ShamirShare(BigInteger.valueOf(i), horner(BigInteger.valueOf(i), randoms));
		}

		return result;
	}

	/**
	 * Evaluates the polynomial a[0] + a[1]*x + ... + a[t-1]*x^(t-1) modulo p at
	 * point x using Horner's rule.
	 * 
	 * @param x point at which to evaluate the polynomial
	 * @param a array of coefficients
	 * @return value of the polynomial at point x
	 */
	private BigInteger horner(BigInteger x, BigInteger[] a) {

		// TODO: implement this
		BigInteger result = BigInteger.ZERO;
		for (int i = 0; i < a.length; i++) {
			return a[x.intValue()] + x;
		}

		return null;
	}

	/**
	 * Recombines the given shares into the secret.
	 * 
	 * @param shares
	 *            A set of at least t out of the n shares for this secret.
	 * 
	 * @return The reconstructed secret.
	 */
	public BigInteger combine(ShamirShare[] shares) {

		// TODO: implement this

		return null;
	}

	public int maxSecretLength() {
		return this.p.bitLength() / 8;
	}

	public int getT() {
		return t;
	}

	public int getN() {
		return n;
	}

	private int t;
	private int n;
	private SecureRandom rng;
	private BigInteger p;

}
