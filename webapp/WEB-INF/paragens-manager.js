// Classe para gerir paragens no frontend
class ParagensManager {
    constructor(baseUrl = '/api/paragens') {
        this.baseUrl = baseUrl;
        this.userId = 'admin'; // Deve vir da sessão do utilizador
    }

    // Obter todas as paragens com filtros
    async obterParagens(filtros = {}) {
        try {
            const params = new URLSearchParams();
            
            if (filtros.zona && filtros.zona !== 'todas') {
                params.append('zona', filtros.zona);
            }
            if (filtros.search) {
                params.append('search', filtros.search);
            }
            if (filtros.ativas) {
                params.append('ativas', filtros.ativas);
            }

            const response = await fetch(`${this.baseUrl}?${params}`);
            
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            
            const data = await response.json();
            console.log(`📥 Recebidas ${data.total} paragens`);
            return data;
            
        } catch (error) {
            console.error('❌ Erro ao obter paragens:', error);
            throw error;
        }
    }

    // Obter uma paragem específica
    async obterParagem(stopId) {
        try {
            const response = await fetch(`${this.baseUrl}/${stopId}`);
            
            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error('Paragem não encontrada');
                }
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            
            return await response.json();
            
        } catch (error) {
            console.error(`❌ Erro ao obter paragem ${stopId}:`, error);
            throw error;
        }
    }

    // Guardar paragem (criar ou atualizar)
    async guardarParagem(paragem) {
        try {
            const response = await fetch(this.baseUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'User-ID': this.userId
                },
                body: JSON.stringify(paragem)
            });
            
            const data = await response.json();
            
            if (!response.ok) {
                throw new Error(data.error || `Erro HTTP: ${response.status}`);
            }
            
            console.log(`✅ Paragem ${data.action}: ${paragem.stop_name}`);
            return data;
            
        } catch (error) {
            console.error('❌ Erro ao guardar paragem:', error);
            throw error;
        }
    }

    // Eliminar paragem (soft delete)
    async eliminarParagem(stopId) {
        try {
            const response = await fetch(`${this.baseUrl}/${stopId}`, {
                method: 'DELETE',
                headers: {
                    'User-ID': this.userId
                }
            });
            
            const data = await response.json();
            
            if (!response.ok) {
                throw new Error(data.error || `Erro HTTP: ${response.status}`);
            }
            
            console.log(`🗑️ Paragem ${stopId} eliminada`);
            return data;
            
        } catch (error) {
            console.error(`❌ Erro ao eliminar paragem ${stopId}:`, error);
            throw error;
        }
    }

    // Importar paragens de ficheiro TXT
    async importarParagens(fileContent) {
        const linhas = fileContent.split('\n');
        const resultados = {
            sucesso: 0,
            erros: 0,
            detalhes: []
        };

        for (let i = 0; i < linhas.length; i++) {
            const linha = linhas[i].trim();
            if (!linha) continue;

            try {
                // Assumindo formato: ID,Nome,Latitude,Longitude,Zona
                const [stopId, stopName, stopLat, stopLon, zoneId] = linha.split(',');
                
                const paragem = {
                    stop_id: parseInt(stopId.trim()),
                    stop_name: stopName.trim(),
                    stop_lat: parseFloat(stopLat.trim()),
                    stop_lon: parseFloat(stopLon.trim()),
                    zone_id: parseInt(zoneId.trim())
                };

                await this.guardarParagem(paragem);
                resultados.sucesso++;
                resultados.detalhes.push(`✅ Linha ${i + 1}: ${paragem.stop_name}`);
                
            } catch (error) {
                resultados.erros++;
                resultados.detalhes.push(`❌ Linha ${i + 1}: ${error.message}`);
            }
        }

        return resultados;
    }

    // Exportar paragens para CSV
    async exportarCSV(filtros = {}) {
        try {
            const data = await this.obterParagens(filtros);
            const paragens = data.paragens;

            const headers = ['ID', 'Nome', 'Latitude', 'Longitude', 'Zona', 'Ativa', 'Criada em'];
            const csvContent = [
                headers.join(','),
                ...paragens.map(p => [
                    p.stop_id,
                    `"${p.stop_name}"`,
                    p.stop_lat,
                    p.stop_lon,
                    p.zone_id,
                    p.ativa ? 'Sim' : 'Não',
                    new Date(p.created_at).toLocaleDateString('pt-PT')
                ].join(','))
            ].join('\n');

            // Criar e fazer download do ficheiro
            const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = `paragens_${new Date().toISOString().split('T')[0]}.csv`;
            link.click();

            return { success: true, total: paragens.length };
            
        } catch (error) {
            console.error('❌ Erro ao exportar CSV:', error);
            throw error;
        }
    }
}

// Exemplo de uso
const paragensManager = new ParagensManager();

// Obter todas as paragens ativas da zona 1
paragensManager.obterParagens({ zona: '1', ativas: 'ativas' })
    .then(data => console.log('Paragens:', data))
    .catch(error => console.error('Erro:', error));

// Guardar nova paragem
const novaParagem = {
    stop_id: 100,
    stop_name: 'Nova Paragem',
    stop_lat: 41.5555,
    stop_lon: -8.4444,
    zone_id: 1
};

paragensManager.guardarParagem(novaParagem)
    .then(result => console.log('Paragem guardada:', result))
    .catch(error => console.error('Erro:', error));