import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isPassword: Boolean = false,
    isNationalId: Boolean = false
) {

    var passwordVisible by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (isNationalId) {
                if (it.length <= 14 && it.all { char -> char.isDigit() }) {
                    onValueChange(it)
                }
            } else {
                onValueChange(it)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = hint)
        },

        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        visualTransformation =
            if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType =
                if (isNationalId)
                    KeyboardType.Number
                else if (isPassword)
                    KeyboardType.Password
                else
                    KeyboardType.Text,

            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            if (isPassword) {
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Icon(
                        imageVector =

                            if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,

                        contentDescription = null
                    )
                }
            }
        }
    )
}