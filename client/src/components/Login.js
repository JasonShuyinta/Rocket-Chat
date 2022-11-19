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
  InputAdornment
} from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material"
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
  const [showPwd, setShowPwd] = useState(false);
  const [showSignUpPwd, setShowSignUpPwd] = useState(false);
  const [usernameAlreadyTakenError, setUsernameAlreadyTakenError] = useState(false);
  const [genericError, setGenericError] = useState(false);
  const [incorrectCredentials, setIncorrectCredentials] = useState(false);
  const [usernameDoesNotExist, setUsernameDoesNotExist] = useState(false);

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
      .catch((err) => {
        if(err.response.status === 403) {
          setIncorrectCredentials(true);
        } else if(err.response.status === 404) {
          setUsernameDoesNotExist(true);
        } else {
          setGenericError(true);
        }
      });
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
      .catch((err) => {
        if(err.response.status === 403) {
          setUsernameAlreadyTakenError(true);
        } else {
          setGenericError(true);
        }
      });
  };

  return (
    <Container className="login">
      <Grid container spacing={3}>
        <Grid item  xs={12} md={5} style={{ display: "flex", flexDirection: "column" }}>
          <TextField
            placeholder="Username"
            value={username}
            type="text"
            onChange={(e) => { setUsername(e.target.value); setIncorrectCredentials(false); setUsernameDoesNotExist(false); }}
            style={{ marginBottom: "1rem" }}
          />
          <TextField
            placeholder="Password"
            type={showPwd ? "text" : "password"}
            value={password}
            onChange={(e) => {setPassword(e.target.value); ; setIncorrectCredentials(false); setUsernameDoesNotExist(false); }}
            style={{ marginBottom: "1rem" }}
            InputProps={{
              endAdornment: (
                <InputAdornment
                  style={{ cursor: "pointer" }}
                  position="end"
                  onClick={() => setShowPwd(!showPwd)}
                >
                  {showPwd ? (
                    <VisibilityOff style={{ color: "gray" }} />
                  ) : (
                    <Visibility style={{ color: "gray" }} />
                  )}
                </InputAdornment>
              ),
            }}
          />
          {usernameDoesNotExist && <span style={{color: "red"}}>Username does not exist, please try another username.</span>}
          {incorrectCredentials && <span style={{color: "red"}}>Incorrect credentials. Please try again.</span>}
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
            onChange={(e) => { setNewUsername(e.target.value); setUsernameAlreadyTakenError(false); setGenericError(false); }}
            fullWidth
            style={{ marginBottom: "1rem" }}
          />
          <TextField
            placeholder="Enter your password"
            value={newPassword}
            onChange={(e) => { setNewPassword(e.target.value); setUsernameAlreadyTakenError(false); setGenericError(false);  }}
            required
            fullWidth
            type={showSignUpPwd ? "text" : "password"}
            style={{ marginBottom: "1rem" }}
            InputProps={{
              endAdornment: (
                <InputAdornment
                  style={{ cursor: "pointer" }}
                  position="end"
                  onClick={() => setShowSignUpPwd(!showSignUpPwd)}
                >
                  {showSignUpPwd ? (
                    <VisibilityOff style={{ color: "gray" }} />
                  ) : (
                    <Visibility style={{ color: "gray" }} />
                  )}
                </InputAdornment>
              ),
            }}
          />
          <br></br>
          {usernameAlreadyTakenError && <span style={{color:"red"}}>Username already taken. Please choose another username</span>}
          {genericError && <span style={{color: "ref"}}>Something went wrong, please try again later.</span>}
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
