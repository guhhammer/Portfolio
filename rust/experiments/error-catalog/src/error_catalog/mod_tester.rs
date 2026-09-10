use crate::error_catalog::application_error::ApplicationError;

use crate::error_catalog::database::{DB_CONN_ERROR, DB_QUERY_ERROR_STATIC};


/*
https://chatgpt.com/c/68ca12c9-69d4-8321-8858-eca693a267da

Dialog on static vs const and other stuff.
*/

pub fn run() {


    println!();
    println!("{:?}", DB_CONN_ERROR.clone().with_details("shit".to_string()));
    /*
        Compiling tester v0.1.0 (/home/gustavo/Desktop/rust-projects/tester)
        warning: taking a mutable reference to a `const` item
        --> src/main.rs:9:22
        |
        9  |     println!("{:?}", DB_CONN_ERROR.with_details("shit".to_string()));
        |                      ^^^^^^^^^^^^^
        |
        = note: each usage of a `const` item creates a new temporary
        = note: the mutable reference will refer to this temporary, not the original `const` item
        note: `const` item defined here
        --> src/errors/error.rs:59:1
        |
        59 | pub const DB_CONN_ERROR: ApplicationError<String, String> = ApplicationError {
        | ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        = note: `#[warn(const_item_mutation)]` on by default

        warning: `tester` (bin "tester") generated 1 warning
    */

    /*

    Should define all error details and extra if const
    else do pub static for change at runtime.

    + When to use which for errors

        If your errors are compile-time constants that never change (e.g. "DB001"), const is fine.

        If your errors are global values you want to take, clone, and modify at runtime → static is better.

        Since you said “defined errors that will change” → you don’t want const, because you’ll clone them and mutate details.

    */


    println!();
    println!("{:?}", DB_CONN_ERROR.clone().with_details("shit".to_string()));

    let err = ApplicationError {
        error_code: "DB001",
        error_info: "Database connection failed",
        error_details: Some("Host 127.0.0.1 timed out".to_string()),
        extra: Some("Retries: 3".to_string()),
    };


    println!();

    println!("{err}");      // Display
    println!("{err:?}");    // Debug

    println!();

    println!("{}", DB_QUERY_ERROR_STATIC.clone().with_extra("hey new shit"));      // Display
    println!("{DB_QUERY_ERROR_STATIC:?}");    // Debug


    println!();

    /*

    errors on what can and cant can both be avoid by .clone()... 
    thing is the time and memory it takes. 
    
    */

}

/*
=== Const vs Static, Cloning, and Usage Guidelines ===

1. Const vs Static (Rust errors)

- const:
  * No memory slot, compiler inlines the value everywhere.
  * If referenced in multiple places, may be duplicated.
  * Optimizer usually deduplicates, so binary bloat is negligible.

- static:
  * One canonical global memory slot, all uses point to it.
  * Ideal when you want a "catalog entry" you can clone.
  * Works best when you need a single shared definition.

Impact on app size:
- Both are nearly the same in practice.
- Difference is in bytes, not kilobytes/megabytes.

2. When to prefer const or static

Use const if:
- Error definitions are compile-time literals only.
- You do not care about having one unique instance.

Use static if:
- You want one canonical instance at runtime.
- You plan to clone predefined errors and then attach details.
- Fits well as a "catalog" of reusable errors.

3. Is clone() bad in this context?

- No. For this struct, Clone is cheap.
- &'static str copies are just pointer + length (no heap).
- Option<String> is None in predefined errors, so no allocations.
- Only allocates when you actually attach new details.

Compared to I/O, networking, or DB calls, the clone cost is irrelevant.
So cloning is clean, safe, and not a performance concern.


*/