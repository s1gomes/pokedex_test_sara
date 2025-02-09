package com.dominio.pokedex.main.pagination

import com.dominio.pokedex.main.model.PokemonsModel
import retrofit2.Response

class Paginator (
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (offset: Int, limit: Int) -> Response<PokemonsModel>?,
    private inline val onError: suspend (String?) -> Unit,
    private inline val onSuccess: suspend (pokemonsModel: PokemonsModel) -> Unit
) {
    private var offset: Int = 0
    private var limit: Int = 150
    private var isMakingRequest = false

    suspend fun loadNextItems() {
        if(isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(offset, limit)
        isMakingRequest = false
        val items = result?.body()
         if (result?.isSuccessful == false || items == null) {
            onError(result?.message())
            onLoadUpdated(false)
            return
        }
        offset += limit
        onSuccess(items)
        onLoadUpdated(false)
    }
}