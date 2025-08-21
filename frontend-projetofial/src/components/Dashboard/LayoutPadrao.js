// src/components/LayoutPadrao.jsx
import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Dashboard.css'; // Reaproveita o estilo do Dashboard

const LayoutPadrao = ({ children }) => {
  const location = useLocation();
  const isActive = (path) => location.pathname === path;

  return (
    <div className="dashboard-container">
      <aside className="sidebar">
        <div className="sidebar-header">
          <h2 className="sidebar-title">Painel Admin</h2>
        </div>

        <nav className="sidebar-nav">
          <Link to="/" className={`sidebar-link ${isActive('/') ? 'active' : ''}`}>
            📊 Dashboard
          </Link>
          <Link to="/cadastro-premio" className={`sidebar-link ${isActive('/cadastro-premio') ? 'active' : ''}`}>
            🏆 Cadastrar Prêmio
          </Link>
          <Link to="/cadastro-autor" className={`sidebar-link ${isActive('/cadastro-autor') ? 'active' : ''}`}>
            ✍️ Cadastrar Autor
          </Link>
          <Link to="/cadastro-avaliador" className={`sidebar-link ${isActive('/cadastro-avaliador') ? 'active' : ''}`}>
            👨‍⚖️ Cadastrar Avaliador
          </Link>
          <Link to="/envio-projeto" className={`sidebar-link ${isActive('/envio-projeto') ? 'active' : ''}`}>
            📤 Enviar Projeto
          </Link>
          <Link to="/avaliacao-projeto" className={`sidebar-link ${isActive('/avaliacao-projeto') ? 'active' : ''}`}>
            ⭐ Avaliar Projeto
          </Link>
          <Link to="/listagem-projetos" className={`sidebar-link ${isActive('/listagem-projetos') ? 'active' : ''}`}>
            📋 Listar Projetos
          </Link>
        
        </nav>

        <div className="sidebar-footer">
          <p className="footer-text">Sistema v1.0</p>
        </div>
      </aside>

      <main className="main-content">
        {children}
      </main>
    </div>
  );
};

export default LayoutPadrao;
