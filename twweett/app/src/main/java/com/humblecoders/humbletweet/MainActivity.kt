package com.humblecoders.humbletweet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwitterApp()
        }
    }
}

data class Tweet(
    val authorName: String = "",
    val content: String = ""
)

@Composable
fun TwitterApp() {
    val db = Firebase.firestore
    var authorName by remember { mutableStateOf("") }
    var tweetContent by remember { mutableStateOf("") }
    var tweets by remember { mutableStateOf<List<Tweet>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Humble Twitter",
            fontSize = 50.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = authorName,
            onValueChange = { authorName = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = tweetContent,
            onValueChange = { tweetContent = it },
            label = { Text("Tweet") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                db.collection("tweets").add(Tweet(authorName, tweetContent))
                //tweetContent = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Post")
        }


        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                db.collection("tweets").get().addOnSuccessListener { documents ->
                    tweets = documents.map { doc ->
                        Tweet(
                            doc.getString("authorName") ?: "",
                            doc.getString("content") ?: ""
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Load Tweets")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tweets) { tweet ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(tweet.authorName)
                        Text(tweet.content)
                    }
                }
            }
        }
    }
}