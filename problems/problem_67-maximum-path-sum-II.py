### By starting at the top of the triangle below and moving to adjacent numbers on the row below, the maximum total from top to bottom is 23.

###    3
###   7 4
###  2 4 6
### 8 5 9 3

### That is, 3 + 7 + 4 + 9 = 23.

### Find the maximum total from top to bottom in triangle.txt (right click and 'Save Link/Target As...'),
### a 15K text file containing a triangle with one-hundred rows.

### NOTE: This is a much more difficult version of Problem 18. It is not possible to try every route to solve this problem,
### as there are 299 altogether! If you could check one trillion (1012) routes every second it would take over twenty billion 
### years to check them all. There is an efficient algorithm to solve it. ;o)

# Alrighty! First thing's first: I need to parse the text file:
rows = []
with open('files/p067_triangle.txt') as file:
  for line in file:
    # remove /n at end of line
    line = line[:-1]
    # split line into array
    row = line.split(' ')
    # map over row, turning strings -> ints
    row = list(map(lambda s: int(s), row))
    # add row to rows
    rows.append(row)

# print(rows)

# Looking good!

# So, second: copy over my solution to Problem 18.

def actual_max_path_sum():
  totals = rows.copy() # (Note: We no longer need to worry about node_rows; rows is just fine!)

  row_index = 1
  while row_index < len(rows):
    for i in range(row_index + 1):
      max_parent = 0
      if i == 0: # first in the row
        max_parent = totals[row_index - 1][0]
      elif i == row_index: # last in the row
        max_parent = totals[row_index - 1][i - 1]
      else:
        max_parent = max(totals[row_index - 1][i - 1], totals[row_index - 1][i])

      totals[row_index][i] += max_parent
    
    row_index += 1

  return max(totals[-1])

print(actual_max_path_sum())

# Works first time!