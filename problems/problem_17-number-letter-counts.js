/*  If the numbers 1 to 5 are written out in words: one, two, three, four, five, then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.
    If all the numbers from 1 to 1000 (one thousand) inclusive were written out in words, how many letters would be used?

    NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and forty-two) contains 23 letters and 115 (one hundred and fifteen) 
    contains 20 letters. The use of "and" when writing out numbers is in compliance with British usage.
*/

// Okay, first thing's first: What are all the relevant words? Then, how is a number converted into them?

// Perhaps it would be worth keeping tract of all the relevant words in a map:
const numberWords = {
  "1": "one", "2": "two", "3": "three", "4": "four", "5": "five", "6": "six", "7": "seven", "8": "eight", "9": "nine",
  "10": "ten", "11": "eleven", "12": "twelve", "13": "thirteen", "14": "fourteen", "15": "fifteen", "16": "sixteen", "17": "seventeen", "18": "eighteen", "19": "nineteen",
  "20": "twenty", "30": "thirty", "40": "forty", "50": "fifty", "60": "sixty", "70": "seventy", "80": "eighty", "90": "ninety",
  "100": "hundred",
  "1000": "one thousand"
};

// Note:
//  - There's nothing special about the spacing, above. New lines just helped me keep track of things.
//  - "hundred" and should really be "one hundred", but there's no need: "one" is already present.
//  - However, as we won't be going higher than 1000, it can be treated as a special case.

// So, we need a function which takes a number and convers it into its word representation:

function intToWords(n) {
  if (n === 1000) { 
    return numberWords["1000"];
  }

  // otherwise, look at the _hundreds_ and last two digits
  const hundredsDigit = Math.floor(n / 100);
  const lastTwoDigits = n % 100

  // build up the appropriate strings
  const hundredsString = numberWords[String(hundredsDigit)] + " " + numberWords["100"];

  const tensDigit = Math.floor(lastTwoDigits / 10);
  const onesDigit = lastTwoDigits % 10;

  const lastTwoDigitsString = lastTwoDigits === 0 ? "" :
    " and " + (
      lastTwoDigits < 20 ? numberWords[String(lastTwoDigits)] : 
      onesDigit > 0 ? numberWords[String(tensDigit) + "0"] + "-" + numberWords[String(onesDigit)] :
      numberWords[String(lastTwoDigits)]
    );

  // and return them
  if (n < 100) { // (remembering to get rid of the " and " if n < 100)
    return lastTwoDigitsString.substring(5, lastTwoDigitsString.length)
  }

  // otherwise
  return hundredsString + lastTwoDigitsString
}

intToWords(1) // => "one"
intToWords(15) // => "fifteen"
intToWords(26) // => "twenty-six"
intToWords(365) // => "three hundred and sixty-five"
intToWords(1000) // => "one thousand"

// Looks good! And interestingly, it becomes kinda-sorta American after 1000:
intToWords(1001) // => "ten hundred and one"
intToWords(1200) // => "twelve hundred"
intToWords(1547) // => "fifteen hundred and fourty-seven"

// But 'breaks' if n gets too big:
intToWords(10000) // => "hundred hundred"
intToWords(53758) // => "undefined hundred and fifty-eight"

// Although "hundred hundred" isn't _wrong_! :P

// Anyway, it's fit for purpose. And all that now needs to be done is the letter-counting!

function letterCount(s) {
  const sArray = Array.from(s);
  const count = sArray.filter(char => char !== " " && char !== "-").length;

  return count;
}

function sumCount(n) { // (Notice something different about this function?)
  let totalCount = 0;
  
  for (let i = 0; i < n; i++) {
    totalCount += letterCount(intToWords(i + 1));
  }

  return totalCount;
}

// Works well enough! :D