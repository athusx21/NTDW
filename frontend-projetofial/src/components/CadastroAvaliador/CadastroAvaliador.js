// AvaliadorCadastro.jsx
import React, { useState } from 'react';
import { api } from '../../services/api';
import LayoutPadrao from "../Dashboard/LayoutPadrao";


const AvaliadorCadastro = () => {
    const [nome, setNome] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const avaliador = { nome };
        await api.cadastrarAvaliador(avaliador);
        alert('Avaliador cadastrado com sucesso!');
        setNome('');
    };

    return (
        <LayoutPadrao>
        <div>
            <h1>Cadastrar Avaliador</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nome:</label>
                    <input
                        type="text"
                        value={nome}
                        onChange={(e) => setNome(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Cadastrar</button>
            </form>
        </div>
        </LayoutPadrao>
    );
};

export default AvaliadorCadastro;
