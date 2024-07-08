package com.example.filamed2

import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.time.LocalDate
import java.time.Period


class PrioridadeController {

    private lateinit var nomeCompleto: String
    private lateinit var cpf: String
    private var sexo: Char = '\u0000'
    private lateinit var dataNascimento: LocalDate
    private lateinit var relatoQueixasSintomas: String
    private var prioridade: Int = 0
    private var prioridadeString: String = ""

    private var qtdPacientesEnfileirados = 0
    private lateinit var fila: FilaPrioridade
    private var filaAtendidosEmergencia = FilaPrioridade(10)
    private var filaAtendidosMuitaUrgencia = FilaPrioridade(10)
    private var filaAtendidosUrgencia = FilaPrioridade(10)
    private var filaAtendidosPoucaUrgencia = FilaPrioridade(10)
    private var filaAtendidosNaoUrgencia = FilaPrioridade(10)

    private var qtdCriancas: Int = 0
    private var qtdAdolescentes: Int = 0
    private var qtdAdultos: Int = 0
    private var qtdIdosos: Int = 0

    private var qtdPrioridadeEmergencia = 0
    private var qtdPrioridadeMuitaUrgencia = 0
    private var qtdPrioridadeUrgencia = 0
    private var qtdPrioridadePoucaUrgencia = 0
    private var qtdPrioridadeNaoUrgente = 0
    private var senha: String = "Senha não definida"

    private lateinit var stage: Stage
    private lateinit var scene: Scene
    private lateinit var root: Parent

    private fun chamarTelaHome(event: ActionEvent) {
        val loader = FXMLLoader(javaClass.getResource("home-view.fxml"))
        root = loader.load()

        inserirPacienteCadastrado()
        println(fila).toString()
        println(qtdPacientesEnfileirados)

        atualizarDadosTelaHome(loader)

        //root = FXMLLoader.load(javaClass.getResource("home-view.fxml"))
        stage = (event.source as Node).scene.window as Stage
        scene = Scene(root)
        stage.scene = scene
        stage.show()
    }

    private fun inserirPacienteCadastrado() {
        val paciente = Paciente(nomeCompleto, cpf, sexo, dataNascimento, relatoQueixasSintomas, prioridade)
        paciente.senha = this.senha
        fila.enfileirar(paciente)
        qtdPacientesEnfileirados++
    }

    private fun atualizarDadosTelaHome(loader: FXMLLoader) {
        val homecontroller: HomeController = loader.getController()

        val dataNascimentoAtual = dataNascimento

        nomeCompleto = fila.espiar()?.nomeCompleto ?: "Erro ao obter nome"
        dataNascimento = (fila.espiar()?.dataNascimento ?: "Erro ao obter idade") as LocalDate

        if (fila.espiar()?.prioridade == 5) {
            prioridadeString = "Emergência"
        } else if (fila.espiar()?.prioridade == 4) {
            prioridadeString = "Muita Urgência"
        } else if (fila.espiar()?.prioridade == 3) {
            prioridadeString = "Urgência"
        } else if (fila.espiar()?.prioridade == 2) {
        prioridadeString = "Pouca Urgência"
        } else if (fila.espiar()?.prioridade == 1) {
            prioridadeString = "Não Urgência"
        }

        var dataAtual: LocalDate = LocalDate.now()
        var periodo: Period = Period.between(dataNascimentoAtual, dataAtual)
        var idade: Int = periodo.years

        if (idade in 0..11) {
            qtdCriancas++
        } else if (idade in 12..17) {
            qtdAdolescentes++
        } else if (idade in 18..59) {
            qtdAdultos++
        } else if (idade >= 60) {
            qtdIdosos++
        }

        periodo = Period.between(dataNascimento, dataAtual)
        idade = periodo.years

        homecontroller.setDadosHome(nomeCompleto, idade.toString(), prioridadeString, qtdPacientesEnfileirados, fila,
            qtdCriancas, qtdAdolescentes, qtdAdultos, qtdIdosos, qtdPrioridadeEmergencia, qtdPrioridadeMuitaUrgencia,
            qtdPrioridadeUrgencia, qtdPrioridadePoucaUrgencia, qtdPrioridadeNaoUrgente, senha, filaAtendidosEmergencia, filaAtendidosMuitaUrgencia, filaAtendidosUrgencia, filaAtendidosPoucaUrgencia,
            filaAtendidosNaoUrgencia)
    }

    fun setDadosPrioridade(nomeCompleto: String, cpf: String, sexo: Char, dataNascimento: LocalDate, relatoQueixasSintomas: String,
                 qtdPacientesEnfileirados: Int, fila: FilaPrioridade, qtdCriancas: Int, qtdAdolescentes: Int,
                 qtdAdultos: Int, qtdIdosos: Int, qtdPrioridadeEmergencia: Int, qtdPrioridadeMuitaUrgencia: Int,
                 qtdPrioridadeUrgencia: Int, qtdPrioridadePoucaUrgencia: Int, qtdPrioridadeNaoUrgente: Int, senha: String,
                           filaAtendidosEmergencia: FilaPrioridade, filaAtendidosMuitaUrgencia: FilaPrioridade,
                           filaAtendidosUrgencia: FilaPrioridade, filaAtendidosPoucaUrgencia: FilaPrioridade,
                           filaAtendidosNaoUrgencia: FilaPrioridade) {
        this.nomeCompleto = nomeCompleto
        this.cpf = cpf
        this.sexo = sexo
        this.dataNascimento = dataNascimento
        this.relatoQueixasSintomas = relatoQueixasSintomas
        this.qtdPacientesEnfileirados = qtdPacientesEnfileirados
        this.fila = fila
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


    fun prioridadeEmergencia(event: ActionEvent) {
        this.prioridade = 5
        this.qtdPrioridadeEmergencia++
        this.senha = "R$qtdPrioridadeEmergencia"
        chamarTelaHome(event)
    }

    fun prioridadeMuitaUrgencia(event: ActionEvent) {
        this.prioridade = 4
        this.qtdPrioridadeMuitaUrgencia++
        this.senha = "O$qtdPrioridadeMuitaUrgencia"
        chamarTelaHome(event)
    }

    fun prioridadeUrgencia(event: ActionEvent) {
        this.prioridade = 3
        this.qtdPrioridadeUrgencia++
        this.senha = "Y$qtdPrioridadeUrgencia"
        chamarTelaHome(event)
    }

    fun prioridadePoucaUrgencia(event: ActionEvent) {
        this.prioridade = 2
        this.qtdPrioridadePoucaUrgencia++
        this.senha = "G$qtdPrioridadePoucaUrgencia"
        chamarTelaHome(event)
    }
    fun prioridadeNaoUrgencia(event: ActionEvent) {
        this.prioridade = 1
        this.qtdPrioridadeNaoUrgente++
        this.senha = "B$qtdPrioridadeNaoUrgente"
        chamarTelaHome(event)
    }

}