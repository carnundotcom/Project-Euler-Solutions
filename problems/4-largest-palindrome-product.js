//// The problem: A palindromic number reads the same both ways.
//// The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.

//// Find the largest palindrome made from the product of two 3-digit numbers.

// Okay. I can't immediately think of a clever way to do this, so why not brute-force it?
// So, I could look loop through _all_ of the products of two 3-digit numbers checking for palindromes.
// Then sort the list of palidromes largest-to-smallest and return the largest.

function largestPalindromeProductOfNDigitNumbers(n) {
  const largestNDigitNumber = Math.pow(10, n) - 1;
  const palindromes = []
  
  for (let i = largestNDigitNumber; i > 0; i--) {
    for (let j = largestNDigitNumber; j > 0; j--) {
      if (isPalindrome(i * j) && !palindromes.includes(i * j)) {
        palindromes.push(i * j);
      }
    }
  }

  palindromes.sort((a, b) => b - a);
  return palindromes[0];
}

function isPalindrome(n) {
  const nString = n.toString();
  for (let i = 0; i < Math.floor(nString.length / 2); i++) {
    if (nString.substring(i, i + 1) !== nString.substring(nString.length - i - 1, nString.length - i)) {
      return false;
    }
  }

  return true
}

console.log(largestPalindromeProductOfNDigitNumbers(2)); // Expect: 9009
console.log(largestPalindromeProductOfNDigitNumbers(3));