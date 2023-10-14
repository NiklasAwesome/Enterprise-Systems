use serde::{Serialize, Deserialize};

#[derive(Serialize, Deserialize, Debug)]
pub struct Person {
    key: String,
    name: String,
}


pub fn vectest() -> Vec<Person> {
    let mut vec: Vec<Person> = Vec::new();
    vec.push(Person {key: String::from("1"), name: String::from("kalle")});
    vec.push(Person {key: String::from("2"), name: String::from("kalle")});
    vec.push(Person {key: String::from("3"), name: String::from("kalle")});
    vec.push(Person {key: String::from("4"), name: String::from("kalle")});
    return vec
}

pub fn jsonfromvec() -> String {
    let j = serde_json::to_string(&vectest()).unwrap();

    return j
}


