// src/pages/AvaliacaoProjeto.jsx
import React, { useEffect, useState } from "react";
import { api } from "../../services/api";
import LayoutPadrao from "../Dashboard/LayoutPadrao";
import "./AvaliacaoProjeto.css";

const AvaliacaoProjeto = () => {
  const [projetos, setProjetos] = useState([]);
  const [avaliadores, setAvaliadores] = useState([]);
  const [form, setForm] = useState({
    projetoId: "",
    avaliadorId: "",
    nota: "",
    parecer: ""
  });
  const [mensagem, setMensagem] = useState("");
  const [tipoMensagem, setTipoMensagem] = useState("info");
  const [enviando, setEnviando] = useState(false);

  useEffect(() => {
    api.getProjetos().then(setProjetos).catch(console.error);
    api.getAvaliadores().then(setAvaliadores).catch(console.error);
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    const { projetoId, avaliadorId, nota, parecer } = form;

    if (!projetoId || !avaliadorId || !nota || !parecer) {
      setMensagem("Por favor, preencha todos os campos.");
      setTipoMensagem("erro");
      return;
    }

    const notaNum = Number(nota);
    const projetoIdNum = Number(projetoId);
    const avaliadorIdNum = Number(avaliadorId);

    console.log("Payload enviado:", JSON.stringify({
      avaliadorId: avaliadorIdNum,
      nota: notaNum,
      parecer
    }, null, 2));

    setEnviando(true);

    try {
      // Chamada da API com projetoId na URL e payload correto
      await api.avaliarProjeto(projetoIdNum, {
        avaliadorId: avaliadorIdNum,
        nota: notaNum,
        parecer
      });

      setMensagem("✅ Avaliação enviada com sucesso!");
      setTipoMensagem("sucesso");
      setForm({ projetoId: "", avaliadorId: "", nota: "", parecer: "" });
    } catch (error) {
      console.error(error);
      setMensagem("❌ Erro ao enviar avaliação: " + error.message);
      setTipoMensagem("erro");
    } finally {
      setEnviando(false);
    }
  };

  return (
    <LayoutPadrao>
      <div className="form-container">
        <h2>Avaliar Projeto</h2>

        {mensagem && (
          <p className={`mensagem ${tipoMensagem}`}>
            {mensagem}
          </p>
        )}

        <label>Projeto:</label>
        <select name="projetoId" onChange={handleChange} value={form.projetoId}>
          <option value="">Selecione</option>
          {projetos.map(p => (
            <option key={p.id} value={p.id}>{p.titulo}</option>
          ))}
        </select>

        <label>Avaliador:</label>
        <select name="avaliadorId" onChange={handleChange} value={form.avaliadorId}>
          <option value="">Selecione</option>
          {avaliadores.map(a => (
            <option key={a.id} value={a.id}>{a.nome}</option>
          ))}
        </select>

        <label>Nota:</label>
        <input
          type="number"
          name="nota"
          min="0"
          max="10"
          onChange={handleChange}
          value={form.nota}
        />

        <label>Parecer:</label>
        <textarea
          name="parecer"
          onChange={handleChange}
          value={form.parecer}
        ></textarea>

        <button onClick={handleSubmit} disabled={enviando}>
          {enviando ? "Enviando..." : "Enviar Avaliação"}
        </button>
      </div>
    </LayoutPadrao>
  );
};

export default AvaliacaoProjeto;
