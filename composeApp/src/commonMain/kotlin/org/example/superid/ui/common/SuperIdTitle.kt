package org.example.superid.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import superid_kmp.composeapp.generated.resources.Res
import superid_kmp.composeapp.generated.resources.fonte_titulo

@Composable
fun SuperIdTitle(modifier: Modifier = Modifier, isOnMainScreen: Boolean = true, fontSize: TextUnit = 28.sp){                        // Composable do titulo
    val title_font = FontFamily(Font(Res.font.fonte_titulo))                                                                          // da logo do SuperId
    val superIdColor = if (isOnMainScreen) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onBackground   // que também varia
    Text(                                                                                                                           // de acordo com o tema
        buildAnnotatedString {                                                                                                      // usa string anotada
            withStyle(                                                                                                              // para combinar diferentes
                style = SpanStyle(fontFamily = title_font, fontSize = fontSize, color = superIdColor,                               // estilos
                    shadow = Shadow(Color.DarkGray, offset = Offset(1f, 1f),blurRadius = 4f)
                )
            ){
                append("Super")
            }
            withStyle(
                style = SpanStyle(fontFamily = title_font, fontSize = fontSize, color = Color(0xFF014E92),
                    shadow = Shadow(Color.DarkGray, offset = Offset(1f, 1f),blurRadius = 4f)
                )
            ){
                append(" ID")
            }
        },
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
}