#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct Knapsack {
    weight_capacity: i32,

    weight_value: Vec<(i32, usize)>,
}

impl Knapsack {
    #[allow(dead_code)]
    pub fn new(weight_capacity: i32) -> Self {
        Knapsack {
            weight_capacity,
            weight_value: Vec::new(),
        }
    }

    #[allow(dead_code)]
    pub fn set_weight_value(&mut self, weight_value: Vec<(i32, usize)>) -> () {
        self.weight_value = weight_value;
    }

    #[allow(dead_code)]
    fn finder(&self, debug: bool) -> Option<[(i32, usize); 3]> {
        if self.weight_value.is_empty() {
            return None;
        }

        /*
        Example:

            0a) let wv: [(i32, usize); 4] = [ (1, 10), (2, 20), (3, 30), (4, 40), ];

            0b) weight_capacity: i32 = 5;

        */

        let wmax = self.weight_capacity.clone();
        if debug {
            println!("wmax {:?}", wmax);
        }

        let mut m_wv: Vec<(i32, i32, usize, usize, i32, usize)> = Vec::new();
        /*

            1) Define matrix weight value as ~  [ (w1 w2 v1 v2 (w1+w2) (v1+v2)); wv.len()]

        */

        for i in 0..self.weight_value.clone().len() {
            for j in i..self.weight_value.clone().len() {
                if &self.weight_value[i].0 == &self.weight_value[j].0 {
                    m_wv.push((
                        self.weight_value[i].0,
                        self.weight_value[j].0,
                        self.weight_value[i].1,
                        self.weight_value[j].1,
                        self.weight_value[i].0,
                        self.weight_value[i].1,
                    ));

                    continue;
                }

                m_wv.push((
                    self.weight_value[i].0,
                    self.weight_value[j].0,
                    self.weight_value[i].1,
                    self.weight_value[j].1,
                    self.weight_value[i].0 + self.weight_value[j].0,
                    self.weight_value[i].1 + self.weight_value[j].1,
                ));
            }
        } /*
              2) row index weight x col weight, then consider only upper diagonal;
              3) diagonal is the weight for each index the weight_value array;
              4) push the w1 (row), w2 (col), v1 (row), v2 (col), w1 + w2, v1 + v2 as tuple to
                 matrix_weight_value ~ m_wv.

                 | w1 w2 w3 w4 |
              w1 |  1  3  4  5 |
              w2 |  0  2  5  6 |
              w3 |  0  0  3  7 |
              w4 |  0  0  0  4 |
          */

        let mut poss: Vec<(i32, i32, usize, usize, i32, usize)> = m_wv
            .clone()
            .into_iter()
            .filter(|&(_, _, _, _, w, _)| w <= wmax)
            .collect();

        poss.sort_by(|a, b| a.4.cmp(&b.4));

        if debug {
            println!("Possible solutions: {:?}", poss);
        }
        /*
            5) filter weights that are less than max capacity weight (wmax);
            6) then order by value sumn ( < );


               | w1 w2 w3 w4 |          | v1 v2 v3 v4 |        poss ~ [
            w1 |  1  3  4  5 |   \   v1 | 10 30 40 50 |   \    (1, 1, 10, 10, 1, 10), (2, 2, 20, 20, 2, 20),
            w2 |  0  2  5  0 | == \  v2 |    20 50    | == \   (1, 2, 10, 20, 3, 30), (3, 3, 30, 30, 3, 30),
            w3 |  0  0  3  0 | == /  v3 |       30    | == /   (1, 3, 10, 30, 4, 40), (4, 4, 40, 40, 4, 40),
            w4 |  0  0  0  4 |   /   v4 |          40 |   /    (1, 4, 10, 40, 5, 50), (2, 3, 20, 30, 5, 50)  ]

            7) find max v+v value in sorted array;       ~ Poss last value: 50
            8) select only tuples with max v+v within; ~   [(1, 4, 10, 40, 5, 50), (2, 3, 20, 30, 5, 50)]
        */

        let plastv: usize = poss.last().unwrap().5;
        if debug {
            println!("Poss last value: {:?}", plastv);
        }

        let mut res: Vec<(i32, i32, usize, usize, i32, usize)> = poss
            .clone()
            .into_iter()
            .filter(|&(_, _, _, _, _, v)| v == plastv)
            .collect();

        res.sort_by(|a, b| a.0.cmp(&b.0).then_with(|| a.1.cmp(&b.1)));

        if debug {
            println!("Result: {:?}", res);
        }

        let smallest_diff: Vec<i32> = res
            .clone()
            .into_iter()
            .map(|(a, b, _, _, _, _)| (a - b).abs())
            .collect();

        let ans = res[smallest_diff
            .iter()
            .position(|&x| x == *smallest_diff.iter().min().unwrap())
            .unwrap()];
        if debug {
            println!("Weights with smallest difference: {:?}", ans);
        }
        /*

           9) Get weights with smallest difference in-between. ~ (2, 3, 20, 30, 5, 50)

        */

        Some([(ans.0, ans.2), (ans.1, ans.3), (ans.4, ans.5)])
        //      w1      v1      w2      v3     w1+w2  v1+v2
    }

    #[allow(dead_code)]
    pub fn find_best_solution_of_2(&mut self) -> Option<[(i32, usize); 3]> {
        self.finder(false)
    }

    #[allow(dead_code)]
    pub fn debugger_of_2(&mut self) -> Option<[(i32, usize); 3]> {
        self.finder(true)
    }

    #[allow(dead_code)]
    pub fn find_best_solution_of_2_commented(&mut self) -> () {
        let sol: [(i32, usize); 3] = self.finder(false).unwrap();

        print!(
            "\nBest solution for knapsack of weight_value (wv):\n\n\twv = {:?}\n",
            self.weight_value
        );
        print!("\n\tSolution:    {:?}\n\t|", sol);
        print!(
            "\n\t|-- Item1 - (weight: {:?}, value: {:?})\n\t|",
            sol[0].0, sol[0].1
        );
        print!(
            "\n\t|-- Item2 - (weight: {:?}, value: {:?})\n\t|",
            sol[1].0, sol[1].1
        );
        print!(
            "\n\t|-- Best pair: - (weight: {:?}, value: {:?})\n\t|",
            sol[2].0, sol[2].1
        );
    }

    #[allow(dead_code)]
    pub fn knapsack(&mut self) -> (usize, Vec<bool>) {
        let n = self.weight_value.len();
        let capacity = self.weight_capacity as usize;
        let mut dp = vec![vec![0; capacity + 1]; n + 1];
        let mut selected = vec![vec![false; capacity + 1]; n + 1];

        for i in 1..=n {
            let (weight, value) = self.weight_value[i - 1];
            let weight = weight as usize;

            for w in 0..=capacity {
                if weight <= w {
                    let take_value = dp[i - 1][w - weight] + value;
                    if take_value > dp[i - 1][w] {
                        dp[i][w] = take_value;
                        selected[i][w] = true;
                    } else {
                        dp[i][w] = dp[i - 1][w];
                        selected[i][w] = false;
                    }
                } else {
                    dp[i][w] = dp[i - 1][w];
                    selected[i][w] = false;
                }
            }
        }

        // Determine which items are included
        let mut included_items = vec![false; n];
        let mut w = capacity;
        for i in (1..=n).rev() {
            if selected[i][w] {
                included_items[i - 1] = true;
                let (weight, _) = self.weight_value[i - 1];
                w -= weight as usize;
            }
        }

        (dp[n][capacity], included_items)
    }
}

#[allow(dead_code)]
fn example5() -> () {
    let wv: Vec<(i32, usize)> = [(1, 10), (2, 20), (3, 30), (4, 40)].to_vec();

    let mut ks: Knapsack = Knapsack::new(5);

    ks.set_weight_value(wv);

    println!("\n{:?}", ks.debugger_of_2());

    ks.find_best_solution_of_2_commented();
}

#[allow(dead_code)]
pub fn main() -> () {
    if false {
        example5();
    }

    let wv = vec![
        (3, 4),
        (2, 2),
        (1, 1),
        (5, 10),
        (4, 7),
        (6, 8),
        (3, 5),
        (7, 14),
        (2, 3),
        (4, 6),
    ]
    .to_vec();

    let wvc = wv.clone();

    let mut ks: Knapsack = Knapsack::new(15);

    ks.set_weight_value(wv);

    println!("{:?}", ks.debugger_of_2());

    ks.find_best_solution_of_2_commented();

    println!("\n\n");

    let ans = ks.knapsack();

    let mut comp = Vec::new();
    for i in 0..wvc.clone().len() {
        if ans.1[i] {
            comp.push(wvc.clone()[i])
        }
    }

    println!(
        "Best pair:  (total value: {:?}) components: {:?}",
        ans.0, comp
    );
}
