
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
('50a81231-b0ce-4208-9bf1-ac732daa87f2','2019-01-01 00:00:00.000000','2019-01-01 00:00:00.000000','DOCUMENT_TYPE','Documento','DOCUMENTO', 3, 1)
ON CONFLICT (id) DO NOTHING;


