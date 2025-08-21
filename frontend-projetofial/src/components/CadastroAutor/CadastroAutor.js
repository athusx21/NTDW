// CadastroAutor.jsx
import React, { useState } from 'react';
import { api } from '../../services/api';
import LayoutPadrao from "../Dashboard/LayoutPadrao";

const CadastroAutor = () => {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [cpf, setCpf] = useState('');
    const [dataNascimento, setDataNascimento] = useState('');
    



         const handleSubmit = async (e) => {
            e.preventDefault();
            const autor = { nome, email, telefone, cpf, dataNascimento };
            await api.cadastrarAutor(autor);
            alert('Autor cadastrado com sucesso!');
            setNome('');
            setEmail('');
            setTelefone('');
            setCpf('');
            setDataNascimento('');
        };

    return (
        <LayoutPadrao>
        <div>
            <h1>Cadastrar Autor</h1>
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
                    <label>E-mail:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Telefone:</label>
                    <input
                        type="text"
                        value={telefone}
                        onChange={(e) => setTelefone(e.target.value)}
                        required    
                    />
                </div>
                   <div>
                    <label>Cpf:</label>
                    <input
                        type="text"
                        value={cpf}
                        onChange={(e) => setCpf(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Data de nascimento:</label>
                    <input
                        type="date"
                        value={dataNascimento}
                        onChange={(e) => setDataNascimento(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Cadastrar Autor</button>
            </form>
        </div>
        </LayoutPadrao>
    );
};

export default CadastroAutor;
