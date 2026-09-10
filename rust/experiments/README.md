# Experiments

Small crates written to learn one library or one language feature before using it in a real project. Each one runs with `cargo run` from its folder.

| Crate | What it tries out |
| --- | --- |
| [`hello-world/`](hello-world/) | The first Rust program. |
| [`rocket-hello/`](rocket-hello/) | Rocket basics: three routes, including an async handler that sleeps for `/delay/<seconds>`. |
| [`mongodb-connection/`](mongodb-connection/) | The MongoDB driver with Tokio: insert, find, update and delete a document. |
| [`argon2-login/`](argon2-login/) | Password hashing with Argon2 and a `users` collection with a unique index, the model later used by the gas-station API. |
| [`error-catalog/`](error-catalog/) | A catalogue of typed application errors (database, user, application) and a tester that prints how each renders. |
| [`language-tests/`](language-tests/) | Ownership and `&mut self` methods on a struct. |
| [`tauri-hello/`](tauri-hello/) | The minimal Tauri app (vanilla TypeScript front end calling a Rust command). |
