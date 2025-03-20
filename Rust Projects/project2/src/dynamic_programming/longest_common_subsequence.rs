#[allow(dead_code)]
fn subsequences_within(word: &str) -> usize {
    word.len() * (word.len() + 1) / 2
}

#[allow(dead_code)]
fn word_range(word: &str) -> Vec<usize> {
    (1..=word.len()).map(|x| x).collect()
}

#[allow(dead_code)]
fn subsequences_of(word: &str) -> Vec<&str> {
    let mut ss = Vec::new();

    for ss_len in word_range(word) {
        for j in 0..word.len() + 1 - ss_len {
            ss.push(&word[j..ss_len + j]);
        }
    }

    ss
}

#[allow(dead_code)]
fn rev_ss_vec(ss_vec: &mut Vec<&str>) -> () {
    ss_vec.reverse();
}

#[allow(dead_code)]
fn matcher(
    word: &str,
    ss_vec: Vec<&str>,
    get_all_lcss_at_level: bool,
    find_for_lcs_with: usize,
) -> Option<Vec<String>> {
    if find_for_lcs_with > word.len() {
        return None;
    }

    let mut all_lcss: Vec<String> = Vec::new();

    for w in ss_vec {
        if find_for_lcs_with != 0 && w.len() != find_for_lcs_with {
            continue;
        }

        if word.len() < w.len() {
            continue;
        }

        for i in 0..=word.len() - w.len() {
            if &word[i..i + w.len()] == w {
                if get_all_lcss_at_level {
                    all_lcss.push(w.to_string());
                    continue;
                } else {
                    let mut ans: Vec<String> = Vec::new();
                    ans.push(w.to_string());

                    return Some(ans);
                }
            }
        }
    }

    if get_all_lcss_at_level {
        let llcs: usize = if find_for_lcs_with == 0 {
            all_lcss[0].len()
        } else {
            find_for_lcs_with
        };

        return Some(all_lcss.into_iter().filter(|x| x.len() == llcs).collect());
    } else {
        if find_for_lcs_with != 0 {
            let mut rv: Vec<String> = Vec::new();

            if all_lcss.is_empty() {
                return Some(rv);
            } else {
                rv.push(all_lcss[0].clone());

                return Some(rv);
            }
        }
    }

    None
}

#[allow(dead_code)]
pub fn longest_common_subsequence(
    dna_sequence1: &str,
    dna_sequence2: &str,
    get_all_lcss_at_level: bool,
    find_for_lcs_with: usize,
) -> Option<Vec<String>> {
    if dna_sequence2.len() > 500 {
        return None;
    } // length of &str exceeds total ss_vec size limit.

    let mut ss_v = subsequences_of(&dna_sequence2);

    rev_ss_vec(&mut ss_v);

    matcher(
        &dna_sequence1,
        ss_v,
        get_all_lcss_at_level,
        find_for_lcs_with,
    )
}

#[allow(dead_code)]
pub fn lcs_subsequence(
    dna_sequence1: &str,
    dna_sequence2: &str,
    get_all_lcss_at_level: bool,
    find_for_lcs_with: usize,
) -> Option<Vec<String>> {
    longest_common_subsequence(
        dna_sequence1,
        dna_sequence2,
        get_all_lcss_at_level,
        find_for_lcs_with,
    )
}

#[allow(dead_code)]
pub fn lcs(
    dna_sequence1: &str,
    dna_sequence2: &str,
    get_all_lcss_at_level: bool,
    find_for_lcs_with: usize,
) -> Option<Vec<String>> {
    longest_common_subsequence(
        dna_sequence1,
        dna_sequence2,
        get_all_lcss_at_level,
        find_for_lcs_with,
    )
}

#[allow(dead_code)]
pub fn lcs_commented(dna_sequence1: &str, dna_sequence2: &str) -> () {
    let ss1 = lcs_subsequence(dna_sequence1, dna_sequence2, false, 0).unwrap();
    let ss2 = lcs_subsequence(dna_sequence1, dna_sequence2, true, 0).unwrap();

    let ss3 = lcs_subsequence(dna_sequence1, dna_sequence2, false, 7).unwrap();
    let ss4 = lcs_subsequence(dna_sequence1, dna_sequence2, true, 7).unwrap();

    print!(
        "| lcs_commented:\n|\n|\t dna_sequence1={:?}\n|\t dna_sequence2={:?} ",
        dna_sequence1, dna_sequence2
    );

    print!(
        "\n|\n|\t lcs_subsequence(dna_sequence1, dna_sequence2, false, 0).unwrap() ~ {:?}",
        ss1
    );
    print!("\n|\t [gets first subsequence with max size it finds.]");

    print!(
        "\n|\n|\t lcs_subsequence(dna_sequence1, dna_sequence2, true, 0).unwrap() ~ {:?}",
        ss2
    );
    print!("\n|\t [gets all subsequences with the max size it finds.]");

    print!(
        "\n|\n|\t lcs_subsequence(dna_sequence1, dna_sequence2, false, 7).unwrap() ~ {:?}",
        ss3
    );
    print!("\n|\t [gets first subsequence with size 7 it finds.]");

    print!(
        "\n|\n|\t lcs_subsequence(dna_sequence1, dna_sequence2, true, 7).unwrap() ~ {:?}",
        ss4
    );
    print!("\n|\t [gets all subsequences with size 7 it finds.]");

    println!("\n|\n");
}

#[allow(dead_code)]
fn phrase_example(show: bool) -> () {
    if show {
        let word = "i want  the world to win the world her feeel";

        println!(
            "{:?}\n\n\n{:?}\n{:?}\n{:?}",
            word.len(),
            subsequences_within(word),
            word_range(word),
            subsequences_of(word)
        );

        let mut ss_v = subsequences_of(word);
        rev_ss_vec(&mut ss_v);

        println!("{:?}\n", ss_v.contains(&" the world"));

        let word2 = "i'd like to drink  her feeel with you at the end of the world"; // lcs.len(): 10.

        println!("{:?}", matcher(word2, ss_v, true, 0));
    }
}

#[allow(dead_code)]
fn dna_20_example(show: bool) -> () {
    if show {
        let dna_pair = [
            ("ATCGGACTTGAGGCTACGTA", "GCTACGTAGGACTTGACCTA"), // LCS: "GGACTTGA"
            ("CTGATCAGCTAACCGTGGAC", "AACCGTGGACTGAAGTCCGT"), // LCS: "AACCGTGGAC"
            ("AGTCCGTAAGCGTCCATGCT", "TCGAGTCCGTAGATGCCATG"), // LCS: "AGTCCGTA"
            ("GCGTATGGCAGTACGTTGCA", "TACGTTGCAGCGTATGGCAA"), // LCS: "GCGTATGGCA"
            ("ACGTGACCTAGCGTAACTGA", "TAGCGTAACTGATCGTGAAC"), // LCS: "TAGCGTAACTGA"
        ];

        for p in dna_pair {
            lcs_commented(p.0, p.1);
        }
    }
}

#[allow(dead_code)]
fn debugger(dna_sequence1: &str, dna_sequence2: &str, debug_info: bool) -> () {
    if debug_info {
        print!("\n| Finding subsequences of dna_sequence2 in dna_sequence1: ");
        print!(
            "\n|\n|\t dna_sequence1: {:?}\n|\t dna_sequence2: {:?} ",
            dna_sequence1, dna_sequence2
        );
        print!(
            "\n|\n|\t -- subsequences_within(dna_sequence2) ~ {:?} ",
            subsequences_within(dna_sequence2)
        );

        let dnas2 = if dna_sequence2.len() == 0 {
            "[]".to_string()
        } else {
            format!("[1 .. {:?}]", dna_sequence2.len())
        };

        print!("\n|\t -- word_range(dna_sequence2) ~ {:?} \n|\n", dnas2);

        let s = subsequences_of(dna_sequence2);

        if s.len() > 10 {
            for index in (0..10).map(|x| if x < 5 { x } else { s.len() + 4 - x }) {
                let mut showi = s[index].to_string();

                if showi.len() > 10 {
                    showi = showi[0..5].to_string()
                        + &"..".to_string()
                        + &showi[showi.len() - 6..showi.len() - 1].to_string();
                }

                println!("|\t -- subsequence {:?}: {:?}", index, showi);
            }
        }
    }

    let ss1 = lcs_subsequence(dna_sequence1, dna_sequence2, false, 0).unwrap();

    print!(
        "\n|\n|\t -- lcs(dna_sequence1, dna_sequence2, false, 0).unwrap() ~ {:?}\n|\n",
        ss1
    );
}

#[allow(dead_code)]
pub fn main() -> () {
    phrase_example(false);

    dna_20_example(false);

    let dna1 = "ACGTTCGTAGCTAGCTAGCTGATCGTACGTAGCTACGTAGCTACGTAGCTACGTACGTAGCTAGCTACGTACGTACGTAGCTACGTAGCTAGCTACGTAGCTAGCTAGCTAGCTGATCGTAGCTAGCT";

    let dna2 = "TAGCTACGTAGCTAGCTAGCTAGCTGATCGTACGTACGTACGTAGCTAGCTACGTAGCTACGTACGTACGTACGTAGCTAGCTACGTAGCTAGCTAGCTACGTAGCTAGACTAGCTACGTAGCTAGCT";

    debugger(&dna1, &dna2, true);
}

/*



    abcdef

    a b c d e f  6

    ab bc cd ef 4

    abc bcd cde def 4

    abcd bcde cdef  3

    abcde bcdef  2

    abcdef  1]


    1 a -> 1

    2 ab -> a b ab -> 3

    3 abc -> a b c ab bc abc -> 6

    4 abcd -> a b c d ab bc cd abc bcd abcd -> 10

    5 abcde -> a b c d e ab bc cd de abc bcd cde abcd bcde abcde ->  15

    6 abcdef -> a b c d e f ab bc cd de ef



   0 1 3 6 10 15 21
  0 1 2 3 4  5  6

    "a" -> 0 + 1;
    "ab" -> 1 + 2;
    "abc" -> 1 + 2 + 3

    n * (n+1) / 2


    ///
    ///
    ///
    ///
    ///
    ///

    Pair 1:
    Common substring length: 6

    DNA1: ATCGGACTTGAGGCTACGTA
    DNA2: GCTACGTAGGACTTGACCTA
    Common substring: GACTTG

    Pair 2:
    Common substring length: 9

    DNA1: CTGATCAGCTAACCGTGGAC
    DNA2: AACCGTGGACTGAAGTCCGT
    Common substring: AACCGTGGA

    Pair 3:
    Common substring length: 8

    DNA1: AGTCCGTAAGCGTCCATGCT
    DNA2: TCGAGTCCGTAGATGCCATG
    Common substring: AGTCCGTA

    Pair 4:
    Common substring length: 11

    DNA1: GCGTATGGCAGTACGTTGCA
    DNA2: TACGTTGCAGCGTATGGCAA
    Common substring: CGTATGGCAGT

    Pair 5:
    Common substring length: 7

    DNA1: ACGTGACCTAGCGTAACTGA
    DNA2: TAGCGTAACTGATCGTGAAC
    Common substring: CGTAACT


*/
