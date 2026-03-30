<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="form-container">
    <div class="form-header">
        <h1>Créer un Devis</h1>
    </div>
    
    <div class="alert alert-danger" id="errorAlert" style="display: none;"></div>
    <div class="alert alert-success" id="successAlert" style="display: none;"></div>
    
    <form id="devisForm">
        <!-- Date du devis -->
        <div class="form-group">
            <label for="dateDevis">
                Date du Devis <span class="required">*</span>
            </label>
            <input type="date" id="dateDevis" name="dateDevis" required>
        </div>
        
        <!-- Demande (auto-complete) -->
        <div class="form-group">
            <label for="demandeId">
                Demande <span class="required">*</span>
            </label>
            <input type="text" id="demandeSearch" placeholder="Rechercher une demande..." autocomplete="off">
            <select id="demandeId" name="demandeId" required style="margin-top: 5px;">
                <option value="">Sélectionnez une demande</option>
            </select>
            <small class="form-text text-muted">La dernière demande créée est automatiquement sélectionnée</small>
        </div>
        
        <!-- Type Devis (dropdown) -->
        <div class="form-group">
            <label for="typeDevisId">
                Type de Devis <span class="required">*</span>
            </label>
            <select id="typeDevisId" name="typeDevisId" required>
                <option value="">Sélectionnez un type de devis</option>
                <c:forEach var="typeDevis" items="${typeDevisList}">
                    <option value="${typeDevis.id}">${typeDevis.libelle}</option>
                </c:forEach>
            </select>
        </div>
        
        <!-- Détails du devis -->
        <div class="details-section" style="margin-top: 30px;">
            <h4>Détails du Devis</h4>
            <div class="table-responsive">
                <table class="data-table" id="detailsTable">
                    <thead>
                        <tr>
                            <th>Libellé</th>
                            <th>Quantité</th>
                            <th>Prix Unitaire</th>
                            <th>Montant</th>
                            <th style="width: 100px;">Actions</th>
                        </tr>
                    </thead>
                    <tbody id="detailsTableBody">
                        <!-- Details will be added here dynamically -->
                    </tbody>
                </table>
            </div>
            
            <button type="button" class="btn btn-secondary mt-2" id="addDetailBtn">
                <i class="fas fa-plus"></i> Ajouter un détail
            </button>
            
            <div class="total-section mt-3">
                <h5>Total: <span id="totalAmount">0.00</span> €</h5>
            </div>
        </div>
        
        <div class="button-group mt-4">
            <button type="submit" class="btn-submit">Créer le Devis</button>
            <a href="/devis/list" class="btn-cancel">Annuler</a>
        </div>
    </form>
</div>

<script>
    // Store details in memory (not saved to DB until transaction)
    let details = [];
    let detailCounter = 0;

    // Load last demande via AJAX
    function loadLastDemande() {
        $.ajax({
            url: '/api/demandes/last',
            type: 'GET',
            dataType: 'json',
            success: function(demande) {
                if (demande) {
                    const select = document.getElementById('demandeId');
                    const option = document.createElement('option');
                    option.value = demande.id;
                    option.textContent = `Demande #${demande.id} - ${demande.client?.nom || 'N/A'}`;
                    option.selected = true;
                    select.appendChild(option);
                }
            },
            error: function(xhr, status, error) {
                console.error('Erreur lors du chargement de la dernière demande:', error);
            }
        });
    }

    // Add a new detail row
    function addDetailRow(detail = null) {
        const id = detail ? detail.id : detailCounter++;
        const libelle = detail ? detail.libelle : '';
        const quantite = detail ? detail.quantite : 1;
        const prixUnitaire = detail ? detail.prixUnitaire : 0;
        
        const row = document.createElement('tr');
        row.dataset.id = id;
        row.innerHTML = `
            <td>
                <input type="text" class="detail-libelle" value="${libelle}" placeholder="Libellé" required>
            </td>
            <td>
                <input type="number" class="detail-quantite" value="${quantite}" min="1" required onchange="updateRowCalculations(this)">
            </td>
            <td>
                <input type="number" class="detail-prix" value="${prixUnitaire}" step="0.01" min="0" required onchange="updateRowCalculations(this)">
            </td>
            <td class="detail-montant">
                ${(quantite * prixUnitaire).toFixed(2)} €
            </td>
            <td>
                <button type="button" class="btn btn-sm btn-danger" onclick="removeDetail(this)">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        
        document.getElementById('detailsTableBody').appendChild(row);
        
        if (!detail) {
            details.push({ id: id, libelle: '', quantite: 1, prixUnitaire: 0 });
        }
    }

    // Remove a detail row
    function removeDetail(button) {
        const row = button.closest('tr');
        const id = parseInt(row.dataset.id);
        details = details.filter(d => d.id !== id);
        row.remove();
        calculateTotal();
    }

    // Update calculations for a row
    function updateRowCalculations(input) {
        const row = input.closest('tr');
        const id = parseInt(row.dataset.id);
        
        const libelle = row.querySelector('.detail-libelle').value;
        const quantite = parseInt(row.querySelector('.detail-quantite').value) || 0;
        const prixUnitaire = parseFloat(row.querySelector('.detail-prix').value) || 0;
        
        const detail = details.find(d => d.id === id);
        if (detail) {
            detail.libelle = libelle;
            detail.quantite = quantite;
            detail.prixUnitaire = prixUnitaire;
        }
        
        row.querySelector('.detail-montant').textContent = (quantite * prixUnitaire).toFixed(2) + ' €';
        calculateTotal();
    }

    // Calculate total amount
    function calculateTotal() {
        let total = 0;
        document.querySelectorAll('#detailsTableBody tr').forEach(row => {
            const quantite = parseInt(row.querySelector('.detail-quantite').value) || 0;
            const prix = parseFloat(row.querySelector('.detail-prix').value) || 0;
            total += quantite * prix;
        });
        document.getElementById('totalAmount').textContent = total.toFixed(2);
    }

    // Form submission via AJAX
    document.getElementById('devisForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Collect all details from the table
        const detailsData = [];
        document.querySelectorAll('#detailsTableBody tr').forEach(row => {
            detailsData.push({
                libelle: row.querySelector('.detail-libelle').value,
                quantite: parseInt(row.querySelector('.detail-quantite').value),
                prixUnitaire: parseFloat(row.querySelector('.detail-prix').value)
            });
        });
        
        const formData = {
            dateDevis: document.getElementById('dateDevis').value,
            demandeId: parseInt(document.getElementById('demandeId').value),
            typeDevis: {
                id: parseInt(document.getElementById('typeDevisId').value)
            },
            details: detailsData
        };
        
        $.ajax({
            url: '/api/devis',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                $('#successAlert').text('Devis créé avec succès!').show();
                setTimeout(() => {
                    window.location.href = '/devis/list';
                }, 1500);
            },
            error: function(xhr, status, error) {
                console.error('Erreur:', error);
                $('#errorAlert').text('Erreur lors de la création du devis').show();
            }
        });
    });

    // Add detail button click handler
    document.getElementById('addDetailBtn').addEventListener('click', function() {
        addDetailRow();
    });

    // Initialize
    $(document).ready(function() {
        // Set today's date
        document.getElementById('dateDevis').valueAsDate = new Date();
        
        // Load last demande
        loadLastDemande();
        
        // Add one empty detail row
        addDetailRow();
    });
</script>