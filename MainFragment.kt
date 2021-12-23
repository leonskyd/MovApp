package com.example.movapp.ui.main
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mymovie.databinding.MainFragmentBinding

class MainFragment : Fragment() { 

    companion object {
        fun newInstance(bundle: Bundle?): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        
    getMovieInfo()
    }

    private fun getMovieInfo() = with(binding) {
        arguments?.getParcelable<Movie>("MOVIE_INFO")?.let{ movie ->
            title.text = movie.title
            year.text = movie.release
            genre.text = movie.genre
            overView.text = movie.overview
            rank.text = movie.rank.toString()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
