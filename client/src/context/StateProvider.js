import axios from "axios";
import React, { useContext, useState, useEffect } from "react";
import useLocalStorage from "../hooks/useLocalStorage";

const SERVER_URL = `${process.env.REACT_APP_SERVER_URL}`;

const StateContext = React.createContext();

export function useStateContext() {
  return useContext(StateContext);
}

export default function StateProvider({ children }) {
  const [user, setUser] = useState("");
  const [localStorageUser, setLocalStorageUser] = useLocalStorage("-user", "");
  const [contacts, setContacts] = useState([]);
  const [conversations, setConversations] = useState([]);
  const [messages, setMessages] = useState([]);
  const [selectedConversation, setSelectedConversation] = useState("");

  useEffect(() => {
    if (localStorageUser) {
      axios
        .get(`${SERVER_URL}/contact/all/${localStorageUser.id}`)
        .then((res) => setContacts(res.data))
        .catch((err) => console.log(err));

      axios
        .get(
          `${SERVER_URL}/conversation/getAllConversations/${localStorageUser.id}`
        )
        .then((res) => {
          setConversations(res.data.conversations);
        })
        .catch((err) => console.log(err));
    }
  }, [localStorageUser]);

  return (
    <StateContext.Provider
      value={{
        user,
        setUser,
        localStorageUser,
        setLocalStorageUser,
        contacts,
        setContacts,
        conversations,
        setConversations,
        selectedConversation,
        setSelectedConversation,
        messages,
        setMessages,
      }}
    >
      {children}
    </StateContext.Provider>
  );
}
