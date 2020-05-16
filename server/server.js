const http = require('http');

const handleEANlookup = (res, EAN) => {
  // resJSON = lookupEANinDatabase(EAN);
  result = {
    name: "mleko",
    calories: "157"
  };
  res.writeHead(200, {'Content-Type': 'application/json'});
  res.end(JSON.stringify(result));
}

const requestListener = (req, res) => {
  if (req.url.startsWith("/lookupEAN=")) {
    const EAN = req.url.substr(req.url.indexOf('=') + 1, 12);
    console.log(EAN);
    if (EAN.length != 12) {
      console.log("ERROR: Invalid EAN");
      res.end('ERROR, Invalid EAN');
    }
    handleEANlookup(res, EAN);
  } else {
    res.end('Error, Not Found\n');
  } 
}
const server = http.createServer(requestListener);
server.listen(8080);