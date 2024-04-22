package app.ify.musicappui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView () {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope : CoroutineScope = rememberCoroutineScope()

    Scaffold (
      topBar = {
       TopAppBar(title = { Text("Home")},
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
           LazyColumn(content = ) 
        }
    ){
        Text("Text", modifier = Modifier.padding(it))
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