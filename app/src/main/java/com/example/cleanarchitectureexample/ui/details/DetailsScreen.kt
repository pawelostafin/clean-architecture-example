package com.example.cleanarchitectureexample.ui.details

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.cleanarchitectureexample.ui.settings.BackButton
import com.example.cleanarchitectureexample.ui.theme.AppTheme

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {

    val backButtonId = remember { "backButtonId" }
    val constraints = remember {
        createConstraints(
            backButtonId = backButtonId
        )
    }

    ConstraintLayout(
        modifier = Modifier
            .background(AppTheme.colors.backgroundPrimary),
        constraintSet = constraints
    ) {
        BackButton(
            modifier = Modifier.layoutId(backButtonId),
            onClick = { viewModel.backButtonClicked() }
        )
    }
}

private fun createConstraints(
    backButtonId: String
): ConstraintSet {
    return ConstraintSet {
        val backButton = createRefFor(backButtonId)

        constrain(backButton) {
            top.linkTo(parent.top, 8.dp)
            start.linkTo(parent.start, 8.dp)
        }
    }
}