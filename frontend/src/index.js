import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navigation from './components/Navigation';
import Footer from './components/Footer';
import Weather from './components/Weather';
import Documentation from './components/Documentation';
import Export from './components/Export';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <BrowserRouter basename="/weather">
      <Navigation />
      <Routes>
        <Route path="/" element={<Weather/>} />
        <Route path="/export" element={<Export/>} />
        <Route path="/documentation" element={<Documentation />} />
      </Routes>
      <Footer />
    </BrowserRouter>
);
