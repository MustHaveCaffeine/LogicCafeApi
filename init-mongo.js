db.createUser({
    user: "application",
    pwd: "topsecret",
    roles: [
        {
            role: "readWrite",
            db: "appdata"
        }
    ]
});

db.createCollection("users");
db.users.createIndex( { userName: -1 } );
db.users.insert({
    firstName: "Ajay",
    lastName: "Saini",
    username: "sainiajay",
    emailAddress: "ajaysaini@gmail.com"
});