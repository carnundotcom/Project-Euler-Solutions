//// A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,

//// a^2 + b^2 = c^2
//// For example, 3^2 + 4^2 = 9 + 16 = 25 = 5^2.

//// There exists exactly one Pythagorean triplet for which a + b + c = 1000.
//// Find the product abc.

// Hmm. The basic idea: enumerate all possible triplets a < b < c, where c always equals 1000 - a - b,
//  stopping at the one Pythagorean triplet, and calculate the product.

function findPythagoreanTriplet(sum) {
  let a = 1;
  let b = 2;
  let c = sum - a - b;

  while ((a * a) + (b * b) !== (c * c)) {
    if (b < c) {
      b++;
    } else {
      a++;
      b = a + 1;
    }
    c = sum - a - b;
  }

  return a * b * c;
}

console.log(findPythagoreanTriplet(1000));

// NOTE: The above may not halt for sum !== 1000...! :D