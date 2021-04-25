package com.happylication.boardthebus.injection.component

import com.happylication.boardthebus.fragment.FavoritesFragment
import com.happylication.boardthebus.fragment.SearchFragment
import com.happylication.boardthebus.injection.module.FavoritesFragmentScope
import com.happylication.boardthebus.injection.module.SearchFragmentScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
@SearchFragmentScope
interface SearchFragmentSubcomponent : AndroidInjector<SearchFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<SearchFragment>
}