package org.example.superid.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import superid_kmp.composeapp.generated.resources.Res
import superid_kmp.composeapp.generated.resources.logo_clara_transparente
import superid_kmp.composeapp.generated.resources.logo_transparente

@Composable
fun SuperIdTitlePainter(painter: DrawableResource = Res.drawable.logo_clara_transparente ){                        // Composable da logo do SuperId
    Image(
        painter = painterResource(painter),
        contentDescription = "SuperIdTitle",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(164.dp)
    )
}

@Composable
fun SuperIdTitlePainterVerified(){                                                                  // Composable de logo dinâmica do SuperId, que muda
    if(isSystemInDarkTheme()){                                                                      // a logo de acordo com o tema do sistema(escuro ou claroa)
        SuperIdTitlePainter()
    }else{
        SuperIdTitlePainter(Res.drawable.logo_transparente)
    }
}