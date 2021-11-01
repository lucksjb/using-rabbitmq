package com.example.demo.models;

import java.time.LocalDate;

import com.example.demo.config.jackson.LocalDateToStringConverter;
import com.example.demo.config.jackson.StringToLocalDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Cliente {
    private Long id;
    private String nome;

    @JsonSerialize(converter = LocalDateToStringConverter.class)
    @JsonDeserialize(converter = StringToLocalDateConverter.class)
    private LocalDate nascimento;

    
    public Cliente() {
    }

    public Cliente(Long id, String nome, LocalDate nascimento) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nascimento=" + nascimento + ", nome=" + nome + "]";
    }

     public static class Builder {
        private Cliente cliente = new Cliente();

        public static Builder newCliente() {
            return new Builder();
        }

        public Builder addId(Long id) {
            this.cliente.setId(id);
            return this;
        }

        public Builder addNome(String nome) {
            this.cliente.setNome(nome);
            return this;
        }

        public Builder addNascimento(LocalDate nascimento) {
            this.cliente.setNascimento(nascimento);
            return this;
        }

        public Cliente build() {
            return cliente;
        }
    }

}
