package com.example.yourdigitalpath.presentation.welcom_screen

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
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
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.55f)
                    .background(Color(0xFF3D5A80))
            ) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .offset(x = (240).dp, y = -40.dp)
                        .background(
                            color = Color(0x334A6D9E),
                            shape = CircleShape
                        )
                )
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = -290.dp, y = -30.dp)
                        .background(
                            color = Color(0x334A6D9E),
                            shape = CircleShape
                        )
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 40.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon1),
                        contentDescription = "icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                width = 2.dp,
                                color = Color(0x63B7B6B6),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clip(RoundedCornerShape(20.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "مسارك الذكي",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "بوابتك الرسمية لاستخراج\nوثائق الأحوال المدنية",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight(0.55f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "ابدأ الآن",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1D23)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "سجل دخولك للوصول إلى خدماتك",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(28.dp))

                    Button(
                        onClick = { navController.navigate("login_screen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3D5A80),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "تسجيل الدخول",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = { navController.navigate("register_screen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFF3D5A80))
                    ) {
                        Text(
                            text = "حساب جديد",
                            fontSize = 15.sp,
                            color = Color(0xFF3D5A80),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = Color(0xFFE4E8ED),
                            thickness = 1.dp
                        )
                        Text(
                            text = "  أو  ",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = Color(0xFFE4E8ED),
                            thickness = 1.dp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFE4E8ED))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "الدخول بالبطاقة القومية",
                                fontSize = 14.sp,
                                color = Color(0xFF3D5A80),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Image(
                                painter = painterResource(id = R.drawable.personalid),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
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