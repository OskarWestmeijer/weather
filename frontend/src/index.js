import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navigation from './components/Navigation';
import Footer from './components/Footer';
import Dashboard from './components/dashboard/Dashboard';
import Documentation from './components/documentation/Documentation';
import Export from './components/export/Export';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <BrowserRouter>
      <Navigation />
      <Routes>
        <Route path="/" element={<Dashboard/>} />
        <Route path="/export" element={<Export/>} />
        <Route path="/documentation" element={<Documentation />} />
      </Routes>
      <Footer />
    </BrowserRouter>
);
