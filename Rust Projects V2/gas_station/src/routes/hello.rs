use crate::datastructures::message::Message;
use chrono::Local;
use rocket::serde::json::Json;
use serde_json::json;

#[get("/")]
pub fn hello() -> Json<Message> {
  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Json(Message::new(
    "/",
    "Hi, this is the gas_station server rust project!",
    "success",
    Some(s),
  ))
}

#[get("/help")]
pub fn help() -> Json<Message> {
  let timestamp = Local::now().format("%Y%m%d-%H%M%S").to_string(); // <- convert to String
  let s = json!({ "timestamp": timestamp }).to_string();
  Json(Message::new("/help", HELP_TEXT, "success", Some(s)))
}

/*

┌──(gustavo㉿kali)-[~/Desktop/rust-projects]
└─$ curl -kX GET -v "https://localhost:8080/api/alice/incoming/Gassy?tail_index=15&grid_index=0" \
  -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsImV4cCI6MTc1Njk1OTQ5Nn0.JbhmgJk056DgsbAt9HZjssZ3xqiSjIwx20qu3vbTtMA"


NEW WAY TO CALL IT NOW

*/

const HELP_TEXT: &str = r#"
curl -kX GET https://localhost:8080/api/                          
{"property":"/","value":"..."}

curl -kX POST https://localhost:8080/api/login \
-H "Content-Type: application/json" \
-d '{"username":"alice","password":"secret123"}'
{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsImV4cCI6MTc1NjkzMzEyMH0.2mipRmOkvleDWfUZDy2wKQsN5PoFb3BJuX_-B-TcHNw"}   

<name>/profile
curl -kX GET https://localhost:8080/api/alice/profile \           
-H "Authorization: Bearer {{JWT TOKEN}}"
"Profile data for alice"                

curl -kX GET https://localhost:8080/api/ping  
"ping sent"           

<name>/test-db/<grid_name>
curl -kX GET https://localhost:8080/api/alice/test-db/Gassy \
-H "Authorization: Bearer {{JWT TOKEN}}"   
{"property":"test-db","value":"..."}

<name>/fuel-catalog/<grid_name>
curl -kX GET https://localhost:8080/api/alice/fuel-catalog/Gassy \
-H "Authorization: Bearer {{JWT TOKEN}}"
{"property":"fuel-catalog","value":"..."}

<name>/gaspump-schema/<grid_name>
curl -kX GET https://localhost:8080/api/alice/gaspump-schema/Gassy \
-H "Authorization: Bearer {{JWT TOKEN}}"
{"property":"gaspump-schema","value":"..."}

<name>/grid-display/<grid_name>
curl -kX GET https://localhost:8080/api/alice/grid-display/Gassy \  
-H "Authorization: Bearer {{JWT TOKEN}}"
{"property":"grid-display","value":"..."}

<name>/grid-schema/<grid_name>
curl -kX GET https://localhost:8080/api/alice/grid-schema/Gassy \ 
-H "Authorization: Bearer {{JWT TOKEN}}"
{"property":"grid-schema","value":"..."}

<name>/grid-supply/<grid_name>
curl -kX GET https://localhost:8080/api/alice/grid-supply/Gassy \
-H "Authorization: Bearer {{JWT TOKEN}}"
{"property":"grid-supply","value":"..."}

<name>/grid/<grid_name>
curl -kX GET https://localhost:8080/api/alice/grid/Gassy \       
-H "Authorization: Bearer {{JWT TOKEN}}"
{"property":"grid","value":"..."}

<name>/incoming/<grid_name>?tail=<number>
curl -kX GET https://localhost:8080/api/alice/incoming/Gassy?tail=10 \
-H "Authorization: Bearer {{JWT TOKEN}}"
{"property":"incoming","value":"..."}

<name>/price-maker/<grid_name>
curl -kX GET https://localhost:8080/api/alice/price-maker/Gassy \
-H "Authorization: Bearer {{JWT TOKEN}}"
{"property":"price-maker","value":"..."}

<name>/price-tracker/<grid_name>
curl -kX GET https://localhost:8080/api/alice/price-tracker/Gassy \      
-H "Authorization: Bearer {{JWT TOKEN}}"
{"property":"price-tracker","value":"..."}

"#;

//(-k = allow insecure/self-signed certs; if you later get a trusted cert, you won’t need this.)
// USE FLAG -k (CURL -kX ...) TO GET https site.
// paste those cli cmds comments in fumble.rs here.

/*
──(gustavo㉿kali)-[~/Desktop/rust-projects]
└─$ curl -kX POST https://127.0.0.1:9000/api/login \
    -H "Content-Type: application/json" \
    -d '{"username":"alice","password":"secret123"}'

    ┌──(gustavo㉿kali)-[~]
└─$ curl -kX GET https://127.0.0.1:9000/api/alice/profile \
    -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsImV4cCI6MTc1NjM0MDQ4OH0.nPXDZUTGNBRjeZBOVY2XZ6YmvDV5BN_Is-jZfKtpmjU"
*/
