use std::collections::{BinaryHeap, HashSet};

use crate::datastructure::graph::Graph;

#[allow(dead_code)]
pub fn prim_mst(graph: &mut Graph) -> Vec<(usize, usize, i32)> {
    let mut mst: Vec<(usize, usize, i32)> = Vec::new();
    let mut visited: HashSet<usize> = HashSet::new();
    let mut min_heap: BinaryHeap<(usize, usize, i32)> = BinaryHeap::new();

    visit_vertex(graph, 0, &mut visited, &mut min_heap);

    while let Some(edge) = min_heap.pop() {
        if visited.contains(&edge.1) {
            continue;
        }

        // Add the edge to MST
        mst.push(edge.clone());
        visited.insert(edge.1);

        // Visit all neighbors of the newly added vertex
        visit_vertex(graph, edge.1, &mut visited, &mut min_heap);
    }

    mst
}

fn visit_vertex(
    graph: &mut Graph,
    vertex: usize,
    visited: &mut HashSet<usize>,
    min_heap: &mut BinaryHeap<(usize, usize, i32)>,
) {
    visited.insert(vertex);

    for &(neighbor, weight) in graph.get_adjacence_list(vertex) {
        if !visited.contains(&neighbor) {
            min_heap.push((vertex, neighbor, weight));
        }
    }
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut graph = Graph::new(6, false, true);

    graph.add_edge_weighted(0, 1, 4);
    graph.add_edge_weighted(0, 2, 2);
    graph.add_edge_weighted(0, 3, 6);
    graph.add_edge_weighted(1, 2, 3);
    graph.add_edge_weighted(1, 3, 8);
    graph.add_edge_weighted(1, 4, 5);
    graph.add_edge_weighted(2, 3, 7);
    graph.add_edge_weighted(2, 4, 9);
    graph.add_edge_weighted(3, 4, 11);
    graph.add_edge_weighted(3, 5, 10);
    graph.add_edge_weighted(4, 5, 1);

    let mst = prim_mst(&mut graph);

    println!("Minimum Spanning Tree (MST):");
    for &(u, v, weight) in mst.iter() {
        println!("({}, {}, {})", u, v, weight);
    }
}
