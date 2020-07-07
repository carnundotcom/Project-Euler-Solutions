### By starting at the top of the triangle below and moving to adjacent numbers on the row below, the maximum total from top to bottom is 23.

###    3
###   7 4
###  2 4 6
### 8 5 9 3

### That is, 3 + 7 + 4 + 9 = 23.

### Find the maximum total from top to bottom of the triangle below:

###               75
###              95 64
###             17 47 82
###            18 35 87 10
###           20 04 82 47 65
###          19 01 23 75 03 34
###         88 02 77 73 07 63 67
###        99 65 04 28 06 16 70 92
###       41 41 26 56 83 40 80 70 33
###      41 48 72 33 47 32 37 16 94 29
###     53 71 44 65 25 43 91 52 97 51 14
###    70 11 33 28 77 73 17 78 39 68 17 57
###   91 71 52 38 17 14 91 43 58 50 27 29 48
###  63 66 04 68 89 53 67 30 73 16 69 87 40 31
### 04 62 98 27 23 09 70 98 73 93 38 53 60 04 23

### NOTE: As there are only 16384 routes, it is possible to solve this problem by trying every route.
### However, Problem 67, is the same challenge with a triangle containing one-hundred rows; 
### it cannot be solved by brute force, and requires a clever method! ;o)

# Alright, here's what I'm thinking:
#   - Starting from the top of the triangle, I have two choices: left or right.
#   - Each choice corresponds a sub-triangle of reachable numbers.
#   - I should pick the option corresponding to the sub-triangle with _the greatest total_.
#     (Or, the sub-triangle with _the greatest average value_... which amounts to the same thing.)
#   - Then, repeat.

# So, I need two things: a means of storing the triangle values, and a function which calculates the total of a triangle
# defined by a starting value.

# I think its best I treat each value as a _node_, as follows:

rows = [
                              [75],
                            [95, 64],
                          [17, 47, 82],
                        [18, 35, 87, 10],
                      [20,  4, 82, 47, 65],
                    [19,  1, 23, 75,  3, 34],
                  [88,  2, 77, 73,  7, 63, 67],
                [99, 65,  4, 28,  6, 16, 70, 92],
              [41, 41, 26, 56, 83, 40, 80, 70, 33],
            [41, 48, 72, 33, 47, 32, 37, 16, 94, 29],
          [53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14],
        [70, 11, 33, 28, 77, 73, 17, 78, 39, 68, 17, 57],
      [91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48],
    [63, 66,  4, 68, 89, 53, 67, 30, 73, 16, 69, 87, 40, 31],
  [ 4, 62, 98, 27, 23,  9, 70, 98, 73, 93, 38, 53, 60,  4, 23]
]

def make_nodes(row):
  return [{ "value": i, "parents": [], "children": [] } for i in row]

node_rows = list(map(lambda row: make_nodes(row), rows)) # => [[{'value': 75}], [{'value': 95}, {'value': 64}], ...]

# add tuples representing indexes into node_rows for parents and children
for i, row in enumerate(node_rows):
  for j, node in enumerate(row):
    if i > 0:
      if j > 0:
        node["parents"].append((i - 1, j - 1))
      if j < len(row) - 1:
        node["parents"].append((i - 1, j))

    if i < len(node_rows) - 1:
      node["children"].append((i + 1, j))
      node["children"].append((i + 1, j + 1))

# print(node_rows)

# Looks good! Now, I need a function which returns the total of a given sub-triangle:
def triangle_total(head):
  total = head["value"]
  to_visit = [node_indices for node_indices in head["children"]]
  visited = set()

  while len(to_visit) > 0:
    next_node_indices = to_visit.pop(0)
    next_node = node_rows[next_node_indices[0]][next_node_indices[1]]

    total += next_node["value"]

    for child_indices in next_node["children"]:
      if child_indices not in visited and child_indices not in to_visit:
        to_visit.append(child_indices)

    visited.add(next_node_indices)

  return total

# print(triangle_total(node_rows[0][0])) # => 5873

# Again, looks good! But just to be sure:

t = 0
for row in node_rows:
  for node in row:
    t += node["value"]

# print(t) # => 5873

# Nice! Alright. Nowww, we need to step down the triangle:
def max_path_sum(head):
  total = head["value"]
  child_indices = head["children"]

  while len(child_indices) > 0:
    left_child_indices = child_indices[0]
    right_child_indices = child_indices[1]

    left_child_node = node_rows[left_child_indices[0]][left_child_indices[1]]
    right_child_node = node_rows[right_child_indices[0]][right_child_indices[1]]

    left_triangle_total = triangle_total(left_child_node)
    right_triangle_total = triangle_total(right_child_node)

    if left_triangle_total >= right_triangle_total:
      total += left_child_node["value"]
      child_indices = left_child_node["children"]
    else:
      total += right_child_node["value"]
      child_indices = right_child_node["children"]

  return total

# print(max_path_sum(node_rows[0][0])) # => 883

# So... it works! But it gives the wrong answer. :(

# It seems, stepping through the debugger, that the path this algorithm takes is far from optimal. So it's back to the drawing board!
# (On reflection, this makes sense: there's no reason the optimal path wouldn't go through smaller sub-triangles!)

# Hmm. What we need is a way of _guaranteeing_ that a given choice is the right one.

# Let's try to think this through on the smaller triangle:

#    3
#   7 4
#  2 4 6
# 8 5 9 3

# We know the path will eventually look like this:

#    [3]
#   [7] 4
#  2 [4] 6
# 8 5 [9] 3

# And the trick is to get there asking only simple, definite questions. Not questions which would only get at the _likelihood_
# the path goes this way or that.

# But, huh! We don't actually need to generate the path. We only need the max sum!
# Meaning... we can 'collapse' the triangle as we go?

# Yep!! Check this out:

#    3
#   7 4
#  2 4 6
# 8 5 9 3

#   10  7
#  2  4  6
# 8  5  9  3

#  12  14  13
# 8   5   9   3

# 20  19  23  17

# Notice what we just did? The max sum has to be the max of the four numbers left over! (23, in this case.)
# Reason being, at each stage we added the max parent to each child...
# So the final numbers represent the numbers in the final row, _plus_ the total of the max paths leading to them.

# I didn't put that very well. But I think you get me!

# So let's code it up!

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

# And that does it!
