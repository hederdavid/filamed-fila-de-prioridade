package com.example.filamed2

interface Enfileiravel {
    fun enfileirar(paciente: Paciente)
    fun desenfileirar(): Paciente?
    fun atualizar(paciente: Paciente)
    fun espiar(): Paciente?
    fun estaCheia(): Boolean
    fun estaVazia(): Boolean
    fun imprimir(): String
}