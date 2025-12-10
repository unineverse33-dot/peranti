package com.example.bookstore.feature.community

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bookstore.viewmodel.CommunityViewModel

@Composable
fun CreatePostScreen(
    viewModel: CommunityViewModel,
    userId: String,
    onDone: () -> Unit
) {
    val context = LocalContext.current

    var text by remember { mutableStateOf("") }
    var imageBytes by remember { mutableStateOf<ByteArray?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            imageBytes = bytes
        }
    }

    Column(Modifier.padding(20.dp)) {

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Tuliskan postingan...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload Foto")
        }

        imageBytes?.let { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.createPost(userId, text, imageBytes)
                onDone()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Posting")
        }
    }
}
