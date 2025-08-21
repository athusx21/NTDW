// 📁 src/pages/ListagemProjetos.jsx (trecho do filtro)
import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';
import LayoutPadrao from "../Dashboard/LayoutPadrao";


const ListagemProjetos = () => {
  const [projetos, setProjetos] = useState([]);
  const [filtro, setFiltro] = useState('todos');

  useEffect(() => {
    api.getProjetos().then(setProjetos);
  }, []);

  const filtrarProjetos = () => {
    switch (filtro) {
      case 'avaliados': return projetos.filter(p => p.avaliacoes?.length);
      case 'nao-avaliados': return projetos.filter(p => !p.avaliacoes?.length);
      case 'vencedores': return [...projetos.filter(p => p.avaliacoes?.length)].sort((a, b) => b.mediaNota - a.mediaNota).slice(0, 3);
      default: return projetos;
    }
  };

  return (
    <LayoutPadrao>
            <div className="details-view">
            <div className="details-header">
                <h2>Projetos</h2>
                <div>
                <button onClick={() => setFiltro('todos')}>Todos</button>
                <button onClick={() => setFiltro('avaliados')}>Avaliados</button>
                <button onClick={() => setFiltro('nao-avaliados')}>Não avaliados</button>
                <button onClick={() => setFiltro('vencedores')}>Vencedores</button>
                </div>
            </div>
            <table className="details-table">
                <thead>
                <tr>
                    <th>Título</th>
                    <th>Autores</th>
                    <th>Área</th>
                    <th>Nota Média</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                {filtrarProjetos().map(p => (
                    <tr key={p.id}>
                    <td>{p.titulo}</td>
                    <td>{p.autores?.map(a => a.nome).join(', ')}</td>
                    <td>{p.areaTematica}</td>
                    <td>{p.mediaNota?.toFixed(2) || '-'}</td>
                    <td>{p.avaliacoes?.length ? 'Avaliado' : 'Pendente'}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            </div>
    </LayoutPadrao>        
  );
};

export default ListagemProjetos;