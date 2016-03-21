package br.com.emanuelvictor.iguassu.web.entity;

import br.com.emanuelvictor.iguassu.web.entity.address.Pais;
import br.com.emanuelvictor.iguassu.web.entity.base.PessoaFisica;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Candidato extends PessoaFisica {

	private static final long serialVersionUID = 699441248742650338L;
	private String pathFoto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private NecessidadeEspecial necessidadeEspecial;

    @Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SituacaoCandidato situacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private EstadoCivil estadoCivil;

    @ManyToOne(optional = true, cascade = CascadeType.REFRESH)
	private Pais nacionalidade;

    @Column(nullable = true, length = 20000)
    private String observacoes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    protected Calendar dataDeContrato;// = Calendar.getInstance();

    public Calendar getDataDeContrato() {
        return dataDeContrato;
    }

    public void setDataDeContrato(Calendar dataDeContrato) {
        this.dataDeContrato = dataDeContrato;
    }

    public String getPathFoto() {
		return pathFoto;
	}

	public void setPathFoto(String pathFoto) {
		this.pathFoto = pathFoto;
	}


    public SituacaoCandidato getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoCandidato situacao) {
        this.situacao = situacao;
    }

    public Pais getNacionalidade() {
		if (nacionalidade==null){
            return new Pais();
        }
        return nacionalidade;
	}

	public void setNacionalidade(Pais nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

    public NecessidadeEspecial getNecessidadeEspecial() {
        return necessidadeEspecial;
    }

    public void setNecessidadeEspecial(NecessidadeEspecial necessidadeEspecial) {
        this.necessidadeEspecial = necessidadeEspecial;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

    public boolean contratoVencido() {
        if (this.dataDeContrato==null){
            return false;
        }
        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 180);
//        System.out.println(new SimpleDateFormat().format(calendar.getTime()));
//        System.out.println(new SimpleDateFormat().format(dataDeContrato.getTime()));
        if (this.dataDeContrato.before(calendar)){
            return true;
        }
        return false;
    }

}