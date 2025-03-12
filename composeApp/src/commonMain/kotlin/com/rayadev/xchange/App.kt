package com.rayadev.xchange

import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.rayadev.xchange.presentation.exchange.ExchangeScreen
import com.rayadev.xchange.ui.CustomTheme
import com.rayadev.xchange.presentation.exchange.ExchangeViewModel
import org.jetbrains.compose.resources.stringResource
import xchange.composeapp.generated.resources.Res
import xchange.composeapp.generated.resources.app_name

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(sharedViewModel: ExchangeViewModel) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var reloadTrigger by remember { mutableStateOf(0) }

    CustomTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.app_name),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    actions = {
                        IconButton(onClick = { reloadTrigger++ }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Recargar",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    scrollBehavior = scrollBehavior,
                    modifier = Modifier.shadow(elevation = 4.dp)
                )
            },
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .wrapContentSize(Alignment.Center)
        ) { paddingValues ->
            // La recomposición se dispara cuando reloadTrigger cambia
            ExchangeScreen(sharedViewModel, paddingValues, reloadTrigger)
        }
    }
}