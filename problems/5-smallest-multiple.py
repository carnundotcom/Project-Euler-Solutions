### The problem: 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
### What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?

# Hmm. First thought: if a number is evenly divisible by 20, then it's evenly divisible by 10, 5, 4, 2, and 1 as well.
# So if I check n % 20 == 0 first, I don't need to check n % 20 == 0, n % 5 == 0, etc. How else can I reduce checks like this?

# This trick doesn't work for the primes, so I'll have to check them, unless they're factors of larger numbers below 20.

# Hmm... ah! I only need to check 11-20. The factors of numbers <= 20 are all <= 10. Doh!

# How else can I improve performance?

# Here are a couple things we know:
# - n must be a multiple of 20;
# - n's digits must sum to a mutiple of 9 (18) and a multiple of 3 (12), but we need only check the first;
# - n is _at least_ as big as 2520.

# That's clever enough for me. Let's get coding!

# helper
def is_multiple(n):
    n_string = str(n)
    sum_of_digits = 0
    for digit in n_string:
        sum_of_digits += int(digit)

    if not sum_of_digits % 9 == 0:
        return False

    for i in reversed(range(11, 20)):
        if i != 18 and i != 12:
            if not n % i == 0:
                return False
    
    return True

n = 2520
while True:
    if is_multiple(n):
        print(f"The smallest positive number that is evenly divisible by all of the numbers from 1 to 20 is {n}")
        break
    
    print(n)
    n += 20

# print(2*3*4*5*6*7*8*9*10*11*12*13*14*15*16*17*18*19*20)
# print(is_multiple(2*3*4*5*6*7*8*9*10*11*12*13*14*15*16*17*18*19*20))
# print(is_multiple(2*3*4*5*6*7*8*9*10*11*12*13*14*15*16*17*18*19*20 + 1))

# Alternatively (don't know why I forgot about this!): https://en.wikipedia.org/wiki/Least_common_multiple#Using_prime_factorization