<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="main-container">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px;">
        <h1 class="page-title" style="margin: 0;">Gestion des Demandes</h1>
        <div>
            <a href="/demande/create" class="action-link">+ Créer une Demande</a>
        </div>
    </div>

    <div id="errorAlert" class="alert alert-danger" style="display: none; margin-bottom: 20px;"></div>
    <div id="successAlert" class="alert alert-info" style="display: none; margin-bottom: 20px;"></div>

    <div class="table-section">
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Description</th>
                    <th>Lieu</th>
                    <th>Client</th>
                    <th>Date</th>
                    <th>Statut</th>
                    <th style="width: 200px;">Actions</th>
                </tr>
            </thead>
            <tbody id="demandesTableBody">
                <c:forEach var="demande" items="${demandes}">
                    <tr>
                        <td>${demande.id}</td>
                        <td>${demande.description}</td>
                        <td>${demande.lieu.adresse} - ${demande.lieu.district}</td>
                        <td>${demande.client.nom}</td>
                        <td>${demande.dateDemandeFormatted}</td>
                        <td>${demande.status}</td>
                        <td>
                            <a href="/demande/edit/${demande.id}" class="btn btn-sm btn-primary">Modifier</a>
                            <button type="button" class="btn btn-sm btn-danger" onclick="deleteDemande(${demande.id})">Supprimer</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    function deleteDemande(id) {
        if (confirm('Êtes-vous sûr de vouloir supprimer cette demande?')) {
            fetch('/api/demandes/' + id, {
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
