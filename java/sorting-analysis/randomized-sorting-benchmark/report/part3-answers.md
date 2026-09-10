# Part 3 - conclusions on the randomized algorithms

**Do the randomized algorithms represent a viable option?**

Overall, comparing the three array orderings (ascending, descending and random), the randomised-and-recursive algorithms perform worse than the purely recursive ones.

Quicksort was only slightly faster on the random array. The purely recursive quicksort partitions at the middle of the array, while the randomised version partitions at a random point. For quicksort, randomisation makes sense for large arrays because of the high probability that the array is unsorted (ascending or descending being the two worst cases for the algorithm among the three evaluated).

Among selection sort, merge sort and quicksort, randomisation made a positive difference only for quicksort, and only in one scenario. This is because quicksort is a divide-and-conquer algorithm where all of the ordering happens during the division, in the recursive calls and partitions. Merge sort splits the arrays down to pairs and merges the smallest parts back up, so randomisation does not help: it only moves the split point randomly. It would be better applied if the algorithm also used parallelism and switched to insertion sort for subsets up to 1000 elements, for large inputs. For selection sort, randomisation does not help at all: the algorithm depends on a sorting order, and with randomisation the chance of finding the smallest values early and ordering them ascending is very low, so randomisation hurts how the algorithm operates and its times were much worse than the plain recursive version, even for small inputs.

**Does this approach make no sense at all?**

It only makes sense when: (1) the input is very large; (2) the algorithm uses randomisation before ordering, in the division phase, in pointer organisation or in building a heap; (3) the algorithm can be parallelised; and (4) other algorithms can be inserted at certain stages of the sort.

**Is there a relationship regarding the average cost of the randomized algorithms?**

Yes: proportionally, the average time of the randomised algorithms was best compared with the ascending and descending scenarios.
