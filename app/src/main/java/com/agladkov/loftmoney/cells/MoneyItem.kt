package com.agladkov.loftmoney.cells

import com.agladkov.loftmoney.remote.MoneyRemoteItem

data class MoneyItem(val title: String, val value: String) {

    companion object {
        @JvmStatic
        fun getInstance(moneyRemoteItem: MoneyRemoteItem): MoneyItem =
                MoneyItem(moneyRemoteItem.name, moneyRemoteItem.price.toString() + "$")

    }

}