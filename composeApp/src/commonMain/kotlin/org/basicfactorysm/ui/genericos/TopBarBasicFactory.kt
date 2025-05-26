package org.basicfactorysm.ui.genericos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.logo_factory_v2
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource


@Composable
fun TopBarBasicFactory(screen: String, navigator: Navigator) {
    TopAppBar(
        title = {
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(Res.drawable.logo_factory_v2),
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight().padding(vertical = 4.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(screen)

            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(40.dp).clickable { navigator.goBack() }
            )
        }
    )
}