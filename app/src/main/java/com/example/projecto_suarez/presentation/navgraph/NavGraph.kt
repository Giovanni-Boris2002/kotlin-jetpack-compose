package com.example.projecto_suarez.presentation.navgraph

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.projecto_suarez.presentation.home.HomeScreen
import com.example.projecto_suarez.presentation.home.HomeViewModel
import com.example.projecto_suarez.presentation.news_navigator.NewsNavigator
import com.example.projecto_suarez.presentation.onboarding.OnBoardingScreen
import com.example.projecto_suarez.presentation.onboarding.OnBoardingViewModel
import com.example.projecto_suarez.presentation.search.SearchScreen
import com.example.projecto_suarez.presentation.search.SearchViewModel

@Composable
fun NavGraph(
    startDestination: String
){
    val navController = rememberNavController()
    Log.d("Test", startDestination)

    NavHost(navController = navController, startDestination = startDestination){
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ){
            composable(
                route = Route.OnBoardingScreen.route
            ){
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ){
            composable(
                route = Route.NewsNavigatorScreen.route
            ){
                NewsNavigator()
            }
        }
    }

}
