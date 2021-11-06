package com.appcessible.boardthebus.injection.component

import com.appcessible.boardthebus.fragment.FavoritesFragment
import com.appcessible.boardthebus.fragment.SearchFragment
import com.appcessible.boardthebus.injection.module.FavoritesFragmentScope
import com.appcessible.boardthebus.injection.module.SearchFragmentScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
@SearchFragmentScope
interface SearchFragmentSubcomponent : AndroidInjector<SearchFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<SearchFragment>
}