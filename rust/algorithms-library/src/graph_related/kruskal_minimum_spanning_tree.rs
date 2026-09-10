use crate::datastructure::graph::Graph;

pub struct UnionFind {
    parent: Vec<usize>,
    rank: Vec<usize>,
}

impl UnionFind {
    pub fn new(size: usize) -> Self {
        Self {
            parent: (0..size).collect(),
            rank: vec![0; size],
        }
    }

    pub fn find(&mut self, x: usize) -> usize {
        if self.parent[x] != x {
            self.parent[x] = self.find(self.parent[x]);
        }
        self.parent[x]
    }

    pub fn union(&mut self, x: usize, y: usize) {
        let root_x = self.find(x);
        let root_y = self.find(y);

        if root_x != root_y {
            if self.rank[root_x] > self.rank[root_y] {
                self.parent[root_y] = root_x;
            } else if self.rank[root_x] < self.rank[root_y] {
                self.parent[root_x] = root_y;
            } else {
                self.parent[root_y] = root_x;
                self.rank[root_x] += 1;
            }
        }
    }
}

#[allow(dead_code)]
pub fn kruskal_mst(graph: &mut Graph) -> Vec<(usize, usize, i32)> {
    let mut mst: Vec<(usize, usize, i32)> = Vec::new();
    let mut uf: UnionFind = UnionFind::new(graph.get_n_vertices());

    let mut g_list: Vec<(usize, usize, i32)> = Vec::new();

    for i in 0..graph.get_n_vertices() {
        for n in graph.get_adjacence_list(i) {
            g_list.push((i, n.0, n.1));
        }
    }

    g_list.sort_by(|a, b| a.1.cmp(&b.1));

    for edge in g_list {
        if uf.find(edge.0) != uf.find(edge.1) {
            uf.union(edge.0, edge.1);
            mst.push(edge);
        }
    }

    mst
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut graph = Graph::new(4, false, true);

    graph.add_edge_weighted(0, 1, 10);
    graph.add_edge_weighted(0, 2, 6);
    graph.add_edge_weighted(0, 3, 5);
    graph.add_edge_weighted(1, 3, 15);
    graph.add_edge_weighted(2, 3, 4);

    let mst = kruskal_mst(&mut graph);

    println!("Edges in the Minimum Spanning Tree:");
    for edge in mst {
        println!("v:{} -> U:{} (w:{})", edge.0, edge.1, edge.2);
    }
}
