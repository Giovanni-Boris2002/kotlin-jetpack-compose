package com.example.projecto_suarez.presentation.app_navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.projecto_suarez.R
import com.example.projecto_suarez.data.remote.dto.GeneralResponse
import com.example.projecto_suarez.data.remote.dto.LabResponse
import com.example.projecto_suarez.presentation.app_navigator.components.BottomNavigation

import com.example.projecto_suarez.presentation.home.HomeScreen
import com.example.projecto_suarez.presentation.home.HomeViewModel
import com.example.projecto_suarez.presentation.navgraph.Route
import com.example.projecto_suarez.presentation.app_navigator.components.BottomNavigationItem
import com.example.projecto_suarez.presentation.details.DetailsScreen
import com.example.projecto_suarez.presentation.details.DetailsViewModel

@Composable
fun AppNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Labs"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Mylabs"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2

        else -> 0
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route

    }
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            BottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Route.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Route.BookmarkScreen.route
                        )
                    }
                }
            )
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                viewModel.getCourses();
                val state = viewModel.list.value
                HomeScreen(
                    articles = state,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = {
                    }
                )
            }
            composable(route = Route.SearchScreen.route) { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                viewModel.getLabs();
                val state = viewModel.list.value
                HomeScreen(
                    articles = state,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails =  { lab ->
                        navigateToDetails(
                            navController = navController,
                            item = lab
                        )
                    }
                )
            }
            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()

                navController.previousBackStackEntry?.savedStateHandle?.get<LabResponse?>("lab")
                    ?.let { lab ->
                        DetailsScreen(
                            lab = lab,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            navigateToHome = {  navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            ) }
                        )
                    }

            }
            composable(route = Route.BookmarkScreen.route) { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                viewModel.getLabs();
                val state = viewModel.list.value
                HomeScreen(
                    articles = state,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = {
                    }
                )
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }

}

private fun navigateToDetails(navController: NavController, item: LabResponse) {
    navController.currentBackStackEntry?.savedStateHandle?.set("item", item)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}