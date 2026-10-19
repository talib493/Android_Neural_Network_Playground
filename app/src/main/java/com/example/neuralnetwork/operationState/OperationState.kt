package com.example.neuralnetwork.operationState

sealed interface OperationState<out T> {
    object Idle: OperationState<Nothing>
    object Loading: OperationState<Nothing>
    data class Success <T>(val data:T): OperationState<T>
    data class Error (val message: String): OperationState<Nothing>
}