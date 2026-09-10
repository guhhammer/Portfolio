mod activities;
mod algorithms;
mod backtracking;
mod cryptographic;
mod datastructure;
mod dynamic_programming;
mod graph_related;
mod mathematical;
mod search_related;
mod sorting;
mod string_related;

use algorithms::main_executioner_wrapper::MainWrapper;

fn main() {
    [
        (activities::a1::main as fn(), "a1", false),
        (activities::a2::main as fn(), "a2", false),
        (activities::a3::main as fn(), "a3", false),
        (
            backtracking::hamilton_path_problem::main as fn(),
            "hamilton_path_problem",
            false,
        ),
        (
            cryptographic::caeser_cipher::main as fn(),
            "caeser_cypher",
            false,
        ),
        (
            cryptographic::diffie_hellman_key_exchange::main as fn(),
            "diffie_hellman_key_exchange",
            false,
        ),
        (
            datastructure::binary_tree::main as fn(),
            "binary_tree",
            false,
        ),
        (datastructure::deque::main as fn(), "deque", false),
        (datastructure::graph::main as fn(), "graph", false),
        (datastructure::hash_table::main as fn(), "hash_table", false),
        (datastructure::heap::main as fn(), "heap", false),
        (datastructure::linkedlist::main as fn(), "linkedlist", false),
        (datastructure::queue::main as fn(), "queue", false),
        (datastructure::stack::main as fn(), "stack", false),
        (datastructure::set::main as fn(), "set", false),
        (datastructure::trie::main as fn(), "trie", false),
        (
            dynamic_programming::fibonacci_sequence::main as fn(),
            "fibonacci_sequence",
            false,
        ),
        (
            dynamic_programming::knapsack_problem::main as fn(),
            "knapsack_problem",
            false,
        ),
        (
            dynamic_programming::longest_common_subsequence::main as fn(),
            "longest_common_subsequence",
            false,
        ),
        (
            graph_related::a_star_search::main as fn(),
            "a_star_search",
            false,
        ),
        (
            graph_related::dijkstra_shortest_path::main as fn(),
            "dijkstra_shortest_path",
            false,
        ),
        (
            graph_related::kruskal_minimum_spanning_tree::main as fn(),
            "kruskal_minimum_spanning_tree",
            false,
        ),
        (
            graph_related::prim_minimum_spanning_tree::main as fn(),
            "prim_minimum_spanning_tree",
            false,
        ),
        (
            mathematical::bell_numbers::main as fn(),
            "bell_numbers",
            false,
        ),
        (
            mathematical::catalan_numbers::main as fn(),
            "catalan_numbers",
            false,
        ),
        (
            mathematical::chinese_remainder_theorem::main as fn(),
            "chinese_remainder_theorem",
            false,
        ),
        (
            mathematical::euclidean_algorithm_for_gcd::main as fn(),
            "euclidean_algorithm_for_gcd",
            false,
        ),
        (
            mathematical::eulers_totient_function::main as fn(),
            "eulers_totient_function",
            false,
        ),
        (
            mathematical::fast_exponentiation::main as fn(),
            "fast_exponentiation",
            false,
        ),
        (
            mathematical::iterative_factorial::main as fn(),
            "iterative_factorial",
            false,
        ),
        (
            mathematical::iterative_fibonacci::main as fn(),
            "iterative_fibonacci",
            false,
        ),
        (
            mathematical::las_vegas_algorithms_for_optimization_problems::main as fn(),
            "las_vegas_algorithms_for_optimization_problems",
            false,
        ),
        (
            mathematical::modular_exponentiation::main as fn(),
            "modular_exponentiation",
            false,
        ),
        (
            mathematical::monte_carlo_integration::main as fn(),
            "monte_carlo_integration",
            false,
        ),
        (
            mathematical::permutations_and_combinations::main as fn(),
            "permutations_and_combinations",
            false,
        ),
        (
            mathematical::random_number_generation::main as fn(),
            "random_number_generation",
            false,
        ),
        (
            mathematical::recursive_factorial::main as fn(),
            "recursive_factorial",
            false,
        ),
        (
            mathematical::recursive_fibonacci::main as fn(),
            "recursive_fibonacci",
            false,
        ),
        (
            mathematical::sampling_algorithms_reservoir_sampling::main as fn(),
            "sampling_algorithms_reservoir_sampling",
            false,
        ),
        (
            mathematical::sieve_of_eratosthenes_for_finding_prime_numbers::main as fn(),
            "sieve_of_eratosthenes_for_finding_prime_numbers",
            false,
        ),
        (
            mathematical::using_memoizaiton_factorial::main as fn(),
            "using_memoizaiton_factorial",
            false,
        ),
        (
            mathematical::voronoi_diagrams::main as fn(),
            "voronoi_diagrams",
            false,
        ),
        (
            mathematical::wilsons_theorem::main as fn(),
            "wilsons_theorem",
            false,
        ),
        (
            search_related::binary_search::main as fn(),
            "binary_search",
            false,
        ),
        (
            search_related::breadth_first_search::main as fn(),
            "breadth_first_search",
            false,
        ),
        (
            search_related::depth_first_search::main as fn(),
            "depth_first_search",
            false,
        ),
        (
            search_related::linear_search::main as fn(),
            "linear_search",
            false,
        ),
        (sorting::kronometrmain::main as fn(), "kronometrmain", false),
        (sorting::sortingmain::main as fn(), "sortingmain", false),
        (
            string_related::boyer_moore::main as fn(),
            "boyer_moore_algorithm",
            false,
        ),
        (
            string_related::kmp_knuth_morris_pratt_pattern_matching::main as fn(),
            "kmp_pattern_matching",
            false,
        ),
        (
            string_related::rabin_karp::main as fn(),
            "rabin_karp_algorithm",
            false,
        ),
        (
            string_related::trie_based_string_search::main as fn(),
            "trie_based_string_search",
            false,
        ),
    ]
    .map(|c| MainWrapper::call(c.0, c.1, c.2));
}
