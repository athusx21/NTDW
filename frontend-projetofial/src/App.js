import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Dashboard from './components/Dashboard/Dashboard';  // Corrigido o nome do componente
import CadastroPremio from './components/CadastroPremio/CadastroPremio';
import CadastroAutor from './components/CadastroAutor/CadastroAutor';
import CadastroAvaliador from './components/CadastroAvaliador/CadastroAvaliador';
import EnvioProjeto from './components/EnvioProjeto/EnvioProjeto';
import AvaliacaoProjeto from './components/AvaliacaoProjeto/AvaliacaoProjeto';
import ListagemProjetos from './components/ListagemProjetos/ListagemProjetos';
import './App.css';

function App() {
  return (
    <div className="app-container">
      <Router>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/cadastro-premio" element={<CadastroPremio />} />
          <Route path="/cadastro-autor" element={<CadastroAutor />} />
          <Route path="/cadastro-avaliador" element={<CadastroAvaliador />} />
          <Route path="/envio-projeto" element={<EnvioProjeto />} />
          <Route path="/avaliacao-projeto" element={<AvaliacaoProjeto />} />
          <Route path="/listagem-projetos" element={<ListagemProjetos />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
