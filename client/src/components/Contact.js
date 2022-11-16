import React from "react";
import {
  Avatar,
  Box,
  Divider,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
} from "@mui/material";
import { useStateContext } from "../context/StateProvider";

export default function Contact() {
  const { contacts } = useStateContext();

  return (
    <Box style={{ flexGrow: "1", overflow: "auto" }}>
      <List>
        {contacts.map((contact) => (
          <div key={contact.id}>
            <ListItem disablePadding>
              <ListItemButton>
                <Avatar
                  src={contact.image}
                  alt="contact_img"
                  style={{ marginRight: "1rem" }}
                />
                <ListItemText>{contact.username}</ListItemText>
              </ListItemButton>
            </ListItem>
            <Divider />
          </div>
        ))}
      </List>
    </Box>
  );
}
