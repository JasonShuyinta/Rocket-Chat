import React from "react";
import { SocketProvider } from "./context/SocketProvider";
import Dashboard from "./components/Dashboard";
import StateProvider from "./context/StateProvider";

function App() {

  const dashboard = (
    <StateProvider>
      <SocketProvider>
        <Dashboard />
      </SocketProvider>
    </StateProvider>
  );

  return <>{dashboard}</>;
}

export default App;
