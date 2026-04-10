import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LoginHeader() {
    Column {
        Text(
            text = "مرحباً بك",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "سجل دخولك للمتابعة",
            color = Color.Gray
        )
    }
}