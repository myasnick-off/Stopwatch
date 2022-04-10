package com.example.stopwatch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stopwatch.databinding.FragmentMainBinding
import com.example.stopwatch.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTimer1()
        initTimer2()
        initSuperButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTimer1() {

        viewModel.tickerList[FIRST_TIMER]
            .onEach { time ->
                binding.timer1.textTime.text = time
            }
            .launchIn(CoroutineScope(Dispatchers.Main))

        binding.timer1.buttonStart.setOnClickListener {
            viewModel.start(FIRST_TIMER)
        }

        binding.timer1.buttonPause.setOnClickListener {
            viewModel.pause(FIRST_TIMER)
        }

        binding.timer1.buttonStop.setOnClickListener {
            viewModel.stop(FIRST_TIMER)
        }
    }

    private fun initTimer2() {

        viewModel.tickerList[SECOND_TIMER]
            .onEach { time ->
                binding.timer2.textTime.text = time
            }
            .launchIn(CoroutineScope(Dispatchers.Main))

        binding.timer2.buttonStart.setOnClickListener {
            viewModel.start(SECOND_TIMER)
        }

        binding.timer2.buttonPause.setOnClickListener {
            viewModel.pause(SECOND_TIMER)
        }

        binding.timer2.buttonStop.setOnClickListener {
            viewModel.stop(SECOND_TIMER)
        }
    }

    private fun initSuperButtons() {
        binding.superButtonStart.setOnClickListener {
            viewModel.start(FIRST_TIMER)
            viewModel.start(SECOND_TIMER)
        }

        binding.superButtonPause.setOnClickListener {
            viewModel.pause(FIRST_TIMER)
            viewModel.pause(SECOND_TIMER)
        }

        binding.superButtonStop.setOnClickListener {
            viewModel.stop(FIRST_TIMER)
            viewModel.stop(SECOND_TIMER)
        }
    }

    companion object {
        private const val FIRST_TIMER = 0
        private const val SECOND_TIMER = 1

        fun newInstance() = MainFragment()
    }
}