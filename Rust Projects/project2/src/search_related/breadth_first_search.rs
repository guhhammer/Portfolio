use crate::datastructure::graph::Graph;

#[allow(dead_code)]
pub fn breadth_first_search(graph: &mut Graph, vertex: usize) -> bool {
    let mut layer: Vec<usize> = Vec::new();

    layer.push(0); // index where all graph starts.

    let mut index = 0;

    while index < layer.len() - 1 || index == 0 {
        print!("{:?}", index);

        if index == vertex {
            return true;
        }

        print!(" -> ");

        for n in graph.get_adjacence_list(index) {
            layer.push(n.0);
        }

        index += 1;
    }

    false
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut graph = Graph::new(6, false, true);

    // Add edges to the graph
    graph.add_edge_weighted(0, 1, 1);
    graph.add_edge_weighted(0, 2, 1);
    graph.add_edge_weighted(1, 3, 1);
    graph.add_edge_weighted(2, 3, 1);
    graph.add_edge_weighted(3, 4, 1);
    graph.add_edge_weighted(4, 5, 1);

    // Perform breadth-first search for vertex 5
    let target_vertex = 5;
    let found = breadth_first_search(&mut graph, target_vertex);

    if found {
        println!("\nVertex {} found in the graph.", target_vertex);
    } else {
        println!("\nVertex {} not found in the graph.", target_vertex);
    }
}
