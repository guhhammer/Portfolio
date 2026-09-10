use mongodb::Collection;
use mongodb::bson::Document;

/// Rocket-managed application state: the MongoDB collections the routes use.
pub struct AppState {
    pub users_collection: Collection<Document>,
    pub donations_collection: Collection<Document>,
}
