package com.dicoding.dicodingeventsubmission.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingeventsubmission.R
import com.dicoding.dicodingeventsubmission.data.datastore.SettingPreferences
import com.dicoding.dicodingeventsubmission.data.datastore.dataStore
import com.dicoding.dicodingeventsubmission.databinding.FragmentSettingBinding
import com.dicoding.dicodingeventsubmission.ui.viewmodel.SettingViewModel
import com.dicoding.dicodingeventsubmission.ui.viewmodelfactory.SettingViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var switchTheme: SwitchMaterial
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var pref: SettingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        // Initialize preferences and viewmodel
        switchTheme = binding.switchTheme
        pref = SettingPreferences.getInstance(requireActivity().dataStore)
        settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java)

        // observe theme setting
        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        // Set listener for switch
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
        return binding.root
    }
}