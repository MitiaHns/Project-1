package com.forage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.forage.service.ClientService;
import com.forage.service.DemandeService;
import com.forage.service.LieuService;
import com.forage.service.StatutService;
import com.forage.service.TypeDevisService;

@Controller
public class WebController {

    private final ClientService clientService;
    private final DemandeService demandeService;
    private final LieuService lieuService;
    private final TypeDevisService typeDevisService;
    private final StatutService statutService;

    public WebController(ClientService clientService, DemandeService demandeService, LieuService lieuService, TypeDevisService typeDevisService, StatutService statutService) {
        this.clientService = clientService;
        this.demandeService = demandeService;
        this.lieuService = lieuService;
        this.typeDevisService = typeDevisService;
        this.statutService = statutService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/accueil";
    }

    @GetMapping("/accueil")
    public ModelAndView accueil() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("contentPage", "accueil.jsp");
        mav.addObject("pageTitle", "Accueil - Gestion Forage");
        return mav;
    }

    @GetMapping("/test")
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("contentPage", "test.jsp");
        mav.addObject("pageTitle", "Test - Gestion Forage");
        return mav;
    }

    @GetMapping("/client/list")
    public ModelAndView listClients() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("contentPage", "client/list.jsp");
        mav.addObject("pageTitle", "Liste des Clients - Gestion Forage");
        mav.addObject("clients", clientService.findAll());
        return mav;
    }

    @GetMapping("/client/create")
    public ModelAndView createClientForm() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("contentPage", "client/create.jsp");
        mav.addObject("pageTitle", "Créer un Client - Gestion Forage");
        return mav;
    }

    @GetMapping("/client/edit/{id}")
    public ModelAndView editClientForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("contentPage", "client/create.jsp");
        mav.addObject("pageTitle", "Modifier un Client - Gestion Forage");
        mav.addObject("clientId", id);
        return mav;
    }

    @GetMapping("/demande/list")
    public ModelAndView listDemandes() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("contentPage", "demande/list.jsp");
        mav.addObject("pageTitle", "Liste des Demandes - Gestion Forage");
        mav.addObject("demandes", demandeService.findAll());
        return mav;
    }

    @GetMapping("/demande/create")
    public ModelAndView createDemandeForm() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("contentPage", "demande/create.jsp");
        mav.addObject("pageTitle", "Créer une Demande - Gestion Forage");
        mav.addObject("statuts", statutService.findAll());
        mav.addObject("clients", clientService.findAll());
        mav.addObject("lieux", lieuService.findAll());
        return mav;
    }

    @GetMapping("/demande/edit/{id}")
    public ModelAndView editDemandeForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("contentPage", "demande/create.jsp");
        mav.addObject("pageTitle", "Modifier une Demande - Gestion Forage");
        mav.addObject("demandeId", id);
        mav.addObject("statuts", statutService.findAll());
        mav.addObject("clients", clientService.findAll());
        mav.addObject("lieux", lieuService.findAll());
        return mav;
    }

    @GetMapping("/devis/form")
    public ModelAndView createDevisForm() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("contentPage", "devis/form.jsp");
        mav.addObject("pageTitle", "Créer un Devis - Gestion Forage");
        mav.addObject("typeDevisList", typeDevisService.findAll());
        mav.addObject("statuts", statutService.findAll());
        return mav;
    }
}
