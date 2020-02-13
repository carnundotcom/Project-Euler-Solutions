### The problem: If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9.
### The sum of these multiples is 23.
### Find the sum of all the multiples of 3 or 5 below 1000.

def get_multiples(x, limit):
    multiples = []

    i = 1
    while x*i < limit:
        multiples.append(x*i)
        i += 1
    
    return multiples

multiples_of_3 = get_multiples(3, 1000)
multiples_of_5 = get_multiples(5, 1000)

# Sanity check:
print(multiples_of_3[:5], multiples_of_3[-5:])
print(multiples_of_5[:5], multiples_of_5[-5:])

def merge(l1, l2):
    output = l1

    for num in l2:
        if num not in l1:
            output.append(num)

    return output

multiples_of_3_and_5 = merge(multiples_of_3, multiples_of_5)

sum = 0
for num in multiples_of_3_and_5:
    sum += num

print(f"Sum: {sum}")