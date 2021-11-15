package it.mattiaciraldo.spring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.mattiaciraldo.spring.model.Comune;
import it.mattiaciraldo.spring.model.Provincia;
import it.mattiaciraldo.spring.model.ERole;
import it.mattiaciraldo.spring.repository.ComuneRepository;
import it.mattiaciraldo.spring.repository.ProvinciaRepository;
import it.mattiaciraldo.spring.service.RoleService;
import lombok.Data;

@Data
@Component
public class Common implements CommandLineRunner {
	@Autowired
	ComuneRepository comuneRepository;

	@Autowired
	ProvinciaRepository provinciaRepository;

	@Autowired
	RoleService roleService;

	private static final Logger logger = LoggerFactory.getLogger(Common.class);
	private final String PATH_FILE_COMUNE = "C:\\Users\\Game\\Desktop\\FinalProject\\comuni-italiani.csv";
	private final String PATH_FILE_PROVINCIA = "C:\\Users\\Game\\Desktop\\FinalProject\\province-italiane.csv";

	public Common() {

	}

	public List<Provincia> readFileProvincia() {
		int n = 0;
		File fileProvincia = new File(PATH_FILE_PROVINCIA);
		String readFileProvincia;
		List<Provincia> lstProv = new ArrayList<>();

		try {
			readFileProvincia = FileUtils.readFileToString(fileProvincia, "UTF-8");
			List<String> listFileProvincia = Arrays.asList(readFileProvincia.split("\n"));

			for (String p : listFileProvincia) {
				if (n == 0) {
					n++;
				} else {
					String[] split = p.split(";");
					Provincia provincia = new Provincia();
					provincia.setSigla(split[0]);
					provincia.setNome(split[1]);
					lstProv.add(provincia);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lstProv;
	}

	public List<Comune> readFileComuni() {
		int n = 0;
		File fileComune = new File(PATH_FILE_COMUNE);
		String readFileComune;
		List<Comune> listaComune = new ArrayList<>();
		;
		try {
			readFileComune = FileUtils.readFileToString(fileComune, "UTF-8");
			List<String> listFileComune = Arrays.asList(readFileComune.split("\n"));
			for (Provincia p : readFileProvincia()) {
				for (String c : listFileComune) {
					if (n == 0) {
						n++;
					} else {
						String[] splitC = c.split(";");
						if (splitC[3].equals(p.getNome())) {
							Comune comune = new Comune();
							comune.setNome(splitC[2]);
							comune.setProvincia(p);
							listaComune.add(comune);
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error("Errore");
			e.printStackTrace();
		}
//		listaComune.stream().forEach(a -> System.out.println(a));
		logger.info("Lettura corretta");
		return listaComune;
	}

	public void saveProvincia() {
		Common comuniCommon = new Common();
		if (provinciaRepository.findAll().isEmpty()) {

			for (Provincia p : comuniCommon.readFileProvincia()) {
				provinciaRepository.save(p);
			}
		}
	}

	public void saveComune() {

		Common commonComune = new Common();
		if (comuneRepository.findAll().isEmpty()) {
			for (Comune c : commonComune.readFileComuni()) {
				
				c.setProvincia(provinciaRepository.getByNome(c.getProvincia().getNome()));
				comuneRepository.save(c);
			}
		}
	}

	@Override
	public void run(String... args) throws Exception {
		if (roleService.myFindAllUsers().isEmpty()) {
			roleService.myInsertRole(ERole.ROLE_ADMIN);
			roleService.myInsertRole(ERole.ROLE_USER);
		}

		saveProvincia();
		saveComune();
	}

}