import React, { useContext, useEffect, useState } from "react";
import io from "socket.io-client"
import { useStateContext } from "./StateProvider";
const SocketContext = React.createContext();

const SOCKET_URL = `${process.env.REACT_APP_SERVER_URL}`

export function useSocket() {
  return useContext(SocketContext);
}

export function SocketProvider({  children }) {
  const { localStorageUser } = useStateContext()
  const [socket, setSocket] = useState();

  useEffect(() => {
    const newSocket = io(SOCKET_URL, { 
    query: { userId: localStorageUser.id  },
    });
    setSocket(newSocket);

    return () => newSocket.close();
  }, [localStorageUser]);

  return (
    <SocketContext.Provider value={socket}>{children}</SocketContext.Provider>
  );
}
