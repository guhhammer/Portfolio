use reqwest::blocking::Client;

pub fn register(instance_name: &str, server_url: &str) {
    let client = Client::new();

    let resp = client
        .post(format!("{server_url}/register"))
        .json(&serde_json::json!({"name": instance_name}))
        .send()
        .unwrap();

    if !resp.status().is_success() {
        panic!("Register failed: {:?}", resp.text());
    }
}
