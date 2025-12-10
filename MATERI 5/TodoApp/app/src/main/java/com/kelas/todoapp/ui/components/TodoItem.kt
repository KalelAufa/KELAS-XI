package com.kelas.todoapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelas.todoapp.model.Todo
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TodoItem(
    todo: Todo,
    onDelete: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Format tanggal dan waktu
            val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
            val timeString = timeFormat.format(todo.createdAt)
            val dateString = dateFormat.format(todo.createdAt)
            
            Text(
                text = "$timeString - $dateString",
                color = Color.White,
                fontSize = 12.sp
            )
            
            Text(
                text = todo.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        IconButton(
            onClick = { onDelete(todo.id) }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Todo",
                tint = Color.White
            )
        }
    }
}