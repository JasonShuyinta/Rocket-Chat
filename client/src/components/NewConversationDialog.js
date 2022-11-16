import React, { useState } from "react";
import {
  Checkbox,
  Dialog,
  Button,
  DialogTitle,
  List,
  ListItemText,
  Avatar,
  ListItem,
  ListItemButton,
  ListItemIcon,
  Divider,
  TextField,
  DialogActions,
  IconButton,
} from "@mui/material";
import axios from "axios";
import { useStateContext } from "../context/StateProvider";

const SERVER_URL = `${process.env.REACT_APP_SERVER_URL}`;

export default function NewConversationDialog({
  modalOpenConversation,
  closeModalConversation,
  user,
}) {
  const { contacts, setConversations } = useStateContext();
  const [recipients, setRecipients] = useState([]);
  const [conversationName, setConversationName] = useState("");
  const [avatar, setAvatar] = useState("");
  const [conversationImage, setConversationImage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post(`${SERVER_URL}/conversation/create`, {
        admin: user,
        recipients,
        conversationName,
        conversationImage,
      })
      .then((res) => {
        setConversations((prevConversations) => [
          ...prevConversations,
          res.data,
        ]);
        closeModalConversation();
      })
      .catch((err) => console.log(err));
  };

  const handleCheckboxChange = (contactId) => {
    setRecipients((prevContacts) => {
      if (prevContacts.includes(contactId)) {
        return prevContacts.filter((prevId) => {
          return contactId !== prevId;
        });
      } else {
        return [...prevContacts, contactId];
      }
    });
  };

  const handleGroupImage = (file) => {
    setAvatar(URL.createObjectURL(file));
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
      setConversationImage(reader.result);
    };
    reader.onerror = function (error) {
      console.log("Error: ", error);
    };
  };

  return (
    <Dialog
      open={modalOpenConversation}
      onClose={closeModalConversation}
      fullWidth
    >
      <div style={{ padding: "2rem" }}>
        <DialogTitle>Create Conversation</DialogTitle>
        <form onSubmit={handleSubmit}>
          {recipients.length > 1 && (
            <div style={{ display: "flex" }}>
              <div style={{ marginBottom: "1rem", textAlign: "center" }}>
                <input
                  accept="image/*"
                  id="btn-image"
                  type="file"
                  style={{ display: "none" }}
                  onChange={(e) => handleGroupImage(e.target.files[0])}
                />
                <label htmlFor="btn-image">
                  <IconButton component="span">
                    <Avatar src={avatar} alt="avatar" />
                  </IconButton>
                </label>
              </div>
              <TextField
                placeholder="Group name"
                value={conversationName}
                onChange={(e) => setConversationName(e.target.value)}
                fullWidth
              />
            </div>
          )}
          <List>
            {contacts.map((contact) => (
              <div key={contact.id}>
                <ListItem disablePadding>
                  <ListItemButton
                    onClick={() => handleCheckboxChange(contact.id)}
                  >
                    <ListItemIcon>
                      <Checkbox />
                      <Avatar src={contact.image} alt="contact_image" />
                    </ListItemIcon>
                    <ListItemText style={{ marginLeft: "1rem" }}>
                      {contact.username}
                    </ListItemText>
                  </ListItemButton>
                </ListItem>
                <Divider />
              </div>
            ))}
          </List>
          <DialogActions>
            <Button type="submit" variant="contained">
              Create
            </Button>
          </DialogActions>
        </form>
      </div>
    </Dialog>
  );
}
