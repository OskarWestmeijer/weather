import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navigation from './components/Navigation';
import Footer from './components/Footer';
import Weather from './components/Weather';
import Documentation from './components/Documentation';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <BrowserRouter basename="/weather/ui">
      <Navigation />
      <Routes>
        <Route path="/" element={<Weather header="Current weather data for Luebeck, Germany"/>} />
        <Route path="/documentation" element={<Documentation />} />
      </Routes>
      <Footer />
    </BrowserRouter>
);
