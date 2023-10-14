#[macro_use] extern crate rocket;

mod persons;

#[get("/list")]
fn list() -> String {
    persons::jsonfromvec()
}

#[get("/find.name?<name>")]
fn find_name(name: String) -> String {
    "Hello, world!".to_string()
}

#[get("/find.key?<key>")]
fn find_key(key: String) -> String {
    "Hello, world!".to_string()
}


#[launch]
fn rocket() -> _ {
    rocket::build().configure(rocket::Config::figment().merge(("port", 8070))).mount("/person", routes![list, find_name, find_key])
}