use crate::adapters::server_get_routes::get_super_secret;
use crate::adapters::server_get_routes::priv_key;
use crate::adapters::server_post_routes::register;
use crate::config::SERVER_URL;
use crate::core::instance::naming;
use crate::core::logger::log_info;

pub fn state_tracker_run(naming_ok: bool) {
    //  GET_THIS_KEY FROM SERVER EACH TIME TOO. ROUNDABOUT IT DAILY.
    if naming_ok {
        let secret_key = get_super_secret(SERVER_URL);
        {
            let mut i = crate::INSTANCE_NAME.write().unwrap();
            *i = naming(secret_key);
            log_info(&format!("#APPLICATION-ID: {i}"));
        }
    }

    // GET_PRIV_KEY FROM SERVER EACH TIME TOO. ROUNDABOUT IT DAILY.
    {
        let i = crate::INSTANCE_NAME.read().unwrap();

        register(&i, SERVER_URL);

        let mut p = crate::PRIV_KEY.write().unwrap();

        *p = priv_key(&i, SERVER_URL);
    };
}
