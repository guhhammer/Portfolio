pub mod login;
pub mod r_delete_grid;
pub mod r_get_grid;
pub mod r_patch_grid;
pub mod r_post_grid;
pub mod r_simulations;

pub mod fumble;
pub mod hello;
pub mod tests;

pub fn mount_routes() -> Vec<rocket::Route> {
  routes![
    login::login,
    r_delete_grid::delete_grid,
    r_delete_grid::delete_grid_supplier,
    r_delete_grid::delete_grid_isles,
    r_delete_grid::delete_grid_price_controller,
    r_get_grid::get_grid_supplier,
    r_get_grid::get_grid_isles,
    r_get_grid::get_grid_price_controller,
    r_get_grid::get_grid_list,
    r_patch_grid::patch_one_price_controller_route,
    r_patch_grid::patch_many_price_controller_route,
    r_patch_grid::patch_grid_name_route,
    r_patch_grid::patch_one_supplier_route,
    r_patch_grid::patch_many_supplier_route,
    r_patch_grid::patch_one_isles_route,
    r_post_grid::post_grid,
  ]
}

pub fn mount_simulation_routes() -> Vec<rocket::Route> {
  routes![
    r_simulations::fuel_catalog,
    r_simulations::gaspump_schema,
    r_simulations::grid_display,
    r_simulations::grid_schema,
    r_simulations::grid_supply,
    r_simulations::full_grid,
    r_simulations::grid_name,
    r_simulations::incoming,
    r_simulations::price_maker,
    r_simulations::price_tracker,
  ]
}

pub fn mount_test_routes() -> Vec<rocket::Route> {
  routes![
    // REMOVE FUMBLE LATER FROM --RELEASE.
    fumble::profile,
    fumble::proxy_to_other,
    hello::hello,
    hello::help,
    tests::post_grid,
  ]
}

/* DELETE COMMANDS:

# Delete entire grid
curl -kX DELETE -v "https://localhost:8080/api/grid/SimulationGrid"
# Response: {"action":"delete grid","status":"ok"}

# Delete supplier field only
curl -kX DELETE -v "https://localhost:8080/api/grid-supplier/SimulationGrid"
# Response: {"action":"delete grid supplier","status":"ok"}

# Delete isles field only
curl -kX DELETE -v "https://localhost:8080/api/grid-isles/SimulationGrid"
# Response: {"action":"delete grid isles","status":"ok"}

# Delete price_controller field only
curl -kX DELETE -v "https://localhost:8080/api/grid-price-controller/SimulationGrid"
# Response: {"action":"delete grid price controller","status":"ok"}

*/
/* PATCH COMMANDS:

# Patch a single fuel price in price_controller
curl -kX PATCH -v "https://localhost:8080/api/grid-price-controller/SimulationGrid/Gasoline70/6200"
# Response: {"property":"patch price_controller","value":"ok"}

# Patch multiple fuels in price_controller
curl -kX PATCH -v "https://localhost:8080/api/grid-price-controller/SimulationGrid" \
    -H "Content-Type: application/json" \
    -d '{
        "Gasoline70": 6200,
        "Gasoline95": 6800,
        "Diesel50": 5000
        }'
# Response: {"property":"patch many price_controller","value":"ok"}

# Patch grid name
curl -kX PATCH -v "https://localhost:8080/api/grid-name/SimulationGrid/NewSimulationGrid"
# Response: {"property":"patch grid name","value":"ok"}

# Patch a single supplier price
curl -kX PATCH -v "https://localhost:8080/api/grid-supplier/SimulationGrid/Ethanol70/4300"
# Response: {"property":"patch supplier","value":"ok"}

# Patch multiple supplier prices
curl -kX PATCH -v "https://localhost:8080/api/grid-supplier/SimulationGrid" \
    -H "Content-Type: application/json" \
    -d '{
        "Gasoline70": 6200,
        "Ethanol70": 4300,
        "Diesel50": 5000
        }'
# Response: {"property":"patch many supplier","value":"ok"}

# Patch isles
curl -kX PATCH -v "https://localhost:8080/api/grid-isles/SimulationGrid" \
    -H "Content-Type: application/json" \
    -d '[
        [
            {
            "number": 1,
            "triggers": [
                {"number":0,"liters_thousandth":0,"price_thousandth":6100,"fuel_type":"Gasoline70","pump_status":"Active"},
                {"number":1,"liters_thousandth":0,"price_thousandth":6800,"fuel_type":"Gasoline95","pump_status":"Active"}
            ]
            },
            {
            "number": 2,
            "triggers": [
                {"number":0,"liters_thousandth":0,"price_thousandth":6200,"fuel_type":"Gasoline70","pump_status":"Active"},
                {"number":1,"liters_thousandth":0,"price_thousandth":5000,"fuel_type":"Diesel50","pump_status":"Active"}
            ]
            }
        ]
        ]'
# Response: {"property":"patch isles","value":"ok"}

*/
/* GET COMMANDS:

# Get supplier of a specific grid
curl -kX GET -v "https://localhost:8080/api/grid-supplier/SimulationGrid"
# Response: {"property":"grid-supplier","value":"{\"Gasoline70\":6200,\"Ethanol70\":4300,\"Diesel50\":5000}"}

# Get price_controller of a specific grid
curl -kX GET -v "https://localhost:8080/api/grid-price-controller/SimulationGrid"
# Response: {"property":"grid-price-controller","value":"{\"Gasoline70\":6200,\"Gasoline95\":6800,\"Diesel50\":5000}"}

# Get isles of a specific grid
curl -kX GET -v "https://localhost:8080/api/grid-isles/SimulationGrid"
# Response: {"property":"grid-isles","value":"[[{\"number\":1,\"triggers\":[{\"number\":0,\"liters_thousandth\":0,\"price_thousandth\":6100,\"fuel_type\":\"Gasoline70\",\"pump_status\":\"Active\"},{\"number\":1,\"liters_thousandth\":0,\"price_thousandth\":6800,\"fuel_type\":\"Gasoline95\",\"pump_status\":\"Active\"}]},{\"number\":2,\"triggers\":[{\"number\":0,\"liters_thousandth\":0,\"price_thousandth\":6200,\"fuel_type\":\"Gasoline70\",\"pump_status\":\"Active\"},{\"number\":1,\"liters_thousandth\":0,\"price_thousandth\":5000,\"fuel_type\":\"Diesel50\",\"pump_status\":\"Active\"}]}]]"}

# Get list of all grid names
curl -kX GET -v "https://localhost:8080/api/grid-name-list"
# Response: {"property":"grid-name-list","value":"[\"SimulationGrid\",\"AnotherGrid\"]"}

*/
/* POST COMMANDS:

    TO USE POST, JUST USE BASH SCRIPTS ALREADY CONFIGURED.

*/
