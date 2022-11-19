const { Server } = require("socket.io");
const http = require('http');

const server = http.createServer((req, res) => {
  const headers = {
    'Access-Control-Allow-Origin':'*',
    'Access-Control-Allow-Methods':'POST, GET, OPTIONS'
  }

  if (['GET', 'POST'].indexOf(req.method) > -1) {
    res.writeHead(200, headers);
    return;
  }

  res.writeHead(405, headers);
  res.end(`${req.method} is not allowed for the request.`);

}).listen(5000, () => {
  console.log("Server is listening at port 5000");
})

const io = new Server(server , {
  cors: {
    origin: ["http://localhost:3000", '0.0.0.0/0'] 
  },
});

io.on("connection", (socket) => {
  const id = socket.handshake.query.userId;
  socket.join(id);

  socket.on("send-message", ({ recipients, conversationId }) => {
    recipients.forEach((recipient) => {
      socket.to(recipient.id).emit("receive-message", { conversationId });
    });
  });
});
