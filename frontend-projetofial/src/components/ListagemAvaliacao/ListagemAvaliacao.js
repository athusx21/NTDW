// 📁 src/pages/ListagemAvaliacoes.jsx
import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';
import LayoutPadrao from "../Dashboard/LayoutPadrao";

const ListagemAvaliacoes = () => {
  const [avaliacoes, setAvaliacoes] = useState([]);

  useEffect(() => {
    api.getAvaliacoes().then(setAvaliacoes);
  }, []);

  return (
    <LayoutPadrao>
    <div className="details-view">
      <h2>Lista de Avaliações</h2>
      <table className="details-table">
        <thead>
          <tr>
            <th>Projeto</th>
            <th>Avaliador</th>
            <th>Nota</th>
            <th>Parecer</th>
            <th>Data</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {avaliacoes.map(av => (
            <tr key={av.id}>
              <td>{av.projeto.titulo}</td>
              <td>{av.avaliador.nome}</td>
              <td>{av.nota}</td>
              <td>{av.parecer}</td>
              <td>{av.data}</td>
              <td>{av.status || 'Concluído'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
    </LayoutPadrao>
  );
};

export default ListagemAvaliacoes;