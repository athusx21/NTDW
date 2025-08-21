// src/context/AppContext.js
import React, { createContext, useState } from 'react';

export const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const [premios, setPremios] = useState([]);
  const [autores, setAutores] = useState([]);
  const [avaliadores, setAvaliadores] = useState([]);
  const [projetos, setProjetos] = useState([]);

  return (
    <AppContext.Provider value={{ premios, setPremios, autores, setAutores, avaliadores, setAvaliadores, projetos, setProjetos }}>
      {children}
    </AppContext.Provider>
  );
};