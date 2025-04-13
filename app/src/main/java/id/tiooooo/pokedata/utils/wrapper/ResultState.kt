package id.tiooooo.pokedata.utils.wrapper

sealed class ResultState<out T> {
    data object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val throwable: String?) : ResultState<Nothing>()
}
