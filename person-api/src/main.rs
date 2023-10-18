#[macro_use] extern crate rocket;

mod persons;
mod kafka;

#[get("/list")]
fn list() -> String {
    kafka::send_to_kafka("/person/list".to_owned());
    persons::list()
}

#[get("/find.name?<name>")]
fn find_name(name: String) -> String {
    kafka::send_to_kafka(format!("/person/find.name?name={}", name));
    persons::find_name(name)
}

#[get("/find.key?<key>")]
fn find_key(key: String) -> String {
    kafka::send_to_kafka(format!("/person/find.key?key={}", key));
    persons::find_key(key)
}


#[launch]
fn rocket() -> _ {
    rocket::build().configure(rocket::Config::figment().merge(("port", 8060))).mount("/person", routes![list, find_name, find_key])
}