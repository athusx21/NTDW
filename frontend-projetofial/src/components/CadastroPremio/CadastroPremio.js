// PremioCadastro.jsx
import React, { useState } from 'react';
import { api } from '../../services/api';
import LayoutPadrao from "../Dashboard/LayoutPadrao";


const PremioCadastro = () => {
    const [nome, setNome] = useState('');
    const [descricao, setDescricao] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const premio = { nome, descricao };
        await api.cadastrarPremio(premio);
        alert('Prêmio cadastrado com sucesso!');
        setNome('');
        setDescricao('');
    };

    return (
    <LayoutPadrao>
        <div>
            <h1>Cadastrar Prêmio</h1>
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
                <div>
                    <label>Descrição:</label>
                    <textarea
                        value={descricao}
                        onChange={(e) => setDescricao(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Cadastrar</button>
            </form>
        </div>
    </LayoutPadrao>
    );
};

export default PremioCadastro;
