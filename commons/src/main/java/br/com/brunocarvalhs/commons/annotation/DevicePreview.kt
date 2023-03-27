package br.com.brunocarvalhs.commons.annotation

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    device = Devices.PHONE,
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
annotation class DevicesPreview

@Preview(
    device = Devices.PHONE,
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class DevicesPreviewDark