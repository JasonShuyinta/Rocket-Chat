import React from "react";
import { useStateContext } from "../context/StateProvider";
import Login from "./Login";
import OpenConversation from "./OpenConversation";
import Sidebar from "./Sidebar";
import { useMediaQuery } from "react-responsive";
import MobileSidebar from "./MobileSidebar";

export default function Dashboard() {
  const { localStorageUser, selectedConversation } = useStateContext();

  const isTabletOrMobile = useMediaQuery({ query: "(max-width: 500px)" });

  return (
    <>
      {localStorageUser ? (
        <>
          {isTabletOrMobile ? (
            <>
              {selectedConversation ? <OpenConversation /> : <MobileSidebar />}
            </>
          ) : (
            <div className="dashboard">
              <Sidebar />
              {selectedConversation && <OpenConversation />}
            </div>
          )}
        </>
      ) : (
        <Login />
      )}
    </>
  );
}
