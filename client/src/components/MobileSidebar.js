import React, { useState } from "react";
import { Tab, Tabs, Button } from "@mui/material";
import Conversation from "./Conversation";
import Contact from "./Contact";
import NewConversationDialog from "./NewConversationDialog";
import NewContactDialog from "./NewContactDialog";
import { IoIosContact } from "react-icons/io";
import { BiConversation } from "react-icons/bi";
import { useStateContext } from "../context/StateProvider";

const CONVERSATIONS_KEY = "conversations";
const CONTACTS_KEY = "contacts";

export default function MobileSidebar() {
    const { localStorageUser } = useStateContext();
    const [activeKey, setActiveKey] = useState(0);
    const [modalOpenConversation, setModalOpenConversation] = useState(false);
    const [modalOpenContact, setModalOpenContact] = useState(false);
  
    const conversationOpen = activeKey === 0;
  
    const closeModalConversation = () => {
      setModalOpenConversation(false);
    };
  
    const closeModalContact = () => {
      setModalOpenContact(false);
    };
  
    function showTab() {
      switch (activeKey) {
        case 0:
          return <Conversation conversations={localStorageUser.conversations} />;
        case 1:
          return <Contact />;
        default:
          break;
      }
    }


  return (
    <div className="sidebar-mobile">
    <Tabs value={activeKey} variant="fullWidth">
      <Tab
        icon={<BiConversation size="25px" />}
        label={CONVERSATIONS_KEY}
        onClick={() => setActiveKey(0)}
      />
      <Tab
        icon={<IoIosContact size="25px" />}
        label={CONTACTS_KEY}
        onClick={() => setActiveKey(1)}
      />
    </Tabs>
    <div style={{ flexGrow: "1" }}>{showTab()}</div>
    <div className="id-container">
      Your username: <span style={{ color: "grey" }}>{localStorageUser.username}</span>
    </div>
    {conversationOpen ? (
      <Button
        onClick={() => setModalOpenConversation(true)}
        variant="contained"
        color="primary"
        fullWidth
      >
        New Conversation
      </Button>
    ) : (
      <Button
        onClick={() => setModalOpenContact(true)}
        variant="contained"
        color="primary"
        fullWidth
      >
        New Contact
      </Button>
    )}
    <NewConversationDialog
      modalOpenConversation={modalOpenConversation}
      closeModalConversation={closeModalConversation}
      user={localStorageUser}
    />
    <NewContactDialog
      modalOpenContact={modalOpenContact}
      closeModalContact={closeModalContact}
      user={localStorageUser}
    />
  </div>
  )
}
