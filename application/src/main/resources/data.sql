
-- pais initialization
INSERT into tbl_pais (id, date_created, date_updated, nome, status) values
      ('e9dc1d36-977b-40b9-b6b8-299c49e41cd3','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Cabo Verde', 1),
      ('882d378f-f581-482f-a63c-e4411bbb1e00','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Portugal', 1),
      ('f5ec16b5-9594-4b05-b3a2-0ee0b6dd4e74','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Brasil', 1)
      ON CONFLICT (id) DO NOTHING;


-- profile initialization
INSERT into tbl_profile (id, date_created, date_updated, name, is_editable,force_access_check, status) values
('e9dc1d36-977b-40b9-b6b8-299c49e41cd3','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Administrador', 0, 1, 1),
('882d378f-f581-482f-a63c-e4411bbb1e00','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Utilizador', 1,1, 1),
('f5ec16b5-9594-4b05-b3a2-0ee0b6dd4e74','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','Guarda Prisional',1, 1, 1)
ON CONFLICT (id) DO NOTHING;

-- profile domain
INSERT into tbl_domain(id, date_created, date_updated, domain_type, name, value, ordem, status)  values
('c9752bbb-aac5-4ec6-a7ff-32b63848ef5c','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_SEXO','Maculino','M', 1 ,1),
('94706886-96be-4f51-9cca-74d64ec60854','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_SEXO','Femenino','F', 2, 1),
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
('f1d745d2-aa1d-4616-9fc8-814325996fac','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOMAIN_NIVEL_ESCOLAR','Cicenciado','LICENCIADO', 4, 1)
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

-- pais initialization
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
