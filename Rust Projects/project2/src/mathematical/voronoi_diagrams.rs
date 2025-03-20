#[allow(dead_code)]
#[derive(Debug, Clone, Copy)]
pub struct Point {
    x: f64,
    y: f64,
}

#[allow(dead_code)]
impl Point {
    #[allow(dead_code)]
    pub fn new(x: f64, y: f64) -> Self {
        Point { x, y }
    }
}

#[allow(dead_code)]
#[derive(Debug)]
pub struct Line {
    midpoint: Option<Point>,
    perp_slope: f64,
}

impl Line {
    #[allow(dead_code)]
    pub fn new(p1: Point, p2: Point) -> Self {
        let mp: Point = Point {
            x: (p1.x + p2.x) / 2.0,
            y: (p1.y + p2.y) / 2.0,
        };

        if p1.x == p2.x {
            return Line {
                midpoint: None,
                perp_slope: mp.x,
            };
        }

        Line {
            midpoint: Some(mp),
            perp_slope: -1.0 / ((p2.y - p1.y) / (p2.x - p1.x)),
        }
    }

    #[allow(dead_code)]
    pub fn display(&self) -> () {
        if let Some(mid) = &self.midpoint {
            println!(
                "Line: y - {:?} = {:?} x (x - {:?})",
                mid.y, self.perp_slope, mid.x
            );
        } else {
            println!("Vertical line at x = {:?}", self.perp_slope);
        }
    }
}

#[allow(dead_code)]
fn line_intersection(line1: &Line, line2: &Line) -> Option<Point> {
    match (&line1.midpoint, &line2.midpoint) {
        (Some(mp1), Some(mp2)) => {
            let (m1, c1) = (line1.perp_slope, mp1.y - line1.perp_slope * mp1.x);
            let (m2, c2) = (line2.perp_slope, mp2.y - line2.perp_slope * mp2.x);

            if m1 == m2 {
                return None;
            }

            let x = (c2 - c1) / (m1 - m2);
            let y = m1 * x + c1;

            Some(Point { x, y })
        }
        (None, Some(mp2)) => {
            let x = line1.perp_slope;
            let y = line2.perp_slope * x + (mp2.y - line2.perp_slope * mp2.x);

            Some(Point { x, y })
        }
        (Some(mp1), None) => {
            let x = line2.perp_slope;
            let y = line1.perp_slope * x + (mp1.y - line1.perp_slope * mp1.x);

            Some(Point { x, y })
        }
        (None, None) => None,
    }
}

#[allow(dead_code)]
fn get_lines(arr: Vec<Point>) -> Vec<Line> {
    let mut lines: Vec<Line> = Vec::new();

    if arr.len() <= 1 {
        return lines;
    }

    for i in 0..arr.len() {
        for j in i + 1..arr.len() {
            let l = Line::new(arr[i], arr[j]);

            if !l.midpoint.is_none() {
                lines.push(l);
            }
        }
    }

    lines
}

#[allow(dead_code)]
fn are_lines_concurrent(line1: &Line, line2: &Line, line3: &Line) -> Option<Point> {
    match line_intersection(line1, line2) {
        Some(intersection) => {
            if let Some(mid) = &line3.midpoint {
                let (m, c) = (line3.perp_slope, mid.y - line3.perp_slope * mid.x);
                if (m * intersection.x + c - intersection.y).abs() < f64::EPSILON {
                    return Some(intersection);
                }
            } else {
                if (line3.perp_slope - intersection.x).abs() < f64::EPSILON {
                    return Some(intersection);
                }
            }
            None
        }
        None => None,
    }
}

#[allow(dead_code)]
pub fn main() -> () {
    let a = Point { x: 2.0, y: 3.0 };
    let b = Point { x: 4.0, y: 7.0 };
    let c = Point { x: 5.0, y: 4.0 };
    let d = Point { x: 7.0, y: 2.0 };
    let e = Point { x: 1.0, y: 1.0 };

    let ls: Vec<Line> = get_lines(vec![a, b, c]);

    let ls2: Vec<Line> = get_lines(vec![a, d, e]);

    for l in &ls {
        l.display();
    }

    match line_intersection(&ls[0], &ls[1]) {
        Some(Point { x, y }) => println!("Intersection: ({}, {})", x, y),
        None => println!("The lines are parallel and do not intersect."),
    }

    match are_lines_concurrent(&ls[0], &ls[1], &ls[2]) {
        Some(Point { x, y }) => println!("Intersection: ({}, {})", x, y),
        None => println!("The lines do not intersect at a common point."),
    }

    match are_lines_concurrent(&ls2[0], &ls2[1], &ls2[2]) {
        Some(Point { x, y }) => println!("Intersection: ({}, {})", x, y),
        None => println!("The lines do not intersect at a common point."),
    }
}
