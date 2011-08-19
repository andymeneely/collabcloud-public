package org.chaoticbits.collabcloud.testinputs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

public class IsPrime {

	private LinkedList<Long> primes = new LinkedList<Long>();
	private Set<Long> primeSet = new HashSet<Long>();

	public IsPrime() {
		long[] initial = { 2, 3, 5, 7, 11, 13, 17, 19, 23 };
		for (long i : initial) {
			primes.add(i);
			primeSet.add(i);
		}
	}

	public boolean isPrime(int i) {
		return isPrime((long) i);
	}

	public boolean isPrime(long num) {
		if (num < 0) // just do negatives like the positives
			return isPrime(-1 * num);
		if (primeSet.contains(num)) // we know it already
			return true;
		if (primeSetDivides(num)) // if the primes we know already divide it
			return false;
		// if not, build up our ordered primes list to sqrt of this number
		for (long candidate = lastPrime() + 1; candidate <= (long) Math.sqrt(num) + 1; candidate++) {
			boolean isCandidatePrime = true;
			for (int i = 0; i < primes.size(); i++) {
				if (candidate % primes.get(i) == 0) { // if divisible by a lower prime, ain't prime
					isCandidatePrime = false;
					break;
				}

				// break after square root of candidate
				if (primes.get(i) > ((long) Math.sqrt(candidate)) + 1)
					break;
			}
			if (isCandidatePrime) {
				primes.addLast(candidate); // add to both list and hashset
				primeSet.add(candidate);
				// Also, check against num while we're here
				if (num % candidate == 0)
					return false;
			}
		}
		return true;// If we've gotten here, num is prime
	}

	private boolean primeSetDivides(long num) {
		for (Long prime : primes) {
			if (prime > ((long) Math.sqrt(num)) + 1)
				break;
			if (num > prime && num % prime == 0)
				return true;

		}
		return false;
	}

	private Long lastPrime() {
		return primes.get(primes.size() - 1);
	}

	public Set<Long> distinctFactors(long num) {
		HashSet<Long> factors = new HashSet<Long>();
		if (isPrime(num)) {
			factors.add(num);
			return factors;
		}
		for (long i = 2; i < Math.sqrt(num) + 1; i++) {
			if (num % i == 0 && isPrime(i)) {
				factors.add(i);
				factors.addAll(distinctFactors(num / i));
			}
		}
		return factors;
	}

	@Test
	public void testPrimes() throws Exception {
		assertTrue(new IsPrime().isPrime(5));
		assertFalse(new IsPrime().isPrime(4));
		assertTrue(new IsPrime().isPrime(71917));
		assertFalse(new IsPrime().isPrime(38809));
	}

	@Test
	public void testPrimesSingleCache() throws Exception {
		IsPrime isPrime = new IsPrime();
		assertTrue(isPrime.isPrime(197));
		assertTrue(isPrime.isPrime(971));
		assertTrue(isPrime.isPrime(719));
		assertTrue(isPrime.isPrime(71917));
		assertFalse(isPrime.isPrime(38809));
	}

	@Test
	public void find1001stprime() throws Exception {
		IsPrime isPrime = new IsPrime();
		int numPrimes = 0;
		int candidate = 1;
		while (numPrimes < 1001) {
			candidate++;
			if (isPrime.isPrime(candidate))
				numPrimes++;
		}
		assertEquals(7927, candidate);
	}

	@Test
	public void testNegatives() throws Exception {
		IsPrime isPrime = new IsPrime();
		assertTrue(isPrime.isPrime(-5));
		assertFalse(isPrime.isPrime(-6));
	}

	@Test
	public void testPrimeFactors() throws Exception {
		IsPrime chk = new IsPrime();
		assertEquals(2, chk.distinctFactors(14L).size());
		assertEquals(2, chk.distinctFactors(15L).size());
		assertEquals(3, chk.distinctFactors(644L).size());
		assertEquals(3, chk.distinctFactors(645L).size());
		assertEquals(3, chk.distinctFactors(646L).size());
		Set<Long> factors = chk.distinctFactors(644L);
		assertTrue(factors.contains(2L));
		assertTrue(factors.contains(7L));
		assertTrue(factors.contains(23L));
	}

}