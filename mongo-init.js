db.createUser({
  user: "retailer",
  pwd: "retailer",
  roles: [
    { role: "readWrite", db: "retailerDatabase" }
  ]
});