// ProjetoCadastro.jsx
import React, { useState, useEffect } from 'react';
import { api } from '../../services/api';
import LayoutPadrao from "../Dashboard/LayoutPadrao";

const ProjetoCadastro = () => {
    const [titulo, setTitulo] = useState('');
    const [resumo, setResumo] = useState('');
    const [areaTematica, setAreaTematica] = useState('');
    const [autores, setAutores] = useState([]);
    const [autoresSelecionados, setAutoresSelecionados] = useState([]);
    const [carregando, setCarregando] = useState(false);

    // Carregar autores disponíveis
    useEffect(() => {
        const carregarAutores = async () => {
            try {
                const autoresData = await api.getAutores();
                setAutores(autoresData);
            } catch (error) {
                console.error('Erro ao carregar autores:', error);
                alert('Erro ao carregar autores');
            }
        };
        carregarAutores();
    }, []);

        const handleSubmit = async (e) => {
            e.preventDefault();
            
            if (autoresSelecionados.length === 0) {
                alert('Selecione pelo menos um autor');
                return;
            }

            const projeto = { 
                titulo, 
                resumo, 
                areaTematica,
                autoresIds: autoresSelecionados // Mude para autoresIds
            };
            
            console.log('Dados enviados:', JSON.stringify(projeto, null, 2));
            
            try {
                setCarregando(true);
                await api.enviarProjeto(projeto);
                alert('Projeto cadastrado com sucesso!');
                
                // Reset form
                setTitulo('');
                setResumo('');
                setAreaTematica('');
                setAutoresSelecionados([]);
            } catch (error) {
                alert('Erro ao cadastrar projeto: ' + error.message);
                console.error('Detalhes do erro:', error);
            } finally {
                setCarregando(false);
            }
        };

    const handleAutorChange = (autorId) => {
        setAutoresSelecionados(prev => {
            if (prev.includes(autorId)) {
                return prev.filter(id => id !== autorId);
            } else {
                return [...prev, autorId];
            }
        });
    };

    return (
        <LayoutPadrao>
            <div>
                <h1>Cadastrar Projeto</h1>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label>Título:</label>
                        <input
                            type="text"
                            value={titulo}
                            onChange={(e) => setTitulo(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label>Resumo:</label>
                        <textarea
                            value={resumo}
                            onChange={(e) => setResumo(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label>Área Temática:</label>
                        <input
                            type="text"
                            value={areaTematica}
                            onChange={(e) => setAreaTematica(e.target.value)}
                            required
                        />
                    </div>

                    {/* Seleção de Autores */}
                    <div>
                        <label>Autores:</label>
                        <div style={{ border: '1px solid #ccc', padding: '10px', maxHeight: '200px', overflowY: 'auto' }}>
                            {autores.length === 0 ? (
                                <p>Carregando autores...</p>
                            ) : (
                                autores.map(autor => (
                                    <div key={autor.id} style={{ marginBottom: '5px' }}>
                                        <label>
                                            <input
                                                type="checkbox"
                                                checked={autoresSelecionados.includes(autor.id)}
                                                onChange={() => handleAutorChange(autor.id)}
                                            />
                                            {autor.nome} - {autor.email}
                                        </label>
                                    </div>
                                ))
                            )}
                        </div>
                        <small>Selecione um ou mais autores</small>
                    </div>

                    <button type="submit" disabled={carregando}>
                        {carregando ? 'Cadastrando...' : 'Cadastrar'}
                    </button>
                </form>
            </div>
        </LayoutPadrao>
    );
       
};

export default ProjetoCadastro;