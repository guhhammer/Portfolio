#####################################################
#####################################################

from itertools import combinations


### Transform gen combinations entries to dict keys.
###
def generator_to_dict(gen):

	d = {}

	for i in gen:

		d[':'.join([str(_) for _ in i])] = False

	return d


### Fork combination entry into a bucket of possible combinations of lesser size.
###
def possibilities_for(comb, number_of_combs):

	for i in comb:

		yield combinations(i, number_of_combs)


### Check which entries have many matches. Returns: [[index, how many matches], ... ].
###
def requisities_fulfilled(dictionary, possibilities):

	index = 0
	for p in possibilities:

		p = [list(i) for i in p]

		many = [ dictionary[':'.join([str(d) for d in _])] for _ in p ].count(False)

		[dictionary.__setitem__(':'.join([str(d) for d in _]), True) for _ in p ]		
		
		yield [index, many]
		
		index += 1


### Retrive entries with unselected combination entries. (Many has to be greater than 0 for the comb to be chosen.)
###
def smaller_set(comb, requisites):

	for c,r in zip(comb, requisites):

		if r[1] > 0:
			
			yield c


###	Returns a generator for the remaining selected combinations.
###
def selector_for_(comb_higher, comb_lower, comb_lower_size_number):

	match_dict = generator_to_dict(comb_lower)

	possibilities = possibilities_for(comb_higher, comb_lower_size_number)

	requisites = requisities_fulfilled(match_dict, possibilities)

	s_set = smaller_set(comb_higher, requisites)

	return s_set


#####################################################
#####################################################


"""
	
	For an entry in comb(15, 14), you can derive 14 entries within it.

	If you extend the matching process, you will end up getting the first
	supressing enties to set the whole dictionary to true.

	comb(15, 14)[0] ->  [...] (size 14) ~ matches 14
				[1] ->  [...] (size 14) ~ matches 1
			
	You are setting a tree of needed matches and how much they retrieve, call it buckets.

	Some of them will fill at maximum and others will not, these won't be generated.

"""


"""

	I derived my ideas from this sites:

		- https://en.wikipedia.org/wiki/Field_of_sets
		- https://en.wikipedia.org/wiki/Power_set
		- https://en.wikipedia.org/wiki/Lindenbaum%E2%80%93Tarski_algebra
		- https://en.wikipedia.org/wiki/Logical_disjunction
		- https://en.wikipedia.org/wiki/Naive_set_theory
		- https://en.wikipedia.org/wiki/Image_(mathematics)#Properties
		- https://en.wikipedia.org/wiki/List_of_set_identities_and_relations
		- https://en.wikipedia.org/wiki/Ordered_field#Positive_cone
		- https://en.wikipedia.org/wiki/Topological_space#Definitions
		- https://en.wikipedia.org/wiki/Stone%27s_representation_theorem_for_Boolean_algebras
		- https://en.wikipedia.org/wiki/Stone_duality
		- https://en.wikipedia.org/wiki/Sigma-ring
		- https://en.wikipedia.org/wiki/%CE%A3-algebra
		- https://en.wikipedia.org/wiki/Ring_of_sets
		- https://en.wikipedia.org/wiki/Probability_theory
		- https://en.wikipedia.org/wiki/Dynkin_system
		- https://en.wikipedia.org/wiki/Measure_(mathematics)
		- https://en.wikipedia.org/wiki/Pi-system
		- https://en.wikipedia.org/wiki/List_of_Boolean_algebra_topics

"""


"""

	How is the execution for a supposed combinations set:
	
	C(n, r) = n! / (r! * (n-r)!)


	### Short mode:

	ss = selector_for_(

						[(1,2,3,4), (2,3,4,5), (4,5,6,7), (1,2,4,5)], 										# Higher-(r) combinations Set 
						
						[(1, 2), (1, 3), (1, 4), (2, 3), (1,5), (2, 4), (3, 4), (2, 3),
						 (2, 4), (2, 5), (3, 4), (3, 5), (4, 5), (4, 5), (4, 6), (4, 7),
						 (5, 6), (5, 7), (6, 7)], 															# lesser-(r) combinations Set

						 2																					# lesser-(r)

						 )


	print(list(ss))																							# Display



	### within the function mechanics:

	
	d = generator_to_dict(
						
						[(1, 2), (1, 3), (1, 4), (2, 3), (1,5), (2, 4), (3, 4), (2, 3), 
						(2, 4), (2, 5), (3, 4), (3, 5), (4, 5), (4, 5), (4, 6), (4, 7),
						(5, 6), (5, 7), (6, 7)]																# Make a dictionary.

						)


	a = possibilities_for([(1,2,3,4), (2,3,4,5), (4,5,6,7), (1,2,4,5)], 2)									# get all combinations for a higher set

	r = requisities_fulfilled(d, a)																			# Set entries and their dict entries.

	s = smaller_set([[1,2,3,4], [2,3,4,5], [4,5,6,7], [1,2,4,5]], r)										# retrieve matches for higher set.

	print(list(s))																							# display

"""


#####################################################
#####################################################
