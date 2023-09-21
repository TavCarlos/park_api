package com.demoparkapi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente_vaga")
@Entity
public class ClienteVaga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "numero_recibo", unique = true, nullable = false, length = 15)
	private String recibo;
	@Column(name = "placa", nullable = false, length = 8)
	private String placa;
	@Column(name = "marca", nullable = false, length = 45)
	private String marca;
	@Column(name = "modelo", nullable = false, length = 45)
	private String modelo;
	@Column(name = "cor", nullable = false, length = 45)
	private String cor;
	@Column(name = "data_entrada", nullable = false)
	private LocalDateTime dataEntrada;
	@Column(name = "data_saida")
	private LocalDateTime dataSaida;
	@Column(name = "valor", columnDefinition = "decimal(7,2)")
	private BigDecimal valor;
	@Column(name = "desconto", columnDefinition = "decimal(7,2)")
	private BigDecimal desconto;
	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "id_vaga", nullable = false)
	private Vaga vaga;
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteVaga other = (ClienteVaga) obj;
		return Objects.equals(id, other.id);
	}
}
