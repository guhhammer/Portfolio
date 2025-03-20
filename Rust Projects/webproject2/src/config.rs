
/// Database-related configuration settings.
pub mod db_config {

    /// The path to store and serve trending users' profile images.
    pub const DAILY_FOLDER_PATH: &str = "migrations/daily/";

    /// The name of the database.
    pub const DATABASE_NAME: &str = "webproject2DB";

    /// The URI for connecting to the database.
    pub const DATABASE_URI: &str = "mongodb://localhost:27017/webproject2DB";

    /// When set to true, the database will start empty.
    /// For production, set this to false.
    pub const DATABASE_START_EMPTY: bool = true;

    /// Loads last snapshot available, when system is down.
    pub const LOAD_FROM_LAST_SNAPSHOTS: bool = false;

    /// When set to true, the database will be populated with example data.
    /// For production, set this to false.
    pub const POPULATE_DB_EXAMPLE: bool = true;

    /// The path to the JSON file used for populating example users.
    pub const POPULATE_DB_USERS_PATH: &str = "migrations/examples/users_example.json";

    /// The path to the JSON file used for populating example donantions.
    pub const POPULATE_DB_DONATIONS_PATH: &str = "migrations/examples/donations_example.json";

    /// The path to the JSON file used for populating example donantions.
    pub const POPULATE_DB_TRENDING_PATH: &str = "migrations/examples/trending.json";

    /// Aggregate the names and order them with the path.
    pub const COLLECTION_NAMES: [&str; 3] = ["users", "donations", "trending"];
    pub const COLLECTION_PATHS: [&str; 3] = [POPULATE_DB_USERS_PATH, POPULATE_DB_DONATIONS_PATH, POPULATE_DB_TRENDING_PATH];
    
    /// The path to the snapshot JSON files containing the last program state.
    pub const SNAPSHOTS_FILE_PATH: &str = "migrations/snapshots/";
    
    /// The timer for snapshoting the database collection's state.
    pub const SNAPSHOT_TIMER_SECONDS: u64 = 60 * 20; // 20 minutes.

    /// The path to store and serve trending users' profile images.
    pub const TRENDS_FOLDER_PATH: &str = "static/images/trends";

    /// The timer for trend state refresh.
    pub const TREND_REFRESH_TIMER: u64 = 60 * 10; // 10 minutes.

}