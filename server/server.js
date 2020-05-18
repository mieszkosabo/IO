const http = require('http');
const mysql = require('mysql');


const fs = require("fs");
const textByLine = fs.readFileSync('login.txt').toString().split("\n");
const usr = textByLine[0];
const pw = textByLine[1];

const handleCategoryLookup = (res) => {
  
  // Ustalamy połączenie z mysql
  const con = mysql.createConnection({
    host: "localhost",
    user: usr,
    password: pw,
    database: "projectdb"
  });

  // łączymy się z bazą
  con.connect((err) => {
    if (err) {
      console.log("DB ERROR!");
    } else {
      console.log("connection established");
    }
  });

  const lookupCategoryInDatabase = () => {
    const CategoryLookupQuery = `SELECT category FROM CATEGORY;`;

    // pierwszy arg to kwerenda, drugi to callback
    // czyli funkcja wywoływana po zfeczowaniu danych
    con.query(CategoryLookupQuery, (err, rows) => {
      if (err) {
        console.log("query error");
      } else {
        res.writeHead(200, {'Content-Type': 'application/json'});
        let Categories = []
        for (let i = 0; i < rows.length; ++i) {
          Categories[i] = rows[i].category;
        }
        res.write(JSON.stringify(Categories));
        res.end();
      }
    });
  };

  lookupCategoryInDatabase()

  con.end((err) => {
    // The connection is terminated gracefully
    // Ensures all remaining queries are executed
    // Then sends a quit packet to the MySQL server.
  });
}

const handleEANlookup = (res, EAN) => {

  // Ustalamy połączenie z mysql
  const con = mysql.createConnection({
    host: "localhost",
    user: usr,
    password: pw,
    database: "projectdb"
  });

  // łączymy się z bazą
  con.connect((err) => {
    if (err) {
      console.log("DB ERROR!");
    } else {
      console.log("connection established");
    }
  });

  const lookupEANinDatabase = (ean) => {
    const EANlookupQuery = `SELECT * FROM PRODUCT NATURAL JOIN GOODNESS WHERE EAN = ${ean}`;

    // pierwszy arg to kwerenda, drugi to callback
    // czyli funkcja wywoływana po zfeczowaniu danych
    con.query(EANlookupQuery, (err, rows) => {
      if (err) {
        console.log("query error");
      } else {
        console.log(rows[0]);
        res.writeHead(200, {'Content-Type': 'application/json'});
        res.end(JSON.stringify(rows[0]));
      }
    });
  };
  
  lookupEANinDatabase(EAN);

  con.end((err) => {
    // The connection is terminated gracefully
    // Ensures all remaining queries are executed
    // Then sends a quit packet to the MySQL server.
  });
}

const requestListener = (req, res) => {
  if (req.url.startsWith("/lookupEAN=")) {
    const EAN = req.url.substr(req.url.indexOf('=') + 1, 13);
    console.log(EAN);
    console.log(EAN.length);
    if (EAN.length != 13) {
      console.log("ERROR: Invalid EAN");
      res.end('ERROR, Invalid EAN');
    }
    handleEANlookup(res, EAN);
  } else if (req.url.startsWith("/Category")) {
    handleCategoryLookup(res);
  } 
  else {
    res.end('Error, Not Found\n');
  } 
}

const server = http.createServer(requestListener);
server.listen(8080);