### The problem: The prime factors of 13195 are 5, 7, 13 and 29. What is the largest prime factor of the number 600851475143?

# Hmm... I'm thinking The Sieve of Erastosthenes is my friend, here!
# And if I can remember, the idea is... start with the smallest prime, 2. Circle it.
# Cross out all of its multiples (up to whatever number).
# Go to the next number not crossed out: which will be three. Circle it.
# Cross out all of its multiples... starting from 3^2, as 3x2 will have already been crossed out in the previous step!
# ... And so on. Whatever's left, and circled, is prime.

# But, hmm. This will give me all of the primes less than a given number.
# The idea is I then loop through them to see which are divisors?
# Why not skip generating _all_ the primes, and just try to find prime divisors from the off?

# Yes. I think I'm going to go with that! Find divisors, loop through largest-to-smallest to find the largest prime.
# Ah! So The Sieve will come in handy after all. :)

import math

def largest_prime_factor(n):
    divisors = []
    for i in range(int(math.sqrt(n))):
        if i >= 2 and n % i == 0:
            divisors.append(i)

    for i in reversed(divisors):
        if sieve(i):
            print(f"The largest prime factor of {n} is {i}.")
            return

def sieve(n):
    visited = [False for i in range(int(math.sqrt(n)))]
    to_check = [i + 2 for i in range(int(math.sqrt(n)))]

    ### Helper
    def is_prime(n, m):
        if n == 2: # Need a special case for n = 2!
            return True
        if n % m == 0:
            return False

    for m in to_check:
        if not visited[m - 2]:
            visited[m - 2] = True

            # print("A", n, m, is_prime(n, m))
            if is_prime(n, m):
                return True
            if is_prime(n, m) == False:
                return False

            j = 0
            while m**2 + m*j < math.sqrt(n): # Can start checking from m^2 here as any multiple of m less than m^2 has already been visited.*
                visited[(m**2 + m*j) - 2] = True

                # print("B", n, m, is_prime(n, m))
                if is_prime(n, m):
                    return True
                if is_prime(n, m) == False:
                    return False

                j += 1

    return True


n1 = 13195 # expect: 29
n2 = 600851475143

largest_prime_factor(n1)
largest_prime_factor(n2)