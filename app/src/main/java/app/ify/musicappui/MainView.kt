package app.ify.musicappui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView () {


    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val viewModel: MainViewModel = viewModel()
    //Allow us to find out on which "View" we currently are
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val dialogOpen = remember {
        mutableStateOf(false)
    }
    val currentScreen = remember {
        viewModel.currentScreen.value
    }

    val title = remember {
        // TODO change that to currentScreen.title
        mutableStateOf(currentScreen.title)
    }

    val bottomBar: @Composable () -> Unit = {
        if (currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home){
            BottomNavigation(Modifier.wrapContentSize()){
                screensInBottom.forEach { 
                    item ->
                    BottomNavigationItem(selected = currentRoute == item.bRoute,
                        onClick = { controller.navigate(item.bRoute) },
                        icon = {
                            Icon(contentDescription = item.bTitle, painter =  painterResource(id = item.icon))
                        },
                        label = { Text(text = item.bTitle)}
                        , selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black
                    )
                }

            }
        }
    }

    Scaffold (
        bottomBar = bottomBar,
      topBar = {
       TopAppBar(title = { Text(title.value)},
         navigationIcon = { IconButton(onClick = {
             // Open the drawer
             scope.launch {
                 scaffoldState.drawerState.open()
             }
         }) {
          Icon(imageVector = Icons.Default.AccountCircle , contentDescription = "Menu")
         }}
       )

      }, scaffoldState = scaffoldState,
        drawerContent = {
           LazyColumn(Modifier.padding(16.dp)) {
              items(screensInDrawer) {
                  item -> 
                  DrawerItem(selected = currentRoute == item.dRoute, item = item) {
                     scope.launch {
                         scaffoldState.drawerState.close()
                     }
                      if (item.dRoute == "add_account") {
                         dialogOpen.value = true
                      }else {
                        controller.navigate(item.dRoute)
                          title.value = item.dTilte
                      }
                  }
              }
           }
        }
    ){
        Navigation(navController = controller, viewModel = viewModel, pd = it)
        
        AccountDialog(dialogOpen = dialogOpen)
    }
}
@SuppressLint("SuspiciousIndentation")
@Composable
  fun DrawerItem(
      selected: Boolean,
      item: Screen.DrawerScreen,
      onDrawerItemClicked : () -> Unit
  ) {
    val background = if (selected) Color.DarkGray else Color.White
      Row(
          Modifier
              .fillMaxSize()
              .padding(horizontal = 8.dp, vertical = 16.dp)
              .background(background)
              .clickable {
                  onDrawerItemClicked()
              }){
          Icon(
              painter = painterResource(id = item.icon),
              contentDescription = item.dTilte,
              Modifier.padding(end = 8.dp, top = 4.dp)
          )
          Text(
              text = item.dTilte,
             style = MaterialTheme.typography.headlineLarge,
          )

      }

  }
  @Composable
 fun Navigation(navController: NavController, viewModel: MainViewModel, pd:PaddingValues) {

     NavHost(navController as NavHostController,
         startDestination = Screen.DrawerScreen.AddAccount.route,
         modifier = Modifier.padding(pd)){


         composable(Screen.BottomScreen.Home.bRoute) {
             // TODO Add HOME SCREEN
         }

         composable(Screen.BottomScreen.Browse.bRoute)

         composable(Screen.DrawerScreen.Account.route) {
            AccountView()
         }
         composable(Screen.DrawerScreen.Subscription.route) {
            Subscription()
         }
         }

 }