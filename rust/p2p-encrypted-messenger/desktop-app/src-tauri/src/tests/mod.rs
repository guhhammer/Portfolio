pub mod security;

pub fn run(ok: bool) {
    if !ok {
        return;
    }

    let _ = crate::tests::security::run();
}
