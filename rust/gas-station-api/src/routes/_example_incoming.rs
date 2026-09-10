use crate::datastructures::appstate::Grids;
use crate::datastructures::message::Message;
use crate::guards::auth_user::AuthUser;
use crate::guards::validate::validate_user_and_grid_index;
use regex::Regex;
use rocket::State;
use rocket::http::Status;
use rocket::response::status;
use rocket::serde::json::Json;
use std::fs;
use std::fs::File;
use std::io::BufRead;
use std::io::BufReader;

fn latest_file_in_dir(dir: &str) -> Option<String> {
    let mut files: Vec<_> = fs::read_dir(dir)
        .ok()?
        .filter_map(|entry| entry.ok())
        .filter(|entry| entry.path().is_file())
        .collect();

    // Sort by timestamp in filename
    files.sort_by(|a, b| {
        let a_name = a.file_name().into_string().unwrap_or_default();
        let b_name = b.file_name().into_string().unwrap_or_default();

        // Extract timestamp: incoming_YYYYMMDD-HHMMSS.log
        let a_ts = a_name
            .trim_start_matches("incoming_")
            .trim_end_matches(".log");
        let b_ts = b_name
            .trim_start_matches("incoming_")
            .trim_end_matches(".log");

        a_ts.cmp(b_ts)
    });

    // Return the last (latest) file
    files
        .last()
        .map(|entry| entry.path().to_string_lossy().into_owned())
}

fn tail_file(file_path: &str, n: usize) -> Vec<String> {
    let file = File::open(file_path).unwrap();
    let reader = BufReader::new(file);
    let lines: Vec<String> = reader.lines().map(|l| l.unwrap()).collect();
    lines
        .into_iter()
        .rev()
        .filter(|l| !l.trim().is_empty())
        .map(|l| strip_ansi_codes(&l))
        .take(n)
        .collect::<Vec<_>>()
        .into_iter()
        .rev()
        .collect()
}

fn strip_ansi_codes(input: &str) -> String {
    // Matches escape sequences like \u001b[1;32m
    let re = Regex::new(r"\x1b\[[0-9;]*m").unwrap();
    re.replace_all(input, "").to_string()
}

#[get("/<user>/incoming/<grid_name>?<tail_index>&<grid_index>")]
pub async fn incoming(
    user: String,
    auth: AuthUser,
    grid_name: String,
    tail_index: Option<usize>,
    grid_index: Option<usize>,
    state: &State<Grids>,
) -> Result<Json<Message>, status::Custom<String>> {
    let pos = validate_user_and_grid_index(user, auth, grid_name, grid_index, state).await?;

    let tail = tail_index.unwrap_or(10);

    let _schema = &state.grid_state_pointer.get(pos).ok_or_else(|| {
        status::Custom(
            Status::NotFound,
            "Price tracker not found (Clue: Maybe wrong name).".to_string(),
        )
    })?;

    let last = latest_file_in_dir("./log/incoming-simulation/").ok_or(status::Custom(
        Status::NotFound,
        "No price entries found".to_string(),
    ))?;

    let tail_lines: String = tail_file(&last, tail).join("\n");

    Ok(Json(Message::new("incoming", &tail_lines)))
}
