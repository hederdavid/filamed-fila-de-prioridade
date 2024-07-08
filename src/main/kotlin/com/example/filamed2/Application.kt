package com.example.filamed2

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.Stage
import javafx.util.Duration

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        stage.setOnCloseRequest { event ->
            event.consume()
            confirmarFechar(stage)
        }

        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("telainicial-view.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "FILAMED"
        stage.scene = scene
        stage.show()
        val timeline = Timeline(
            KeyFrame(
                Duration.seconds(1.0), {
                    carregarTelaHome(stage)
                }
            )
        )
        timeline.play()
    }

    private fun carregarTelaHome(stage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("home-view.fxml"))
        val root = loader.load<Parent>()
        val scene = Scene(root)
        stage.scene = scene
        stage.show()
    }

    private fun confirmarFechar(primaryStage: Stage) {
        val alerta = Alert(Alert.AlertType.CONFIRMATION)
        alerta.title = "Confirmação de Fechamento";
        alerta.headerText = "Tem certeza de que deseja fechar o programa?";
        alerta.contentText = "Todas as alterações serão perdidas.";

        alerta.showAndWait().ifPresent { response: ButtonType ->
            if (response == ButtonType.OK) {
                primaryStage.close()
            }
        }
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}