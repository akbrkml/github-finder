package com.badrun.github_finder.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.badrun.github_finder.base.BaseActivity
import com.badrun.github_finder.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding>() {

    override fun onCreated(state: Bundle?) {

    }

    override val bindingInflater: (LayoutInflater) -> MainActivityBinding
        get() = MainActivityBinding::inflate

}