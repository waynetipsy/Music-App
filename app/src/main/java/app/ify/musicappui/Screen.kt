package app.ify.musicappui

import androidx.annotation.DrawableRes

sealed class Screen(val title: String, val route: String ) {
    sealed class DrawerScreen(val dTilte: String, val dRoute: String, @DrawableRes val icon: Int)
        : Screen(dTilte, dRoute) {
            object Account: DrawerScreen(
                dTilte = "Account",
                dRoute = "account",
                R.drawable.ic_account
            )
         object Subscription: DrawerScreen(
             dTilte = "Subscription",
             dRoute = "subscribe",
             R.drawable.ic_subscribe
         )
        object AddAccount: DrawerScreen(
            dTilte = "Add Account",
            dRoute = "add_account",
            R.drawable.baseline_person_add_alt_1_24
        )
        }
}