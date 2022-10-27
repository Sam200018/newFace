package com.example.newface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.newface.ui.theme.NewFaceTheme
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewFaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(
    viewModel: UserViewModel = hiltViewModel()
) {
    val users by viewModel.users.observeAsState(arrayListOf())
    val isLoading by viewModel.isLoading.observeAsState(false)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Calling New Face")
                },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 10.dp,
                actions = {
                    IconButton(onClick = { viewModel.addUser() }) {
                        Icon(Icons.Filled.Add, contentDescription = "add")
                    }
                }
            )
        },

        ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn {
                var usersCount = users.size
                if (isLoading) usersCount ++
                items(count = users.size) { index ->
                    var auxIndex = index
                    if (isLoading) {
                        if (auxIndex == 0)
                            return@items IsLoadingCard()
                        auxIndex--
                    }
                    val user = users[index]
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 1.dp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxWidth()
                    ) {
                        Row(Modifier.padding(8.dp)) {
                            Image(
                                modifier = Modifier.size(50.dp), painter = rememberImagePainter(
                                    data = user.thumbnail,
                                    builder = {

                                    }
                                ),
                                contentDescription = "user image",
                                contentScale = ContentScale.FillHeight
                            )
                            Spacer()
                            Column(Modifier.weight(1f)) {
                                Text(text = "${user.name} ${user.lastName}")
                                Text(text = user.city)
                            }
                            Spacer()
                            IconButton(onClick = { viewModel.deleteUser(user) },) {
                                Icon(Icons.Filled.Delete, "delete user")
                            }
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun IsLoadingCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .testTag("loadingCard")
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            ImageIsLoading()
            Spacer()
            Column {
                Spacer()
                Box(modifier = Modifier.shimmer()) {
                    Column {
                        Box(
                            Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .background(Color.Gray)
                        ) {


                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Spacer(size: Int = 8) =
    Spacer(modifier = Modifier.size(size.dp))

@Composable
fun ImageIsLoading() {
    Box(modifier = Modifier.shimmer()) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray)
        ) {

        }
    }
}