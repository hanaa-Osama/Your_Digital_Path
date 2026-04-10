package com.example.yourdigitalpath.presentation.welcomscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yourdigitalpath.R


@Composable
fun WelcomeScreen(navController: NavController) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // الجزء العلوي
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF3B5985)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon1),
                        contentDescription = "icon",
                        modifier = Modifier
                            .size(80.dp) //
                            .border(
                                width = 2.dp,
                                color = Color(0x63B7B6B6),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        "مستنداتي",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "بوابتك الرسمية لاستخراج\n" +
                                "وثائق الأحوال المدنية",
                        color = Color.Gray,

                        )
                }
            }

            // الجزء السفلي
            Card(
                modifier = Modifier
                    .weight(1.2f)
                    .fillMaxWidth(),

                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "ابدأ الآن",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("سجل دخولك للوصول إلى خدماتك", color = Color.Gray)
                    Spacer(modifier = Modifier.height(32.dp))

                    // زر تسجيل الدخول
                    Button(
                        onClick = { navController.navigate("login_screen") },
                        modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3B5985),
                            contentColor = Color.White
                        )
                    ) {
                        Text("تسجيل الدخول")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // زر إنشاء حساب
                    OutlinedButton(
                        onClick = { navController.navigate("register_screen") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("حساب جديد",color = Color(0xFF3B5985))
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text("أو", color = Color.Gray)

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = { /* الدخول بالبطاقة */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {


                            Text("الدخول بالبطاقة القومية", color = Color(0xFF3B5985))
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.personalid),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(
                                        width = 1.dp,
                                        color = Color(0x63B7B6B6),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun prev() {
    WelcomeScreen(
        navController = NavController(LocalContext.current)
    )

}