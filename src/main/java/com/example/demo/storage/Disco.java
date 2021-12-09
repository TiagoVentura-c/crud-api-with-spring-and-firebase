package com.example.demo.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class Disco {
	
	//@Value("${contato.disco.raiz}")
	private String raiz="C:\\Users\\Tiago Ventura\\Music\\";
	
	//@Value("${contato.disco.diretorio-fotos}")
	//private String diretorioFotos="";
	
	public String salvarFoto(MultipartFile foto) {
		return this.salvar(raiz, foto);
	}
	
	public String salvar(String diretorio, MultipartFile arquivo) {
		File file = new File(diretorio+"\\Photos");
		file.mkdirs();
		Path path = file.toPath().resolve(arquivo.getOriginalFilename());

		try {
			arquivo.transferTo(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path.toString();
	}


}
