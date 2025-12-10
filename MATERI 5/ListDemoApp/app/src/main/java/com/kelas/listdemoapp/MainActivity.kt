package com.kelas.listdemoapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelas.listdemoapp.ui.theme.ListDemoAppTheme

// Model Data MarvelCharacter
data class MarvelCharacter(
    val characterName: String,
    val realName: String,
    val imageResId: Int
)

// Sumber Data - Fungsi untuk mendapatkan daftar karakter Marvel
fun getAllMarvelCharacters(): List<MarvelCharacter> {
    return listOf(
        MarvelCharacter(
            characterName = "Iron Man",
            realName = "Tony Stark",
            imageResId = R.drawable.ic_launcher_foreground
        ),
        MarvelCharacter(
            characterName = "Thor",
            realName = "Thor Odinson",
            imageResId = R.drawable.ic_launcher_foreground
        ),
        MarvelCharacter(
            characterName = "Spider-Man",
            realName = "Peter Parker",
            imageResId = R.drawable.ic_launcher_foreground
        ),
        MarvelCharacter(
            characterName = "Captain America",
            realName = "Steve Rogers",
            imageResId = R.drawable.ic_launcher_foreground
        ),
        MarvelCharacter(
            characterName = "Black Widow",
            realName = "Natasha Romanoff",
            imageResId = R.drawable.ic_launcher_foreground
        ),
        MarvelCharacter(
            characterName = "Hulk",
            realName = "Bruce Banner",
            imageResId = R.drawable.ic_launcher_foreground
        ),
        MarvelCharacter(
            characterName = "Doctor Strange",
            realName = "Stephen Strange",
            imageResId = R.drawable.ic_launcher_foreground
        ),
        MarvelCharacter(
            characterName = "Black Panther",
            realName = "T'Challa",
            imageResId = R.drawable.ic_launcher_foreground
        )
    )
}

// Composable untuk menampilkan satu item karakter Marvel
@Composable
fun MarvelItemRow(character: MarvelCharacter, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Gambar karakter
        Image(
            painter = painterResource(id = character.imageResId),
            contentDescription = character.characterName,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        
        // Detail karakter
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = character.characterName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = character.realName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Screen utama dengan LazyColumn
@Composable
fun MarvelListScreen() {
    val context = LocalContext.current
    val marvelCharacters = getAllMarvelCharacters()
    
    LazyColumn {
        items(marvelCharacters) { character ->
            MarvelItemRow(
                character = character,
                onClick = {
                    Toast.makeText(
                        context,
                        "Anda memilih: ${character.characterName}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListDemoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MarvelListScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarvelListPreview() {
    ListDemoAppTheme {
        MarvelListScreen()
    }
}