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
    handleEANlookup(res, req.url.substr(req.url.indexOf('='), 12));
  } else {
    res.end('Error, Not Found\n');
  } 
}
const server = http.createServer(requestListener);
server.listen(8080);