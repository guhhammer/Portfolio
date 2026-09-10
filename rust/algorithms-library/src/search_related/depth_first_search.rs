use crate::datastructure::graph::Graph;

#[allow(dead_code)]
pub fn depth_first_search(graph: &mut Graph, vertex: usize) -> bool {
    for i in 0..graph.get_n_vertices() {
        print!("{:?} -> ", i);

        for j in graph.get_adjacence_list(i) {
            print!("{:?}", j.0);

            if j.0 == vertex {
                return true;
            }

            print!(" -> ");
        }
    }

    false
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut graph = Graph::new(6, false, true);

    graph.add_edge_weighted(0, 1, 1);
    graph.add_edge_weighted(0, 2, 1);
    graph.add_edge_weighted(1, 3, 1);
    graph.add_edge_weighted(2, 3, 1);
    graph.add_edge_weighted(3, 4, 1);
    graph.add_edge_weighted(4, 5, 1);

    let target_vertex = 5;
    let found = depth_first_search(&mut graph, target_vertex);

    if found {
        println!("\nVertex {} found in the graph.", target_vertex);
    } else {
        println!("\nVertex {} not found in the graph.", target_vertex);
    }
}
