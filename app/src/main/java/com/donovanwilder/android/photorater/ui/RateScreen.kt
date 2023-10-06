package com.donovanwilder.android.photorater.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.donovanwilder.android.photorater.R
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Preview
@Composable
fun RateScreenPreview() {
    RateScreen()
}

@Composable
fun RateScreen(modifier: Modifier = Modifier, viewModel: RateViewModel = viewModel()) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = viewModel.imageUrl.collectAsState()
        val image: String = if(imageUrl.value.isEmpty()) "" else imageUrl.value
        viewModel.getNextEntry()
        val painter = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .build()
        AsyncImage(
            model = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        RatingComponent(viewModel = viewModel)
        Button(onClick = { viewModel.getNextEntry() }) {
            Text(text = "Submit")
        }
    }
}

@Composable
fun RatingComponent(modifier: Modifier = Modifier, viewModel: RateViewModel) {
    val ratingsValue = viewModel.ratingsValue.collectAsState()
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = viewModel.ratingsText)
        Slider(value = ratingsValue.value, onValueChange = {
            viewModel.setRating(it)
        })
    }
}