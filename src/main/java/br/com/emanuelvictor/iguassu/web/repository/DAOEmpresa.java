package br.com.emanuelvictor.iguassu.web.repository;

import br.com.emanuelvictor.iguassu.web.entity.Empresa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DAOEmpresa extends JpaRepository<Empresa, Long> {

//    @Transactional(readOnly = true)
//    @Query("select e from Empresa e")
//    public List<Empresa> find(Pageable pageable);

    @Transactional(readOnly = true)
    @Query("select e from Empresa e WHERE ((e.id =:id or :id is null) and (e.nome LIKE %:nome% or :nome is null) and (e.email LIKE %:email% or :email is null) and (e.telefoneResidencial LIKE %:telefoneResidencial% or :telefoneResidencial is null) and (e.telefoneComercial LIKE %:telefoneComercial% or :telefoneComercial is null) and (e.cnpj LIKE %:cnpj% or :cnpj is null) and (e.endereco.rua LIKE %:rua% or :rua is null) and (e.endereco.numero LIKE %:numero% or :numero is null) and (e.endereco.cep LIKE %:cep% or :cep is null) and (e.endereco.complemento LIKE %:complemento% or :complemento is null))")
    public List<Empresa> find(@Param("id") Long id, @Param("nome") String nome, @Param("email") String email, @Param("telefoneResidencial") String telefoneResidencial, @Param("telefoneComercial") String telefoneComercial, @Param("cnpj") String cnpj,
                              @Param("rua") String rua, @Param("numero") String numero, @Param("cep") String cep,
                              @Param("complemento") String complemento, Pageable pageable);


    @Transactional(readOnly = true)
    @Query("select e from Empresa e WHERE ((e.id =:id or :id is null) and (e.nome LIKE %:nome% or :nome is null) and (e.email LIKE %:email% or :email is null) and (e.telefoneResidencial LIKE %:telefoneResidencial% or :telefoneResidencial is null) and (e.telefoneComercial LIKE %:telefoneComercial% or :telefoneComercial is null) and (e.cnpj LIKE %:cnpj% or :cnpj is null) and (e.endereco.rua LIKE %:rua% or :rua is null) and (e.endereco.numero LIKE %:numero% or :numero is null) and (e.endereco.cep LIKE %:cep% or :cep is null) and (e.endereco.complemento LIKE %:complemento% or :complemento is null)" +
                                            " and (((:bairro_id is not null and e.endereco.bairro.id =:bairro_id)" +
                                            "  or ((:bairro_id is null) and :cidade_id is not null and e.endereco.bairro.cidade.id =:cidade_id)" +
                                            "  or ((:cidade_id is null) and :estado_id is not null and e.endereco.bairro.cidade.estado.id =:estado_id)" +
                                            "  or ((:estado_id is null) and :pais_id is not null and e.endereco.bairro.cidade.estado.pais.id =:pais_id " +
                                            "  ))))")
    public List<Empresa> find(
            @Param("id") Long id,
            @Param("nome") String nome,
            @Param("email") String email, @Param("telefoneResidencial") String telefoneResidencial, @Param("telefoneComercial") String telefoneComercial,
            @Param("cnpj") String cnpj, @Param("rua") String rua,
            @Param("numero") String numero, @Param("cep") String cep,
            @Param("complemento") String complemento,
            @Param("bairro_id") Long bairro_d,
            @Param("cidade_id") Long cidade_id,
            @Param("estado_id") Long estado_id,
            @Param("pais_id") Long pais_id,
            Pageable pageable);


//    @Transactional(readOnly = true)
//    @Query("select e from Empresa e WHERE ((e.id =:id or :id is null) and (e.nome LIKE %:nome% or :nome is null) and (e.cnpj LIKE %:cnpj% or :cnpj is null) and (e.endereco.rua LIKE %:rua% or :rua is null) and (e.endereco.numero LIKE %:numero% or :numero is null) and (e.endereco.cep LIKE %:cep% or :cep is null) and (e.endereco.complemento LIKE %:complemento% or :complemento is null) " +
//            " and ((((:bairro_id is not null and e.endereco.bairro.id =:bairro_id))" +
//            "  or ((:bairro_id is null) and (:cidade_id is not null and e.endereco.bairro.cidade.id =:cidade_id))" +
//            "  or ((:cidade_id is null) and (:estado_id is not null and e.endereco.bairro.cidade.estado.id =:estado_id))" +
//            "  or ((:estado_id is null) and (:pais_id is not null and e.endereco.bairro.cidade.estado.pais.id =:pais_id) " +
//            "  or ((:pais_id is null) and ((e.endereco.bairro.id is not null) or (e.endereco.bairro.id is null))))" +
//            "  )))")
//    public List<Empresa> find(
//            @Param("id") Long id,
//            @Param("nome") String nome,
//            @Param("cnpj") String cnpj, @Param("rua") String rua,
//            @Param("numero") String numero, @Param("cep") String cep,
//            @Param("complemento") String complemento,
//            @Param("bairro_id") Long bairro_d,
//            @Param("cidade_id") Long cidade_id,
//            @Param("estado_id") Long estado_id,
//            @Param("pais_id") Long pais_id,
//            Pageable pageable);


}