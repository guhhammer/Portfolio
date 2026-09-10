#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct Graph {
    vertices: usize,
    adjacency_list: Vec<Vec<(usize, i32)>>,

    undirected: bool,
    weighted: bool,
}

impl Graph {
    #[allow(dead_code)]
    pub fn new(n_vertices: usize, undirected: bool, weighted: bool) -> Self {
        let mut al = Vec::with_capacity(n_vertices);

        for _ in 0..n_vertices {
            al.push(Vec::new());
        }

        Graph {
            vertices: n_vertices,
            adjacency_list: al,
            undirected,
            weighted,
        }
    }

    #[allow(dead_code)]
    pub fn get_n_vertices(&mut self) -> usize {
        self.vertices
    }

    #[allow(dead_code)]
    pub fn max_weight(&mut self) -> i32 {
        let mut max = -1;

        for i in &mut self.adjacency_list {
            for j in i {
                if j.1 > max {
                    max = j.1;
                }
            }
        }

        return max;
    }

    #[allow(dead_code)]
    pub fn add_vertex(&mut self) -> () {
        self.vertices += 1;
        self.adjacency_list.push(Vec::new());
    }

    #[allow(dead_code)]
    pub fn add_edge(&mut self, u: usize, v: usize) -> () {
        self.adjacency_list[u].push((v, 1));

        if self.undirected {
            self.adjacency_list[v].push((u, 1));
        }
    }

    #[allow(dead_code)]
    pub fn add_edge_weighted(&mut self, u: usize, v: usize, weight: i32) -> () {
        self.adjacency_list[u].push((v, weight));

        if self.undirected {
            self.adjacency_list[v].push((u, weight));
        }
    }

    #[allow(dead_code)]
    pub fn get_weight(&mut self, u: usize, v: usize) -> Option<i32> {
        for p in &mut self.adjacency_list[u] {
            if p.0 == v {
                return Some(p.1);
            }
        }

        None
    }

    #[allow(dead_code)]
    pub fn remove_vertex(
        &mut self,
        index: usize,
    ) -> Option<(Vec<(usize, i32)>, Vec<(usize, i32)>)> {
        if self.vertices < index && !self.adjacency_list[index].is_empty() {
            return None;
        }

        let mut ingoing_edge = Vec::new();

        for i in 0..self.vertices {
            if let Some(ingoing) = self.remove_edge(i, index) {
                ingoing_edge.push((i, ingoing.1));
            }
        }

        let mut outgoing_edge = Vec::new();

        for i in self.adjacency_list[index].clone() {
            outgoing_edge.push(i);
        }

        self.adjacency_list[index].clear();
        Some((ingoing_edge, outgoing_edge))
    }

    #[allow(dead_code)]
    pub fn remove_edge(&mut self, v: usize, u: usize) -> Option<(usize, i32)> {
        if let Some(ref mut edge) = self.adjacency_list.get_mut(v) {
            if let Some(pos) = edge.iter().position(|&(vertex, _)| vertex == u) {
                return Some(edge.remove(pos));
            }
        }

        None
    }

    #[allow(dead_code)]
    pub fn display(&mut self) -> () {
        for (i, j) in self.adjacency_list.iter().enumerate() {
            let mut result = String::from(&format!("Vertex {}: [", i));

            let edges_str: Vec<String> = j
                .iter()
                .map(|t| format!("(to {}, weight {}) ", t.0, t.1))
                .collect();

            result.push_str(&edges_str.join(""));
            result.push_str("] ");

            println!("{:?}", result);
        }
    }

    #[allow(dead_code)]
    pub fn in_degree(&mut self, index: usize) -> usize {
        self.adjacency_list[index].len()
    }

    #[allow(dead_code)]
    pub fn out_degree(&mut self, index: usize) -> usize {
        self.adjacency_list
            .iter()
            .filter(|&x| x[0].0 == index)
            .count()
    }

    #[allow(dead_code)]
    pub fn vertices(&mut self) -> Vec<i32> {
        (0..self.vertices).map(|x| x as i32).collect()
    }

    #[allow(dead_code)]
    pub fn edges(&mut self) -> Vec<(usize, i32)> {
        self.adjacency_list
            .iter()
            .flat_map(|x| x.iter().cloned())
            .collect()
    }

    #[allow(dead_code)]
    pub fn adjacent_vertices(&mut self, vertex: usize) -> Vec<usize> {
        self.adjacency_list[vertex]
            .iter()
            .map(|&(v, _)| v)
            .collect()
    }

    #[allow(dead_code)]
    pub fn get_adjacence_list(&mut self, index: usize) -> &Vec<(usize, i32)> {
        &self.adjacency_list[index]
    }

    #[allow(dead_code)]
    pub fn neighbours(&mut self, vertex: usize) -> Vec<usize> {
        self.adjacent_vertices(vertex)
    }

    #[allow(dead_code)]
    pub fn is_connected(&mut self) -> bool {
        if self.vertices == 0 {
            return true;
        }

        let mut visited = vec![false; self.vertices];
        let mut stack = vec![0];
        let mut reachable_vertices = 0;

        while let Some(vertex) = stack.pop() {
            if !visited[vertex] {
                visited[vertex] = true;
                reachable_vertices += 1;

                for &(neighbor, _) in &self.adjacency_list[vertex] {
                    if !visited[neighbor] {
                        stack.push(neighbor);
                    }
                }
            }
        }

        reachable_vertices == self.vertices
    }

    #[allow(dead_code)]
    pub fn has_cycle(&self) -> bool {
        let mut visited = vec![false; self.vertices];
        let mut parent = vec![-1; self.vertices];

        for v in 0..self.vertices {
            if !visited[v] {
                if self.dfs(v, &mut visited, &mut parent) {
                    return true;
                }
            }
        }

        false
    }

    #[allow(dead_code)]
    fn dfs(&self, vertex: usize, visited: &mut Vec<bool>, parent: &mut Vec<i32>) -> bool {
        visited[vertex] = true;

        for &(neighbour, _) in &self.adjacency_list[vertex] {
            if !visited[neighbour] {
                parent[neighbour] = vertex as i32;

                if self.dfs(neighbour, visited, parent) {
                    return true;
                }
            } else if neighbour as i32 != parent[vertex] {
                return true;
            }
        }

        false
    }
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut graph = Graph::new(5, false, false);

    // Add some edges
    graph.add_edge(0, 1);
    graph.add_edge(0, 2);
    graph.add_edge(1, 2);
    graph.add_edge(3, 4);

    // Display the graph
    println!("Initial Graph:");
    graph.display();
    println!();

    // Check if the graph is connected
    println!("Is the graph connected? {}", graph.is_connected());
    println!();

    // Check if the graph has a cycle
    println!("Does the graph have a cycle? {}", graph.has_cycle());
    println!();

    // Add more edges
    graph.add_edge(2, 3);
    graph.add_edge(4, 0);

    // Display the updated graph
    println!("Updated Graph:");
    graph.display();
    println!();

    // Check again if the graph is connected
    println!("Is the graph connected now? {}", graph.is_connected());
    println!();

    // Check again if the graph has a cycle
    println!("Does the graph have a cycle now? {}", graph.has_cycle());

    // Print vertices and edges
    println!("\nVertices: {:?}", graph.vertices());
    println!("\nEdges: {:?}", graph.edges());

    // Display adjacency list
    println!("\nAdjacency List:");
    for (i, neighbors) in graph.adjacency_list.iter().enumerate() {
        print!("Vertex {}: ", i);
        for &(v, weight) in neighbors {
            print!("(to {}, weight {})", v, weight);
        }
        println!();
    }

    // Test in-degree and out-degree
    println!("\nIn-degree of vertex 2: {}", graph.in_degree(2));
    println!("\nOut-degree of vertex 2: {}", graph.out_degree(2));

    // Test removing a vertex
    if let Some((ingoing, outgoing)) = graph.remove_vertex(2) {
        println!("\nRemoved vertex 2");
        println!("Ingoing edges: {:?}", ingoing);
        println!("Outgoing edges: {:?}", outgoing);
    }

    // Display updated adjacency list after removal
    println!("\nUpdated Adjacency List:");
    for (i, neighbors) in graph.adjacency_list.iter().enumerate() {
        print!("Vertex {}: ", i);
        for &(v, weight) in neighbors {
            print!("(to {}, weight {})", v, weight);
        }
        println!();
    }

    // Remove an edge (e.g., between vertex 1 and 2)
    graph.remove_edge(3, 4);

    // Display state after edge removal
    println!("\nAdjacency List after removing edge (1, 2):");
    for (i, neighbors) in graph.adjacency_list.iter().enumerate() {
        print!("Vertex {}: ", i);
        for &(v, weight) in neighbors {
            print!("(to {}, weight {})", v, weight);
        }
        println!();
    }
}
