import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blqes.digi.Login.CustomTextField

@Composable
fun LoginForm(
    phone: String,
    onPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    Column {
        Text("رقم الهاتف أو الرقم القومي")

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = phone,
            onValueChange = onPhoneChange,
            hint = "01012345678"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("كلمة المرور")

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            hint = "********",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "نسيت كلمة المرور؟",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}