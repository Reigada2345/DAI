<script>
    // Carregar rotas no início
    window.onload = function () {
        fetch("/api/routes")
            .then(response => response.json())
            .then(rotas => {
                mostrarRotas(rotas);
            });
    };

    function mostrarRotas(rotas) {
        const tabela = document.getElementById("tabela-rotas");
        tabela.innerHTML = ""; // Limpa
        rotas.forEach(r => {
            const linha = document.createElement("tr");
            linha.innerHTML = `
                <td>${r.route_id}</td>
                <td contenteditable="true" onblur="editarRota(${r.route_id}, this.innerText, 'route_short_name')">${r.route_short_name}</td>
                <td contenteditable="true" onblur="editarRota(${r.route_id}, this.innerText, 'route_long_name')">${r.route_long_name}</td>
                <td><button onclick="removerRota(${r.route_id})">Remover</button></td>
            `;
            tabela.appendChild(linha);
        });
    }

    function adicionarRota() {
        const shortName = document.getElementById("route_short_name").value;
        const longName = document.getElementById("route_long_name").value;

        fetch("/api/routes", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                route_short_name: shortName,
                route_long_name: longName
            })
        }).then(() => window.location.reload());
    }

    function editarRota(id, novoValor, campo) {
        fetch(`/api/routes/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ [campo]: novoValor })
        });
    }

    function removerRota(id) {
        fetch(`/api/routes/${id}`, {
            method: "DELETE"
        }).then(() => window.location.reload());
    }
</script>
