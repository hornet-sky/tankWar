package my.business

import my.model.View

interface Destroyable: View {
    fun isDestroy(): Boolean
}