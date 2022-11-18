const { Server } = require("socket.io");
const { createServer } = require("http");

const httpServer = createServer();
const io = new Server(httpServer , {
  cors: {
    origin: "http://localhost:3000",
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

httpServer.listen(5000, () => {
  console.log("Server is listening at port 5000!");
  return "Server is started";
});
