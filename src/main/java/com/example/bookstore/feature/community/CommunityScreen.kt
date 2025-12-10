package com.example.bookstore.feature.community

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.bookstore.viewmodel.CommunityViewModel

@Composable
fun CommunityScreen(
    nav: NavHostController,
    viewModel: CommunityViewModel,
    userId: String
) {
    val posts by viewModel.posts.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadPosts()
    }

    Column(Modifier.fillMaxSize()) {

        if (loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyColumn(Modifier.padding(16.dp)) {
            items(posts) { post ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {

                        Text(post.content)

                        post.image_url?.let {
                            AsyncImage(
                                model = it,
                                contentDescription = null,
                                modifier = Modifier.size(120.dp)
                            )
                        }

                        Row {
                            Button(onClick = { viewModel.likePost(post) }) {
                                Text("Like (${post.likes})")
                            }

                            if (post.user_id == userId) {
                                Spacer(Modifier.width(8.dp))
                                Button(onClick = { viewModel.deletePost(post.id!!) }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))
            }
        }
    }
}
