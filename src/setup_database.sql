-- Script de configuração da base de dados MySQL para o sistema de autocarros

-- Criar a base de dados
CREATE DATABASE IF NOT EXISTS bus_management 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE bus_management;

-- Criar utilizador específico para a aplicação (opcional mas recomendado)
-- CREATE USER 'bus_app'@'localhost' IDENTIFIED BY 'password_segura';
-- GRANT ALL PRIVILEGES ON bus_management.* TO 'bus_app'@'localhost';
-- FLUSH PRIVILEGES;

-- Tabela principal de paragens
CREATE TABLE IF NOT EXISTS paragens (
    stop_id INT PRIMARY KEY,
    stop_name VARCHAR(255) NOT NULL,
    stop_lat DECIMAL(10, 8) NOT NULL,
    stop_lon DECIMAL(11, 8) NOT NULL,
    zone_id INT NOT NULL,
    ativa BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    INDEX idx_zone (zone_id),
    INDEX idx_ativa (ativa),
    INDEX idx_name (stop_name),
    INDEX idx_coordinates (stop_lat, stop_lon)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO paragens (stop_id, stop_name, stop_lat, stop_lon, zone_id)
VALUES (1, 'BOM JESUS', 41.554462, -8.381193, 1);

-- Tabela de auditoria para histórico de alterações
CREATE TABLE IF NOT EXISTS paragens_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    stop_id INT NOT NULL,
    action_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    old_data JSON,
    new_data JSON,
    user_id VARCHAR(100),
    action_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_stop_id (stop_id),
    INDEX idx_timestamp (action_timestamp),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE routes (
    route_id INT PRIMARY KEY,
    agency_id VARCHAR(255),
    route_short_name VARCHAR(255),
    route_long_name VARCHAR(255),
    route_type INT
);

-- Tabela de zonas (para referência)
CREATE TABLE IF NOT EXISTS zonas (
    zone_id INT PRIMARY KEY,
    zone_name VARCHAR(100) NOT NULL,
    zone_description TEXT,
    ativa BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inserir zonas básicas
INSERT IGNORE INTO zonas (zone_id, zone_name, zone_description) VALUES
(1, 'Zona 1', 'Centro da cidade'),
(2, 'Zona 2', 'Periferia norte'),
(3, 'Zona 3', 'Periferia sul'),
(4, 'Zona 4', 'Zona industrial');

-- Tabela de utilizadores/administradores (opcional)
CREATE TABLE IF NOT EXISTS utilizadores (
    user_id VARCHAR(100) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    tipo ENUM('admin', 'operador', 'visualizador') DEFAULT 'visualizador',
    ativo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inserir utilizador admin padrão
INSERT IGNORE INTO utilizadores (user_id, nome, email, tipo) VALUES
('admin', 'Administrador', 'admin@empresa.com', 'admin');

-- View para estatísticas das paragens
CREATE OR REPLACE VIEW paragens_stats AS
SELECT 
    z.zone_name,
    COUNT(p.stop_id) as total_paragens,
    COUNT(CASE WHEN p.ativa = TRUE THEN 1 END) as paragens_ativas,
    COUNT(CASE WHEN p.ativa = FALSE THEN 1 END) as paragens_inativas,
    MIN(p.created_at) as primeira_paragem,
    MAX(p.updated_at) as ultima_atualizacao
FROM zonas z
LEFT JOIN paragens p ON z.zone_id = p.zone_id
GROUP BY z.zone_id, z.zone_name
ORDER BY z.zone_id;

-- Procedimento para reativar paragem
DELIMITER //
CREATE PROCEDURE ReativarParagem(IN p_stop_id INT, IN p_user_id VARCHAR(100))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    UPDATE paragens 
    SET ativa = TRUE, updated_at = CURRENT_TIMESTAMP 
    WHERE stop_id = p_stop_id;
    
    INSERT INTO paragens_audit (stop_id, action_type, new_data, user_id)
    VALUES (p_stop_id, 'UPDATE', JSON_OBJECT('ativa', TRUE), p_user_id);
    
    COMMIT;
END //
DELIMITER ;

-- Função para obter distância entre duas paragens (em metros)
DELIMITER //
CREATE FUNCTION DistanciaParagens(lat1 DECIMAL(10,8), lon1 DECIMAL(11,8), lat2 DECIMAL(10,8), lon2 DECIMAL(11,8))
RETURNS DECIMAL(10,2)
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE distance DECIMAL(10,2);
    SET distance = (
        6371000 * ACOS(
            COS(RADIANS(lat1)) * COS(RADIANS(lat2)) * 
            COS(RADIANS(lon2) - RADIANS(lon1)) + 
            SIN(RADIANS(lat1)) * SIN(RADIANS(lat2))
        )
    );
    RETURN distance;
END //
DELIMITER ;

-- Mostrar estrutura criada
SHOW TABLES;
SELECT 'Base de dados configurada com sucesso!' as status;