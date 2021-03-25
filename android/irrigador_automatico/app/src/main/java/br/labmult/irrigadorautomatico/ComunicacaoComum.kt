package br.labmult.irrigadorautomatico

interface ComunicacaoComum {
    fun modificaDispositivoBluetooth(dispositivo: BluetoothInfo)
    fun obterDispositivoBluetoot(): BluetoothInfo?
}