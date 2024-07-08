package com.example.filamed2

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.stage.Stage
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period

class ConsultarFilasController {

    private var qtdCriancas: Int = 0
    private var qtdAdolescentes: Int = 0
    private var qtdAdultos: Int = 0
    private var qtdIdosos: Int = 0

    private lateinit var fila: FilaPrioridade
    private var filaAtendidosEmergencia = FilaPrioridade(10)
    private var filaAtendidosMuitaUrgencia = FilaPrioridade(10)
    private var filaAtendidosUrgencia = FilaPrioridade(10)
    private var filaAtendidosPoucaUrgencia = FilaPrioridade(10)
    private var filaAtendidosNaoUrgencia = FilaPrioridade(10)
    private var qtdPacientesEnfileirados = 0

    private var qtdPrioridadeEmergencia = 0
    private var qtdPrioridadeMuitaUrgencia = 0
    private var qtdPrioridadeUrgencia = 0
    private var qtdPrioridadePoucaUrgencia = 0
    private var qtdPrioridadeNaoUrgente = 0
    private var senha: String = "Senha não definida"

    private lateinit var stage: Stage
    private lateinit var scene: Scene
    private lateinit var root: Parent

    @FXML
    fun desenfileirarPrioridadeEmergencia(event: ActionEvent) {
        val pacienteDesenfileirado: Paciente? = fila.desenfileirar(5)
        if (pacienteDesenfileirado != null) {
            qtdPacientesEnfileirados--
            val idade = calcularIdade(pacienteDesenfileirado.dataNascimento)
            when {
                idade in 0..11 && qtdCriancas != 0 -> qtdCriancas--
                idade in 12..17 && qtdAdolescentes != 0 -> qtdAdolescentes--
                idade in 18..59 && qtdAdultos != 0 -> qtdAdultos--
                idade >= 60 && qtdIdosos != 0 -> qtdIdosos--
            }
            alertaInformacoesPacienteDesenfileirado(pacienteDesenfileirado, event)
        } else {
            alertaPacienteNulo(event)
        }

    }

    fun desenfileirarPrioridadeMuitaUrgencia(event: ActionEvent) {
        val pacienteDesenfileirado: Paciente? = fila.desenfileirar(4)
        if (pacienteDesenfileirado != null) {
            qtdPacientesEnfileirados--
            val idade = calcularIdade(pacienteDesenfileirado.dataNascimento)
            when {
                idade in 0..11 && qtdCriancas != 0 -> qtdCriancas--
                idade in 12..17 && qtdAdolescentes != 0 -> qtdAdolescentes--
                idade in 18..59 && qtdAdultos != 0 -> qtdAdultos--
                idade >= 60 && qtdIdosos != 0 -> qtdIdosos--
            }
            alertaInformacoesPacienteDesenfileirado(pacienteDesenfileirado, event)
        } else {
            alertaPacienteNulo(event)
        }
    }

    fun desenfileirarPrioridadeUrgencia(event: ActionEvent) {

        val pacienteDesenfileirado: Paciente? = fila.desenfileirar(3)
        if (pacienteDesenfileirado != null) {
            qtdPacientesEnfileirados--
            val idade = calcularIdade(pacienteDesenfileirado.dataNascimento)
            when {
                idade in 0..11 && qtdCriancas != 0 -> qtdCriancas--
                idade in 12..17 && qtdAdolescentes != 0 -> qtdAdolescentes--
                idade in 18..59 && qtdAdultos != 0 -> qtdAdultos--
                idade >= 60 && qtdIdosos != 0 -> qtdIdosos--
            }
            alertaInformacoesPacienteDesenfileirado(pacienteDesenfileirado, event)
        } else {
            alertaPacienteNulo(event)
        }
    }

    fun desenfileirarPrioridadePoucaUrgencia(event: ActionEvent) {
        val pacienteDesenfileirado: Paciente? = fila.desenfileirar(2)
        if (pacienteDesenfileirado != null) {
            qtdPacientesEnfileirados--
            val idade = calcularIdade(pacienteDesenfileirado.dataNascimento)
            when {
                idade in 0..11 && qtdCriancas != 0 -> qtdCriancas--
                idade in 12..17 && qtdAdolescentes != 0 -> qtdAdolescentes--
                idade in 18..59 && qtdAdultos != 0 -> qtdAdultos--
                idade >= 60 && qtdIdosos != 0 -> qtdIdosos--
            }
            alertaInformacoesPacienteDesenfileirado(pacienteDesenfileirado, event)
        } else {
            alertaPacienteNulo(event)
        }
    }

    fun desenfileirarPrioridadeNaoUrgencia(event: ActionEvent) {
        val pacienteDesenfileirado: Paciente? = fila.desenfileirar(1)
        if (pacienteDesenfileirado != null) {
            qtdPacientesEnfileirados--
            val idade = calcularIdade(pacienteDesenfileirado.dataNascimento)
            when {
                idade in 0..11 && qtdCriancas != 0 -> qtdCriancas--
                idade in 12..17 && qtdAdolescentes != 0 -> qtdAdolescentes--
                idade in 18..59 && qtdAdultos != 0 -> qtdAdultos--
                idade >= 60 && qtdIdosos != 0 -> qtdIdosos--
            }
            alertaInformacoesPacienteDesenfileirado(pacienteDesenfileirado, event)
        } else {
            alertaPacienteNulo(event)
        }
    }

    private fun alertaPacienteNulo(event: ActionEvent) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "Informações"
        alert.headerText = "Nenhum paciente na fila!"
        alert.contentText = "A fila de pacientes está vazia, nenhum paciente desenfileirado!"
        alert.setOnCloseRequest {
            carregarTelaHome(event)
        }
        alert.showAndWait()
    }

    private fun alertaInformacoesPacienteDesenfileirado(paciente: Paciente, event: ActionEvent) {
        paciente.dataHoraDesenfileiramento = LocalDateTime.now()
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "Informações"
        alert.headerText = "Paciente atendido"
        alert.contentText = "Nome do paciente: " + paciente.nomeCompleto + " | Senha: " + paciente.senha + " | Tempo de permanência na fila: " +
                paciente.calcularTempoNaFila()
        alert.setOnCloseRequest {
            carregarTelaHome(event)
        }
        alert.showAndWait()


    }

    fun setDadosConsultarFilas(fila: FilaPrioridade, qtdPacientesEnfileirados: Int, qtdCriancas: Int, qtdAdolescentes: Int,
                             qtdAdultos: Int, qtdIdosos: Int, qtdPrioridadeEmergencia: Int, qtdPrioridadeMuitaUrgencia:
                             Int, qtdPrioridadeUrgencia: Int, qtdPrioridadePoucaUrgencia: Int, qtdPrioridadeNaoUrgente:
                             Int, senha: String, filaAtendidosEmergencia: FilaPrioridade, filaAtendidosMuitaUrgencia: FilaPrioridade,
                             filaAtendidosUrgencia: FilaPrioridade, filaAtendidosPoucaUrgencia: FilaPrioridade,
                             filaAtendidosNaoUrgencia: FilaPrioridade) {
        this.fila = fila
        this.qtdPacientesEnfileirados = qtdPacientesEnfileirados
        this.qtdCriancas = qtdCriancas
        this.qtdAdolescentes = qtdAdolescentes
        this.qtdAdultos = qtdAdultos
        this.qtdIdosos = qtdIdosos

        this.qtdPrioridadeEmergencia = qtdPrioridadeEmergencia
        this.qtdPrioridadeMuitaUrgencia = qtdPrioridadeMuitaUrgencia
        this.qtdPrioridadeUrgencia = qtdPrioridadeUrgencia
        this.qtdPrioridadePoucaUrgencia = qtdPrioridadePoucaUrgencia
        this.qtdPrioridadeNaoUrgente = qtdPrioridadeNaoUrgente
        this.senha = senha

        this.filaAtendidosEmergencia = filaAtendidosEmergencia
        this.filaAtendidosMuitaUrgencia = filaAtendidosMuitaUrgencia
        this.filaAtendidosUrgencia = filaAtendidosUrgencia
        this.filaAtendidosPoucaUrgencia = filaAtendidosPoucaUrgencia
        this.filaAtendidosNaoUrgencia = filaAtendidosNaoUrgencia
    }

    private fun carregarTelaHome(event: ActionEvent) {
        val loader = FXMLLoader(javaClass.getResource("home-view.fxml"))
        root = loader.load()

        atualizarDadosTelaHome(loader)

        stage = (event.source as Node).scene.window as Stage
        scene = Scene(root)
        stage.scene = scene
        stage.show()
    }

    private fun atualizarDadosTelaHome(loader: FXMLLoader) {
        var nome = "NENHUM PACIENTE NA FILA"
        var idade = "0"
        var prioridade = "NÃO DEFINIDA"
        val homeController: HomeController = loader.getController()
        if (fila.espiar()?.nomeCompleto != null) {
            nome = fila.espiar()!!.nomeCompleto
        }

        if (fila.espiar()?.dataNascimento?.let { calcularIdade(it) } != null ) {
            idade = calcularIdade(fila.espiar()!!.dataNascimento).toString()
        }

        if (fila.espiar()?.prioridade != null) {
            if (fila.espiar()?.prioridade == 5) {
                prioridade = "Emergência"
            } else if (fila.espiar()?.prioridade == 4) {
                prioridade = "Muita urgência"
            } else if (fila.espiar()?.prioridade == 3) {
                prioridade = "Urgência"
            } else if (fila.espiar()?.prioridade == 2) {
                prioridade = "Pouca urgência"
            } else if (fila.espiar()?.prioridade == 1) {
                prioridade = "Não urgência"
            }
        }
        homeController.setDadosHome(nome, idade, prioridade,  qtdPacientesEnfileirados, fila,
            qtdCriancas, qtdAdolescentes, qtdAdultos, qtdIdosos, qtdPrioridadeEmergencia, qtdPrioridadeMuitaUrgencia,
            qtdPrioridadeUrgencia, qtdPrioridadePoucaUrgencia, qtdPrioridadeNaoUrgente, senha, filaAtendidosEmergencia, filaAtendidosMuitaUrgencia, filaAtendidosUrgencia, filaAtendidosPoucaUrgencia,
            filaAtendidosNaoUrgencia)
    }

    private fun calcularIdade(dataNascimento: LocalDate) : Int {
        var dataAtual: LocalDate = LocalDate.now()
        var periodo: Period = Period.between(dataNascimento, dataAtual)
        var idade: Int = periodo.years
        return idade
    }
}