<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="main-container">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px;">
        <h1 class="page-title" style="margin: 0;">Gestion des Clients</h1>
        <a href="/client/create" class="action-link">+ Ajouter un Client</a>
    </div>

    <div id="errorAlert" class="alert alert-danger" style="display: none; margin-bottom: 20px;"></div>
    <div id="successAlert" class="alert alert-info" style="display: none; margin-bottom: 20px;"></div>

    <div class="table-section">
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Email</th>
                    <th>Téléphone</th>
                    <th style="width: 200px;">Actions</th>
                </tr>
            </thead>
            <tbody id="clientsTableBody">
                <c:forEach var="client" items="${clients}">
                    <tr>
                        <td>${client.id}</td>
                        <td>${client.nom}</td>
                        <td>${client.mail}</td>
                        <td>${client.telephone}</td>
                        <td>
                            <a href="/client/edit/${client.id}" class="btn btn-sm btn-primary">Modifier</a>
                            <button type="button" class="btn btn-sm btn-danger" onclick="deleteClient(${client.id})">Supprimer</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    function deleteClient(id) {
        if (confirm('Êtes-vous sûr de vouloir supprimer ce client?')) {
            fetch('/api/clients/' + id, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    location.reload();
                } else {
                    alert('Erreur lors de la suppression');
                }
            })
            .catch(error => console.error('Error:', error));
        }
    }
</script>
