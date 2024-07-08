package com.example.filamed2

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CadastroController {

    @FXML
    private lateinit var nomeField: TextField
    @FXML
    private lateinit var cpfField: TextField
    @FXML
    private lateinit var sexoField: TextField
    @FXML
    private lateinit var dataNascimentoField: TextField
    @FXML
    private lateinit var relatoQueixasSintomasField: TextField

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


    fun carregarTelaPrioridade(event: ActionEvent) {
        if (isDadosInseridosCorretos()) {
            if (isCpfNaoCadastrado(cpfField.text)) {
                if (isDataNascimentoCorreta(dataNascimentoField.text)) {
                    val loader = FXMLLoader(javaClass.getResource("prioridade-view.fxml"))
                    root = loader.load()
                    atualizarDadosTelaPrioridade(loader)
                    stage = (event.source as Node).scene.window as Stage
                    scene = Scene(root)
                    stage.scene = scene
                    stage.show()
                } else {
                    exibirAlertaDataInvalida()
                }

            } else {
                exibirAlertaCpfJaCadastrado()
            }
        } else {
            exibirAlertaCamposVazios()
        }
    }

    fun setDadosCadastro(fila: FilaPrioridade, qtdPacientesEnfileirados: Int, qtdCriancas: Int, qtdAdolescentes: Int,
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

        this.filaAtendidosEmergencia = filaAtendidosEmergencia
        this.filaAtendidosMuitaUrgencia = filaAtendidosMuitaUrgencia
        this.filaAtendidosUrgencia = filaAtendidosUrgencia
        this.filaAtendidosPoucaUrgencia = filaAtendidosPoucaUrgencia
        this.filaAtendidosNaoUrgencia = filaAtendidosNaoUrgencia
    }

    private fun atualizarDadosTelaPrioridade(loader: FXMLLoader) {
        val nomeCompleto: String = nomeField.text
        val cpf: String = cpfField.text
        val sexo: Char = sexoField.text[0]

        val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dataNascimentoString: String = dataNascimentoField.text
        val dataNascimento: LocalDate = LocalDate.parse(dataNascimentoString, formato)

        val relatoQueixasSintomas: String = relatoQueixasSintomasField.text
        val prioridadeController: PrioridadeController = loader.getController()



        prioridadeController.setDadosPrioridade(nomeCompleto, cpf, sexo, dataNascimento, relatoQueixasSintomas,
                qtdPacientesEnfileirados, fila, qtdCriancas, qtdAdolescentes, qtdAdultos, qtdIdosos, qtdPrioridadeEmergencia,
            qtdPrioridadeMuitaUrgencia, qtdPrioridadeUrgencia, qtdPrioridadePoucaUrgencia, qtdPrioridadeNaoUrgente, senha,
            filaAtendidosEmergencia, filaAtendidosMuitaUrgencia, filaAtendidosUrgencia, filaAtendidosPoucaUrgencia,
            filaAtendidosNaoUrgencia)
    }

    private fun isDadosInseridosCorretos(): Boolean {
        if (nomeField.text.isNotBlank() && cpfField.text.isNotBlank() && sexoField.text.isNotBlank() &&
            dataNascimentoField.text.isNotBlank() && relatoQueixasSintomasField.text.isNotBlank()) {
            return true
        } else {
            return false
        }
    }

    private fun exibirAlertaCamposVazios() {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = "Campos vazios"
        alert.headerText = "Por favor, preencha todos os campos."
        alert.contentText = "Certifique-se de que todos os campos estão preenchidos."

        alert.showAndWait()
    }

    private fun isDataNascimentoCorreta(data: String): Boolean {
        val formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        try {
            LocalDate.parse(data, formatoData)
            return true
        } catch (e: java.time.format.DateTimeParseException) {
            return false
        }
    }

    private fun exibirAlertaDataInvalida() {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = "Data inválida"
        alert.headerText = "Formato de data inválido."
        alert.contentText = "Certifique-se de que a data está no formato DD/MM/AAAA. Exemplo: 05/11/2003"

        alert.showAndWait()
    }

    private fun isCpfNaoCadastrado(cpf: String): Boolean {
        val cpfFormatado = cpf.replace("[.\\-\\s]".toRegex(), "")
        var resultado = true

        for (i in 0 until fila.size()) {
            val paciente = fila.get(i)
            val cpfPacienteJaCadastrado = paciente?.cpf?.replace("[.\\-\\s]".toRegex(), "")
            if (cpfPacienteJaCadastrado == cpfFormatado) {
                resultado = false
                break  // Interrompe o loop pois o CPF já foi encontrado
            }
        }

        return resultado
    }

    private fun exibirAlertaCpfJaCadastrado() {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = "CPF já cadastrado"
        alert.headerText = "CPF já existente."
        alert.contentText = "O CPF informado já está cadastrado no sistema."

        alert.showAndWait()
    }
}