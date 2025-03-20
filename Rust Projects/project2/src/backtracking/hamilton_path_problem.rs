use crate::datastructure::graph::Graph;

#[allow(dead_code)]
fn is_hamiltonian_path(graph: &Graph, path: &mut Vec<usize>, visited: &mut Vec<bool>) -> bool {
    if path.len() == graph.clone().get_n_vertices() {
        return true;
    }

    let current = *path.last().unwrap();

    for &neighbor in graph.clone().get_adjacence_list(current) {
        if !visited[neighbor.0] {
            visited[neighbor.0] = true;
            path.push(neighbor.0);

            if is_hamiltonian_path(graph, path, visited) {
                return true;
            }

            visited[neighbor.0] = false;
            path.pop();
        }
    }

    false
}

#[allow(dead_code)]
pub fn find_hamiltonian_path(graph: &mut Graph) -> Option<Vec<usize>> {
    let n = graph.get_n_vertices();

    for start in 0..n {
        let mut path = vec![start];
        let mut visited = vec![false; n];
        visited[start] = true;

        if is_hamiltonian_path(graph, &mut path, &mut visited) {
            return Some(path);
        }
    }

    None
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut graph = Graph::new(5, false, false);

    graph.add_edge(0, 1);
    graph.add_edge(0, 2);
    graph.add_edge(1, 2);
    graph.add_edge(1, 3);
    graph.add_edge(2, 3);
    graph.add_edge(2, 4);
    graph.add_edge(3, 4);

    match find_hamiltonian_path(&mut graph) {
        Some(path) => {
            println!("Hamiltonian Path found: {:?}", path);
        }
        None => {
            println!("No Hamiltonian Path exists.");
        }
    }
}
