import {
  FormControl,
  OutlinedInput,
  IconButton,
  InputAdornment,
  Typography,
  Avatar,
  Dialog,
  DialogTitle,
  DialogContent,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  DialogActions,
  Button,
  Divider,
} from "@mui/material";
import React, { useState, useCallback, useEffect } from "react";
import SendIcon from "@mui/icons-material/Send";
import axios from "axios";
import { useStateContext } from "../context/StateProvider";
import { useSocket } from "../context/SocketProvider";
import { useMediaQuery } from "react-responsive";
import SentimentSatisfiedAltIcon from "@mui/icons-material/SentimentSatisfiedAlt";
import ArrowBackIosRoundedIcon from "@mui/icons-material/ArrowBackIosRounded";

import data from "@emoji-mart/data";
import Picker from "@emoji-mart/react";
import { Box } from "@mui/system";

const SERVER_URL = `${process.env.REACT_APP_SERVER_URL}`;

export default function OpenConversation() {
  const {
    messages,
    setMessages,
    selectedConversation,
    setSelectedConversation,
    setConversations,
    localStorageUser,
  } = useStateContext();
  const isTabletOrMobile = useMediaQuery({ query: "(max-width: 500px)" });

  const [text, setText] = useState("");
  const [openMembersDialog, setOpenMembersDialog] = useState(false);
  const [showEmojiPicker, setShowEmojiPicker] = useState(false);
  const socket = useSocket();

  const setRef = useCallback((node) => {
    if (node) {
      node.scrollIntoView({ smooth: true });
    }
  }, []);

  const retrieveMessages = useCallback(
    (conversationId) => {
      if (conversationId === selectedConversation.id) {
        axios
          .get(`${SERVER_URL}/conversation/${conversationId}`)
          .then((res) => {
            setMessages(res.data.messages);
          })
          .catch((err) => console.log(err));
      } else {
        axios
          .get(
            `${SERVER_URL}/conversation/getAllConversations/${localStorageUser.id}`
          )
          .then((res) => {
            setConversations(res.data.conversations);
          })
          .catch((err) => console.log(err));
      }
    },
    [
      setConversations,
      setMessages,
      localStorageUser.id,
      selectedConversation.id,
    ]
  );

  useEffect(() => {
    if (socket == null) return;

    socket.on("receive-message", ({ conversationId }) => {
      retrieveMessages(conversationId);
    });
    return () => socket.off("receive-message");
  }, [socket, retrieveMessages]);

  const handleSubmit = (e) => {
    e.preventDefault();
    sendMessage(text);
    setText("");
  };

  const sendMessage = () => {
    axios
      .post(
        `${SERVER_URL}/conversation/addMessage/${selectedConversation.id}`,
        {
          authorId: localStorageUser.id,
          text,
        }
      )
      .then((res) => {
        if (res.status === 200) {
          setMessages(res.data.messages);
          emitMessage();
        }
      })
      .catch((err) => console.log(err));
  };

  const emitMessage = () => {
    var recipientsWithoutSender = selectedConversation.recipients.filter(
      (r) => r.id !== localStorageUser.id
    );
    socket.emit("send-message", {
      recipients: recipientsWithoutSender,
      conversationId: selectedConversation.id,
    });
  };

  const showMembers = () => {
    setOpenMembersDialog(true);
  };

  return (
    <>
      <div
        className={`open-conversation ${isTabletOrMobile ? "full-height" : ""}`}
      >
        <div
          className={`${
            isTabletOrMobile
              ? "conversation-header-mobile"
              : "conversation-header"
          }`}
        >
          <div style={{ display: isTabletOrMobile ? "flex" : "block" }}>
            <IconButton
              onClick={() => setSelectedConversation("")}
              style={{
                display: isTabletOrMobile ? "flex" : "none",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <ArrowBackIosRoundedIcon />
            </IconButton>
            {selectedConversation.recipients.length > 2 ? (
              <div style={{ display: "flex", alignItems: "center" }}>
                <Avatar
                  src={selectedConversation.conversationImage}
                  alt="recipient_image"
                  style={{ marginRight: "1rem" }}
                />
                <Typography>
                  <span
                    style={{ cursor: "pointer", fontWeight: "bolder" }}
                    onClick={() => showMembers()}
                  >
                    {selectedConversation.conversationName}
                  </span>
                </Typography>
              </div>
            ) : (
              <div style={{ display: "flex", alignItems: "center" }}>
                <Avatar
                  src={selectedConversation.recipients[0].image}
                  alt="recipient_image"
                  style={{ marginRight: "1rem" }}
                />
                <Typography>
                  <span>
                    Conversation with{" "}
                    {selectedConversation.recipients
                      .filter((r) => r.id !== localStorageUser.id)
                      .map((recipient) => (
                        <span key={recipient.id}>
                          {recipient.username} &nbsp;
                        </span>
                      ))}
                  </span>
                </Typography>
              </div>
            )}
          </div>
        </div>
        <div className="open-conversation-container">
          <div className="messages">
            {messages.map((m, index) => {
              const fromMe = m.authorId === localStorageUser.id;
              const lastMessage = messages.length - 1 === index;
              const author = selectedConversation.recipients.find(
                (el) => el.id === m.authorId
              ).username;
              return (
                <div
                  ref={lastMessage ? setRef : null}
                  key={index}
                  className={`single-message-container ${
                    fromMe ? "from-me" : "from-others"
                  } `}
                >
                  <p className={`${fromMe ? "send" : "receive"}`}>
                    <span>{m.text}</span>
                  </p>
                  <div
                    className={`sender ${
                      fromMe ? "sender-from-me" : "sender-from-others"
                    }`}
                  >
                    {fromMe ? (
                      <>
                        You
                        <span
                          className="timestamp"
                          style={{ marginLeft: "0.5rem" }}
                        >
                          {m.timestamp.substring(11, 16)}
                        </span>
                      </>
                    ) : (
                      <>
                        <span
                          className="timestamp"
                          style={{ marginRight: "0.5rem" }}
                        >
                          {" "}
                          {m.timestamp.substring(11, 16)}
                        </span>
                        {author}
                      </>
                    )}
                  </div>
                </div>
              );
            })}
          </div>
        </div>
        <form onSubmit={handleSubmit}>
          <div style={{ display: "flex" }} className="input-container">
            <Box
              sx={{
                display: showEmojiPicker ? "inline" : "none",
                zIndex: 10,
                position: "fixed",
                bottom: 65,
                left: 300,
              }}
            >
              <Picker style={{width: "50%", height: "50%"}} data={data} onEmojiSelect={(emoji) => setText( prev => prev + emoji.native) }/>
            </Box>
            <FormControl variant="outlined" fullWidth className="text-input">
              <OutlinedInput
                fullWidth
                type="text"
                value={text}
                onChange={(e) => setText(e.target.value)}
                startAdornment={
                  <InputAdornment position="start">
                    <IconButton
                      style={{ padding: "0" }}
                      onClick={() => setShowEmojiPicker((prev) => !prev)}
                    >
                      <SentimentSatisfiedAltIcon />
                    </IconButton>
                  </InputAdornment>
                }
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton type="submit" edge="end">
                      <SendIcon />
                    </IconButton>
                  </InputAdornment>
                }
              />
            </FormControl>
          </div>
        </form>
      </div>
      <Dialog
        fullWidth
        open={openMembersDialog}
        onClose={() => setOpenMembersDialog(false)}
      >
        <DialogTitle>
          {selectedConversation.conversationName} members:
        </DialogTitle>
        <DialogContent>
          <List>
            {selectedConversation.recipients.map((r) => (
              <div key={r.id}>
                <ListItem>
                  <ListItemIcon>
                    <Avatar src={r.image} alt="member_image" />
                  </ListItemIcon>
                  <ListItemText>{r.username}</ListItemText>
                </ListItem>
                <Divider />
              </div>
            ))}
          </List>
          <DialogActions>
            <Button
              variant="contained"
              onClick={() => setOpenMembersDialog(false)}
            >
              Close
            </Button>
          </DialogActions>
        </DialogContent>
      </Dialog>
    </>
  );
}
