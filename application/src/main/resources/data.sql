
-- !IMPORTANTE
-- EN CASO DE NECESSIDADE DE LIMPAR A DB, NÃO SE ESQUES
-- DE VOLTAR A COMENTAR OD DELETES LOGO A SEGUIR À COMPILAÇÃO DO PROJETO
-- THANK U!
/**
      DROP table if exists tbl_user_profiles;
      DROP table if exists tbl_logs_event;
      DROP table if exists tbl_profile;
      DROP table if exists tbl_recluso_crime;
      DROP table if exists tbl_recluso;
      DROP table if exists tbl_documentos;
      DROP table if exists tbl_user;
      DROP table if exists tbl_domain;
      DROP table if exists tbl_geografia;
      DROP table if exists tbl_ilha;
      DROP table if exists tbl_images;
      DROP table if exists tbl_pais;
      DROP table if exists tbl_tasks;
      DROP table if exists tbl_user;
 */


/*
IMPORTANTE !caso for necessario comentar, não esqueça de voltar a comentar
delete from tbl_recluso;
delete from tbl_domain;

*/


-- pais initialization
-- INSERT into tbl_pais (id, date_created, date_updated, nome, status) values
--       ('e9dc1d36-977b-40b9-b6b8-299c49e41cd3','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Cabo Verde', 1),
--       ('882d378f-f581-482f-a63c-e4411bbb1e00','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Portugal', 1),
--       ('f5ec16b5-9594-4b05-b3a2-0ee0b6dd4e74','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Brasil', 1)
--       ON CONFLICT (id) DO NOTHING;


-- profile initialization
INSERT into tbl_profile (id, date_created, date_updated, name, is_editable,force_access_check, status) values
('e9dc1d36-977b-40b9-b6b8-299c49e41cd3','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Administrador', 0, 1, 1),
('882d378f-f581-482f-a63c-e4411bbb1e00','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Utilizador', 1,1, 1),
('f5ec16b5-9594-4b05-b3a2-0ee0b6dd4e74','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Guarda Prisional',1, 1, 1)
ON CONFLICT (id) DO NOTHING;

--
INSERT INTO tbl_user (id, date_created, date_updated, name, username, email, profile_image_id, access_token, is_editable, status) VALUES
('d7224655-312a-40fe-ac2b-d02ae239846f','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Administrador', 'admin', 'admin@sigp.cv', null, '642befc13a6c41faae330fa49f0e5d65df2bfbecc4a44e399f3f6fe1336edb51894ed29aa77fcb667272166e5f02a7091c23940598c3a60015c1759d625ce3b3cc431d3f2d6fe66afd395460e02e3f0979a35e437198ea969c3b355a9689bea64f8afbdaef51d6091070475ced655202826f1f2edc22a2ba4a49f8f55e1595d6', 0, 1)
ON CONFLICT (id) DO NOTHING;

-- profile domain
INSERT into tbl_domain(id, date_created, date_updated, domain_type, name, value, ordem, status)  values
('c9752bbb-aac5-4ec6-a7ff-32b63848ef5c','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_SEXO','Masculino','M', 1 ,1),
('94706886-96be-4f51-9cca-74d64ec60854','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_SEXO','Feminino','F', 2, 1),
('128188a0-2728-4adc-b351-fac639cb5407','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_SEXO','Não especificado','N/D', 3, 1),

('c707f6a9-05a6-4cd8-857f-2554f2a59659','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_ESTADO_CIVIL','Solteiro','SOLTEIRO', 1, 1),
('e9dc1d36-977b-40b9-b6b8-299c49e41cd3','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_ESTADO_CIVIL','Casado','CASADO', 2, 1),
('36e5499b-7594-4024-a120-e144d485e027','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_ESTADO_CIVIL','União de facto','UF', 3, 1),

('882d378f-f581-482f-a63c-e4411bbb1e00','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOCUMENT_TYPE','Imagem','IMAGEM', 1, 1),
('7cd29981-240d-49d2-85de-f77acf327a7d','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOCUMENT_TYPE','Video','VIDEO', 2, 1),
('50a81231-b0ce-4208-9bf1-ac732daa87f2','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOCUMENT_TYPE','Documento','DOCUMENTO', 3, 1),

('0b8d732f-d1db-4dbf-8f71-7d47d1bbe227','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_TIPO_DOC_IDENTIF','Bilhete de Identidade','BILHEITE_IDENT', 1, 1),
('60e806e0-0158-42bb-89bc-7a53dfebb02d','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_TIPO_DOC_IDENTIF','Cartão de Identidade','CARTAO_IDENT', 2, 1),
('03a42f09-204d-4104-9983-e81ce74ea2ef','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_TIPO_DOC_IDENTIF','Passaporte','PASSAPORTE', 3, 1),
('092cf611-56f9-49a2-8d26-84335e60b07b','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_TIPO_DOC_IDENTIF','Carta de Condução','CARTA_CONDUC', 4, 1),

('946a5d50-48e0-4007-9618-6d4c5c5e8dd2','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_PROFISSAO','Carpinteiro','CARPINTEIRO', 1, 1),
('f58db0be-cf16-405e-af19-a42d4a9786ab','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_PROFISSAO','Comerciante','COMERCIANTE', 2, 1),
('d91ec7ea-3d71-41c4-8f05-f065f17e82fa','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_PROFISSAO','Agricultor','AGRICULTOR', 3, 1),
('4a101203-f827-42ab-b06d-f4cdc1cd9061','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_PROFISSAO','Mecánico','MECANICO', 4, 1),

('59c7c3fe-714f-4895-b2a1-3f1903cc044c','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_NIVEL_ESCOLAR','Sem Habilitação','SEM_HABILITACAO', 1, 1),
('bba310c3-64c0-462f-b8ad-4e327b550ec4','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_NIVEL_ESCOLAR','Ensino Primário','ENSINO_PRIMAR', 2, 1),
('e2654c60-9cac-460c-a1a0-256f527a2111','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_NIVEL_ESCOLAR','Ensino Secundário','ENSINO_SECUND', 3, 1),
('f1d745d2-aa1d-4616-9fc8-814325996fac','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_NIVEL_ESCOLAR','Licenciado','LICENCIADO', 4, 1)
ON CONFLICT (id) DO NOTHING;



-- profile geografia
INSERT into tbl_geografia(id, tipo, nome, date_created, date_updated, status) values
('e9dc1d36-977b-40b9-b6b8-299c49e41cd3', 'TIPO_CIDADE', 'Praia','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
('882d378f-f581-482f-a63c-e4411bbb1e00', 'TIPO_CIDADE', 'Mindelo','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000',1),
('f5ec16b5-9594-4b05-b3a2-0ee0b6dd4e74', 'TIPO_CIDADE', 'Assomada','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000',1),

('c707f6a9-05a6-4cd8-857f-2554f2a59659', 'TIPO_FREGUESIA', 'Nossa Senhora da Luz','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
('36e5499b-7594-4024-a120-e144d485e027', 'TIPO_FREGUESIA', 'Nossa Senhora da Luz','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000',1),
('50a81231-b0ce-4208-9bf1-ac732daa87f2', 'TIPO_FREGUESIA', 'Nha Santa Catarina','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000',1)
ON CONFLICT (id) DO NOTHING;

-- ilah initialization
INSERT into tbl_ilha (id, nome, date_created, date_updated, status) values
      ('59c7c3fe-714f-4895-b2a1-3f1903cc044c','S.Antao','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
      ('bba310c3-64c0-462f-b8ad-4e327b550ec4','S.Vicente','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
      ('e2654c60-9cac-460c-a1a0-256f527a2111','S.Nicolau','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
      ('f1d745d2-aa1d-4616-9fc8-814325996fac','Sal','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
      ('af5f9e6d-7f1c-448b-ba87-02a5f758b040','Boavista','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
      ('59838f14-fcb9-4ac5-8ad7-b54981e23ae5','Maio','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
      ('20f2e47e-a099-4fa1-a00c-293502087e6f','Santiago','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
      ('4fcdc6c9-953f-4c7f-86af-6a719afcb61b','Fogo','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1),
      ('e2fcc5d3-d6f6-4b2c-ad36-762ada428e91','Brava','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000', 1)
      ON CONFLICT (id) DO NOTHING;


-- pais initialization
INSERT into tbl_pais (id,nome, capital, continente, date_created, date_updated, ordem, status) values

('6f9bb942-2d41-4076-96ba-b01e92ad6acc', 'Cabo Verde', 'Praia', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 1, 1),
('68908342-a2d5-4629-a98b-c58a46916668', 'Guiné-Bissau', 'Bissau', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 2, 1),
('dc5e1ee4-014a-4fc2-8a84-2d060b9e72d7', 'Brasil', 'Brasília', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 3, 1),
('542f2d03-cd0c-4e63-b06a-aaad2f7cb6d4', 'Portugal', 'Lisboa', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 4, 1),
('5ce99f3f-7776-4abf-90ab-2674d36ebc98', 'São Tomé e Príncipe', 'São Tomé', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 5, 1),
('62b6a2aa-18ed-4176-9bb5-58606260bcd2', 'Angola', 'Luanda', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 6, 1),
('adb11f76-60f4-4d82-828f-6a12c8ee8753', 'Senegal', 'Dacar', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 7, 1),

('ac30474a-6c84-4f9d-a1c0-78025dc6f5cd', 'Afeganistão', 'Cabul', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('6a009043-fe59-449c-b30b-688c120ae8a3', 'África do Sul', 'Pretória', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('df220a38-e618-4ac5-9927-9225f20d1523', 'Albânia', 'Tirana', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('eea7c1f4-e36a-4038-8e9b-93ed1e151264', 'Alemanha', 'Berlim', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('d987a8b8-706f-4762-94bb-307528a87cd4', 'Andorra', 'Andorra-a-Velha', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('d781426f-065f-4adc-b9a1-d08b71c9bd15', 'Antiga e Barbuda', 'São João', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('84674bf1-5d67-4e50-a50a-5b09f8ec625c', 'Arábia Saudita', 'Riade', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('3740d681-183f-4c27-9c98-c992f7c579e8', 'Argélia', 'Argel', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('d2d557b0-ab99-4b73-9a2e-2b4b26b7c17a', 'Argentina', 'Buenos Aires', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('f5796464-ceda-4aca-8087-a21489b51c95', 'Arménia', 'Erevã', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('eddf8fc2-1afd-4a85-9c08-ab0f37e07871', 'Austrália', 'Camberra', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('3d241449-d77f-43b5-bdb6-a6ddab92c541', 'Áustria', 'Viena', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('45390ab5-d9b3-473b-a55b-6d78727329fb', 'Azerbaijão', 'Bacu', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('ef1aee49-1f6d-43a5-91dc-c411e275c891', 'Bahamas', 'Nassau', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('be8b3d30-f1f0-451d-9341-db054b6a4b59', 'Bangladexe', 'Daca', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('36c2041c-cf34-45f4-bc37-ab3585df589d', 'Barbados', 'Bridgetown', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('4df254c1-d5ef-4957-aeed-c7e20f8dc554', 'Barém', 'Manama', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('3a0f8780-daa3-44fe-bad4-8d62f80eb839', 'Bélgica', 'Bruxelas', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('0ff36946-db59-4c4c-abec-194c4e941a31', 'Belize', 'Belmopã', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('af13d3c5-7ff4-4a2f-89d2-948f77e44826', 'Benim', 'Porto Novo', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('80bbd034-491e-4e7a-a7df-a6182aed18d3', 'Bielorrússia', 'Minsque', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('b292e55b-5d5c-438f-a066-4f8313d6993e', 'Bolívia', 'Sucre', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('a1d7fa88-2967-4742-892c-0a151ead0e0a', 'Bósnia e Herzegovina', 'Saraievo', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('ac1c846c-58d2-47a7-9756-5af8a609445b', 'Botsuana', 'Gaborone', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('2ff3aef6-5cce-4a02-a100-f8773261e59e', 'Brunei', 'Bandar Seri Begauã', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('1c540683-3847-46dd-ac91-eda551b02766', 'Bulgária', 'Sófia', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('cd251348-c819-4e66-9d21-a34f3a7b6027', 'Burquina Faso', 'Uagadugu', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('e9959cf0-aa5b-408f-816b-f2d0591b0c5f', 'Burúndi', 'Bujumbura', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('dee3428c-8038-4953-b39d-62dd939773c7', 'Butão', 'Timbu', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('70542af1-3336-4ffb-8e82-6c9be1fa8fe6', 'Camarões', 'Iaundé', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('2b1b86b2-6ca3-40e0-851e-d9f89f898901', 'Camboja', 'Pnom Pene', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('285d0f3e-8add-43b1-b4fb-0fef41178d56', 'Canadá', 'Otava', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('bf187cba-c555-40fb-a728-de76f3d105ed', 'Catar', 'Doa', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('77663587-4512-4093-a563-32326fe74f5d', 'Cazaquistão', 'Astana', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('444ae0f7-94d7-486f-958e-d6575dcb4da6', 'Chade', 'Jamena', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('9e122c18-89fa-4612-84ba-36890e7ae783', 'Chile', 'Santiago', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('811f2a21-ae95-4f19-9f3f-e50a3fccf2a3', 'China', 'Pequim', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('45b075d5-22df-4dc6-836c-1d57340eeb19', 'Chipre', 'Nicósia', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('ce8460f8-d476-400c-ae7f-fe3c301274dc', 'Colômbia', 'Bogotá', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('1e601352-8e04-456e-8554-8f2437be0b4b', 'Comores', 'Moroni', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('cf8fa5ae-c740-494b-9a3f-ad860cc97180', 'Congo-Brazzaville', 'Brazavile', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('4088df31-f67f-477f-b46d-4b88744f3183', 'Coreia do Norte', 'Pionguiangue', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('9a2744d8-d584-459b-9ac5-5291de5b9c3f', 'Coreia do Sul', 'Seul', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('47c1e488-ef6b-4eda-b1a3-9d116a875166', 'Cosovo', 'Pristina', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('b9952be5-cdea-42f3-9054-8fb52d95a584', 'Costa do Marfim', 'Iamussucro', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('49a2b0f5-5974-4acd-937c-1521bcf915bd', 'Costa Rica', 'São José', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('fc274dc5-41ac-4c30-9259-2124cc2180d8', 'Croácia', 'Zagrebe', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('141d348c-0507-45f4-addf-140d2390d940', 'Cuaite', 'Cidade do Cuaite', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('342a563a-b99a-45c5-809b-614e0045831d', 'Cuba', 'Havana', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('fb6df087-aabf-4667-a693-e6d461f32b88', 'Dinamarca', 'Copenhaga', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('15d56eb3-a2f8-4f50-b71b-fcb83653b0c4', 'Dominica', 'Roseau', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('3a83a03e-8d0a-477e-86f6-6aea2569380c', 'Egito', 'Cairo', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('792df40a-6a99-4c22-9ec0-1389c024cb66', 'Emirados Árabes Unidos', 'Abu Dabi', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('c40bb91d-7151-484f-b601-ca84bad4ebcf', 'Equador', 'Quito', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('e7a89b02-f807-4c56-b1d0-1366e4f3306e', 'Eritreia', 'Asmara', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('9e774c02-a2b4-4899-9aa8-5c7e31b40693', 'Eslováquia', 'Bratislava', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('e37107fb-d792-4720-8b16-60df1c81d716', 'Eslovénia', 'Liubliana', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('84aa5e4e-deaf-4fcf-ad12-734f99e04e68', 'Espanha', 'Madrid', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('95a9a654-3c0c-4d24-932f-0fb34a5b355a', 'Estado da Palestina', 'Jerusalém Oriental', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('ef20cca9-fffb-4f57-a9aa-5432f7c56d8a', 'Estados Unidos', 'Washington, D.C.', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('abe7eb3d-889b-4019-9430-95aa2310c281', 'Estónia', 'Talim', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('5caa44f7-c584-4a6e-b507-21bbc904a0a7', 'Etiópia', 'Adis Abeba', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('f6f1535c-0fe2-4ea6-87dc-9ef8c6e9460f', 'Fiji', 'Suva', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('864465ff-4591-4b26-80a2-2ec526684cbc', 'Filipinas', 'Manila', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('033bd282-5f70-494c-9de9-ad8324a6347a', 'Finlândia', 'Helsínquia', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('356dc083-19b2-4a31-ad1e-550a86a6b6cd', 'França', 'Paris', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('630b8bcc-e40a-4295-b664-608a7ecc841a', 'Gabão', 'Libreville', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('2592f5ed-1ce8-4c2c-9c9f-44c85e45a5ae', 'Gâmbia', 'Banjul', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('f22707e1-57c9-4d81-ba33-e29c1878a097', 'Gana', 'Acra', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('4bb6eb05-4398-4e28-905b-74fe70273aa3', 'Geórgia', 'Tebilíssi', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('b2bca2ec-308c-47a6-ab33-d268a07a9b69', 'Granada', 'São Jorge', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('2cf5980e-69c8-4b6a-87da-622179acf5c9', 'Grécia', 'Atenas', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('95a0ddb8-05f2-4e05-86e4-aeefbe71f8b3', 'Guatemala', 'Cidade da Guatemala', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('9e0ac3bc-004f-47c2-9465-15c08efae2c8', 'Guiana', 'Georgetown', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('47738944-b29f-483f-9759-2660e5ca51e7', 'Guiné', 'Conacri', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('3a66208e-8432-44e6-bc0a-02014170c75f', 'Guiné Equatorial', 'Malabo', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('c1a90132-dd7f-46b1-9878-26c1fcc92012', 'Haiti', 'Porto Príncipe', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('cda9a542-deb9-4261-9e88-20bd3d356fc0', 'Honduras', 'Tegucigalpa', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('21330224-0a0b-4a96-bb2a-d87fc39aa3a0', 'Hungria', 'Budapeste', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('12d06eb5-1ce7-44e2-a0c0-dec19dd85aec', 'Iémen', 'Saná', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1)
/*
,
('00000000000000000', 'Ilhas Marechal', 'Majuro', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Índia', 'Nova Déli', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Indonésia', 'Jacarta', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Irão', 'Teerão', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Iraque', 'Bagdade', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Irlanda', 'Dublim', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Islândia', 'Reiquiavique', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Israel', 'Jerusalém', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Itália', 'Roma', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Jamaica', 'Kingston', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Japão', 'Tóquio', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Jibuti', 'Jibuti', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Jordânia', 'Amã', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Laus', 'Vienciana', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Lesoto', 'Maseru', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Letónia', 'Riga', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Líbano', 'Beirute', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Libéria', 'Monróvia', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Líbia', 'Trípoli', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Listenstaine', 'Vaduz', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Lituânia', 'Vílnius', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Luxemburgo', 'Luxemburgo', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Macedónia', 'Escópia', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Madagáscar', 'Antananarivo', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Malásia', 'Cuala Lumpur', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Maláui', 'Lilôngue', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Maldivas', 'Malé', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Mali', 'Bamaco', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Malta', 'Valeta', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Marrocos', 'Rebate', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Maurícia', 'Porto Luís', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Mauritânia', 'Nuaquechote', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'México', 'Cidade do México', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Mianmar', 'Nepiedó', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Micronésia', 'Paliquir', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Moçambique', 'Maputo', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Moldávia', 'Quixinau', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Mónaco', 'Mónaco', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Mongólia', 'Ulã Bator', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Montenegro', 'Podgoritsa', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Namíbia', 'Vinduque', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Nauru', 'Iarém', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Nepal', 'Catmandu', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Nicarágua', 'Manágua', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Níger', 'Niamei', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Nigéria', 'Abuja', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Noruega', 'Oslo', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Nova Zelândia', 'Wellington', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Omã', 'Mascate', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Países Baixos', 'Amesterdão', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Palau', 'Ngerulmud', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Panamá', 'Cidade do Panamá', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Papua Nova Guiné', 'Porto Moresby', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Paquistão', 'Islamabade', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Paraguai', 'Assunção', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Peru', 'Lima', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Polónia', 'Varsóvia', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Quénia', 'Nairóbi', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Quirguistão', 'Bisqueque', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Quiribáti', 'Taraua do Sul', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Reino Unido', 'Londres', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'República Centro-Africana', 'Bangui', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'República Checa', 'Praga', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'República Democrática do Congo', 'Quinxassa', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'República Dominicana', 'São Domingos', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Roménia', 'Bucareste', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Ruanda', 'Quigali', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Rússia', 'Moscovo', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Salomão', 'Honiara', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Salvador', 'São Salvador', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Samoa', 'Apia', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Santa Lúcia', 'Castries', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'São Cristóvão e Neves', 'Basseterre', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'São Marinho', 'São Marinho', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'São Vicente e Granadinas', 'Kingstown', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Seicheles', 'Vitória', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Serra Leoa', 'Freetown', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Sérvia', 'Belgrado', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Singapura', 'Singapura', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Síria', 'Damasco', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Somália', 'Mogadíscio', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Sri Lanca', 'Sri Jaiavardenapura-Cota', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Suazilândia', 'Lobamba', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Sudão', 'Cartum', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Sudão do Sul', 'Juba', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Suécia', 'Estocolmo', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Suíça', 'Berna', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Suriname', 'Paramaribo', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Tailândia', 'Banguecoque', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Taiuã', 'Taipé', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Tajiquistão', 'Duchambé', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Tanzânia', 'Dodoma', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Timor-Leste', 'Díli', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Togo', 'Lomé', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Tonga', 'Nucualofa', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Trindade e Tobago', 'Porto de Espanha', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Tunísia', 'Tunes', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Turcomenistão', 'Asgabate', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Turquia', 'Ancara', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Tuvalu', 'Funafuti', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Ucrânia', 'Quieve', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Uganda', 'Campala', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Uruguai', 'Montevideu', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Usbequistão', 'Tasquente', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Vanuatu', 'Porto Vila', 'Oceania',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Vaticano', 'Vaticano', 'Europa',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Venezuela', 'Caracas', 'América',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Vietname', 'Hanói', 'Ásia',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Zâmbia', 'Lusaca', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1),
('00000000000000000', 'Zimbábue', 'Harare', 'África',  '2019-01-01 00:00:00.000000', '2019-01-01 00:00:00.000000', 100, 1)
*/
ON CONFLICT (id) DO NOTHING;
