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
     private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
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
       
               MovieLoader.loadMovieFromWeb(movie.index,
                  object:MovieLoader.OnMovieLoadListener{
                      override fun onLoaded(webMovie: WebMovie) {
                              title.text = webMovie?.original_title
                              year.text = webMovie?.release_date
                              overView.text = webMovie?.overview
                              rank.text = webMovie?.vote_average.toString()


                          viewModel.saveHistory(movie)

                      }

                      override fun onFailed(throwable: Throwable) {
                          Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
                      }

                  })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
