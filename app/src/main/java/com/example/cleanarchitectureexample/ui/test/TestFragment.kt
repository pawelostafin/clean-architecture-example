package com.example.cleanarchitectureexample.ui.test

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.navigation.navigate
import com.example.cleanarchitectureexample.navigation.navigateBack
import com.example.cleanarchitectureexample.ui.base.BaseComposeFragment
import com.example.cleanarchitectureexample.ui.base.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : BaseComposeFragment<TestViewModel>() {

    override val viewModel: TestViewModel by viewModel()

    @Composable
    fun animatedBackgroundColorState(viewModel: TestViewModel): State<Color> {
        val rgbBackgroundColor by viewModel.backgroundColor.collectAsState()
        val backgroundColor = remember(rgbBackgroundColor) { Color.fromRgbColor(rgbBackgroundColor) }
        return animateColorAsState(
            targetValue = backgroundColor,
            animationSpec = tween(
                durationMillis = 1000
            )
        )
    }

    @Composable
    override fun ContentView() {
        val text by viewModel.text.collectAsState()
        val animatedColor by animatedBackgroundColorState(viewModel)

        Column(
            modifier = Modifier.background(animatedColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.background(Color.Gray),
                text = text
            )
            Button(onClick = viewModel::testButtonClicked) {
                Text(text = "Click to test")
            }
            Spacer(modifier = Modifier.requiredHeight(20.dp))
            Button(onClick = viewModel::changeBackgroundButtonClicked) {
                Text(text = "change background")
            }
            Spacer(modifier = Modifier.requiredHeight(20.dp))
            Button(onClick = viewModel::navigateButtonClicked) {
                Text(text = "navigate")
            }
        }
    }

    override fun onBackPressed() {
        viewModel.backButtonClicked()
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.navigation, ::handleNavigation)
    }

    private fun handleNavigation(navigation: TestViewModel.Navigation) {
        when (navigation) {
            TestViewModel.Navigation.Back -> navigateBack()
            TestViewModel.Navigation.Next -> navigate(fragmentResId = R.id.testFragment)
        }
    }

}

fun Color.Companion.fromRgbColor(rgbColor: RgbColor): Color {
    return Color(
        red = rgbColor.red,
        green = rgbColor.green,
        blue = rgbColor.blue
    )
}