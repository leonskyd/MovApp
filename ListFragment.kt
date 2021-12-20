package com.example.mymovie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovie.databinding.ListFragmentBinding
import com.example.mymovie.databinding.MainFragmentBinding
import com.example.mymovie.ui.main.Adapter
import com.example.mymovie.ui.main.AppState
import com.example.mymovie.ui.main.MainFragment
import com.example.mymovie.ui.main.Movie
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = Adapter()



    private lateinit var viewModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager= LinearLayoutManager(requireActivity())

        adapter.listener = Adapter.OnItemClick {
            val bundle = Bundle()
            bundle.putParcelable("MOVIE_INFO", it)
            requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.list_container, MainFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commit()
        }

        viewModel.getData().observe(viewLifecycleOwner, {state -> render(state)})
        viewModel.getMovie()

    }

    private fun render(state: AppState?) {
        when (state) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                val movie: List<Movie> = state.movieData as List<Movie>
                adapter.setMovie(movie)
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
                Snackbar.make(binding.root,
                        state.error.message.toString(),
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("Try it again") {
                            viewModel.getMovie()
                        }.show()

            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}