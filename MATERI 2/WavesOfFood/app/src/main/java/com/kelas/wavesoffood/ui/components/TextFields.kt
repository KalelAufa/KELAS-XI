package com.kelas.wavesoffood.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kelas.wavesoffood.ui.theme.*

// Standard Input Field
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = placeholder?.let { { Text(it) } },
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = if (isError) ErrorRed else TextSecondary
                    )
                }
            },
            trailingIcon = trailingIcon?.let {
                {
                    IconButton(onClick = onTrailingIconClick ?: {}) {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = if (isError) ErrorRed else TextSecondary
                        )
                    }
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction?.invoke() },
                onNext = { onImeAction?.invoke() },
                onSearch = { onImeAction?.invoke() }
            ),
            enabled = enabled,
            readOnly = readOnly,
            maxLines = maxLines,
            minLines = minLines,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) ErrorRed else GreenPrimary,
                unfocusedBorderColor = if (isError) ErrorRed else DividerColor,
                focusedLabelColor = if (isError) ErrorRed else GreenPrimary,
                unfocusedLabelColor = TextSecondary,
                cursorColor = GreenPrimary,
                errorBorderColor = ErrorRed,
                errorLabelColor = ErrorRed
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = ErrorRed,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

// Password Input Field
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: (() -> Unit)? = null,
    enabled: Boolean = true
) {
    var passwordVisible by remember { mutableStateOf(false) }
    
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        leadingIcon = Icons.Default.Lock,
        trailingIcon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        isError = isError,
        errorMessage = errorMessage,
        keyboardType = KeyboardType.Password,
        imeAction = imeAction,
        onImeAction = onImeAction,
        enabled = enabled,
        modifier = modifier
    )
}

// Search Input Field
@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    onSearchClick: (() -> Unit)? = null,
    onClearClick: (() -> Unit)? = null,
    enabled: Boolean = true
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        label = "",
        placeholder = placeholder,
        leadingIcon = Icons.Default.Search,
        trailingIcon = if (value.isNotEmpty()) Icons.Default.Clear else null,
        onTrailingIconClick = onClearClick,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Search,
        onImeAction = onSearchClick,
        enabled = enabled,
        modifier = modifier
    )
}
