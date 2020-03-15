# The problem: By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
# What is the 10 001st prime number?

# Okay. I'm listing primes. So, naive approach: start some i at 2, and check if 2 is prime using the Sieve of Erastosthenes.
# If it is, increment a count of primes, and i.
# Else, just increment i.
# Check again if i is prime.
# And so on... until the prime count equals 10,001.
# At which point, return i.

def nth_prime(n):
    i = 2
    prime_count = 1

    while prime_count < n:
        i += 1

        if sieve(i):
            prime_count += 1

    return i


# From Problem 3:
import math

def sieve(n):
    visited = [False for i in range(int(math.sqrt(n)))]
    to_check = [i + 2 for i in range(int(math.sqrt(n)))]

    for m in to_check:
        if not visited[m - 2]:
            visited[m - 2] = True

            if is_prime(n, m):
                return True
            if is_prime(n, m) == False:
                return False

            j = 0
            while m**2 + m*j < math.sqrt(n): # Can start checking from m^2 here as any multiple of m less than m^2 has already been visited.*
                visited[(m**2 + m*j) - 2] = True

                if is_prime(n, m):
                    return True
                if is_prime(n, m) == False:
                    return False

                j += 1

    return True

# Helper:
def is_prime(n, m):
    if n == 2: # Need a special case for n = 2!
        return True
    if n % m == 0:
        return False

print(nth_prime(1)) # expect 2
print(nth_prime(2)) # expect 3
print(nth_prime(3)) # expect 5
print(nth_prime(6)) # expect 13
print(nth_prime(10001))