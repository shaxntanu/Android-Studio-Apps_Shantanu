package com.example.secretmessageapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EncoderDecoderApp() {
    var inputEncode by remember { mutableStateOf("") }
    var encodedResult by remember { mutableStateOf("") }
    var inputDecode by remember { mutableStateOf("") }
    var decodedResult by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Encoder/Decoder App",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 60.dp, bottom = 16.dp)
        )

        OutlinedTextField(
            value = inputEncode,
            onValueChange = { inputEncode = it },
            label = { Text("Enter a string to encode") },
            modifier = Modifier.fillMaxWidth(),
        )

        Button(
            onClick = {
                encodedResult = encode(inputEncode)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Encode")
        }

        SelectionContainer {
            Text(
                text = "Encoded: $encodedResult",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = inputDecode,
            onValueChange = { inputDecode = it },
            label = { Text("Enter a string to decode") },
            modifier = Modifier.fillMaxWidth(),
        )

        // Button to decode the string
        Button(
            onClick = {
                decodedResult = decode(inputDecode)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Decode")
        }

        // Display the decoded string with selection enabled
        SelectionContainer {
            Text(
                text = "Decoded: $decodedResult",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }


}

// Function to encode the string
fun encode(input: String): String {
    return input.map { char ->
        (char.code + 2).toChar() // Shift each character by 2
    }.joinToString("")
}

// Function to decode the string
fun decode(input: String): String {
    return input.map { char ->
        (char.code - 2).toChar() // Shift each character back by 2
    }.joinToString("")
}