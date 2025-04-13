import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import "./index.css";

/**
 * Hlavní vstupní bod aplikace.
 * - Používá `ReactDOM.createRoot()` pro renderování v režimu React 18.
 * - Obaluje aplikaci do `React.StrictMode` pro lepší detekci problémů.
 * - `BrowserRouter` zajišťuje správu routování v aplikaci.
 */
ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </React.StrictMode>
);
