import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.text.style.TextAlign


@Composable
fun LoginHeader() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    Column {
        Text(
            text = "مرحباً بك",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.End
        )

        Text(
            text = "سجل دخولك للمتابعة",
            color = Color.Gray
        )
    }}
}