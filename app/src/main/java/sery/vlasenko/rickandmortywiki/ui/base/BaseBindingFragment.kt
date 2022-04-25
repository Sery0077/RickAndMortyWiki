package sery.vlasenko.rickandmortywiki.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import sery.vlasenko.rickandmortywiki.ui.vmfactories.ViewModelFactory
import javax.inject.Inject

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseBindingFragment<Binding : ViewBinding, VM : ViewModel>(
    private val inflate: Inflate<Binding>
) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    protected abstract val model: VM

    private var _binding: Binding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}