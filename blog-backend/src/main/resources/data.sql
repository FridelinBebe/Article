-- src/main/resources/data.sql
-- Ce fichier sera exécuté automatiquement par Spring Boot au démarrage si 'ddl-auto' est à 'create' ou 'create-drop'

-- Insérer des articles
INSERT INTO ARTICLE (ID, TITLE, CONTENT, AUTHOR, PUBLICATION_DATE) VALUES
(RANDOM_UUID(), 'Premier Article de Blog', 'Ceci est le contenu détaillé de mon tout premier article. Il parle de l''importance de la persévérance en programmation.', 'Alice', '2023-10-26'),
(RANDOM_UUID(), 'Introduction à Spring Boot REST API', 'Découvrez comment construire des APIs RESTful puissantes et efficaces avec Spring Boot et Java.', 'Bob', '2023-11-15');

-- Insérer des commentaires (assurez-vous que les ARTICLE_ID correspondent à des IDs existants après le démarrage)
-- Note: Les IDs générés par RANDOM_UUID() sont différents à chaque exécution.
-- Pour un `data.sql` fiable avec H2, il est préférable de définir des IDs fixes si vous voulez des relations immédiates.
-- Ou, comme ici, on peut laisser Spring gérer les relations via l'application après le démarrage.
-- Pour des tests plus complexes, utilisez des tests d'intégration Spring.

-- Example with fixed UUIDs for consistent relations (change ddl-auto to 'create' in application.properties)
-- DELETE FROM COMMENT;
-- DELETE FROM ARTICLE;
--
-- INSERT INTO ARTICLE (ID, TITLE, CONTENT, AUTHOR, PUBLICATION_DATE) VALUES
-- ('83e9d8e0-1a2b-4c3d-8e9f-0123456789ab', 'Mon Article Fixe', 'Contenu de l''article fixe pour tests.', 'FixedAuthor', '2024-01-01');
--
-- INSERT INTO COMMENT (ID, AUTHOR, TEXT, ARTICLE_ID) VALUES
-- ('90a1b2c3-d4e5-6f7a-8b9c-0123456789ab', 'FixedCommenter', 'Commentaire sur l''article fixe.', '83e9d8e0-1a2b-4c3d-8e9f-0123456789ab');
