package it.mattiaciraldo.spring.controller.modelandview;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import it.mattiaciraldo.spring.controller.AuthController;
import it.mattiaciraldo.spring.model.Cliente;
import it.mattiaciraldo.spring.model.Comune;
import it.mattiaciraldo.spring.model.Fattura;
import it.mattiaciraldo.spring.model.Indirizzo;
import it.mattiaciraldo.spring.model.TipoCliente;
import it.mattiaciraldo.spring.repository.ClienteRepository;
import it.mattiaciraldo.spring.repository.ComuneRepository;
import it.mattiaciraldo.spring.repository.FatturaRepository;
import it.mattiaciraldo.spring.repository.IndirizzoRepository;
import it.mattiaciraldo.spring.security.LoginRequest;
import it.mattiaciraldo.spring.security.SignupRequest;
import it.mattiaciraldo.spring.service.ClienteService;
import it.mattiaciraldo.spring.service.FatturaService;
import it.mattiaciraldo.spring.service.IndirizzoService;
import it.mattiaciraldo.spring.service.StatoFatturaService;
import it.mattiaciraldo.spring.service.UserService;

@RestController
@RequestMapping("/access")
public class AccessController {
	String username;
	String password;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	FatturaService fatturaService;

	@Autowired
	AuthController authController;

	@Autowired
	ClienteService clienteService;

	@Autowired
	UserService userService;

	@Autowired
	FatturaRepository fatturaRepository;
	@Autowired
	StatoFatturaService statoFatturaService;

	@Autowired
	IndirizzoRepository indirizzoRepository;
		
	@Autowired
	IndirizzoService indirizzoService;
	
	@Autowired
	ComuneRepository comuneRepository;
	
	
	
	@PostMapping("/login")
	public ModelAndView creaLogin(@RequestParam String username, String password) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
		this.password = password;
		this.username = username;
		if (authController.authenticateUser(loginRequest).getStatusCode().is2xxSuccessful()) {
			Pageable page = Pageable.ofSize(20);
			ModelAndView view = new ModelAndView("homepage");
			view.addObject("listaClienti", clienteService.getAllClienti(page));
			view.addObject("listaFatture", fatturaService.getAllFatture(page));
			view.addObject("listaUtenti", userService.myFindAllUsers());

			return view;
		} else {
			return new ModelAndView("errore");
		}
	}

	@GetMapping("/ordina")
	public ModelAndView ordinaClienti(@RequestParam(required = false, defaultValue = "0") int pageNumber, //
			@RequestParam(required = false, defaultValue = "5") int pageSize, //
			@RequestParam(required = false, defaultValue = "nome") String ordina) {
		ModelAndView model = new ModelAndView("homepage");
		Pageable page = PageRequest.of(pageNumber, pageSize);
		if (ordina.equals("provincia")) {
			model.addObject("listaClienti", clienteService.orderByProvincia(page));
		} else if (ordina.equals("dataUltimoContatto")) {
			model.addObject("listaClienti", clienteService.orderByDataUltimoContatto(page));

		} else if (ordina.equals("datainserimento")) {
			model.addObject("listaClienti", clienteService.orderByDataInserimento(page));
		} else if (ordina.equals("fatturatoAnnuale")) {
			model.addObject("listaClienti", clienteService.orderByFatturatoAnnuale(page));
		} else if (ordina.equals("nome")) {
			model.addObject("listaClienti", clienteService.orderByRagioneSociale(page));
		} else {
			model.addObject("listaClienti", clienteService.findAllClientePageSizeSort(pageNumber, pageSize, ordina));
		}
		model.addObject("listaFatture", fatturaService.getAllFatture(page));
		model.addObject("listaUtenti", userService.myFindAllUsers());
		return model;
	}

	@GetMapping("/aggiungi")
	public ModelAndView aggiungi() {
		ModelAndView vista = new ModelAndView("aggiungicliente");
		vista.addObject("cliente", new Cliente());
		return vista;
	}

	@PostMapping("/nuovo")
	public ModelAndView creaCliente(Cliente cliente) {
		clienteService.saveCliente(cliente);
		return creaLogin(username, password);
	}

	@GetMapping("/homepage")
	public ModelAndView retHome() {
		return creaLogin(username, password);
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteCliente(@PathVariable(required = true) Long id) {
		if (clienteService.deleteCliente(id).getStatusCode().is2xxSuccessful()) {
			clienteService.deleteCliente(id);
			return new ModelAndView("delete");
		}
		return new ModelAndView("notfound");
	}

	@GetMapping("/deletefattura/{id}")
	public ModelAndView deleteFattura(@PathVariable(required = true) Long id) {
		fatturaService.deleteFatturaById(id);
		return new ModelAndView("delete");
	}

	@GetMapping("/addIndirizzo")
	public ModelAndView addIndirizzo() {
		ModelAndView m = new ModelAndView("addindirizzo");
		m.addObject("indirizzo", new Indirizzo());
		return m;
	}
	
	@PostMapping("/indirizzoparamsave")
	public ModelAndView addAddress(@RequestParam String via, String civico, String cap, String localita, Long comune) {
		Indirizzo indirizzo = new Indirizzo();
		indirizzo.setVia(via);
		indirizzo.setCivico(civico);
		indirizzo.setCap(cap);
		indirizzo.setLocalita(localita);
		indirizzo.setComune(comuneRepository.getById(comune));
		indirizzoService.saveIndirizzo(indirizzo);
		ModelAndView m= new ModelAndView("viewindirizzo");
		m.addObject("indirizzo", indirizzoService.getAllIndirizzo(Pageable.unpaged()));
		return m;
	}

	@GetMapping("/showindirizzo")
	public ModelAndView showInd() {
		ModelAndView m= new ModelAndView("viewindirizzo");
		m.addObject("indirizzo", indirizzoService.getAllIndirizzo(Pageable.unpaged()));
		return m;
	}
	
	
	@GetMapping("/modificacliente/{id}")
	public ModelAndView editCliente(@PathVariable(required = true) Long id) {
		ModelAndView m = new ModelAndView("modificacliente");
		Cliente cliente = clienteRepository.getById(id);
		m.addObject("cliente", cliente);
		return m;
	}

	@PostMapping("/edit")
	public ModelAndView updateCliente2(long id, @RequestParam String ragioneSociale, String partitaIva,
			TipoCliente tipoCliente, String email, String pec, String telefono, String nomeContatto,
			String cognomeContatto, String telefonoContatto, String emailContatto,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContatto, Double fatturatoAnnuale ) {
		Cliente cliente = clienteRepository.findById(id).get();
		cliente.setRagioneSociale(ragioneSociale);
		cliente.setPartitaIva(partitaIva);
		cliente.setTipoCliente(tipoCliente);
		cliente.setEmail(email);
		cliente.setPec(pec);
		cliente.setTelefono(telefono);
		cliente.setNomeContatto(nomeContatto);
		cliente.setCognomeContatto(cognomeContatto);
		cliente.setTelefonoContatto(telefonoContatto);
		cliente.setEmailContatto(emailContatto);
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(dataUltimoContatto);
		cliente.setFatturatoAnnuale(fatturatoAnnuale);

		clienteRepository.save(cliente);

		return creaLogin(username, password);
	}

	@PostMapping("/signup")
	public ModelAndView creaSignUp(@RequestParam String username, String email, String password, String nome,
			String cognome) {
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername(username);
		signupRequest.setEmail(email);
		signupRequest.setPassword(password);
		signupRequest.setNome(nome);
		signupRequest.setCognome(cognome);
		this.password = password;
		this.username = username;
		ModelAndView view;
		if (authController.registerUser(signupRequest).getStatusCode().isError()) {
			view = new ModelAndView("errore");
		} else {
			Pageable page = Pageable.unpaged();
			view = new ModelAndView("homepage");
			view.addObject("listaClienti", clienteService.getAllClienti(page));
			view.addObject("listaFatture", fatturaService.getAllFatture(page));
			view.addObject("listaUtenti", userService.myFindAllUsers());

		}
		return view;
	}

	@GetMapping("/aggiung")
	public ModelAndView addFattura() {
		ModelAndView m = new ModelAndView("aggiungifattura");

		return m;
	}

	@PostMapping("/newcliente")
	public ModelAndView creaCliente(@RequestParam String ragioneSociale, String partitaIva, TipoCliente tipoCliente,
			String email, String pec, String telefono, String nomeContatto, String cognomeContatto,
			String telefonoContatto, String emailContatto, Long indirizzoSedeOperativa,
			Long indirizzoSedeLegale, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContatto,
			Double fatturatoAnnuale) {
		Cliente cliente = new Cliente();
		cliente.setRagioneSociale(ragioneSociale);
		cliente.setPartitaIva(partitaIva);
		cliente.setTipoCliente(tipoCliente);
		cliente.setEmail(email);
		cliente.setPec(pec);
		cliente.setTelefono(telefono);
		cliente.setNomeContatto(cognomeContatto);
		cliente.setCognomeContatto(cognomeContatto);
		cliente.setTelefonoContatto(telefonoContatto);
		cliente.setEmailContatto(emailContatto);
		if(indirizzoSedeOperativa != null) {
			for(Indirizzo i : indirizzoRepository.findAll()) {
				if(indirizzoSedeOperativa == i.getId()) {
					cliente.setIndirizzoSedeOperativa(indirizzoRepository.getById(indirizzoSedeOperativa));
				}
			}}
		if(indirizzoSedeLegale != null) {
			for(Indirizzo i : indirizzoRepository.findAll()) {
				if(indirizzoSedeLegale == i.getId()) {
					cliente.setIndirizzoSedeLegale(indirizzoRepository.getById(indirizzoSedeLegale));
				}
			}
		
		}
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(dataUltimoContatto);
		cliente.setFatturatoAnnuale(fatturatoAnnuale);
		clienteService.saveCliente(cliente);
		return creaLogin(username, password);
	}

	@PostMapping("/newfattura")
	public ModelAndView creaFattura(@RequestParam Integer numero, Integer anno, Double importo, Long stato,
			Long cliente) {
		Fattura fattura = new Fattura();
		fattura.setData(LocalDate.now());
		fattura.setNumeroFattura(numero);
		fattura.setAnno(anno);
		fattura.setImporto(importo);
		fattura.setStatoFattura(statoFatturaService.getStatoFatturaById(stato).get());
		fattura.setCliente(clienteRepository.getById(cliente));
		fatturaService.aggiungiFattura(fattura);
		return creaLogin(username, password);
	}

	@GetMapping("/modificafattura/{id}")
	public ModelAndView edi(@PathVariable(required = true) Long id) {
		ModelAndView m = new ModelAndView("moddfattura");
		Fattura fattura = fatturaRepository.getById(id);
		m.addObject("fattura", fattura);
		return m;
	}

	@PostMapping("/fatturaedit")
	public ModelAndView modificaFattura(Fattura fattura) {
		fatturaService.updateFattura(fattura.getId(), fattura);
		return creaLogin(username, password);
	}

	@GetMapping("/find")
	public ModelAndView ricerca(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInserimento,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContatto, Double fatturatoAnnuale,
			String nomeContatto) {
		ModelAndView v = new ModelAndView("result");
		Pageable p = Pageable.unpaged();
		if (dataInserimento != null) {
			v.addObject("list", clienteService.findByDataInserimento(dataInserimento, p));
		}
		if (dataUltimoContatto != null) {
			v.addObject("list", clienteService.findByDataUltimoContatto(dataUltimoContatto, p));
		}
		if (nomeContatto != null) {
			v.addObject("list", clienteService.findByParteDelNome(nomeContatto, p));
		}
		if (fatturatoAnnuale != null) {
			v.addObject("list", clienteService.findByFatturatoAnnuale(fatturatoAnnuale, p));
		}

		return v;
	}
}