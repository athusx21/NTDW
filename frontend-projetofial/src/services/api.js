const API_URL = "http://localhost:8080/api";

const request = async (endpoint, options = {}) => {
  try {
    const response = await fetch(`${API_URL}${endpoint}`, {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...options.headers
      }
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(errorData.message || 'Erro na requisição');
    }

    if (response.status === 204) return null;
    return await response.json();
  } catch (error) {
    console.error("Erro na API:", error);
    throw error;
  }
};

export const api = {
  // 🏆 Premios
  getPremios: () => request("/premios"),
  getPremio: (id) => request(`/premios/${id}`),
  cadastrarPremio: (premio) => request("/premios", { method: 'POST', body: JSON.stringify(premio) }),
  atualizarPremio: (id, premio) => request(`/premios/${id}`, { method: 'PUT', body: JSON.stringify(premio) }),
  deletarPremio: (id) => request(`/premios/${id}`, { method: 'DELETE' }),

  // 👥 Autores
  getAutores: () => request("/autores"),
  getAutor: (id) => request(`/autores/${id}`),
  cadastrarAutor: (autor) => request("/autores", { method: 'POST', body: JSON.stringify(autor) }),
  atualizarAutor: (id, autor) => request(`/autores/${id}`, { method: 'PUT', body: JSON.stringify(autor) }),
  deletarAutor: (id) => request(`/autores/${id}`, { method: 'DELETE' }),

  // 🧑‍🏫 Avaliadores
  getAvaliadores: () => request("/avaliadores"),
  getAvaliador: (id) => request(`/avaliadores/${id}`),
  cadastrarAvaliador: (avaliador) => request("/avaliadores", { method: 'POST', body: JSON.stringify(avaliador) }),
  atualizarAvaliador: (id, avaliador) => request(`/avaliadores/${id}`, { method: 'PUT', body: JSON.stringify(avaliador) }),
  deletarAvaliador: (id) => request(`/avaliadores/${id}`, { method: 'DELETE' }),

  // 📁 Projetos
  getProjetos: () => request("/projetos"),
  getProjeto: (id) => request(`/projetos/${id}`),
  enviarProjeto: (projeto) => request("/projetos", {
    method: 'POST',
    body: JSON.stringify({
      titulo: projeto.titulo,
      resumo: projeto.resumo,
      areaTematica: projeto.areaTematica,
      autoresIds: projeto.autoresIds
    }),
  }),
  atualizarProjeto: (id, projeto) => request(`/projetos/${id}`, { method: 'PUT', body: JSON.stringify(projeto) }),
  deletarProjeto: (id) => request(`/projetos/${id}`, { method: 'DELETE' }),

  // 📊 Avaliações
  getAvaliacoes: () => request("/avaliacoes"),
  getAvaliacao: (id) => request(`/avaliacoes/${id}`),
  criarAvaliacao: (avaliacao) => request("/avaliacoes", { method: 'POST', body: JSON.stringify(avaliacao) }),
  
  // 🔹 Atualizado: enviar projetoId pela URL e avaliadorId como campo simples
  avaliarProjeto: (projetoId, { avaliadorId, nota, parecer } = {}) =>
    request(`/projetos/${projetoId}/avaliacao`, {
      method: 'POST',
      body: JSON.stringify({
        avaliadorId, // agora só o ID
        nota,
        parecer
      }),
    }),
  
  atualizarAvaliacao: (id, avaliacao) => request(`/avaliacoes/${id}`, { method: 'PUT', body: JSON.stringify(avaliacao) }),
  deletarAvaliacao: (id) => request(`/avaliacoes/${id}`, { method: 'DELETE' }),

  // 📅 Cronogramas
  getCronogramas: () => request("/cronogramas"),
  getCronograma: (id) => request(`/cronogramas/${id}`),
  cadastrarCronograma: (cronograma) => request("/cronogramas", { method: 'POST', body: JSON.stringify(cronograma) }),
  atualizarCronograma: (id, cronograma) => request(`/cronogramas/${id}`, { method: 'PUT', body: JSON.stringify(cronograma) }),
  deletarCronograma: (id) => request(`/cronogramas/${id}`, { method: 'DELETE' }),

  // 📈 Resumo Geral
  getResumo: () => request("/projetos/resumo"),

  // 🔎 Filtros e relacionamentos
  getAvaliacoesPorProjeto: (projetoId) => request(`/projetos/${projetoId}/avaliacoes`),
  getAvaliacoesPorAvaliador: (avaliadorId) => request(`/avaliadores/${avaliadorId}/avaliacoes`),
  getProjetosAvaliados: () => request("/projetos?avaliados=true"),
  getProjetosNaoAvaliados: () => request("/projetos?avaliados=false"),
  getProjetosVencedores: () => request("/projetos?vencedores=true"),
};
