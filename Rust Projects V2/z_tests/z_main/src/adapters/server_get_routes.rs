use reqwest::blocking::Client;

pub fn get_super_secret(_server_url: &str) -> &[u8] {
    b"some_super_secret_key"
}

pub fn priv_key(instance_name: &str, server_url: &str) -> String {
    let client = Client::new();

    let resp = client
        .get(format!("{server_url}/get_priv_key?"))
        .json(&serde_json::json!({"name": instance_name}))
        .send()
        .unwrap();

    if !resp.status().is_success() {
        panic!("get priv key failed: {:?}", resp.text());
    }

    resp.text().unwrap()
}
