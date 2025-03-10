package io.github.devopMarkz.joga_facil.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_partida")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "local", nullable = false, columnDefinition = "TEXT")
    private String local;

    @Column(name = "custo_total", nullable = false)
    private Double custoTotal;

    @Column(name = "vagas_disponiveis")
    private Integer vagasDisponiveis;

    @ManyToOne
    @JoinColumn(name = "organizador_id")
    private Usuario organizador;

    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipantePartida> participantes = new ArrayList<>();

    public Partida() {
    }

    public Partida(Long id, LocalDateTime dataHora, String local, Double custoTotal, Integer vagasDisponiveis, Usuario organizador) {
        this.id = id;
        this.dataHora = dataHora;
        this.local = local;
        this.custoTotal = custoTotal;
        this.vagasDisponiveis = vagasDisponiveis;
        this.organizador = organizador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(Double custoTotal) {
        this.custoTotal = custoTotal;
    }

    public Integer getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(Integer vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }

    public List<ParticipantePartida> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipantePartida> participantes) {
        this.participantes = participantes;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Partida partida = (Partida) object;
        return Objects.equals(id, partida.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}