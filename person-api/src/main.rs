#[macro_use] extern crate rocket;

mod persons;

#[get("/list")]
fn list() -> String {
    persons::list()
}

#[get("/find.name?<name>")]
fn find_name(name: String) -> String {
    persons::find_name(name)
}

#[get("/find.key?<key>")]
fn find_key(key: String) -> String {
    persons::find_key(key)
}


#[launch]
fn rocket() -> _ {
    rocket::build().configure(rocket::Config::figment().merge(("port", 8060))).mount("/person", routes![list, find_name, find_key])
}