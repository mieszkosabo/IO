const http = require('http');
const mysql = require('mysql');


const fs = require("fs");
const textByLine = fs.readFileSync('login.txt').toString().split("\n");
const usr = textByLine[0];
const pw = textByLine[1];

const handleUnitsLookup = (res) => {

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

  const lookupUnitsInDatabase = () => {
    const CategoryLookupQuery = `SELECT unit FROM UNIT;`;

    // pierwszy arg to kwerenda, drugi to callback
    // czyli funkcja wywoływana po zfeczowaniu danych
    con.query(CategoryLookupQuery, (err, rows) => {
      if (err) {
        console.log("query error");
      } else {
        res.writeHead(200, {'Content-Type': 'application/json'});
        let Categories = []
        for (let i = 0; i < rows.length; ++i) {
          Categories[i] = rows[i].unit;
        }
        res.write(JSON.stringify(Categories));
        res.end();
      }
    });
  };

  lookupUnitsInDatabase();

  con.end((err) => {
    // The connection is terminated gracefully
    // Ensures all remaining queries are executed
    // Then sends a quit packet to the MySQL server.
  });
}
const handleIngredientsLookup = (res) => {

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

  const lookupIngredientsInDatabase = () => {
    const CategoryLookupQuery = `SELECT rawProduct FROM RAW_PRODUCT;`;

    // pierwszy arg to kwerenda, drugi to callback
    // czyli funkcja wywoływana po zfeczowaniu danych
    con.query(CategoryLookupQuery, (err, rows) => {
      if (err) {
        console.log("query error");
      } else {
        res.writeHead(200, {'Content-Type': 'application/json'});
        let Categories = []
        for (let i = 0; i < rows.length; ++i) {
          Categories[i] = rows[i].rawProduct;
        }
        res.write(JSON.stringify(Categories));
        res.end();
      }
    });
  };

  lookupIngredientsInDatabase();

  con.end((err) => {
    // The connection is terminated gracefully
    // Ensures all remaining queries are executed
    // Then sends a quit packet to the MySQL server.
  });
}
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

  lookupCategoryInDatabase();

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

const handleRecipeLookup = (res, id) => {

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

  const lookupRecipeinDatabase = (id) => {
    const RecipelookupQuery = `SELECT * FROM RECIPE WHERE idRecipe = ${id}`;

    // pierwszy arg to kwerenda, drugi to callback
    // czyli funkcja wywoływana po zfeczowaniu danych
    con.query(RecipelookupQuery, (err, rows) => {
      if (err) {
        console.log("query error");
      } else {
        console.log(rows[0]);
        res.writeHead(200, {'Content-Type': 'application/json'});
        res.end(JSON.stringify(rows[0]));
      }
    });
  };
  
  lookupRecipeinDatabase(id);

  con.end((err) => {
    // The connection is terminated gracefully
    // Ensures all remaining queries are executed
    // Then sends a quit packet to the MySQL server.
  });
}

const handleRecipeProductsLookup = (res, id) => {

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

  const lookupRecipeProductsinDatabase = (id) => {
    const RecipelookupQuery = `SELECT rawProduct, amount, unit FROM UNIT NATURAL JOIN RAW_PRODUCT_USE NATURAL JOIN RAW_PRODUCT WHERE idRecipe = ${id}`;

    // pierwszy arg to kwerenda, drugi to callback
    // czyli funkcja wywoływana po zfeczowaniu danych
    con.query(RecipelookupQuery, (err, rows) => {
      if (err) {
        console.log("query error");
      } else {
        console.log(rows[0]);
        res.writeHead(200, {'Content-Type': 'application/json'});
        res.end(JSON.stringify(rows));
      }
    });
  };
  
  lookupRecipeProductsinDatabase(id);

  con.end((err) => {
    // The connection is terminated gracefully
    // Ensures all remaining queries are executed
    // Then sends a quit packet to the MySQL server.
  });
}

const handleRecipeCategoryLookup = (res, id) => {

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

  const lookupRecipeCategoryinDatabase = (id) => {
    const RecipelookupQuery = `SELECT category FROM CATEGORY NATURAL JOIN RECIPE_CATEGORY NATURAL JOIN RECIPE WHERE idRecipe = ${id}`;

    // pierwszy arg to kwerenda, drugi to callback
    // czyli funkcja wywoływana po zfeczowaniu danych
    con.query(RecipelookupQuery, (err, rows) => {
      if (err) {
        console.log("query error");
      } else {
        console.log(rows[0]);
        res.writeHead(200, {'Content-Type': 'application/json'});
        res.end(JSON.stringify(rows));
      }
    });
  };
  
  lookupRecipeCategoryinDatabase(id);

  con.end((err) => {
    // The connection is terminated gracefully
    // Ensures all remaining queries are executed
    // Then sends a quit packet to the MySQL server.
  });
}

const handleRecipeContentLookup = (res, id) => {

  // Ustalamy połączenie z mysql
  const con = mysql.createConnection({
    host: "localhost",
    user: usr,
    password: pw,
    database: "projectdb"
  });

  const text = fs.readFileSync('recipes/1.txt').toString()
  // łączymy się z bazą
  con.connect((err) => {
    if (err) {
      console.log("DB ERROR!");
    } else {
      console.log("connection established");
    }
  });

  res.end(text);

  con.end((err) => {
    // The connection is terminated gracefully
    // Ensures all remaining queries are executed
    // Then sends a quit packet to the MySQL server.
  });
}



const requestListener = (req, res) => {
  if (req.method === 'POST') {
    console.log("post")
    res.end();
  }
  else if (req.url.startsWith("/lookupEAN=")) {
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
  } else if (req.url.startsWith("/Ingredients")) {
    handleIngredientsLookup(res);
  } else if (req.url.startsWith("/Units")) {
    handleUnitsLookup(res);
  } else if (req.url.startsWith("/Recipes")) {
    handleRecipeLookup(res, 1);
  } else if (req.url.startsWith("/Recipes/Products")) {
    handleRecipeProductsLookup(res, 1);
  } else if (req.url.startsWith("/Recipes/Category")) {
    handleRecipeCategoryLookup(res, 1);
  } else if (req.url.startsWith("/Recipes/Content")) {
    handleRecipeContentLookup(res, 1);
  }
  else {
    res.end('Error, Not Found\n');
  } 
}

const server = http.createServer(requestListener);
server.listen(8080);