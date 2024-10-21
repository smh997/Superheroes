package com.example.superheroes

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.superheroes.model.Hero
import com.example.superheroes.model.HeroRepository


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SuperheroesList(paddingValues: PaddingValues, modifier: Modifier = Modifier) {
    val state = remember { MutableTransitionState(false).apply { targetState = true } }
    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxWidth()
        ){
            itemsIndexed(HeroRepository.heroes){ index, hero ->
                SuperheroesListItem(
                    hero = hero,
                    modifier = Modifier
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.padding_medium),
                            vertical = dimensionResource(id = R.dimen.padding_small)
                        )
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = StiffnessVeryLow,
                                    dampingRatio = DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) }
                            ),
                            exit = slideOutVertically()
                        )
                )
            }
        }
    }

}
@Composable
fun SuperheroesListItem(hero: Hero, modifier: Modifier) {
    Card (
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.elevation)
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .sizeIn(minHeight = dimensionResource(id = R.dimen.image_size))
        ){
            SuperheroInformation(hero, Modifier.weight(1f))
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
            SuperheroIcon(hero.imageRes)
        }
    }
}

@Composable
fun SuperheroIcon(
    @DrawableRes heroIcon: Int, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.image_size))

    ){
        Image(
            painter = painterResource(id = heroIcon),
            contentDescription = null,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
        )
    }
}

@Composable
fun SuperheroInformation(hero: Hero, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
    ){
        Text(
            text = stringResource(id = hero.nameRes),
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = stringResource(id = hero.descriptionRes),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeroItemPreview() {
    SuperheroesListItem(hero = HeroRepository.heroes[0], modifier = Modifier.padding(
        dimensionResource(id = R.dimen.padding_medium)))
}