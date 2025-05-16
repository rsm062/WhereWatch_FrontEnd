package com.example.wherewatch_frontend.data.repository



import com.example.wherewatch_frontend.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
/*TODO
class MovieRestRepository(val taskServiceClient: TaskServiceClient) {

    fun getTasks(): Flow<List<Movie>> =
        observeQuery(retryTime = 2000) {
            taskServiceClient
                .getTasks()
                .map { it.toTask() }
        }

    /**
     * Esta funcion sirve para hacer consultas a un servicio de manera continua
     */
    fun <T> observeQuery(retryTime: Long = 5000, query: suspend () -> List<T>): Flow<List<T>> = flow {
        var lastResult: List<T> = emptyList()
        while (true) {
            try {
                val newResult = query()
                if (newResult != lastResult) {
                    lastResult = newResult
                    emit(newResult)
                }
            } catch (e: Exception) {
                println("Error al obtener datos: ${e.message}")
            }
            delay(retryTime) // Consulta cada 5 segundos
        }
    }.flowOn(Dispatchers.IO)

    suspend fun save(task: Task) {
        taskServiceClient.createTask(CreateTaskDto.fromTask(task))
    }

    suspend fun remove(taskId: Int) {
        taskServiceClient.removeTask(taskId)
    }
}*/