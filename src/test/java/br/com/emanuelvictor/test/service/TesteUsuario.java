package br.com.emanuelvictor.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.emanuelvictor.iguassu.web.entity.address.Bairro;
import br.com.emanuelvictor.iguassu.web.entity.address.Cidade;
import br.com.emanuelvictor.iguassu.web.entity.address.Estado;
import br.com.emanuelvictor.iguassu.web.entity.address.Pais;
import br.com.emanuelvictor.iguassu.web.service.ServiceUsuario;
import br.com.emanuelvictor.iguassu.web.service.address.ServiceEndereco;

@TransactionConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TesteUsuario {
	
    @Autowired
    ServiceUsuario serviceUsuario;
    
    @Autowired
    ServiceEndereco serviceEndereco;
    
    @Test
    @Rollback(false)
    @Transactional
    public void saveUsuario() {
        Pais pais = new Pais();
        pais.setNome("Brasil");
        pais = serviceEndereco.save(pais);

        Estado estado = new Estado();
        estado.setNome("Paraná");
        estado.setPais(pais);
        estado = serviceEndereco.save(estado);

        Cidade cidade = new Cidade();
        cidade.setNome("Foz do Iguaçu");
        cidade.setEstado(estado);
        cidade = serviceEndereco.save(cidade);

        Bairro bairro = new Bairro();
        bairro.setNome("Três Lagoas");
        bairro.setCidade(cidade);
        bairro = serviceEndereco.save(bairro);
    	
    }
	
	

}
