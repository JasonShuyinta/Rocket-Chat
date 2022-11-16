const { Server } = require("socket.io");

const io = new Server(5000, {
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
