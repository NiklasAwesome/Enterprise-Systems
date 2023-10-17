use serde::{Serialize, Deserialize};

#[derive(Serialize, Deserialize, Debug)]
pub struct Person {
    key: String,
    name: String,
}


pub fn person_list() -> Vec<Person> {
    let mut vec: Vec<Person> = Vec::new();
    vec.push(Person {key: String::from("1"), name: String::from("kalle1")});
    vec.push(Person {key: String::from("2"), name: String::from("kalle2")});
    vec.push(Person {key: String::from("3"), name: String::from("kalle3")});
    vec.push(Person {key: String::from("4"), name: String::from("kalle4")});
    vec.push(Person {key: String::from("5"), name: String::from("kalle5")});
    vec.push(Person {key: String::from("6"), name: String::from("kalle6")});
    vec.push(Person {key: String::from("7"), name: String::from("kalle7")});
    vec.push(Person {key: String::from("8"), name: String::from("kalle8")});
    vec.push(Person {key: String::from("9"), name: String::from("kalle9")});
    
    
    vec.push(Person {key: String::from("ahRtu7874Gdgd345Tlgd39TyurrG7"), name: String::from("Jakob Pogulis")});
    vec.push(Person {key: String::from("2657TyuhR57575GGhu7"), name: String::from("Xena")});
    vec.push(Person {key: String::from("3Wytrt5638HUTyPWert"), name: String::from("Marcus Bendtsen")});
    vec.push(Person {key: String::from("4786NmbVyt67d232hj"), name: String::from("Zorro")});
    vec.push(Person {key: String::from("5OOpluoRt5612Nhu"), name: String::from("Q")});
    



    return vec
}

pub fn list() -> String {
    let j = serde_json::to_string(&person_list()).unwrap();

    return j
}

pub fn find_name(name: String) -> String {
    let mut result_vec: Vec<Person> = Vec::new();
    let full_vec: Vec<Person> = person_list();
    for p in full_vec {
        if p.name.eq(&name) {
            result_vec.push(p);
        }
    }

    return serde_json::to_string(&result_vec).unwrap()
}

pub fn find_key(key: String) -> String {
    let mut result_person: Person = Person {key: String::from("0"), name: String::from("0")};
    let full_vec: Vec<Person> = person_list();
    for p in full_vec {
        if p.key.eq(&key) {
            result_person = p;
        }
    }
    if result_person.key.eq(&String::from("0")) {
        return String::from("null")
    }
    return serde_json::to_string(&result_person).unwrap()
}


