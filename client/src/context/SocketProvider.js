import React, { useContext, useEffect, useState } from "react";
import io from "socket.io-client"
import { useStateContext } from "./StateProvider";
const SocketContext = React.createContext();

export function useSocket() {
  return useContext(SocketContext);
}

export function SocketProvider({  children }) {
  const { localStorageUser } = useStateContext()
  const [socket, setSocket] = useState();

  useEffect(() => {
    const newSocket = io("http://35.229.19.149:5000", {
      query: { userId: localStorageUser.id  },
    });
    setSocket(newSocket);

    return () => newSocket.close();
  }, [localStorageUser]);

  return (
    <SocketContext.Provider value={socket}>{children}</SocketContext.Provider>
  );
}
