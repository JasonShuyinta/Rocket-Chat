import React from "react";
import {
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Box,
  Divider,
  Avatar,
} from "@mui/material";
import { useStateContext } from "../context/StateProvider";

export default function Conversation() {
  const { conversations } = useStateContext();

  return (
    <Box className="conversation">
      <List>
        {conversations.map((conversation, index) => {
          if (conversation.recipients.length > 2)
            return <GroupChat conversation={conversation} key={conversation.id}/>;
          else return <SingleConversation conversation={conversation} key={conversation.id}/>;
        })}
      </List>
    </Box>
  );
}

function GroupChat({ conversation }) {
  const { setSelectedConversation, setMessages } = useStateContext();

  const handleSelectConversation = () => {
    setSelectedConversation(conversation);
    setMessages(conversation.messages);
  };

  return (
    <>
      <ListItem disablePadding key={conversation.id}>
        <ListItemButton
          onClick={() => {
            handleSelectConversation();
          }}
        >
          <Avatar
            src={conversation.conversationImage}
            alt="contact_img"
            style={{ marginRight: "1rem" }}
          />
          <ListItemText>{conversation.conversationName}</ListItemText>
        </ListItemButton>
      </ListItem>
      <Divider />
    </>
  );
}

function SingleConversation({ conversation }) {
  const { setSelectedConversation, setMessages, localStorageUser } =
    useStateContext();

  const handleSelectConversation = () => {
    setSelectedConversation(conversation);
    setMessages(conversation.messages);
  };

  return (
    <>
      {conversation.recipients
        .filter((recipient) => recipient.id !== localStorageUser.id)
        .map((r) => (
          <ListItem disablePadding key={r.id}>
            <ListItemButton
              onClick={() => {
                handleSelectConversation();
              }}
            >
              <Avatar
                src={r.image}
                alt="contact_img"
                style={{ marginRight: "1rem" }}
              />
              <ListItemText>{r.username}</ListItemText>
            </ListItemButton>
          </ListItem>
        ))}
      <Divider />
    </>
  );
}
