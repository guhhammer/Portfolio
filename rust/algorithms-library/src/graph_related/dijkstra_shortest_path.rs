use std::collections::BinaryHeap;
use std::i32::MAX;

use crate::datastructure::graph::Graph;

// Dijkstra's algorithm to find shortest paths from a source vertex
pub fn dijkstra(graph: &mut Graph, source: usize) -> Vec<i32> {
    let mut dist: Vec<i32> = vec![MAX; graph.get_n_vertices()]; // Initialize distances with infinity
    let mut min_heap = BinaryHeap::new(); // Min-heap for Dijkstra's algorithm

    // Start from the source vertex with distance 0
    dist[source] = 0;
    min_heap.push((source, 0));

    // Process vertices while there are vertices in the heap
    while let Some((target, weight)) = min_heap.pop() {
        if weight > dist[target] {
            continue;
        }

        for edge in graph.get_adjacence_list(target) {
            let new_dist = dist[target] + edge.1;

            if new_dist < dist[edge.0] {
                dist[edge.0] = new_dist;

                min_heap.push(*edge);
            }
        }
    }

    dist
}

pub fn main() {
    // Create a new graph with 5 vertices
    let mut graph = Graph::new(5, false, true);

    // Add weighted edges to the graph
    graph.add_edge_weighted(0, 1, 10);
    graph.add_edge_weighted(0, 2, 5);
    graph.add_edge_weighted(1, 2, 2);
    graph.add_edge_weighted(1, 3, 1);
    graph.add_edge_weighted(2, 1, 3);
    graph.add_edge_weighted(2, 3, 9);
    graph.add_edge_weighted(2, 4, 2);
    graph.add_edge_weighted(3, 4, 4);
    graph.add_edge_weighted(4, 0, 7);
    graph.add_edge_weighted(4, 3, 6);

    // Source vertex for Dijkstra's algorithm
    let source_vertex = 0;

    // Run Dijkstra's algorithm from the source vertex
    let shortest_paths = dijkstra(&mut graph, source_vertex);

    // Print the shortest paths from the source vertex
    println!("Shortest paths from vertex {}:", source_vertex);
    for (vertex, distance) in shortest_paths.iter().enumerate() {
        println!("Vertex {}: Distance = {}", vertex, distance);
    }
}
