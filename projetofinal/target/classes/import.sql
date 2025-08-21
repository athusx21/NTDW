

-- Inserção de prêmios
INSERT INTO PREMIO (NOME, DESCRICAO, ANO) VALUES ('Prêmio Inovação', 'Premiação para projetos inovadores', 2025), ('Prêmio Sustentabilidade', 'Projetos com impacto ambiental positivo', 2025);

/*Inserção de cronogramas*/
INSERT INTO CRONOGRAMA (DATA_INICIO, DATA_FIM, DESCRICAO) VALUES ('2025-05-01', '2025-06-30', 'Período de inscrições'), ('2025-07-01', '2025-07-15', 'Período de avaliações');

/*Inserção de autores*/
INSERT INTO AUTOR (NOME, EMAIL, TELEFONE, CPF, DATA_NASCIMENTO) VALUES ('Alice Costa', 'alice@example.com', '62999990001', '55555555555', '2004-04-12'), ('Bruno Lima', 'bruno@example.com', '62999990002', '22222222222', '1998-05-15'), ('Carlos Silva', 'carlos@example.com', '62999990003', '33333333333', '1995-08-20'), ('Daniela Oliveira', 'daniela@example.com', '62999990004', '44444444444', '2000-11-30');

-- Inserção de avaliadores
INSERT INTO AVALIADOR (NOME, EMAIL, TELEFONE, ESPECIALIDADE, INSTITUICAO) VALUES ('Dr. João Prado', 'joao@avaliador.com', '62999991111', 'Tecnologia', 'UFG'), ('Dra. Maria Santos', 'maria@avaliador.com', '62999991112', 'Medicina', 'UFMG');

-- Inserção de projetos (4 projetos)
INSERT INTO PROJETO (AREA_TEMATICA, TITULO, RESUMO, DATA_ENVIO, PREMIO_ID, CRONOGRAMA_ID) VALUES ('Tecnologia', 'Sistema de Irrigação Inteligente', 'Automatização de irrigação com IoT e sensores de umidade.', '2025-05-15', 1, 1), ('Medicina', 'Dispositivo para Monitoramento Cardíaco', 'Dispositivo wearable para monitoramento contínuo de pacientes cardíacos.', '2025-05-18', 1, 1), ('Sustentabilidade', 'Reciclagem de Plásticos com Baixo Custo', 'Método inovador para reciclagem de plásticos em comunidades carentes.', '2025-05-20', 2, 1), ('Educação', 'Plataforma de Ensino Adaptativo', 'Sistema que adapta o conteúdo conforme o desempenho do aluno.', '2025-05-22', 1, 1);

-- Associação de autores aos projetos

INSERT INTO PROJETO_AUTOR (PROJETO_ID, AUTOR_ID) VALUES (1, 1), (1, 2), (2, 3), (3, 1), (3, 4), (4, 2), (4, 3), (4, 4);

-- Inserção de avaliações (apenas para 3 projetos, deixando o 4º sem avaliação)
INSERT INTO AVALIACAO (PARECER, NOTA, DATA_AVALIACAO, PROJETO_ID, AVALIADOR_ID) VALUES ('Ótimo projeto com grande potencial de aplicação prática.', 9.0, '2025-07-01', 1, 1),('Solução inovadora que pode salvar vidas. Recomendo ajustes na ergonomia.', 8.5, '2025-07-03', 2, 2),('Projeto social com excelente impacto ambiental. Merece destaque.', 9.5, '2025-07-05', 3, 1); -- Projeto 4 (Plataforma de Ensino) ficará sem avaliação