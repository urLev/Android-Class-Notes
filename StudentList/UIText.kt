package com.goble.studentlist

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {
    class StringResource(
        @param:StringRes val resId: Int
    ): UIText()

    @Composable
    fun asString(): String {
        return when(this) {
            is StringResource -> stringResource(resId)
        }
    }
}