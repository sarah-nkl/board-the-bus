package com.happylication.boardthebus.injection.component

import com.happylication.boardthebus.fragment.FavoritesFragment
import com.happylication.boardthebus.injection.module.FavoritesFragmentScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
@FavoritesFragmentScope
interface FavoritesFragmentSubcomponent : AndroidInjector<FavoritesFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<FavoritesFragment>
}