package com.forage.service;

import org.springframework.stereotype.Service;

import com.forage.model.Client;
import com.forage.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> updateClient(Long id, Client details) {
        return clientRepository.findById(id).map(client -> {
            client.setNom(details.getNom());
            client.setMail(details.getMail());
            client.setTelephone(details.getTelephone());
            return clientRepository.save(client);
        });
    }

    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}