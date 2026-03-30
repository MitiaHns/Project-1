<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="form-container">
    <div class="form-header">
        <h1 id="formTitle">Creer une Demande</h1>
    </div>

    <div class="alert alert-info" style="display: none;" id="successAlert">
        Demande sauvegardee avec succes!
    </div>

    <div class="alert alert-danger" id="errorAlert" style="display: none;"></div>

    <form id="demandeForm">
        <div class="form-group">
            <label for="clientId">
                Client <span class="required">*</span>
            </label>
            <select id="clientId" name="client.id" required>
                <option value="">Selectionnez un client</option>
                <c:forEach var="client" items="${clients}">
                    <option value="${client.id}">${client.nom} - ${client.mail}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="description">
                Description <span class="required">*</span>
            </label>
            <textarea id="description" name="description" required placeholder="Decrivez la demande"></textarea>
        </div>

        <div class="form-group">
            <label for="lieuId">
                Lieu
            </label>
            <select id="lieuId" name="lieu.id">
                <option value="">Selectionnez un lieu</option>
                <c:forEach var="lieu" items="${lieux}">
                    <option value="${lieu.id}">
                        ${lieu.adresse} - ${lieu.district}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="dateDemande">
                Date de Demande <span class="required">*</span>
            </label>
            <input type="date" id="dateDemande" name="dateDemande" required>
        </div>

        <div class="form-group">
            <label for="status">
                Statut <span class="required">*</span>
            </label>
            <select id="status" name="status" required>
                <option value="">Selectionnez un statut</option>
                <c:forEach var="statut" items="${statuts}">
                    <option value="${statut.libelle}" ${statut.libelle == 'Demande creee' ? 'selected' : ''}>
                        ${statut.libelle}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="button-group">
            <button type="submit" class="btn-submit" id="submitBtn">Creer Demande</button>
            <a href="/demande/list" class="btn-cancel">Annuler</a>
        </div>
    </form>
</div>

<script>
    let demandeId = null;

    function checkIfEdit() {
        const dateInput = document.getElementById('dateDemande');
        const pathParts = window.location.pathname.split('/');

        if (pathParts[2] === 'edit' && pathParts[3]) {
            demandeId = pathParts[3];

            if (typeof loadDemandeData === 'function') {
                loadDemandeData(demandeId);
            }

            document.getElementById('formTitle').textContent = 'Modifier une Demande';
            document.getElementById('submitBtn').textContent = 'Modifier Demande';
            document.getElementById('status').disabled = false;
            return;
        }

        document.getElementById('status').value = 'Demande creee';
        document.getElementById('status').disabled = true;

        if (!dateInput.value) {
            dateInput.value = new Date().toISOString().split('T')[0];
        }
    }

    async function loadDemandeData(id) {
        try {
            const response = await fetch('/api/demandes/' + id);
            if (response.ok) {
                const demande = await response.json();
                document.getElementById('description').value = demande.description || '';
                document.getElementById('dateDemande').value = demande.dateDemande || '';
                document.getElementById('clientId').value = demande.client?.id || '';
                document.getElementById('lieuId').value = demande.lieu?.id || '';
                document.getElementById('status').value = demande.status || '';
            } else {
                document.getElementById('errorAlert').textContent = 'Erreur lors du chargement de la demande';
                document.getElementById('errorAlert').style.display = 'block';
            }
        } catch (error) {
            console.error('Erreur:', error);
            document.getElementById('errorAlert').textContent = 'Erreur lors du chargement de la demande';
            document.getElementById('errorAlert').style.display = 'block';
        }
    }

    document.getElementById('demandeForm').addEventListener('submit', async function(e) {
        e.preventDefault();

        const formData = {
            description: document.getElementById('description').value,
            dateDemande: document.getElementById('dateDemande').value,
            client: {
                id: parseInt(document.getElementById('clientId').value, 10)
            }
        };

        const lieuId = document.getElementById('lieuId').value;
        if (lieuId) {
            formData.lieu = {
                id: parseInt(lieuId, 10)
            };
        }

        if (demandeId) {
            formData.status = document.getElementById('status').value;
        }

        try {
            let response;

            if (demandeId) {
                response = await fetch('/api/demandes/' + demandeId, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formData)
                });
            } else {
                response = await fetch('/api/demandes', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formData)
                });
            }

            if (response.ok) {
                document.getElementById('successAlert').style.display = 'block';
                setTimeout(() => {
                    window.location.href = '/demande/list';
                }, 1500);
            } else {
                document.getElementById('errorAlert').textContent = 'Erreur lors de la sauvegarde de la demande';
                document.getElementById('errorAlert').style.display = 'block';
            }
        } catch (error) {
            console.error('Erreur:', error);
            document.getElementById('errorAlert').textContent = 'Erreur lors de la sauvegarde de la demande';
            document.getElementById('errorAlert').style.display = 'block';
        }
    });

    document.addEventListener('DOMContentLoaded', checkIfEdit);
</script>
