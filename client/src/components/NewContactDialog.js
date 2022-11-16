import React, { useState } from "react";
import {
  Dialog,
  DialogTitle,
  TextField,
  Button,
  DialogActions,
} from "@mui/material";
import axios from "axios";
import { useStateContext } from "../context/StateProvider";

const SERVER_URL = `${process.env.REACT_APP_SERVER_URL}`;

export default function NewContactDialog({
  modalOpenContact,
  closeModalContact,
  user,
}) {
  const { setContacts } = useStateContext();
  const [contactUsername, setContactUsername] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post(`${SERVER_URL}/contact/addContact/${user.id}/${contactUsername}`)
      .then((res) => {
        setContacts((prevContacts) => {
          return [...prevContacts, res.data];
        });
        setContactUsername("");
        closeModalContact();
      })
      .catch((err) => console.log(err));
  };

  return (
    <Dialog fullWidth open={modalOpenContact} onClose={closeModalContact}>
      <div style={{ padding: "1rem" }}>
        <DialogTitle>Search contact</DialogTitle>
        <div>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: "1rem" }}>
              <TextField
                value={contactUsername}
                onChange={(e) => setContactUsername(e.target.value)}
                required
                placeholder="Username"
                fullWidth
              />
            </div>
            <DialogActions>
              <Button variant="contained" type="submit">
                Search
              </Button>
            </DialogActions>
          </form>
        </div>
      </div>
    </Dialog>
  );
}
