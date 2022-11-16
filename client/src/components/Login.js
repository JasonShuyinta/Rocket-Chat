import React, { useState } from "react";
import {
  Container,
  Button,
  TextField,
  Grid,
  Typography,
  IconButton,
  Avatar,
  Divider,
} from "@mui/material";
import axios from "axios";
import { useStateContext } from "../context/StateProvider";

const SERVER_URL = `${process.env.REACT_APP_SERVER_URL}`;

export default function Login() {
  const { setUser, setLocalStorageUser } = useStateContext();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [newUsername, setNewUsername] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [avatar, setAvatar] = useState("");
  const [image, setImage] = useState("");

  const handleAvatar = (file) => {
    setAvatar(URL.createObjectURL(file));
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
      setImage(reader.result);
    };
    reader.onerror = function (error) {
      console.log("Error: ", error);
    };
  };

  const login = () => {
    axios
      .post(`${SERVER_URL}/user/login`, { username, password })
      .then((res) => {
        setUser(res.data);
        setLocalStorageUser(res.data)
      })
      .catch((err) => console.log(err));
  };

  const create = () => {
    axios
      .post(`${SERVER_URL}/user`, {
        username: newUsername,
        password: newPassword,
        image,
      })
      .then((res) => {
        setUser(res.data);
        setLocalStorageUser(res.data)
      })
      .catch((err) => console.log(err));
  };

  return (
    <Container className="login">
      <Grid container spacing={3}>
        <Grid item  xs={12} md={5} style={{ display: "flex", flexDirection: "column" }}>
          <TextField
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            style={{ marginBottom: "1rem" }}
          />
          <TextField
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={{ marginBottom: "1rem" }}
          />
          <Button variant="contained" color="primary" onClick={() => login()}>
            Login
          </Button>
        </Grid>
        <Grid item xs={12} md={2} style={{ display: "grid", placeItems: "center" }}>
          <span>
            OR
          </span>
          <Divider orientation="vertical" />
        </Grid>
        <Grid item xs={12} md={5} style={{ display: "flex", flexDirection: "column" }}>
          <div style={{ marginBottom: "1rem", textAlign: "center" }}>
            <Typography>Add an avatar</Typography>
            <input
              accept="image/*"
              id="btn-image"
              type="file"
              style={{ display: "none" }}
              onChange={(e) => handleAvatar(e.target.files[0])}
            />
            <label htmlFor="btn-image">
              <IconButton component="span">
                <Avatar src={avatar} alt="avatar" />
              </IconButton>
            </label>
          </div>
          <TextField
            placeholder="Enter your username"
            required
            value={newUsername}
            onChange={(e) => setNewUsername(e.target.value)}
            fullWidth
            style={{ marginBottom: "1rem" }}
          />
          <TextField
            placeholder="Enter your password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
            fullWidth
            style={{ marginBottom: "1rem" }}
          />
          <Button
            variant="contained"
            color="secondary"
            onClick={() => create()}
          >
            Create a new user
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
}
