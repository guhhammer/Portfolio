use std::collections::{BinaryHeap, HashMap, HashSet};

use crate::datastructure::graph::Graph;

#[allow(dead_code, unused_variables)]
fn heuristic(graph: &Graph, start: usize, goal: usize) -> usize {
    0
}

#[allow(dead_code)]
pub fn a_star(graph: &mut Graph, start: usize, goal: usize) -> Option<(usize, Vec<usize>)> {
    let mut open_set = BinaryHeap::new();

    let mut came_from: HashMap<usize, usize> = HashMap::new();

    let mut g_score: HashMap<usize, usize> = HashMap::new();

    let mut f_score: HashMap<usize, usize> = HashMap::new();

    let mut closed_set: HashSet<usize> = HashSet::new();

    g_score.insert(start.clone(), 0);
    f_score.insert(start.clone(), heuristic(graph, start, goal));

    open_set.push((f_score[&start], start.clone()));

    while let Some((_, node)) = open_set.pop() {
        if node == goal {
            let mut path = vec![node.clone()];
            let mut current = node;

            while let Some(prev) = came_from.get(&current) {
                path.push(prev.clone());
                current = prev.clone();
            }

            path.reverse();
            return Some((g_score[&goal], path));
        }

        closed_set.insert(node.clone());

        let gclone = graph.clone();
        let neighbors = graph.get_adjacence_list(node);

        for edge in neighbors {
            let tentative_g_score = g_score[&node] + edge.1 as usize;

            if closed_set.contains(&edge.0) {
                continue;
            }

            if tentative_g_score < *g_score.get(&edge.0).unwrap_or(&usize::MAX) {
                came_from.insert(edge.0.clone(), node.clone());

                g_score.insert(edge.0.clone(), tentative_g_score);

                f_score.insert(
                    edge.0.clone(),
                    tentative_g_score + heuristic(&gclone, edge.0, goal),
                );

                if !open_set.iter().any(|state| state.1 == edge.0) {
                    open_set.push((f_score[&edge.0], edge.0.clone()));
                }
            }
        }
    }

    None
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut graph = Graph::new(4, false, true);

    graph.add_edge_weighted(0, 1, 10);
    graph.add_edge_weighted(0, 2, 6);
    graph.add_edge_weighted(0, 3, 5);
    graph.add_edge_weighted(1, 3, 15);
    graph.add_edge_weighted(2, 3, 4);

    if let Some((cost, path)) = a_star(&mut graph, 0, 3) {
        println!("Cost: {}, Path: {:?}", cost, path);
    } else {
        println!("No path found.");
    }
}
