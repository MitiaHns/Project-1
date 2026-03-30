<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="form-container">
    <div class="form-header">
        <h1 id="formTitle">Ajouter un Client</h1>
    </div>
    
    <div class="alert alert-info" style="display: none;" id="successAlert">
        Client sauvegardé avec succès!
    </div>
    
    <div class="alert alert-danger" style="display: none;" id="errorAlert"></div>
    
    <form id="clientForm">
        <div class="form-group">
            <label for="nom">
                Nom <span class="required">*</span>
            </label>
            <input type="text" id="nom" name="nom" required placeholder="Entrez le nom du client">
        </div>
        
        <div class="form-group">
            <label for="mail">
                Email <span class="required">*</span>
            </label>
            <input type="email" id="mail" name="mail" required placeholder="Entrez l'adresse email">
        </div>
        
        <div class="form-group">
            <label for="telephone">
                Téléphone
            </label>
            <input type="tel" id="telephone" name="telephone" placeholder="Entrez le numéro de téléphone">
        </div>
        
        <div class="button-group">
            <button type="submit" class="btn-submit" id="submitBtn">Créer Client</button>
            <a href="/client/list" class="btn-cancel">Annuler</a>
        </div>
    </form>
</div>

<script>
    let clientId = null;

    function checkIfEdit() {
        const pathParts = window.location.pathname.split('/');
        if (pathParts[2] === 'edit' && pathParts[3]) {
            clientId = pathParts[3];
            loadClientData(clientId);
            document.getElementById('formTitle').textContent = 'Modifier un Client';
            document.getElementById('submitBtn').textContent = 'Modifier Client';
        }
    }

    async function loadClientData(id) {
        try {
            const response = await fetch('/api/clients/' + id);
            if (response.ok) {
                const client = await response.json();
                document.getElementById('nom').value = client.nom;
                document.getElementById('mail').value = client.mail;
                document.getElementById('telephone').value = client.telephone || '';
            } else {
                document.getElementById('errorAlert').textContent = 'Erreur lors du chargement du client';
                document.getElementById('errorAlert').style.display = 'block';
            }
        } catch (error) {
            console.error('Erreur:', error);
            document.getElementById('errorAlert').textContent = 'Erreur lors du chargement du client';
            document.getElementById('errorAlert').style.display = 'block';
        }
    }

    document.getElementById('clientForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const formData = {
            nom: document.getElementById('nom').value,
            mail: document.getElementById('mail').value,
            telephone: document.getElementById('telephone').value
        };
        
        try {
            let response;
            
            if (clientId) {
                // Modification
                response = await fetch('/api/clients/' + clientId, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formData)
                });
            } else {
                // Création
                response = await fetch('/api/clients', {
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
                    window.location.href = '/client/list';
                }, 1500);
            } else {
                document.getElementById('errorAlert').textContent = 'Erreur lors de la sauvegarde';
                document.getElementById('errorAlert').style.display = 'block';
            }
        } catch (error) {
            console.error('Erreur:', error);
            document.getElementById('errorAlert').textContent = 'Erreur lors de la sauvegarde';
            document.getElementById('errorAlert').style.display = 'block';
        }
    });

    window.addEventListener('load', checkIfEdit);
</script>
