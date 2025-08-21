import React, { useEffect, useState } from "react";
import { api } from "../../services/api";
import { Link, useLocation } from "react-router-dom";
import './Dashboard.css';

const formatarDados = (dados) => {
  return dados.map(item => {
    const autoresFormatados = item.autores 
      ? item.autores.map(autor => typeof autor === 'object' ? autor.nome : autor).join(', ')
      : 'N/A';

    const avaliadoresFormatados = item.avaliadores
      ? item.avaliadores.map(avaliador => typeof avaliador === 'object' ? avaliador.nome : avaliador).join(', ')
      : 'N/A';

    const formatarData = (data) => data ? new Date(data).toLocaleDateString() : 'N/A';

    const cpfFormatado = item.cpf
      ? item.cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4')
      : 'N/A';

    const formatarObjetoAninhado = (obj) => {
      if (!obj) return 'N/A';
      if (typeof obj !== 'object') return obj;
      if (obj.nome || obj.titulo || obj.id) {
        return obj.nome || obj.titulo || `ID: ${obj.id}`;
      }
      return JSON.stringify(obj);
    };

    return {
      ...item,
      id: item.id || 'N/A',
      areaTematica: formatarObjetoAninhado(item.areaTematica),
      titulo: item.titulo || 'N/A',
      resumo: item.resumo || 'N/A',
      dataEnvio: formatarData(item.dataEnvio),
      autores: autoresFormatados,
      avaliadores: avaliadoresFormatados,
      premio: formatarObjetoAninhado(item.premio || item.tituloPremio),
      dataNascimento: formatarData(item.dataNascimento),
      cpf: cpfFormatado,
      parecer: item.parecer || 'N/A',
      nota: item.nota || 'N/A',
      dataAvaliacao: formatarData(item.dataAvaliacao),
      status: item.status || 'N/A'
    };
  });
};

const Dashboard = () => {
  const [resumo, setResumo] = useState({
    totalProjetos: 0,
    totalPremios: 0,
    totalAvaliadores: 0,
    totalAutores: 0,
    totalAvaliacoes: 0
  });
  const [detalhes, setDetalhes] = useState([]);
  const [tipoDetalhes, setTipoDetalhes] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const location = useLocation();

  useEffect(() => {
    const carregarResumo = async () => {
      try {
        setLoading(true);
        const data = await api.getResumo();
        setResumo(data);
      } catch (error) {
        setError("Erro ao carregar o resumo. Tente novamente mais tarde.");
        console.error("Erro ao carregar resumo:", error);
      } finally {
        setLoading(false);
      }
    };
    carregarResumo();
  }, []);

  const carregarDetalhes = async (tipo) => {
    try {
      setLoading(true);
      setTipoDetalhes(tipo);
      let response;
      switch(tipo) {
        case 'premios':
          response = await api.getPremios();
          break;
        case 'autores':
          response = await api.getAutores();
          break;
        case 'projetos':
          response = await api.getProjetos();
          break;
        case 'projetos-avaliados':
          response = await api.getProjetosAvaliados();
          break;
        case 'projetos-nao-avaliados':
          response = await api.getProjetosNaoAvaliados();
          break;
        case 'projetos-vencedores':
          response = await api.getProjetosVencedores();
          break;
        case 'avaliadores':
          response = await api.getAvaliadores();
          break;
        case 'avaliacoes':
          response = await api.getAvaliacoes();
          break;
        default:
          response = [];
      }
      const dadosFormatados = formatarDados(Array.isArray(response) ? response : [response]);
      setDetalhes(dadosFormatados);
    } catch (error) {
      setError(`Erro ao carregar detalhes de ${tipo}`);
      console.error("Erro ao carregar ${tipo}:", error);
    } finally {
      setLoading(false);
    }
  };

  const limparDetalhes = () => {
    setDetalhes([]);
    setTipoDetalhes(null);
  };

  const isActive = (path) => location.pathname === path;

  const renderizarTabela = () => {
    if (detalhes.length === 0) return <p className="no-data">Nenhum dado encontrado</p>;

    const colunasPorTipo = {
      premios: ['titulo', 'descricao', 'dataInicio', 'dataFim'],
      autores: ['nome', 'email', 'dataNascimento', 'cpf', 'afiliacao'],
      projetos: ['titulo', 'autores', 'areaTematica', 'dataEnvio', 'status'],
      'projetos-avaliados': ['titulo', 'nota', 'parecer', 'avaliadores', 'dataAvaliacao'],
      'projetos-nao-avaliados': ['titulo', 'autores', 'areaTematica', 'dataEnvio'],
      'projetos-vencedores': ['titulo', 'nota', 'parecer', 'avaliadores', 'status'],
      avaliadores: ['nome', 'email', 'especialidade', 'afiliacao'],
      avaliacoes: ['titulo', 'avaliadores', 'nota', 'parecer', 'dataAvaliacao', 'status']
    };

    const colunas = colunasPorTipo[tipoDetalhes] || Object.keys(detalhes[0]);

    return (
      <table className="details-table">
        <thead>
          <tr>
            {colunas.map(coluna => (
              <th key={coluna}>{coluna.charAt(0).toUpperCase() + coluna.slice(1)}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {detalhes.map((item, index) => (
            <tr key={index}>
              {colunas.map(coluna => (
                <td key={`${index}-${coluna}`}>
                  {typeof item[coluna] === 'object' ? JSON.stringify(item[coluna]) : (item[coluna] || 'N/A')}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    );
  };

  return (
    <div className="dashboard-container">
      <aside className="sidebar">
        <div className="sidebar-header">
          <h2 className="sidebar-title">Painel Admin</h2>
        </div>

        <nav className="sidebar-nav">
          <Link to="/" className={`sidebar-link ${isActive('/') ? 'active' : ''}`}>📊 Dashboard</Link>
          <Link to="/cadastro-premio" className={`sidebar-link ${isActive('/cadastro-premio') ? 'active' : ''}`}>🏆 Cadastrar Prêmio</Link>
          <Link to="/cadastro-autor" className={`sidebar-link ${isActive('/cadastro-autor') ? 'active' : ''}`}>✍️ Cadastrar Autor</Link>
          <Link to="/cadastro-avaliador" className={`sidebar-link ${isActive('/cadastro-avaliador') ? 'active' : ''}`}>👨‍⚖️ Cadastrar Avaliador</Link>
          <Link to="/envio-projeto" className={`sidebar-link ${isActive('/envio-projeto') ? 'active' : ''}`}>📤 Enviar Projeto</Link>
          <Link to="/avaliacao-projeto" className={`sidebar-link ${isActive('/avaliacao-projeto') ? 'active' : ''}`}>⭐ Avaliar Projeto</Link>
          <Link to="/listagem-projetos" className={`sidebar-link ${isActive('/listagem-projetos') ? 'active' : ''}`}>📋 Listar Projetos</Link>
        
        </nav>

        <div className="sidebar-footer">
          <p className="footer-text">Sistema de Gerenciamento v1.0</p>
        </div>
      </aside>

      <main className="main-content">
        <div className="content-header">
          <h1 className="main-title">📊 Dashboard</h1>
        </div>

        <div className="content-area">
          {error ? (
            <div className="error-message">
              <p>{error}</p>
              <button onClick={() => window.location.reload()}>Tentar novamente</button>
            </div>
          ) : loading ? (
            <div className="loading-container">
              <div className="loading-spinner"></div>
              <p>Carregando dados...</p>
            </div>
          ) : tipoDetalhes ? (
            <div className="details-view">
              <div className="details-header">
                <h2>Detalhes de {tipoDetalhes}</h2>
                <button onClick={limparDetalhes}>← Voltar</button>
              </div>
              {renderizarTabela()}
            </div>
          ) : (
            <div className="cards-container">
              <div className="stat-card" onClick={() => carregarDetalhes('premios')}>🏆 Prêmios: {resumo.totalPremios}</div>
              <div className="stat-card" onClick={() => carregarDetalhes('autores')}>✍️ Autores: {resumo.totalAutores}</div>
              <div className="stat-card" onClick={() => carregarDetalhes('projetos')}>📁 Projetos: {resumo.totalProjetos}</div>
              <div className="stat-card" onClick={() => carregarDetalhes('projetos-avaliados')}>✅ Projetos Avaliados</div>
              <div className="stat-card" onClick={() => carregarDetalhes('projetos-nao-avaliados')}>📂 Projetos Não Avaliados</div>
              <div className="stat-card" onClick={() => carregarDetalhes('projetos-vencedores')}>🏅 Vencedores</div>
              <div className="stat-card" onClick={() => carregarDetalhes('avaliadores')}>👨‍⚖️ Avaliadores: {resumo.totalAvaliadores}</div>
             
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default Dashboard;